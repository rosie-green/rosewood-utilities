package rosie.green.rosewood.providers;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import rosie.green.rosewood.registration.ModItems;

public class RecipeGenerator extends RecipeProvider {
    protected RecipeGenerator(BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
        super(recipes, advancements);
    }

    @Override
    public void buildRecipes() {
        shaped(RecipeCategory.TOOLS, ModItems.WOODEN_SCYTHE)
            .pattern("MMM")
            .pattern(" S ")
            .pattern("S  ")
            .define('M', ItemTags.WOODEN_TOOL_MATERIALS)
            .define('S', Items.STICK)
            .unlockedBy("has_planks", has(ItemTags.WOODEN_TOOL_MATERIALS))
            .save(output);

        shaped(RecipeCategory.TOOLS, ModItems.STONE_SCYTHE)
            .pattern("MMM")
            .pattern(" S ")
            .pattern("S  ")
            .define('M', ItemTags.STONE_TOOL_MATERIALS)
            .define('S', Items.STICK)
            .unlockedBy("has_cobblestone", has(ItemTags.STONE_TOOL_MATERIALS))
            .save(output);

        shaped(RecipeCategory.TOOLS, ModItems.COPPER_SCYTHE)
            .pattern("MMM")
            .pattern(" S ")
            .pattern("S  ")
            .define('M', ItemTags.COPPER_TOOL_MATERIALS)
            .define('S', Items.STICK)
            .unlockedBy("has_copper_ingot", has(ItemTags.COPPER_TOOL_MATERIALS))
            .save(output);

        shaped(RecipeCategory.TOOLS, ModItems.IRON_SCYTHE)
            .pattern("MMM")
            .pattern(" S ")
            .pattern("S  ")
            .define('M', ItemTags.IRON_TOOL_MATERIALS)
            .define('S', Items.STICK)
            .unlockedBy("has_iron_ingot", has(ItemTags.IRON_TOOL_MATERIALS))
            .save(output);

        shaped(RecipeCategory.TOOLS, ModItems.GOLDEN_SCYTHE)
            .pattern("MMM")
            .pattern(" S ")
            .pattern("S  ")
            .define('M', ItemTags.GOLD_TOOL_MATERIALS)
            .define('S', Items.STICK)
            .unlockedBy("has_gold_ingot", has(ItemTags.GOLD_TOOL_MATERIALS))
            .save(output);

        shaped(RecipeCategory.TOOLS, ModItems.DIAMOND_SCYTHE)
            .pattern("MMM")
            .pattern(" S ")
            .pattern("S  ")
            .define('M', ItemTags.DIAMOND_TOOL_MATERIALS)
            .define('S', Items.STICK)
            .unlockedBy("has_diamond", has(ItemTags.DIAMOND_TOOL_MATERIALS))
            .save(output);

        netheriteSmithing(ModItems.DIAMOND_SCYTHE, RecipeCategory.TOOLS, ModItems.NETHERITE_SCYTHE);

        shaped(RecipeCategory.TOOLS, ModItems.CRAFTING_PAD)
            .pattern("  C")
            .pattern(" S ")
            .pattern("S  ")
            .define('S', Items.STICK)
            .define('C', Items.CRAFTING_TABLE)
            .unlockedBy(getHasName(Items.CRAFTING_TABLE), has(Items.CRAFTING_TABLE))
            .save(output);

        shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.REINFORCED_STONE, 3)
            .pattern("ISI")
            .pattern("CSC")
            .pattern("ISI")
            .define('I', ConventionalItemTags.IRON_INGOTS)
            .define('C', Items.IRON_CHAIN)
            .define('S', Items.STONE)
            .unlockedBy(getHasName(Items.STONE), has(Items.STONE))
            .save(output);

        shaped(RecipeCategory.COMBAT, ModItems.REINFORCED_STONE_SWORD)
            .pattern("M")
            .pattern("M")
            .pattern("S")
            .define('M', ModItems.REINFORCED_STONE)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(ModItems.REINFORCED_STONE), has(ModItems.REINFORCED_STONE))
            .save(output);

        shaped(RecipeCategory.TOOLS, ModItems.REINFORCED_STONE_SHOVEL)
            .pattern("M")
            .pattern("S")
            .pattern("S")
            .define('M', ModItems.REINFORCED_STONE)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(ModItems.REINFORCED_STONE), has(ModItems.REINFORCED_STONE))
            .save(output);

        shaped(RecipeCategory.TOOLS, ModItems.REINFORCED_STONE_PICKAXE)
            .pattern("MMM")
            .pattern(" S ")
            .pattern(" S ")
            .define('M', ModItems.REINFORCED_STONE)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(ModItems.REINFORCED_STONE), has(ModItems.REINFORCED_STONE))
            .save(output);

        shaped(RecipeCategory.TOOLS, ModItems.REINFORCED_STONE_AXE)
            .pattern("MM")
            .pattern("MS")
            .pattern(" S")
            .define('M', ModItems.REINFORCED_STONE)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(ModItems.REINFORCED_STONE), has(ModItems.REINFORCED_STONE))
            .save(output);

        shaped(RecipeCategory.TOOLS, ModItems.REINFORCED_STONE_HOE)
            .pattern("MM")
            .pattern(" S")
            .pattern(" S")
            .define('M', ModItems.REINFORCED_STONE)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(ModItems.REINFORCED_STONE), has(ModItems.REINFORCED_STONE))
            .save(output);

        shaped(RecipeCategory.COMBAT, ModItems.REINFORCED_STONE_SPEAR)
            .pattern("  M")
            .pattern(" S ")
            .pattern("S  ")
            .define('M', ModItems.REINFORCED_STONE)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(ModItems.REINFORCED_STONE), has(ModItems.REINFORCED_STONE))
            .save(output);

        shaped(RecipeCategory.TOOLS, ModItems.REINFORCED_STONE_SCYTHE)
            .pattern("MMM")
            .pattern(" S ")
            .pattern("S  ")
            .define('M', ModItems.REINFORCED_STONE)
            .define('S', Items.STICK)
            .unlockedBy(getHasName(ModItems.REINFORCED_STONE), has(ModItems.REINFORCED_STONE))
            .save(output);
    }
}
