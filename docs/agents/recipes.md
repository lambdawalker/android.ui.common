# Recipes

Use `ContentCard` with `SectionHeading` for custom content, or `ActionCard` for one primary action.
Use `CatalogScreen.headerActions` for an app-settings button; the host supplies localized wording,
icon and navigation. An empty state is explicit through `showEmptyState` and `emptyState`.

Place components inside the consuming app's MaterialTheme. Colors, typography and shapes flow from
that theme. Use Modifier, ContentCard padding/spacing, and CatalogScreen width/inset parameters for
layout. Do not wrap reusable components in the sample theme.

For Auth, keep Verify/Resend in the existing screen frame, use CodeVerificationCard for the inner
card, and pass Change address/Sign in through its action slot. The host controller owns email links,
resend deadlines, attempt limits and busy/error state. The extraction does not change that protocol.

## Sample demo list

Install the unpublished `:app` module with `./gradlew :app:installDebug` and launch **UI Common**.
The launcher is a scrolling demo list with **App settings** above its entries. Each entire row is
clickable and opens a dedicated example; **Back to demos** and system Back return to the list at
its previous scroll position. App settings is also available in each example.

| Demo | Components and interaction |
| --- | --- |
| Code verification | `CodeVerificationCard`; type/paste a code, simulate an error with Verify, cycle Ready/Error/Busy/Disabled, or Reset |
| Code input | `OneTimeCodeField`; explicit paste, digit selection, editing and Reset |
| Action card | `ActionCard`; toggle the action label through its callback |
| Content and headings | `ContentCard` containing `SectionHeading` and sample body text |
| Recipient summary | `VerificationRecipientRow` with a synthetic address and host-supplied status |
| Empty state | `CatalogScreen.showEmptyState` and `EmptyStateCard` |

`CatalogScreen` supplies both the list and example layouts. Navigation and state live only in the
sample. Rotation preserves the selected example and its state; leaving it and opening it again
starts fresh. Verification is simulated locally and sends no email. Strings are available in
English and Spanish. No library publication is required to run this demo change.
