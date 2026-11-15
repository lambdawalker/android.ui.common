# Contributor instructions

Read docs/agents/index.md for consumer contracts and docs/superpowers/specs/2026-11-15-shared-ui-migration-design.md for the extraction boundary.

- Public components inherit the host MaterialTheme. No private theme, network, Auth dependency or navigation inside the library.
- Preserve one logical code field, explicit clipboard access and caller-owned state/actions. Auth owns server validation and timers.
- All project-owned modules are Apache-2.0. Retain source attribution and third-party notices.
- Use shared setup/docs CLIs and declarative .project configuration. Regenerate managed launchers/workflows; do not restore Python release/site engines.
- Android baseline: API28+, Java17 bytecode; keep compatible toolchain pins. The sample is not published.
- Run unit/lint/build and applicable Compose interaction/screenshot checks; report unavailable device checks. Candidates, acceptance and baseline validation are distinct.
- Canonical guides are docs/agents; Spanish sources are docs/es with reviewed English hashes. Never accept hashes without content review.
- IMPORT.md is generated from confirmed facts. Until publication this is Development/source integration. Never invent release coordinates or mutate historical tags.
- Run sh ./docs.sh check and root/project-prefix site builds for docs changes. Do not edit generated sites/dist.
- Work on a task branch/PR. No package publication, merge or production deployment without corresponding authorization.
