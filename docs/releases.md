# Releases and maintenance

UI Common 0.1.0 is confirmed on JitPack. The configured Central coordinate is
`com.apexfission.android:ui-common`; this is build configuration, not proof of availability.
Read [IMPORT.md](../IMPORT.md) before selecting a dependency. All project-owned code uses
[Apache 2.0](../LICENSE); preserve upstream attribution.

Setup uses the pinned `android-repo-setup` CLI and generated launchers. Run `sh ./setup.sh`
or `setup.bat` for the wizard. CLI v0.1.28 and Gradle plugin v0.1.20 are independently pinned.
Documentation uses the retained source pin in `.project/docs/config.yaml` for its validated
shared Astro routing/staging behavior. Updating a version number alone does not establish feature parity.

Use `sh ./setup.sh bootstrap check`, `sh ./setup.sh workflows --check`,
`sh ./setup.sh publishing-config check` and `sh ./setup.sh setup check` for drift.
Discovery evaluates the actual Gradle model. Generic release engines and renderers belong to the CLI.

Before each release verify namespace ownership, exact artifact/POM/metadata contracts,
manual publication/finalization workflows and destination environment setup. Central needs
its credentials/OpenPGP key; public JitPack does not. Keep pending/upload journals and receipts,
never blindly re-upload an uncertain attempt. Finalization confirms public artifacts before
advancing IMPORT.md. Documentation retries never upload packages.

The generated documentation pipeline builds PR artifacts and prepares Pages deployment on main.
Configure/approve hosting separately before merging it into an enabled deployment environment.
Package availability is recorded in the release receipts; Pages deployment is tracked separately.

The sample's signing environment is independent of Maven signing. Never commit secrets or local.properties.
Historical documentation is read as data from retained revisions; never execute historical site code.
