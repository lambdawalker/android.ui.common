# Shared Android UI and direct consumer migration

Status: approved by the owner on 2026-11-15; implementation in progress. The owner has selected direct consumer migration without compatibility wrappers, and Apache 2.0 for all project-owned Android modules.

## Intent and scope

Create a reusable Kotlin/Compose Material 3 library in `lambdawalker/android.ui.common`. Move Auth's verification-card UI and Demo Common's component implementations into it. Preserve the current user flows while removing demo/email-specific coupling from the public UI APIs. Apply the applicable Android prompts from `agents.prompts`, including shared setup/docs tooling, host theming, previews, screenshot testing, documentation and release safety.

This is a coordinated change across UI Common, Auth, Card Detector, Permissions and Demo Common. The separate license audit also covers Coordinates, YOLO and Signature. Existing Apache-2.0 repositories need no cosmetic license rewrite. Auth, Signature and UI Common require license declarations. Third-party licenses and notices remain intact.

## Decisions and approaches

- Selected by the owner: migrate consumers directly to the new package and names. Do not add deprecated Demo Common forwarding wrappers.
- Alternative considered: retain Demo Common APIs as a compatibility facade. This would ease a staggered rollout but retain a second API and release stream; the owner chose direct migration.
- Copying implementations into each consumer would defeat shared ownership and is excluded.
- Owner-selected license: Apache 2.0, including the UI extracted from Auth. Preserve existing attribution in moved files and record source repository/revision provenance.

Builder defaults proposed here: one publishable `:ui-common` Android library and an unpublished `:app` component-catalog sample; package `com.apexfission.android.ui.common`; proposed Central identity `com.apexfission.android:ui-common`. Verify namespace ownership before actual publication. Keep the repository name `android.ui.common` and record actual provider coordinates only after confirmation.

## Current evidence and integration boundaries

As inspected on 2026-11-15:

- UI Common contains only its initial README.
- Demo Common has `DemoCard` (generic container and action-card overloads), `CardTitle`, `DemoScreen` and its internal empty-state UI. It uses API 28+, Java 17 bytecode, AGP 9.4.1 and Kotlin 2.4.20.
- Card Detector's sample `WelcomeActivity` imports all three public Demo Common APIs; its catalog declares `com.apexfission.android:demo-common:0.1.2`.
- Permissions' sample `DemoHomeScreen` imports `DemoScreen` and `DemoCard`; a device test references Demo Common's settings-label resource. Its demo uses API 28 while the permissions library remains API 24 and must stay independent of UI Common.
- Auth's `EmailWaitScreen` composes the verification card and keeps Verify/Resend actions in `OnboardingFrame`. `EmailOneTimeCodeField` is a single logical field with six decorative slots and a paste button. Its controller owns email links, verification, resend timing and server state.
- GitHub owner-wide code search found Card Detector and Permissions as direct Demo Common consumers. Repeat a fresh dependency/import scan before migration completion; include any newly found active consumer.
- Auth is changing concurrently. Rebase the migration on current main and preserve its latest flow. Do not overwrite unrelated PR changes. Demo Common has an open Astro migration; reconcile its overlap rather than merging that PR implicitly.

## Public components

| Source | New API | Responsibility |
| --- | --- | --- |
| Generic DemoCard | `ContentCard` | Padded themed container with a composable content slot |
| Action DemoCard overload | `ActionCard` | Heading, description and caller-controlled action |
| CardTitle | `SectionHeading` | Accessible heading with optional decorative icon |
| DemoScreen | `CatalogScreen` | Width-constrained lazy scrolling layout, insets, heading and content slots |
| Internal demo empty state | `EmptyStateCard` | Reusable empty-state heading/body with optional caller actions |
| Auth EmailOneTimeCodeField | `OneTimeCodeField` | One accessible logical code input, visually divided slots and optional paste action |
| Auth EmailWaitScreen card body | `CodeVerificationCard` | Verification content and code field, supporting messages and caller action slots |

All public components accept a `Modifier`, derive color/typography/shapes from the host `MaterialTheme`, and avoid a private runtime theme. Provide useful spacing/layout customization without introducing a separate visual identity. The sample owns its theme. API names and documentation describe general behavior rather than demos or Attestra.

The code field defaults to six numeric digits and allows an explicit supported code length. Keep one logical editable field for selection, paste, deletion, IME and accessibility; visual slots must not create six competing input/focus nodes. Test formatted paste and existing Auth normalization behavior before changing it. Callers own the value, validity, submission and enabled state. Autofocus is explicit. Autofill hints must suit the caller's verification channel rather than hard-code email into a generic component. Never read the clipboard automatically; paste is user initiated.

The full verification card accepts caller-resolved strings or composable slots for instructions, destination/recipient, label, supporting status, error and secondary actions. The Auth adapter supplies its existing localized strings, remaining-attempt message, Change email and Sign in actions. Its Verify/Resend buttons remain at their current screen-level location. The new UI module must not depend on Auth resources, controllers, networking, timers, navigation or Attestra branding.

CatalogScreen uses general header/action/empty-state slots or parameters. App-settings navigation and demo-specific empty-state wording remain consumer choices. Migrate resource references in tests to consumer/new-library ownership. Preserve existing lazy-list scrolling, insets and click behavior.

## Repository and automation

Use the established API 28/Java 17 Compose baseline for the new library and compatible pinned tooling; verify exact plugin support during implementation. Do not raise consumer library minimum SDKs or change unrelated runtime dependencies. Export only dependencies needed by the public Compose API, verified through POM/Gradle metadata.

Use shared `android-repo-setup` and standalone `android-repo-docs` with separately verified immutable pins. Gradle conventions are independently versioned. Generate setup/docs shell, batch and PowerShell launchers, evaluated module inventory, destination configuration and supported workflows. Keep project inputs under `.project/setup`, `.project/docs` and `.project/verification`; do not copy legacy Python release or site engines.

Use bundled Astro/Starlight with `site.backend: astro`. Keep English canonical agent guides, reviewed Spanish counterparts, source-backed examples, API/feature coverage, raw Markdown, `llms.txt`, scoped navigation and root/project-prefix validation. New docs visibly describe Development until a release is confirmed. Prepare manual publishing/finalization and separately retryable site workflows under existing authorization rules. No secrets, live environment writes or publication are implied by this design.

The sample must let users exercise every component, including code typing/paste, busy/error/disabled states, card actions and empty/populated catalogs. Provide source examples that compile. Documentation must explain state ownership, callbacks, accessibility, customization, module dependencies and migration names.

## Consumer rollout and dependency availability

1. Implement and validate UI Common, including its sample, publication contract, docs and source provenance.
2. Prepare direct consumer changes for Auth, Card Detector and Permissions. Use explicit local/composite dependency substitution and a pinned checkout in integration CI to test the unpublished library without advertising a fake public version. No automatic clone during Gradle configuration, no unscoped mavenLocal fallback and no floating branch dependency.
3. Keep consumer PRs draft or explicitly blocked until a real immutable UI Common artifact is available. Publication requires separate authorization; a successful local build is not confirmation. Replace the validation-only substitution with confirmed provider coordinates before normal consumer merge. A release blocker must not prevent building and testing the complete consumer patches first.
4. After direct consumer integration is verified, retire active duplicate component implementation/sample entry points in Demo Common and replace its current README with migration guidance. Preserve historical source tags, artifacts and release documentation. Do not archive/delete the GitHub repository or rewrite old releases as part of this task.

The implementation may keep a temporary explicit integration override for contributor testing if useful, but normal builds must resolve the recorded confirmed dependency. Deliver PR links and dependency order rather than claiming the migration is live before its release dependency exists.

## Validation and acceptance

- Unit tests for code normalization, configured length and invalid inputs. Compose interaction tests for typing, paste, deletion, disabled/busy state, value ownership, action callbacks and logical field semantics.
- Compile all components, previews and sample; run appropriate unit/lint/build checks. Test Auth's code/link/resend flow at its existing integration boundary without modifying backend semantics.
- Deterministic light/dark previews and screenshot scenarios for every component and meaningful screen state, plus representative narrow/large-font/localized layouts and a visibly different host theme.
- Keep screenshot candidates, intentional review/acceptance and regression checks separate. Missing baselines must be reported as candidates; do not manufacture approval or device evidence.
- Verify Card Detector's catalog, Permissions' navigation/settings test and Auth's verification adapters against the same UI Common revision. Preserve accepted provenance and review changed captures explicitly.
- Check generated launcher/workflow drift, localization, unsigned publication AAR/POM/sources/docs/metadata and configured package/API contract without uploading.
- Build/validate docs at `/` and `/android.ui.common/`, check raw/local links and translation hashes, and inspect useful screenshots. Do not claim physical device/autofill behavior from host screenshot tests alone.
- Final repository scan has no active Demo Common imports/dependencies in migrated consumers, no duplicate OTP implementation in Auth and no falsely confirmed release.

## Non-goals and builder discretion

Do not change Auth's authentication protocol, email delivery, security or navigation flow beyond UI delegation. Do not extract every Auth onboarding component. Do not redesign the supplied verification screen or introduce a separate theme system. Do not change historical release/license claims, third-party notices or unrelated modules' toolchains.

The builder may choose focused parameter/data-class organization, private helpers, test fixtures and file layout within these contracts. Exact compatible CLI/plugin revisions and final public coordinates require verified source/provider evidence. Package publication, production deployment and PR merging remain separate actions.
