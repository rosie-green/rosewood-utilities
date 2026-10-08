package rosie.green.rosewood.registration;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.DispenserBlock;
import rosie.green.rosewood.items.CraftingPad;
import rosie.green.rosewood.items.ScytheItem;
import rosie.green.rosewood.items.WoodenBucketCauldronInteractions;
import rosie.green.rosewood.items.WoodenBucketDispenseBehavior;
import rosie.green.rosewood.items.WoodenBucketFamily;
import rosie.green.rosewood.items.WoodenBucketFluidStorage;

import java.util.List;
import java.util.function.Function;

public class ModItems {
    private ModItems() {
        throw new IllegalStateException("Cannot instantiate an utility class.");
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

    public static final List<WoodenBucketFamily> WOODEN_BUCKETS = List.of(
        new WoodenBucketFamily("oak", ItemTags.OAK_LOGS),
        new WoodenBucketFamily("spruce", ItemTags.SPRUCE_LOGS),
        new WoodenBucketFamily("birch", ItemTags.BIRCH_LOGS),
        new WoodenBucketFamily("jungle", ItemTags.JUNGLE_LOGS),
        new WoodenBucketFamily("acacia", ItemTags.ACACIA_LOGS),
        new WoodenBucketFamily("dark_oak", ItemTags.DARK_OAK_LOGS),
        new WoodenBucketFamily("mangrove", ItemTags.MANGROVE_LOGS),
        new WoodenBucketFamily("cherry", ItemTags.CHERRY_LOGS),
        new WoodenBucketFamily("pale_oak", ItemTags.PALE_OAK_LOGS),
        new WoodenBucketFamily("poplar", ItemTags.POPLAR_LOGS),
        new WoodenBucketFamily("crimson", ItemTags.CRIMSON_STEMS, true),
        new WoodenBucketFamily("warped", ItemTags.WARPED_STEMS, true)
    );

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

    public static void init() {
        WoodenBucketCauldronInteractions.register(WOODEN_BUCKETS);
        WoodenBucketFluidStorage.register(WOODEN_BUCKETS);
        var woodenBucketBehavior = new WoodenBucketDispenseBehavior();
        for (var variant : WOODEN_BUCKETS) {
            for (var bucket : variant.items()) {
                DispenserBlock.registerBehavior(bucket, woodenBucketBehavior);
            }
        }

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
            Item previousBucket = Items.BUCKET;
            for (var variant : WOODEN_BUCKETS) {
                for (var bucket : variant.items()) {
                    entries.insertAfter(previousBucket, bucket);
                    previousBucket = bucket;
                }
            }

            entries.insertAfter(STONE_SCYTHE, REINFORCED_STONE_SHOVEL, REINFORCED_STONE_PICKAXE, REINFORCED_STONE_AXE, REINFORCED_STONE_HOE, REINFORCED_STONE_SCYTHE);
        });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(entries -> {
            entries.insertAfter(Items.STONE_SWORD, REINFORCED_STONE_SWORD);
            entries.insertAfter(Items.STONE_SPEAR, REINFORCED_STONE_SPEAR);
        });
    }
}
