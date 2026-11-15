# Screenshot evidence

Library screenshot fixtures cover empty/partial/complete/error/busy/disabled code cards in light/dark,
all catalog primitives, and an alternate host on a narrow large-text layout. Sample fixtures add Spanish.
See `coverage.json` for component-to-fixture ownership.

Render candidates with the manual component validation workflow or Gradle screenshot tasks.
`updateDebugScreenshotTest` creates reference PNGs locally; treat newly generated files as candidates
until a human has inspected them. Ordinary CI uses `validateDebugScreenshotTest` only after reviewed
baselines are committed. Do not treat identical rerenders as human acceptance.

The initial repository ships the executable suite and candidate workflow. Until baseline acceptance
is recorded, CI checks candidate rendering and uploads output without claiming regression validation.
Accepted baseline commits must record reviewer/scenario/toolchain/source provenance here and switch
normal validation to comparison. Never include baseline PNGs in the library's publication.
