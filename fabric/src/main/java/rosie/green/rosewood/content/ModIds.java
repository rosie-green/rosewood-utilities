package rosie.green.rosewood.content;

import net.minecraft.core.registries.Registries;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
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

    public static final WoodenBucketFamily<ResourceKey<Item>> OAK_BUCKET = WoodenBucketFamily.create(item("oak_bucket"), false);
    public static final WoodenBucketFamily<ResourceKey<Item>> SPRUCE_BUCKET = WoodenBucketFamily.create(item("spruce_bucket"), false);
    public static final WoodenBucketFamily<ResourceKey<Item>> BIRCH_BUCKET = WoodenBucketFamily.create(item("birch_bucket"), false);
    public static final WoodenBucketFamily<ResourceKey<Item>> JUNGLE_BUCKET = WoodenBucketFamily.create(item("jungle_bucket"), false);
    public static final WoodenBucketFamily<ResourceKey<Item>> ACACIA_BUCKET = WoodenBucketFamily.create(item("acacia_bucket"), false);
    public static final WoodenBucketFamily<ResourceKey<Item>> DARK_OAK_BUCKET = WoodenBucketFamily.create(item("dark_oak_bucket"), false);
    public static final WoodenBucketFamily<ResourceKey<Item>> MANGROVE_BUCKET = WoodenBucketFamily.create(item("mangrove_bucket"), false);
    public static final WoodenBucketFamily<ResourceKey<Item>> CHERRY_BUCKET = WoodenBucketFamily.create(item("cherry_bucket"), false);
    public static final WoodenBucketFamily<ResourceKey<Item>> PALE_OAK_BUCKET = WoodenBucketFamily.create(item("pale_oak_bucket"), false);
    public static final WoodenBucketFamily<ResourceKey<Item>> POPLAR_BUCKET = WoodenBucketFamily.create(item("poplar_bucket"), false);
    public static final WoodenBucketFamily<ResourceKey<Item>> CRIMSON_BUCKET = WoodenBucketFamily.create(item("crimson_bucket"), true);
    public static final WoodenBucketFamily<ResourceKey<Item>> WARPED_BUCKET = WoodenBucketFamily.create(item("warped_bucket"), true);

    private static BlockItemId block(String name) {
        return BlockItemId.create(id(name), id(name));
    }

    private static ResourceKey<Item> item(String name) {
        return ResourceKey.create(Registries.ITEM, id(name));
    }

    public static Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(Rosewood.MOD_ID, name);
    }
}
