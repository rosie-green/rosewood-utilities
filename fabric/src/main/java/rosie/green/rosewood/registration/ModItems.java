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

    public static void init() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            entries.insertAfter(Items.WOODEN_HOE, WOODEN_SCYTHE);
            entries.insertAfter(Items.STONE_HOE, STONE_SCYTHE);
            entries.insertAfter(Items.COPPER_HOE, COPPER_SCYTHE);
            entries.insertAfter(Items.IRON_HOE, IRON_SCYTHE);
            entries.insertAfter(Items.GOLDEN_HOE, GOLDEN_SCYTHE);
            entries.insertAfter(Items.DIAMOND_HOE, DIAMOND_SCYTHE);
            entries.insertAfter(Items.NETHERITE_HOE, NETHERITE_SCYTHE);
            entries.accept(CRAFTING_PAD);
        });
    }
}
