package rosie.green.rosewood.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.NullMarked;
import rosie.green.rosewood.Rosewood;
import rosie.green.rosewood.datagen.helpers.TagBuilder;
import rosie.green.rosewood.registration.ModItems;

import java.util.concurrent.CompletableFuture;

@NullMarked
public class ItemTagGenerator extends FabricTagsProvider.ItemTagsProvider {
    public ItemTagGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries, BlockTagGenerator blockTagGenerator) {
        super(output, registries, blockTagGenerator);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        var scythes = TagKey.create(Registries.ITEM, Rosewood.id("scythes"));

        tag(scythes)
            .add(ModItems.WOODEN_SCYTHE)
            .add(ModItems.STONE_SCYTHE)
            .add(ModItems.COPPER_SCYTHE)
            .add(ModItems.IRON_SCYTHE)
            .add(ModItems.GOLDEN_SCYTHE)
            .add(ModItems.DIAMOND_SCYTHE)
            .add(ModItems.NETHERITE_SCYTHE)
            .add(ModItems.REINFORCED_STONE_SCYTHE);

        tag(ItemTags.SWORDS).add(ModItems.REINFORCED_STONE_SWORD);
        tag(ItemTags.SHOVELS).add(ModItems.REINFORCED_STONE_SHOVEL);
        tag(ItemTags.PICKAXES).add(ModItems.REINFORCED_STONE_PICKAXE);
        tag(ItemTags.AXES).add(ModItems.REINFORCED_STONE_AXE);
        tag(ItemTags.HOES).add(ModItems.REINFORCED_STONE_HOE);
        tag(ItemTags.SPEARS).add(ModItems.REINFORCED_STONE_SPEAR);

        tag(ItemTags.MINING_ENCHANTABLE)
            .addTag(scythes);

        tag(ItemTags.MINING_LOOT_ENCHANTABLE)
            .addTag(scythes);

        tag(ItemTags.DURABILITY_ENCHANTABLE)
            .addTag(scythes);
    }

    @Override
    protected TagBuilder<Item> tag(TagKey<Item> tag) {
        return new TagBuilder<>(super.tag(tag), this::getItemName);
    }

    @Override
    protected TagBuilder<Item> tag(TagKey<Item> tag, boolean replace) {
        return new TagBuilder<>(super.tag(tag, replace), this::getItemName);
    }

    private ResourceKey<Item> getItemName(Item item) {
        return item.builtInRegistryHolder().key();
    }
}
