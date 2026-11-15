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
