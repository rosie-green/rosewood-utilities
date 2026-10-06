package rosie.green.rosewood.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.NullMarked;
import rosie.green.rosewood.Rosewood;
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
        var scythesAppender = tag(scythes);

        add(scythesAppender, ModItems.WOODEN_SCYTHE);
        add(scythesAppender, ModItems.STONE_SCYTHE);
        add(scythesAppender, ModItems.COPPER_SCYTHE);
        add(scythesAppender, ModItems.IRON_SCYTHE);
        add(scythesAppender, ModItems.GOLDEN_SCYTHE);
        add(scythesAppender, ModItems.DIAMOND_SCYTHE);
        add(scythesAppender, ModItems.NETHERITE_SCYTHE);
        add(scythesAppender, ModItems.REINFORCED_STONE_SCYTHE);

        add(tag(ItemTags.SWORDS), ModItems.REINFORCED_STONE_SWORD);
        add(tag(ItemTags.SHOVELS), ModItems.REINFORCED_STONE_SHOVEL);
        add(tag(ItemTags.PICKAXES), ModItems.REINFORCED_STONE_PICKAXE);
        add(tag(ItemTags.AXES), ModItems.REINFORCED_STONE_AXE);
        add(tag(ItemTags.HOES), ModItems.REINFORCED_STONE_HOE);
        add(tag(ItemTags.SPEARS), ModItems.REINFORCED_STONE_SPEAR);

        tag(ItemTags.MINING_ENCHANTABLE)
            .addTag(scythes);

        tag(ItemTags.MINING_LOOT_ENCHANTABLE)
            .addTag(scythes);

        tag(ItemTags.DURABILITY_ENCHANTABLE)
            .addTag(scythes);
    }

    private void add(TagAppender<Item> tag, Item item) {
        tag.add(item.builtInRegistryHolder().key());
    }
}
