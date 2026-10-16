package rosie.green.rosewood.datagen.providers;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.world.item.Item;
import rosie.green.rosewood.content.ModBlocks;
import rosie.green.rosewood.content.ModItems;
import rosie.green.rosewood.content.WoodenBucketFamily;

public final class ModelGenerator extends FabricModelProvider {
    public ModelGenerator(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generators) {
        generators.createAxisAlignedPillarBlock(ModBlocks.REINFORCED_STONE, TexturedModel.CUBE_TOP);
    }

    private void generateBucketModels(WoodenBucketFamily<Item> buckets, ItemModelGenerators generators) {
        generators.generateFlatItem(buckets.empty(), ModelTemplates.FLAT_HANDHELD_ITEM);
        generators.generateFlatItem(buckets.water(), ModelTemplates.FLAT_HANDHELD_ITEM);

        var lavaBucket = buckets.lava();

        if (lavaBucket != null) {
            generators.generateFlatItem(lavaBucket, ModelTemplates.FLAT_HANDHELD_ITEM);
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerators generators) {
        generators.generateFlatItem(ModItems.WOODEN_SCYTHE, ModelTemplates.FLAT_HANDHELD_ITEM);
        generators.generateFlatItem(ModItems.STONE_SCYTHE, ModelTemplates.FLAT_HANDHELD_ITEM);
        generators.generateFlatItem(ModItems.COPPER_SCYTHE, ModelTemplates.FLAT_HANDHELD_ITEM);
        generators.generateFlatItem(ModItems.IRON_SCYTHE, ModelTemplates.FLAT_HANDHELD_ITEM);
        generators.generateFlatItem(ModItems.GOLDEN_SCYTHE, ModelTemplates.FLAT_HANDHELD_ITEM);
        generators.generateFlatItem(ModItems.DIAMOND_SCYTHE, ModelTemplates.FLAT_HANDHELD_ITEM);
        generators.generateFlatItem(ModItems.NETHERITE_SCYTHE, ModelTemplates.FLAT_HANDHELD_ITEM);

        generators.generateFlatItem(ModItems.CRAFTING_PAD, ModelTemplates.FLAT_ITEM);

        generators.generateFlatItem(ModItems.REINFORCED_STONE_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
        generators.generateFlatItem(ModItems.REINFORCED_STONE_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM);
        generators.generateFlatItem(ModItems.REINFORCED_STONE_PICKAXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        generators.generateFlatItem(ModItems.REINFORCED_STONE_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        generators.generateFlatItem(ModItems.REINFORCED_STONE_HOE, ModelTemplates.FLAT_HANDHELD_ITEM);
        generators.generateSpear(ModItems.REINFORCED_STONE_SPEAR);
        generators.generateFlatItem(ModItems.REINFORCED_STONE_SCYTHE, ModelTemplates.FLAT_HANDHELD_ITEM);

        generateBucketModels(ModItems.OAK_BUCKET, generators);
        generateBucketModels(ModItems.SPRUCE_BUCKET, generators);
        generateBucketModels(ModItems.BIRCH_BUCKET, generators);
        generateBucketModels(ModItems.JUNGLE_BUCKET, generators);
        generateBucketModels(ModItems.ACACIA_BUCKET, generators);
        generateBucketModels(ModItems.DARK_OAK_BUCKET, generators);
        generateBucketModels(ModItems.MANGROVE_BUCKET, generators);
        generateBucketModels(ModItems.CHERRY_BUCKET, generators);
        generateBucketModels(ModItems.PALE_OAK_BUCKET, generators);
        generateBucketModels(ModItems.POPLAR_BUCKET, generators);
        generateBucketModels(ModItems.CRIMSON_BUCKET, generators);
        generateBucketModels(ModItems.WARPED_BUCKET, generators);
    }


}
