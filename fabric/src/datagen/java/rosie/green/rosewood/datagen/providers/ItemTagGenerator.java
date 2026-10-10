package rosie.green.rosewood.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.references.BlockItemIds;
import net.minecraft.tags.ItemTags;
import rosie.green.rosewood.content.ModIds;
import rosie.green.rosewood.datagen.content.ModTags;

import java.util.concurrent.CompletableFuture;

public class ItemTagGenerator extends FabricTagsProvider.ItemTagsProvider {
    public ItemTagGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries, BlockTagGenerator blockTagGenerator) {
        super(output, registries, blockTagGenerator);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        builder(ModTags.SCYTHES)
            .add(ModIds.WOODEN_SCYTHE)
            .add(ModIds.STONE_SCYTHE)
            .add(ModIds.COPPER_SCYTHE)
            .add(ModIds.IRON_SCYTHE)
            .add(ModIds.GOLDEN_SCYTHE)
            .add(ModIds.DIAMOND_SCYTHE)
            .add(ModIds.NETHERITE_SCYTHE)
            .add(ModIds.REINFORCED_STONE_SCYTHE);

        builder(ItemTags.MINING_ENCHANTABLE)
            .addTag(ModTags.SCYTHES);

        builder(ItemTags.MINING_LOOT_ENCHANTABLE)
            .addTag(ModTags.SCYTHES);

        builder(ItemTags.DURABILITY_ENCHANTABLE)
            .addTag(ModTags.SCYTHES);

        builder(ItemTags.SWORDS).add(ModIds.REINFORCED_STONE_SWORD);
        builder(ItemTags.SHOVELS).add(ModIds.REINFORCED_STONE_SHOVEL);
        builder(ItemTags.PICKAXES).add(ModIds.REINFORCED_STONE_PICKAXE);
        builder(ItemTags.AXES).add(ModIds.REINFORCED_STONE_AXE);
        builder(ItemTags.HOES).add(ModIds.REINFORCED_STONE_HOE);
        builder(ItemTags.SPEARS).add(ModIds.REINFORCED_STONE_SPEAR);

        builder(ModTags.OAK_BARK)
            .add(BlockItemIds.OAK_LOG)
            .add(BlockItemIds.OAK_WOOD);

        builder(ModTags.SPRUCE_BARK)
            .add(BlockItemIds.SPRUCE_LOG)
            .add(BlockItemIds.SPRUCE_WOOD);

        builder(ModTags.BIRCH_BARK)
            .add(BlockItemIds.BIRCH_LOG)
            .add(BlockItemIds.BIRCH_WOOD);

        builder(ModTags.JUNGLE_BARK)
            .add(BlockItemIds.JUNGLE_LOG)
            .add(BlockItemIds.JUNGLE_WOOD);

        builder(ModTags.ACACIA_BARK)
            .add(BlockItemIds.ACACIA_LOG)
            .add(BlockItemIds.ACACIA_WOOD);

        builder(ModTags.DARK_OAK_BARK)
            .add(BlockItemIds.DARK_OAK_LOG)
            .add(BlockItemIds.DARK_OAK_WOOD);

        builder(ModTags.MANGROVE_BARK)
            .add(BlockItemIds.MANGROVE_LOG)
            .add(BlockItemIds.MANGROVE_WOOD);

        builder(ModTags.CHERRY_BARK)
            .add(BlockItemIds.CHERRY_LOG)
            .add(BlockItemIds.CHERRY_WOOD);

        builder(ModTags.PALE_OAK_BARK)
            .add(BlockItemIds.PALE_OAK_LOG)
            .add(BlockItemIds.PALE_OAK_WOOD);

        builder(ModTags.POPLAR_BARK)
            .add(BlockItemIds.POPLAR_LOG)
            .add(BlockItemIds.POPLAR_WOOD);

        builder(ModTags.CRIMSON_BARK)
            .add(BlockItemIds.CRIMSON_STEM)
            .add(BlockItemIds.CRIMSON_HYPHAE);

        builder(ModTags.WARPED_BARK)
            .add(BlockItemIds.WARPED_STEM)
            .add(BlockItemIds.WARPED_HYPHAE);
    }
}
