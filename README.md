# Android UI Common

Reusable Material 3 Compose cards, catalogs and verification-code entry that inherit your app's theme.

**JitPack release 0.1.0 is confirmed.** Read [installation](IMPORT.md) and the
[consumer guide](docs/agents/index.md). The sample app demonstrates state, callbacks and localization.

| Component | Use |
| --- | --- |
| ContentCard / ActionCard | Custom content or a heading/body/action card |
| SectionHeading / EmptyStateCard | Accessible headings and caller-owned empty states |
| CatalogScreen | Centered scrolling catalog with header/empty-state slots |
| OneTimeCodeField | One logical numeric field with visual slots and explicit paste |
| CodeVerificationCard | Full verification card with host-owned messages/actions |
| VerificationRecipientRow | Wrapping recipient/status summary with optional decorative icons (Development) |

The detailed `VerificationCardDetails` overload is **Development source**, pending a new release;
it is not available in the confirmed 0.1.0 artifact. The original card signature remains supported.

| Detailed card, light | Detailed card, dark |
| --- | --- |
| ![Development verification card in light mode](docs/assets/screenshots/verification-light.png) | ![Development verification card in dark mode](docs/assets/screenshots/verification-dark.png) |

These are synthetic rendered documentation illustrations, not accepted regression baselines.
See [capture provenance](docs/screenshots/documentation.json). The sample also exposes **App settings**
above the catalog to open Android application details for its installed package.

Start with [quickstart](docs/agents/quickstart.md), [API contracts](docs/agents/api.md),
[migration](docs/agents/migration.md), [testing](docs/agents/testing.md) and [release maintenance](docs/releases.md).

```sh
sh ./setup.sh --help
./gradlew :ui-common:testDebugUnitTest :ui-common:lintDebug :app:assembleDebug
sh ./docs.sh check
sh ./docs.sh build --check
```

Setup/publishing and documentation use separately pinned shared CLIs. The website uses bundled Astro;
this repository owns its content and component evidence. Screenshot candidates require deliberate review.

## License

Project-owned code in all modules is licensed under [Apache 2.0](LICENSE). Third-party dependencies
and data retain their licenses/notices. Extracted source provenance is recorded in the migration guide.
