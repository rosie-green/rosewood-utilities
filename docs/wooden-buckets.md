# Wooden bucket prototype

Each wood family has an empty and water-filled item in Tools & Utilities:
`rosewood_utilities:<wood>_bucket` and `rosewood_utilities:<wood>_water_bucket`.
The families are oak, spruce, birch, jungle, acacia, dark oak, mangrove, cherry,
pale oak, poplar, crimson and warped. Crimson and warped also have lava-filled
items: `rosewood_utilities:crimson_lava_bucket` and
`rosewood_utilities:warped_lava_bucket` (26 items total).

Craft one empty bucket with three logs from the same family. Vanilla log tags
also accept stripped logs and bark blocks; crimson and warped use their stem
and hyphae tags. Planks and mixed wood families do not craft buckets:

```text
L L
 L
```

Empty buckets stack to 16; filled buckets stack to 1. They are reusable and
have no durability. They collect water sources, including waterlogged blocks,
and use vanilla water placement, waterlogging and Nether evaporation behavior.
Crimson and warped buckets additionally collect and place lava sources. All
three states of those two families resist fire and lava damage as dropped items.
Dispensers can fill and empty water and lava, retaining the same wood type.
Unsupported dispenser actions eject the bucket, as with vanilla buckets.

Buckets can also fill cauldrons and collect their contents: water cauldrons must
be full, and only crimson/warped buckets can collect lava. Filled buckets replace
existing cauldron contents, as vanilla buckets do. Lava cannot be poured into a
submerged cauldron. These interactions preserve wood type and creative-mode
infinite-item behavior.

Only vanilla water and lava sources are collected from the world; custom fluids
sharing vanilla fluid tags are rejected. Powder snow, milk and mobs remain
outside scope. Rejected pickups leave the target intact.

Crimson and warped lava buckets fuel furnaces with the same burn-time provider
as vanilla lava buckets. Smelting returns the matching empty bucket, which a
hopper can retrieve from below.

All variants use Fabric's conventional ingredient tags: `c:buckets`,
`c:buckets/empty`, `c:buckets/water`, and `c:buckets/lava`. Recipes using these
tags accept the appropriate variants and return their matching empty buckets.
This snapshot has no vanilla crafting recipes consuming water or lava buckets;
recipes from other mods that hardcode an iron bucket still need to use the tags.
Concrete powder hardens through world water contact, not through bucket crafting.

Fabric fluid-transfer integration fills and drains a whole bucket at a time,
preserving wood type and item component overrides (such as custom names).
Overworld buckets accept water; crimson and warped also accept lava. Transfers
participate in transactions: aborted operations and partial amounts leave the
bucket unchanged. Inventory-only contexts reject conversion when there is no
room for the filled item; player contexts drop overflow on commit, matching
vanilla behavior. Custom fluid types remain unsupported.

Models currently borrow `minecraft:item/bucket`, `minecraft:item/water_bucket`
and `minecraft:item/lava_bucket` textures. When adding art, change the
`generateFlatItem` calls inside the variant loop in `ModelGenerator` to use the
mod items' own textures, then regenerate data.

## Validation

Run data generation before building, in separate Gradle invocations:

```sh
./gradlew :fabric:runDatagen
./gradlew :fabric:build
```

The build runs 20 bucket GameTests (plus Minecraft's default smoke test) through
Fabric Loom's
[GameTest integration](https://docs.fabricmc.net/develop/automatic-testing).
Run them separately with `./gradlew :fabric:runGameTest`; their XML report is
written to `fabric/build/reports/gametest.xml`. The test mod and custom test
fluids are isolated in `src/gametest` and are not included in the release jar.

The suite covers family-preserving pickup/placement, inventory and dispenser
overflow, lava rejection, custom tagged fluids, waterlogging, Nether evaporation,
creative inventory behavior, cauldron interactions, furnace fuel and hopper
retrieval, tag-based recipe remainders, and transactional fluid transfers. The
recipe fixtures in the test mod are only for verification and are not gameplay
recipes.

For additional manual client/multiplayer checks:

- Craft all 12 families with their respective logs/stems and find all 26 items
  in the creative tab. Try stripped logs and bark blocks from the same family.
  Verify planks and mixed-family inputs do not craft buckets.
- In survival, fill one bucket and a stack of buckets; verify water is removed
  and the filled bucket enters the hand/inventory. Repeat with a full inventory
  and confirm overflow drops without duplicating or losing buckets.
- Empty onto a block and into a waterloggable block, then collect it again.
  Confirm the returned container preserves its wood type. Try an obstructed placement.
- With crimson and warped buckets, collect and place lava sources in survival
  and creative; check that the matching empty bucket returns. Repeat using a
  dispenser with single and stacked buckets, including a full inventory.
- Try lava pickup with each other wood family, including dispensers. The lava
  must remain intact. Flowing water/lava and powder snow cannot be collected.
- Drop empty, water-filled and lava-filled crimson/warped buckets into lava and
  fire; confirm they survive.
- In creative, fill and empty buckets and verify vanilla infinite-item behavior.
- Empty water in the Nether; it evaporates and returns the matching empty bucket.
- Dispense every supported state, including stacked empties and a full dispenser
  inventory. Check wood types of returned buckets, waterlogged targets and rejected pickups.
- Repeat pickup/placement in multiplayer and in a protected spawn area.

Server GameTests do not replace manual checks of client prediction, multiplayer
permissions, crafting, or final artwork.
