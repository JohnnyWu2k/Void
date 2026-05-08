# Changelog

All notable changes to this project are documented here.

## [Unreleased]

### Changed
- GitHub Releases now use only the matching version section from `CHANGELOG.md` as release notes.

## [1.4] - 2026-05-08

### Added
- Added English and Traditional Chinese bundles for mod content names, descriptions, and configurable UI labels.
- Added this changelog for future tags and GitHub release notes.
- Added GitHub Actions automation to build `Void.jar` on pushes and pull requests, and publish the jar automatically for `v*` tags.
- Added dark energy as a dedicated liquid resource for advanced machines and power conversion.
- Added a dedicated dark energy liquid icon.
- Added Void Crystallizer to convert dark energy liquid into portable Void Crystals.

### Changed
- Updated the development target to Mindustry v8 Build 157.3 and raised the minimum game version to 154.
- Reworked the progression chain from dark matter synthesis to void energy extraction, Norium forging, antimatter production, advanced power, turrets, and units.
- Split dark energy from item transport: Void Extractor now outputs dark energy liquid, while Void Crystal is a stabilized material.
- Darkened the dark energy liquid color so pipe flow is visually distinct from water and cryofluid.
- Clarified resource roles: dark matter as the base exotic material, dark energy as the liquid energy medium, Void Crystal as portable storage/ammunition, Norium as structural shell material, and antimatter as endgame fuel.
- Rebalanced Annihilation Generator fuel cadence and separated turret roles between piercing beams, lightning beams, Norium shell bursts, and black hole crowd control.
- Replaced silicon missile ammo with pyratite and blast compound, and aligned missile and laser ranges with their practical projectile reach.
- Rebalanced late-game production rates, turret damage cadence, black hole launcher costs, missile launcher range, and Delta unit stats.
- Replaced off-style placeholder sprites for the black hole launcher, unit fabricator, unit reconstructor, and explosive walls with Mindustry-style top-down pixel sprites.
- Refined the black hole launcher sprite to use a square, symmetric turret silhouette matching the Void Piercer and Norium Destroyer style.
- Replaced the Annihilation Generator sprite with the square black hole reactor artwork from `assets/black hole.png`.
- Moved gameplay text from hardcoded Java/HJSON fields into Mindustry bundle files.
- Disabled the local Swing command-runner computer block by default; it now loads only with `-Dvoid.debugComputer=true`.

### Fixed
- Fixed v157 sound API references.
- Removed duplicate HJSON content for Void Piercer and kept the Java implementation as canonical.
- Fixed inconsistent content references such as old `dark-matter` IDs where the project uses `darkmatter`.
- Fixed placeholder and contradictory descriptions for explosive walls and Norium-related content.
