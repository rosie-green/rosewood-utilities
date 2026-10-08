package rosie.green.rosewood.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import org.jspecify.annotations.NullMarked;
import rosie.green.rosewood.registration.ModIds;

import java.util.concurrent.CompletableFuture;

@NullMarked
public class ItemTagGenerator extends FabricTagsProvider.ItemTagsProvider {
    public ItemTagGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries, BlockTagGenerator blockTagGenerator) {
        super(output, registries, blockTagGenerator);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
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
