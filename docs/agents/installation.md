# Installation

UI Common requires Android API 28 or later, Java 17 bytecode and a compatible Kotlin/Compose toolchain.
Its source pins match the established Demo Common toolchain. The sample is not a published artifact.

Read [IMPORT.md](../../IMPORT.md) for confirmed public coordinates. There are none yet. Do not copy
an anticipated version into a normal dependency declaration and describe it as released.

For coordinated development, check out an exact UI Common commit beside the consumer. In the
consumer's settings, explicitly opt into a composite build that substitutes the module:

```kotlin
providers.gradleProperty("uiCommonCheckout").orNull?.let { checkout ->
    includeBuild(checkout) {
        dependencySubstitution {
            substitute(module("com.apexfission.android:ui-common"))
                .using(project(":ui-common"))
        }
    }
}
```

The consumer's migration branch declares `com.apexfission.android:ui-common:0.0.0-SNAPSHOT`
only as a source-substitution key. Pass `-PuiCommonCheckout=/absolute/path/to/checkout` during
integration validation. This is not a published snapshot, and migration branches remain blocked
until a confirmed dependency replaces it. Do not use an unscoped mavenLocal fallback.

After publication, use the exact coordinates in IMPORT.md and remove the requirement for the
explicit source checkout. Card Detector and Permissions consume UI Common in their sample apps;
Permissions' API 24 library must remain independent of it.
