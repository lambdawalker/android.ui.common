# Shared UI Migration Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Extract shared Compose components, migrate their consumers directly, and deliver a CLI-managed Android library with Astro documentation.

**Architecture:** `:ui-common` owns presentation and input behavior; its sample owns demo state. Auth retains authentication/controller/navigation logic. Card Detector and Permissions migrate their sample catalogs without changing their published library dependencies.

**Tech Stack:** Kotlin, Material 3 Compose, Gradle/Android convention plugins, shared Rust setup/docs CLIs, bundled Astro.

**Progress (2026-11-15):** Shared implementation, sample, CLI/Astro configuration and local validation are complete. Consumer source migrations are implemented and integration builds are in progress. Device execution, human screenshot baseline acceptance and the first public release remain explicit gates. See [verification evidence](../../implementation-verification.md).

**Spec:** [Shared UI migration design](../specs/2026-11-15-shared-ui-migration-design.md).

## Global constraints

- Owner decisions: direct migration, no Demo Common compatibility wrappers, Apache 2.0 for project-owned modules.
- New UI library baseline: API 28+, Java 17 bytecode; keep unrelated consumer minimum SDKs/toolchains unchanged.
- Package `com.apexfission.android.ui.common`, publishable `:ui-common`, unpublished `:app` sample.
- Consumer public coordinates require confirmed release evidence. Validate unpublished source through explicit isolated substitution, not a fake release or floating dependency.
- Preserve third-party notices, immutable history and concurrent Auth work. Publication, merging and production deployment remain separately authorized.

## Review focus

- Paste/deletion/selection must work through one logical code field; test full and formatted paste and mid-edit deletion.
- Busy/disabled state must block input and callbacks; test both direct field editing and paste/card actions.
- Host theme, large fonts and narrow width must remain legible; cover light/dark, alternate shape/type/color and constrained layout fixtures.
- Permissions' API 24 library must remain independent of the API 28 UI dependency; inspect app/library dependency graphs.
- Unpublished dependency validation must not claim public availability or silently resolve an old artifact; assert the pinned source/revision in integration CI.

### Task 1: Library build, catalog primitives and source provenance

**Files:** root Gradle/settings/version catalog/wrapper; `ui-common/build.gradle.kts`; `ui-common/src/main/kotlin/com/apexfission/android/ui/common/{ContentCard,ActionCard,SectionHeading,CatalogScreen,EmptyStateCard}.kt`; `ui-common/src/androidTest/.../CatalogComponentsTest.kt`; `docs/migration.md`.

**Interfaces:** `ContentCard` exposes a ColumnScope content slot, modifier/padding/spacing and themed card style. `ActionCard` exposes title, description, action label/callback, modifier and optional icon. `SectionHeading` exposes title/icon/modifier. `CatalogScreen` exposes title/description, padding/insets/max width, LazyListScope content and caller-provided actions/empty state. `EmptyStateCard` exposes title/body/modifier and optional action slot. Final signatures must be documented before consumer changes.

- [ ] Inspect current upstream prompts and compatible tool pins; preserve original component attribution and source SHAs in migration notes.
- [ ] Add interaction fixtures for action callbacks, empty/populated catalogs and host inset/scroll behavior.
- [ ] Establish the library and sample build with explicit public API mode; move/generalize Demo Common implementations and resources using the interfaces above.
- [ ] Run library compile/lint and component interaction tests; record SDK/device blockers rather than claiming success.
- [ ] Commit the independently buildable component module and tests.

### Task 2: Verification card and logical code field

**Files:** `ui-common/src/main/kotlin/com/apexfission/android/ui/common/{OneTimeCodeField,CodeVerificationCard}.kt`; unit normalization tests; `ui-common/src/androidTest/.../OneTimeCodeFieldTest.kt` and `CodeVerificationCardTest.kt`.

**Interfaces:** `OneTimeCodeField(value: String, onValueChange: (String) -> Unit, ...)` has code length default 6, explicit enabled/autofocus/accessibility/autofill configuration and optional user-initiated paste label/action. `CodeVerificationCard` composes that field, caller-resolved instructions/recipient/label/supporting/error/busy content and secondary action slots. It does not submit requests or own timers.

- [ ] Port existing Auth normalization tests and add configured-length, formatted-paste, overlong and invalid-input cases before implementing normalization.
- [ ] Add Compose tests for typing, paste, deletion, one editable semantics node, caller state, enabled/busy transitions and card actions.
- [ ] Extract the field/card with host MaterialTheme styling and no Auth imports/resources; preserve current Auth behavior unless a separately tested correction is necessary.
- [ ] Run focused unit and Compose tests, then compile the complete module.
- [ ] Commit the verification components and tests.

### Task 3: Sample, previews and screenshot coverage

**Files:** `app/src/main/.../MainActivity.kt`; library debug preview fixtures; library/app screenshot source sets; `docs/screenshots/coverage.json` and candidate/acceptance metadata.

**Interfaces:** The sample uses Task 1/2 APIs; it owns editable sample state and routes. No runtime service is called by previews.

- [ ] Implement a runnable catalog exercising all seven components and field/card states.
- [ ] Add light/dark previews and screenshot fixtures for empty/partial/complete/error/busy/disabled verification, populated/empty catalogs and actions.
- [ ] Cover alternate host theme, narrow layout, large fonts and English/Spanish text; map private helpers to meaningful parent scenarios.
- [ ] Render and inspect candidates; keep baseline acceptance explicit and separate from validation. Record exact evidence and outstanding review.
- [ ] Run app/library build, lint, screenshots and relevant interaction tests; commit.

### Task 4: Shared CLI, publication and Astro documentation

**Files:** `.project/setup/*`, `.project/docs/*`, `.project/verification/checks.json`, generated launchers/workflows, publication Gradle configuration, `docs/agents/*`, `docs/es/*`, source/example manifests, `IMPORT.md`, `AGENTS.md`, README.

**Interfaces:** Shared setup owns release identities/journals/verification; shared docs owns source/import/history/site generation. Consumers provide configuration and project content.

- [ ] Configure evaluated inventory, explicit UI AAR verification and compatible independently pinned Gradle plugins and executables; generate and review tracked outputs.
- [ ] Configure shared Astro, canonical/translated guides, compilable quickstart examples, API/feature map and sample screenshot usage.
- [ ] Keep Development/source installation explicit until publication is confirmed; add accurate Apache 2.0 POM metadata and manual release/recovery guidance.
- [ ] Run launcher/workflow/registry/localization drift checks, unsigned artifact/POM verification and docs checks at root and project prefix.
- [ ] Commit tooling/docs and verified build evidence; do not publish a package to make a check pass.

### Task 5: Direct consumer migrations

**Files:** fresh task branches in Auth, Card Detector and Permissions; their catalogs/build dependencies, relevant UI call sites, previews/tests and docs. Auth targets `EmailWaitScreen.kt`, `EmailCodeScreen.kt` where applicable and the existing common OTP helper. Card Detector targets `WelcomeActivity.kt`; Permissions targets `DemoHomeScreen.kt` and `DemoNavigationDeviceTest.kt`.

**Interfaces:** Consumers use only the public Task 1/2 APIs. Validation substitutes the exact UI Common source; normal dependencies ultimately use confirmed provider coordinates.

- [ ] Re-scan owner repositories and fresh main revisions for active consumers and concurrent edits; inspect applicable instructions.
- [ ] Add explicit pinned-source integration validation, preserving independent checkout configuration and reproducibility.
- [ ] Replace catalog imports/dependency aliases/resource references with new APIs; retain app-only dependency scope for Permissions/Card Detector.
- [ ] Replace Auth's card/field rendering with an adapter using current localized text/controller state; remove duplicate UI implementation after callers/tests migrate.
- [ ] Run consumer unit/build/lint, relevant navigation/code-entry tests and screenshot checks against the same UI Common revision.
- [ ] Open coordinated draft consumer PRs with the confirmed-artifact dependency clearly identified; finish patches and local validation before requesting any publication authorization.

### Task 6: Retire duplication and complete delivery

**Files:** Demo Common current implementation/sample/workflows/README and migration guide; confirmed consumer version pins when available; PR descriptions and design/plan status.

- [ ] Once consumer integration is verified and a confirmed artifact is available, replace validation-only dependency defaults with verified coordinates and re-run resolution/integration checks.
- [ ] Remove active duplicate Demo Common implementations and obsolete current sample/tooling deliberately; retain historical tags/artifacts/manuals and a clear migration entry point. Do not delete/archive the repository.
- [ ] Confirm no active migrated consumer imports or resolves Demo Common and Auth has one shared field implementation.
- [ ] Obtain independent whole-change review; fix findings and re-run affected checks.
- [ ] Deliver linked PRs, actual verification results, publication/merge dependency order and remaining owner actions. Do not report an end-to-end migration complete while consumers remain release-blocked.
