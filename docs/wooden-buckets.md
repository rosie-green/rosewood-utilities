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
Dispensers can fill and empty water and lava, retaining the same wood type. Unsupported
dispenser actions eject the bucket, as with vanilla buckets.

This prototype supports water and lava in Nether wood buckets. Powder snow, milk, mobs, cauldrons,
vanilla recipes requiring an iron water bucket, and Fabric fluid-transfer
integration are outside its scope. Rejected pickups must leave the target intact.
Each filled item has its matching empty bucket as a crafting remainder for
recipes that accept it.

Models currently borrow `minecraft:item/bucket`, `minecraft:item/water_bucket`
and `minecraft:item/lava_bucket` textures. When adding art, change the
`generateFlatItem` calls inside the variant loop in `ModelGenerator` to use the mod items' own textures,
then regenerate data.

## Validation

Run data generation before building, in separate Gradle invocations:

```sh
./gradlew :fabric:runDatagen
./gradlew :fabric:build
```

In a development client, check:

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

Compilation and data generation do not replace these in-game checks.
