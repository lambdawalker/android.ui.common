# Troubleshooting and limits

- If the dependency cannot be resolved, verify the JitPack repository and exact coordinate in IMPORT.md.
  Remove the old unpublished snapshot and source-substitution setup when using the confirmed release.
- A disabled input may reflect enabled=false or a non-null busyMessage. The host clears busy state.
- Paste filters numeric characters and truncates to codeLength. Clipboard reading requires the paste action.
- A screen action slot is controlled by its host; disable it explicitly during busy operations.
- Theme differences come from the active host MaterialTheme. The library deliberately supplies no brand theme.
- SDK/JDK/Compose versions must satisfy the checked-in build. Do not downgrade pins to bypass local tooling failures.
- An API/source manifest mismatch requires reviewing source and guides before running docs sources update.
- Device autofill and selection/IME integration need emulator or physical-device checks; screenshots alone are insufficient.
