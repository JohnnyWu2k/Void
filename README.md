# Void

A Mindustry v8 Java mod that adds dark matter production, void energy, Norium, antimatter power, advanced turrets, explosive walls, and Void units.

## Development

- Target: Mindustry v8 Build 157.3.
- Minimum game version: 154, matching the current Java mod template baseline where possible.
- Desktop build: `.\gradlew.bat build`
- Output: `build/libs/VoidDesktop.jar`

The debug computer block is not loaded during normal gameplay. Start the game with `-Dvoid.debugComputer=true` only when you intentionally need the local Swing file manager and command runner.

## Resource Progression

Void resources are split by role:

- Dark Matter: base exotic material for early Void production and energy weapons.
- Dark Energy: liquid energy medium for direct machine input or conversion into normal power.
- Void Crystal: portable stabilized dark energy for ammunition, lenses, and advanced crafting.
- Norium: dense structural material for heavy shells, frames, and containment systems.
- Antimatter: endgame fuel for high-output power and advanced units.

Main production order: Dark Matter Synthesizer -> Void Extractor -> Void Crystallizer -> Norium Forge -> Antimatter Collider -> Annihilation Generator.
