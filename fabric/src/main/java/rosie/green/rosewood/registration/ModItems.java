package rosie.green.rosewood.registration;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import rosie.green.rosewood.Rosewood;
import rosie.green.rosewood.items.CraftingPad;
import rosie.green.rosewood.items.ScytheItem;

import java.util.function.Function;

public class ModItems {
    private ModItems() {
        throw new IllegalStateException("Cannot instantiate an utility class.");
    }

    private static ResourceKey<Item> createKey(String name) {
        return ResourceKey.create(Registries.ITEM, Rosewood.id(name));
    }

    private static ScytheItem registerScythe(
        String name,
        Item.Properties properties,
        int range
    ) {
        return register(
            name,
            props -> new ScytheItem(props, range),
            properties
        );
    }

    private static <T extends Item> T register(
        String name,
        Function<Item.Properties, T> constructor,
        Item.Properties properties
    ) {
        var key = createKey(name);

        return Registry.register(
            BuiltInRegistries.ITEM,
            key,
            constructor.apply(properties.setId(key))
        );
    }

    public static final ScytheItem WOODEN_SCYTHE = registerScythe("wooden_scythe", new Item.Properties().hoe(ToolMaterial.WOOD, 0.0F, -3.0F), 5);
    public static final ScytheItem STONE_SCYTHE = registerScythe("stone_scythe", new Item.Properties().hoe(ToolMaterial.STONE, -1.0F, -2.0F), 8);
    public static final ScytheItem COPPER_SCYTHE = registerScythe("copper_scythe", new Item.Properties().hoe(ToolMaterial.COPPER, -2.0F, -1.0F), 10);
    public static final ScytheItem IRON_SCYTHE = registerScythe("iron_scythe", new Item.Properties().hoe(ToolMaterial.IRON, -2.0F, -1.0F), 16);
    public static final ScytheItem GOLDEN_SCYTHE = registerScythe("golden_scythe", new Item.Properties().hoe(ToolMaterial.GOLD, 0.0F, -3.0F), 10);
    public static final ScytheItem DIAMOND_SCYTHE = registerScythe("diamond_scythe", new Item.Properties().hoe(ToolMaterial.DIAMOND, -3.0F, 0.0F), 20);
    public static final ScytheItem NETHERITE_SCYTHE = registerScythe("netherite_scythe", new Item.Properties().hoe(ToolMaterial.NETHERITE, -4.0F, 0.0F), 40);

    public static final CraftingPad CRAFTING_PAD = register(
        "crafting_pad",
        CraftingPad::new,
        new Item.Properties().stacksTo(1)
    );

    public static BlockItem REINFORCED_STONE = register(
        "reinforced_stone",
        properties -> new BlockItem(ModBlocks.REINFORCED_STONE, properties),
        new Item.Properties().useBlockDescriptionPrefix()
    );

    public static final Item REINFORCED_STONE_SWORD = register(
        "reinforced_stone_sword",
        Item::new,
        new Item.Properties().sword(ToolMaterial.STONE, 3.0F, -2.4F).durability(781).repairable(REINFORCED_STONE)
    );

    public static final Item REINFORCED_STONE_SHOVEL = register(
        "reinforced_stone_shovel",
        Item::new,
        new Item.Properties().shovel(ToolMaterial.STONE, 1.5F, -3.0F).durability(781).repairable(REINFORCED_STONE)
    );

    public static final Item REINFORCED_STONE_PICKAXE = register(
        "reinforced_stone_pickaxe",
        Item::new,
        new Item.Properties().pickaxe(ToolMaterial.STONE, 1.0F, -2.8F).durability(781).repairable(REINFORCED_STONE)
    );

    public static final Item REINFORCED_STONE_AXE = register(
        "reinforced_stone_axe",
        Item::new,
        new Item.Properties().axe(ToolMaterial.STONE, 7.0F, -3.2F).durability(781).repairable(REINFORCED_STONE)
    );

    public static final Item REINFORCED_STONE_HOE = register(
        "reinforced_stone_hoe",
        Item::new,
        new Item.Properties().hoe(ToolMaterial.STONE, -1.0F, -2.0F).durability(781).repairable(REINFORCED_STONE)
    );

    public static final Item REINFORCED_STONE_SPEAR = registerScythe(
        "reinforced_stone_spear",
        new Item.Properties().spear(ToolMaterial.STONE, 0.75F, 0.82F, 0.7F, 4.5F, 13.0F, 9.0F, 5.1F, 13.75F, 4.6F).durability(781).repairable(REINFORCED_STONE),
        8
    );

    public static final Item REINFORCED_STONE_SCYTHE = registerScythe(
        "reinforced_stone_scythe",
        new Item.Properties().hoe(ToolMaterial.STONE, -1.0F, -2.0F).durability(781).repairable(REINFORCED_STONE),
        8
    );

    public static void init() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS).register(entries -> {
            entries.insertAfter(Items.MOSSY_STONE_BRICK_WALL, REINFORCED_STONE);
        });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            entries.insertAfter(Items.WOODEN_HOE, WOODEN_SCYTHE);
            entries.insertAfter(Items.STONE_HOE, STONE_SCYTHE, REINFORCED_STONE_SHOVEL, REINFORCED_STONE_PICKAXE, REINFORCED_STONE_AXE, REINFORCED_STONE_HOE, REINFORCED_STONE_SCYTHE);
            entries.insertAfter(Items.COPPER_HOE, COPPER_SCYTHE);
            entries.insertAfter(Items.IRON_HOE, IRON_SCYTHE);
            entries.insertAfter(Items.GOLDEN_HOE, GOLDEN_SCYTHE);
            entries.insertAfter(Items.DIAMOND_HOE, DIAMOND_SCYTHE);
            entries.insertAfter(Items.NETHERITE_HOE, NETHERITE_SCYTHE);

            entries.accept(CRAFTING_PAD);
        });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(entries -> {
            entries.insertAfter(Items.STONE_SWORD, REINFORCED_STONE_SWORD);
            entries.insertAfter(Items.STONE_SPEAR, REINFORCED_STONE_SPEAR);
        });
    }
}
