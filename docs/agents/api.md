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
