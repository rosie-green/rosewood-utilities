package rosie.green.rosewood.content;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTabOutput;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import rosie.green.rosewood.items.CraftingPad;
import rosie.green.rosewood.items.ScytheItem;

import java.util.function.Function;

public final class ModItems {
    private ModItems() {
        throw new IllegalStateException("Cannot instantiate a utility class.");
    }

    private static ScytheItem registerScythe(ResourceKey<Item> key, Item.Properties properties, int range) {
        return register(key, props -> new ScytheItem(props, range), properties);
    }

    private static <T extends Item> T register(ResourceKey<Item> key, Function<Item.Properties, T> constructor, Item.Properties properties) {
        return Registry.register(BuiltInRegistries.ITEM, key, constructor.apply(properties.setId(key)));
    }

    private static Item.Properties hoe(final ToolMaterial material, final float attackDamageBaseline, final float attackSpeedBaseline) {
        return new Item.Properties().hoe(material, attackDamageBaseline, attackSpeedBaseline);
    }

    public static final ScytheItem WOODEN_SCYTHE = registerScythe(ModIds.WOODEN_SCYTHE, hoe(ToolMaterial.WOOD, 0.0F, -3.0F), 5);
    public static final ScytheItem STONE_SCYTHE = registerScythe(ModIds.STONE_SCYTHE, hoe(ToolMaterial.STONE, -1.0F, -2.0F), 8);
    public static final ScytheItem COPPER_SCYTHE = registerScythe(ModIds.COPPER_SCYTHE, hoe(ToolMaterial.COPPER, -2.0F, -1.0F), 10);
    public static final ScytheItem IRON_SCYTHE = registerScythe(ModIds.IRON_SCYTHE, hoe(ToolMaterial.IRON, -2.0F, -1.0F), 16);
    public static final ScytheItem GOLDEN_SCYTHE = registerScythe(ModIds.GOLDEN_SCYTHE, hoe(ToolMaterial.GOLD, 0.0F, -3.0F), 10);
    public static final ScytheItem DIAMOND_SCYTHE = registerScythe(ModIds.DIAMOND_SCYTHE, hoe(ToolMaterial.DIAMOND, -3.0F, 0.0F), 20);
    public static final ScytheItem NETHERITE_SCYTHE = registerScythe(ModIds.NETHERITE_SCYTHE, hoe(ToolMaterial.NETHERITE, -4.0F, 0.0F).fireResistant(), 40);

    public static final CraftingPad CRAFTING_PAD = register(ModIds.CRAFTING_PAD, CraftingPad::new, new Item.Properties().stacksTo(1));

    public static final BlockItem REINFORCED_STONE = register(
        ModIds.REINFORCED_STONE.item(),
        properties -> new BlockItem(ModBlocks.REINFORCED_STONE, properties),
        new Item.Properties().useBlockDescriptionPrefix()
    );

    private static Item.Properties reinforcedStoneTool(Item.Properties properties) {
        return properties.durability(781).repairable(REINFORCED_STONE);
    }

    public static final Item REINFORCED_STONE_SWORD = register(
        ModIds.REINFORCED_STONE_SWORD,
        Item::new,
        reinforcedStoneTool(new Item.Properties().sword(ToolMaterial.STONE, 3.0F, -2.4F))
    );

    public static final Item REINFORCED_STONE_SHOVEL = register(
        ModIds.REINFORCED_STONE_SHOVEL,
        Item::new,
        reinforcedStoneTool(new Item.Properties().shovel(ToolMaterial.STONE, 1.5F, -3.0F))
    );

    public static final Item REINFORCED_STONE_PICKAXE = register(
        ModIds.REINFORCED_STONE_PICKAXE,
        Item::new,
        reinforcedStoneTool(new Item.Properties().pickaxe(ToolMaterial.STONE, 1.0F, -2.8F))
    );

    public static final Item REINFORCED_STONE_AXE = register(
        ModIds.REINFORCED_STONE_AXE,
        Item::new,
        reinforcedStoneTool(new Item.Properties().axe(ToolMaterial.STONE, 7.0F, -3.2F))
    );

    public static final Item REINFORCED_STONE_HOE = register(
        ModIds.REINFORCED_STONE_HOE,
        Item::new,
        reinforcedStoneTool(hoe(ToolMaterial.STONE, -1.0F, -2.0F))
    );

    public static final Item REINFORCED_STONE_SPEAR = register(
        ModIds.REINFORCED_STONE_SPEAR,
        Item::new,
        reinforcedStoneTool(new Item.Properties().spear(ToolMaterial.STONE, 0.75F, 0.82F, 0.7F, 4.5F, 13.0F, 9.0F, 5.1F, 13.75F, 4.6F))
    );

    public static final Item REINFORCED_STONE_SCYTHE = registerScythe(
        ModIds.REINFORCED_STONE_SCYTHE,
        reinforcedStoneTool(new Item.Properties().hoe(ToolMaterial.STONE, -1.0F, -2.0F)),
        8
    );

    private static WoodenBucketFamily<Item> registerBucket(WoodenBucketFamily<ResourceKey<Item>> buckets) {
        return new WoodenBucketFamily<>(null, null, null);
    }

    public static final WoodenBucketFamily<Item> OAK_BUCKET = registerBucket(ModIds.OAK_BUCKET);
    public static final WoodenBucketFamily<Item> SPRUCE_BUCKET = registerBucket(ModIds.SPRUCE_BUCKET);
    public static final WoodenBucketFamily<Item> BIRCH_BUCKET = registerBucket(ModIds.BIRCH_BUCKET);
    public static final WoodenBucketFamily<Item> JUNGLE_BUCKET = registerBucket(ModIds.JUNGLE_BUCKET);
    public static final WoodenBucketFamily<Item> ACACIA_BUCKET = registerBucket(ModIds.ACACIA_BUCKET);
    public static final WoodenBucketFamily<Item> DARK_OAK_BUCKET = registerBucket(ModIds.DARK_OAK_BUCKET);
    public static final WoodenBucketFamily<Item> MANGROVE_BUCKET = registerBucket(ModIds.MANGROVE_BUCKET);
    public static final WoodenBucketFamily<Item> CHERRY_BUCKET = registerBucket(ModIds.CHERRY_BUCKET);
    public static final WoodenBucketFamily<Item> PALE_OAK_BUCKET = registerBucket(ModIds.PALE_OAK_BUCKET);
    public static final WoodenBucketFamily<Item> POPLAR_BUCKET = registerBucket(ModIds.POPLAR_BUCKET);
    public static final WoodenBucketFamily<Item> CRIMSON_BUCKET = registerBucket(ModIds.CRIMSON_BUCKET);
    public static final WoodenBucketFamily<Item> WARPED_BUCKET = registerBucket(ModIds.WARPED_BUCKET);

    private static void insertBucketsAfter(FabricCreativeModeTabOutput entries, ItemLike item, WoodenBucketFamily<Item> buckets) {
        var lavaBucket = buckets.lava();

        if (lavaBucket != null) {
            entries.insertAfter(item, buckets.empty(), buckets.water(), lavaBucket);
        } else {
            entries.insertAfter(item, buckets.empty(), buckets.water());
        }
    }

    public static void init() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS).register(entries -> {
            entries.insertAfter(Items.MOSSY_STONE_BRICK_WALL, REINFORCED_STONE);
        });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            entries.insertAfter(Items.WOODEN_HOE, WOODEN_SCYTHE);
            entries.insertAfter(Items.STONE_HOE, STONE_SCYTHE);
            entries.insertAfter(Items.COPPER_HOE, COPPER_SCYTHE);
            entries.insertAfter(Items.IRON_HOE, IRON_SCYTHE);
            entries.insertAfter(Items.GOLDEN_HOE, GOLDEN_SCYTHE);
            entries.insertAfter(Items.DIAMOND_HOE, DIAMOND_SCYTHE);
            entries.insertAfter(Items.NETHERITE_HOE, NETHERITE_SCYTHE);

            entries.accept(CRAFTING_PAD);

            entries.insertAfter(STONE_SCYTHE, REINFORCED_STONE_SHOVEL, REINFORCED_STONE_PICKAXE, REINFORCED_STONE_AXE, REINFORCED_STONE_HOE, REINFORCED_STONE_SCYTHE);

            // todo: create one big list? copy vanilla wood building blocks creative tab order?
            insertBucketsAfter(entries, Items.MILK_BUCKET, OAK_BUCKET);
            insertBucketsAfter(entries, OAK_BUCKET.last(), SPRUCE_BUCKET);
            insertBucketsAfter(entries, SPRUCE_BUCKET.last(), BIRCH_BUCKET);
            insertBucketsAfter(entries, BIRCH_BUCKET.last(), JUNGLE_BUCKET);
            insertBucketsAfter(entries, JUNGLE_BUCKET.last(), ACACIA_BUCKET);
            insertBucketsAfter(entries, ACACIA_BUCKET.last(), DARK_OAK_BUCKET);
            insertBucketsAfter(entries, DARK_OAK_BUCKET.last(), MANGROVE_BUCKET);
            insertBucketsAfter(entries, MANGROVE_BUCKET.last(), CHERRY_BUCKET);
            insertBucketsAfter(entries, CHERRY_BUCKET.last(), PALE_OAK_BUCKET);
            insertBucketsAfter(entries, PALE_OAK_BUCKET.last(), POPLAR_BUCKET);
            insertBucketsAfter(entries, POPLAR_BUCKET.last(), CRIMSON_BUCKET);
            insertBucketsAfter(entries, CRIMSON_BUCKET.last(), WARPED_BUCKET);
        });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(entries -> {
            entries.insertAfter(Items.STONE_SWORD, REINFORCED_STONE_SWORD);
            entries.insertAfter(Items.STONE_SPEAR, REINFORCED_STONE_SPEAR);
        });
    }
}
