package rosie.green.rosewood.registration;

import net.minecraft.core.registries.Registries;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import rosie.green.rosewood.Rosewood;

public class ModIds {
    private ModIds() {
        throw new IllegalStateException("Cannot instantiate an utility class.");
    }

    public static final BlockItemId REINFORCED_STONE = block("reinforced_stone");

    public static final ResourceKey<Item> WOODEN_SCYTHE = item("wooden_scythe");
    public static final ResourceKey<Item> STONE_SCYTHE = item("stone_scythe");
    public static final ResourceKey<Item> COPPER_SCYTHE = item("copper_scythe");
    public static final ResourceKey<Item> IRON_SCYTHE = item("iron_scythe");
    public static final ResourceKey<Item> GOLDEN_SCYTHE = item("golden_scythe");
    public static final ResourceKey<Item> DIAMOND_SCYTHE = item("diamond_scythe");
    public static final ResourceKey<Item> NETHERITE_SCYTHE = item("netherite_scythe");

    public static final ResourceKey<Item> CRAFTING_PAD = item("crafting_pad");

    public static final ResourceKey<Item> REINFORCED_STONE_SWORD = item("reinforced_stone_sword");
    public static final ResourceKey<Item> REINFORCED_STONE_SHOVEL = item("reinforced_stone_shovel");
    public static final ResourceKey<Item> REINFORCED_STONE_PICKAXE = item("reinforced_stone_pickaxe");
    public static final ResourceKey<Item> REINFORCED_STONE_AXE = item("reinforced_stone_axe");
    public static final ResourceKey<Item> REINFORCED_STONE_HOE = item("reinforced_stone_hoe");
    public static final ResourceKey<Item> REINFORCED_STONE_SPEAR = item("reinforced_stone_spear");
    public static final ResourceKey<Item> REINFORCED_STONE_SCYTHE = item("reinforced_stone_scythe");

    public static final TagKey<Item> SCYTHES = TagKey.create(Registries.ITEM, id("scythes"));

    private static BlockItemId block(String name) {
        return BlockItemId.create(id(name), id(name));
    }

    private static ResourceKey<Item> item(String name) {
        return ResourceKey.create(Registries.ITEM, id(name));
    }

    private static Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(Rosewood.MOD_ID, name);
    }
}
