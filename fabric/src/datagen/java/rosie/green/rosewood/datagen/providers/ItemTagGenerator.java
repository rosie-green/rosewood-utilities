package rosie.green.rosewood.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import rosie.green.rosewood.registration.ModIds;
import rosie.green.rosewood.registration.ModItems;

import java.util.concurrent.CompletableFuture;

public class ItemTagGenerator extends FabricTagsProvider.ItemTagsProvider {
    public ItemTagGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries, BlockTagGenerator blockTagGenerator) {
        super(output, registries, blockTagGenerator);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        builder(ConventionalItemTags.BUCKETS)
            .addTag(ConventionalItemTags.EMPTY_BUCKETS)
            .addTag(ConventionalItemTags.WATER_BUCKETS)
            .addTag(ConventionalItemTags.LAVA_BUCKETS);
        for (var family : ModItems.WOODEN_BUCKETS) {
            builder(ConventionalItemTags.EMPTY_BUCKETS).add(family.empty().builtInRegistryHolder().key());
            builder(ConventionalItemTags.WATER_BUCKETS).add(family.water().builtInRegistryHolder().key());
            builder(ItemTags.FURNACE_FUEL_BOTTOM_TAKEABLE)
                .add(family.empty().builtInRegistryHolder().key())
                .add(family.water().builtInRegistryHolder().key());
            if (family.lava() != null) {
                builder(ConventionalItemTags.LAVA_BUCKETS).add(family.lava().builtInRegistryHolder().key());
            }
        }

        builder(ModIds.SCYTHES)
            .add(ModIds.WOODEN_SCYTHE)
            .add(ModIds.STONE_SCYTHE)
            .add(ModIds.COPPER_SCYTHE)
            .add(ModIds.IRON_SCYTHE)
            .add(ModIds.GOLDEN_SCYTHE)
            .add(ModIds.DIAMOND_SCYTHE)
            .add(ModIds.NETHERITE_SCYTHE)
            .add(ModIds.REINFORCED_STONE_SCYTHE);

        builder(ItemTags.SWORDS).add(ModIds.REINFORCED_STONE_SWORD);
        builder(ItemTags.SHOVELS).add(ModIds.REINFORCED_STONE_SHOVEL);
        builder(ItemTags.PICKAXES).add(ModIds.REINFORCED_STONE_PICKAXE);
        builder(ItemTags.AXES).add(ModIds.REINFORCED_STONE_AXE);
        builder(ItemTags.HOES).add(ModIds.REINFORCED_STONE_HOE);
        builder(ItemTags.SPEARS).add(ModIds.REINFORCED_STONE_SPEAR);

        builder(ItemTags.MINING_ENCHANTABLE)
            .addTag(ModIds.SCYTHES);

        builder(ItemTags.MINING_LOOT_ENCHANTABLE)
            .addTag(ModIds.SCYTHES);

        builder(ItemTags.DURABILITY_ENCHANTABLE)
            .addTag(ModIds.SCYTHES);
    }
}
