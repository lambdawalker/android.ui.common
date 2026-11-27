# Migration

## Development module split

Current source contains two independent libraries and one unpublished catalog app:

| Module | Components |
| --- | --- |
| `:ui-demo-common` | ContentCard, ActionCard, SectionHeading, EmptyStateCard, CatalogScreen |
| `:ui-code-verification` | OneTimeCodeField, CodeVerificationCard (both overloads), VerificationCardDetails, VerificationRecipientRow |
| `:app` | Unified catalog, navigation, examples and preview controls; depends on both libraries |

Neither library depends on the other. Kotlin packages remain `com.apexfission.android.ui.common`;
Android resource namespaces are `com.apexfission.android.ui.demo` and
`com.apexfission.android.ui.codeverification`. API 28, Java 17, host theming and caller-owned state remain unchanged.

The new modules are **Development/source integration**, with no confirmed published versions.
For a local multi-project build, depend on `project(":ui-demo-common")` and/or
`project(":ui-code-verification")`. Consumers should replace the monolithic dependency across
their complete dependency graph: never combine the old AAR with either extracted library,
because their Kotlin classes overlap. External consumer migrations and publishing automation
are deferred until the release follow-up. Historical release receipts remain unchanged;
release information below concerns the old monolithic artifact, not the split libraries.


| Previous API | Replacement |
| --- | --- |
| DemoCard(content) | ContentCard(content) |
| DemoCard(title, description, actionLabel, onClick) | ActionCard |
| CardTitle | SectionHeading |
| DemoScreen | CatalogScreen |
| DemoCatalogEmptyState (private) | EmptyStateCard |
| EmailOneTimeCodeField (Auth internal) | OneTimeCodeField |
| EmailWaitScreen inner card | CodeVerificationCard |

Imports move to `com.apexfission.android.ui.common`. CatalogScreen renames welcomeNote to
description and replaces the hard-coded settings action with headerActions. Empty-state wording
also belongs to the caller. Update resource references in tests along with dependency aliases.
There are no compatibility wrappers. Existing published Demo Common releases remain unchanged.

Origins: Demo Common main `00375200862e82da838ee75545c779505cdc8ca0`; Auth migration starts from
`f356a5b` (including the merged Apache 2.0 license) with the latest onboarding flow. Source attribution is retained in adapted files.
Card Detector and Permissions migrate sample-only dependency scopes; Auth delegates verification
presentation while keeping controller/navigation behavior. Consumers can now use the confirmed JitPack release 0.1.0. Replace temporary source substitution
with the published dependency in [installation](installation.md), then validate the consumer build.
