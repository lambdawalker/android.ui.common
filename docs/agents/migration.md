# Migration

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
