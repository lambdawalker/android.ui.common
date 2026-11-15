# Installation

UI Common requires Android API 28+, Java 17 bytecode and a compatible Kotlin/Compose toolchain.
The sample app is not a published artifact.

JitPack release **0.1.0** is confirmed. Add JitPack to the dependency repositories in settings.gradle.kts,
then add the dependency to the consuming module:

```kotlin
maven { url = uri("https://jitpack.io") }
```

```kotlin
implementation("com.github.lambdawalker:android.ui.common:ui-common~v0.1.0-jitpack")
```

[IMPORT.md](../../IMPORT.md) is generated from confirmed release receipts and includes Kotlin DSL,
Groovy, version-catalog and Maven examples. This release uses JitPack coordinates; the configured
Central coordinate `com.apexfission.android:ui-common` is not evidence of a Central publication.

Ordinary builds do not require a UI Common checkout or dependency substitution. Remove the temporary
`0.0.0-SNAPSHOT` substitution key and `uiCommonCheckout` settings block when migrating to this release.
For intentional source development, use an isolated composite build and substitute the published
module identity explicitly; do not add an unscoped mavenLocal fallback.

Card Detector and Permissions consume UI Common only in their sample apps. Permissions' API 24
library remains independent of it. Auth consumes the verification presentation in its library.
