# Implementation verification

Local source validation, 2026-11-15:

- Four normalization unit tests passed; library and sample Kotlin compile.
- Sample debug APK and library interaction-test APK assembled.
- 22 screenshot candidates rendered and visually inspected, including light/dark, narrow/large text and Spanish. Four illustrations are included in the docs with image hashes. No regression baselines were accepted.
- Library and sample debug lint passed with zero errors (dependency catalog, unused sample label and sample icon warnings).
- Generated import verification and local publication of fixture version 9.8.7 passed. This fixture is not a public release.
- Shared CLI workflow/config checks and resource localization checks passed.
- Documentation checks and Astro root/project-prefix builds passed.
- Fresh source review completed; its Permissions callback finding was corrected before consumer validation.

Android validation used the checked-in Gradle/AGP/Kotlin versions, Java 25 with Java 17 compilation,
and SDK 37. An exact pinned AAPT2 binary override was needed locally because the Maven proxy returned
intermittent download errors. No toolchain versions were downgraded.

The interaction suite compiled but did not run locally: no emulator/KVM is available. The component
workflow runs it on an emulator and uploads screenshot candidates separately. Human baseline
acceptance, consumer CI, and a confirmed UI Common release remain integration gates. No package was
published and no production site was deployed by this work.
