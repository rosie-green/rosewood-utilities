package rosie.green.rosewood.providers;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TexturedModel;
import org.jspecify.annotations.NullMarked;
import rosie.green.rosewood.registration.ModBlocks;
import rosie.green.rosewood.registration.ModItems;

@NullMarked
public class ModelGenerator extends FabricModelProvider {
    public ModelGenerator(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generators) {
        generators.createTrivialBlock(ModBlocks.REINFORCED_STONE, TexturedModel.CUBE_TOP);
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
    }
}
