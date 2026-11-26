# API contracts

All components live in `com.apexfission.android.ui.common`, accept a Modifier and inherit the
active MaterialTheme. No component installs a runtime theme or knows about an authentication service.

| Component | Inputs and behavior |
| --- | --- |
| ContentCard | ColumnScope content, padding and item spacing; full-width elevated themed card |
| ActionCard | Heading, description, action label/callback, optional icon and enabled state |
| SectionHeading | Heading text and optional decorative icon; accessibility heading semantics |
| EmptyStateCard | Host-owned title, description and optional actions |
| CatalogScreen | Title/description, eyebrow, width/padding/insets, header actions, empty-state slot and lazy content |
| OneTimeCodeField | Caller-owned value/change callback, code length (positive, default 6), enabled/autofocus, accessibility label, optional paste/autofill |
| CodeVerificationCard | Field parameters plus instructions/recipient, busy/error messages, supporting content and actions |
| VerificationCardDetails | Host-resolved code-length legend, explanation and recipient status for the detailed card overload |
| VerificationRecipientRow | Wrapping recipient and optional status, with decorative leading/trailing icon slots |

CatalogScreen consumes scaffold insets once and adds caller padding. Its description is hidden
when `showEmptyState` is true; callers supply the matching empty-state slot. Content remains a
LazyListScope and can still contain shared actions. Header keys are reserved internally.

OneTimeCodeField is one logical editable field, not six independent controls. Visual cells are
excluded from duplicate accessibility output. Typing and explicit paste filter with Char.isDigit
and truncate to codeLength, preserving Auth's existing Unicode digit behavior. The host must
provide a valid value; the component does not silently rewrite an external value during composition.
Server validation remains authoritative. Extremely long code lengths need an appropriate host layout.

The clipboard is read only when the user presses the optional paste button. Autofill is optional
and callers choose a ContentType appropriate to their channel. Hardware/OS autofill behavior needs
device validation. No input is automatically submitted when it reaches the configured length.

CodeVerificationCard disables its field/paste when busyMessage is non-null. Its composable action
slots remain host-owned; explicitly disable those actions when required. Supporting/error messages
are presentation, not server state. Nothing in this module persists credentials or starts network work.

The detailed card layout places instructions above the rounded code cells, the label/length
legend below them, and explanation/recipient summary afterward. `VerificationCardDetails` selects
the detailed overload; the original signature and trailing action lambda remain available. The detailed overload and recipient row are available in the confirmed 0.1.1 artifact. Both overloads use host shapes/colors/typography; no brand palette
is installed.

Development source adds a footer row below the recipient: Paste at the start and caller actions
in a trailing column. Each side has bounded width so large text wraps without overlapping.
A non-null `errorMessage` replaces the explanation with an outlined, tinted notice above the
recipient, marks the logical editor's error semantics, and outlines/tints every digit cell.
The focused cell retains its stronger primary border and caret. The host may replace
`codeLengthLabel` with a localized error legend; the library does not infer a reason or attempt
count. Clearing `errorMessage` restores the explanation and normal cells. These presentation
changes require a release after 0.1.1; the published public signatures remain unchanged.

The focused cell has a host-primary border and tinted fill, plus a caret at a collapsed cursor.
Tapping a populated cell selects that digit for replacement; typing advances the selection to
its next populated neighbor or the next empty cell. Tapping beyond the current code places the
cursor at its end without introducing gaps. Deletion, keyboard selection and external value
changes keep the highlight aligned with the logical editor. Cells grow with scaled text.

Recipient status never changes merely because all digits are entered. Supply truthful host state,
not a success icon or link-opened label inferred from code entry. Decorative icon slots should use
null content descriptions because the adjacent status text supplies their meaning. A null
`pasteLabel` hides the helper button without reading the clipboard; keyboard and accessibility editing remain available.
