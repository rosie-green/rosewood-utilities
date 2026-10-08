package rosie.green.rosewood.gametest;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.Registry;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.LavaFluid;
import net.minecraft.world.level.material.WaterFluid;
import rosie.green.rosewood.items.WoodenBucketFamily;
import rosie.green.rosewood.registration.ModItems;

import java.util.List;

public class WoodenBucketTests implements ModInitializer {
    private static final BlockPos TARGET = new BlockPos(2, 1, 2);
    private static final BlockPos DISPENSER = TARGET.north();
    private static final Fluid TAGGED_WATER = Registry.register(BuiltInRegistries.FLUID,
        Identifier.fromNamespaceAndPath("rosewood_utilities_test", "tagged_water"), new WaterFluid.Source());
    private static final Fluid TAGGED_LAVA = Registry.register(BuiltInRegistries.FLUID,
        Identifier.fromNamespaceAndPath("rosewood_utilities_test", "tagged_lava"), new LavaFluid.Source());

    @Override
    public void onInitialize() {}

    @GameTest
    public void everyFamilyPreservesWaterBucket(GameTestHelper helper) {
        Player player = playerAboveTarget(helper, GameType.SURVIVAL);
        for (var family : ModItems.WOODEN_BUCKETS) {
            roundTrip(helper, player, family.empty(), family.water(), Blocks.WATER.defaultBlockState());
        }
        helper.succeed();
    }

    @GameTest
    public void netherFamiliesPreserveLavaBucket(GameTestHelper helper) {
        Player player = playerAboveTarget(helper, GameType.SURVIVAL);
        for (var family : ModItems.WOODEN_BUCKETS) {
            if (family.lava() != null) {
                roundTrip(helper, player, family.empty(), family.lava(), Blocks.LAVA.defaultBlockState());
            }
        }
        helper.succeed();
    }

    @GameTest
    public void stackedPickupDropsOverflowWithoutLosingBuckets(GameTestHelper helper) {
        Player player = playerAboveTarget(helper, GameType.SURVIVAL);
        fillInventory(player);
        var family = oak();
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(family.empty(), 16));
        helper.setBlock(TARGET, Blocks.WATER);
        useBucket(helper, player);
        helper.assertBlockPresent(Blocks.AIR, TARGET);
        helper.assertTrue(player.getMainHandItem().is(family.empty())
            && player.getMainHandItem().getCount() == 15, "Pickup must consume exactly one empty bucket");
        assertDropped(helper, family.water(), 1);
        helper.succeed();
    }

    @GameTest
    public void creativePickupAndPlacementDoNotConsumeBuckets(GameTestHelper helper) {
        Player player = playerAboveTarget(helper, GameType.CREATIVE);
        var family = oak();
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(family.empty(), 16));
        helper.setBlock(TARGET, Blocks.WATER);
        useBucket(helper, player);
        helper.assertTrue(player.getMainHandItem().is(family.empty())
            && player.getMainHandItem().getCount() == 16, "Creative pickup must retain the empty stack");
        helper.assertTrue(player.getInventory().contains(new ItemStack(family.water())),
            "Creative pickup must provide a filled variant");
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(family.water()));
        useBucket(helper, player);
        helper.assertTrue(player.getMainHandItem().is(family.water()), "Creative placement must retain water bucket");
        helper.assertBlockPresent(Blocks.WATER, TARGET);
        helper.succeed();
    }

    @GameTest
    public void overworldWoodRejectsLavaWithoutRemovingIt(GameTestHelper helper) {
        Player player = playerAboveTarget(helper, GameType.SURVIVAL);
        BlockSource source = dispenser(helper);
        for (var family : ModItems.WOODEN_BUCKETS) {
            if (family.lava() != null) continue;
            helper.setBlock(TARGET, Blocks.LAVA);
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(family.empty()));
            InteractionResult result = family.empty().use(helper.getLevel(), player, InteractionHand.MAIN_HAND);
            helper.assertFalse(result.consumesAction(), "Overworld bucket must reject lava");
            helper.assertBlockPresent(Blocks.LAVA, TARGET);
            helper.assertTrue(player.getMainHandItem().is(family.empty()), "Rejected pickup must retain bucket");
            ItemStack dispensed = dispense(source, new ItemStack(family.empty()));
            helper.assertTrue(dispensed.isEmpty(), "Unsupported dispenser pickup must eject bucket");
            helper.assertBlockPresent(Blocks.LAVA, TARGET);
        }
        helper.succeed();
    }

    @GameTest
    public void flowingAndTaggedCustomFluidsAreRejected(GameTestHelper helper) {
        helper.assertTrue(TAGGED_WATER.defaultFluidState().is(FluidTags.WATER), "Test water tag must be loaded");
        helper.assertTrue(TAGGED_LAVA.defaultFluidState().is(FluidTags.LAVA), "Test lava tag must be loaded");
        for (var family : ModItems.WOODEN_BUCKETS) {
            for (Fluid fluid : new Fluid[]{Fluids.FLOWING_WATER, Fluids.FLOWING_LAVA, TAGGED_WATER, TAGGED_LAVA}) {
                helper.assertTrue(family.empty().getPickupResult(fluid.defaultFluidState()) == null,
                    "Flowing and custom tagged fluids must not become vanilla bucket contents");
            }
        }
        helper.succeed();
    }

    @GameTest
    public void waterloggingRoundTripPreservesBucketAndBlock(GameTestHelper helper) {
        Player player = playerAboveTarget(helper, GameType.SURVIVAL);
        var family = oak();
        helper.setBlock(TARGET, Blocks.OAK_SLAB);
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(family.water()));
        useBucket(helper, player);
        helper.assertTrue(helper.getBlockState(TARGET).getValue(BlockStateProperties.WATERLOGGED),
            "Water bucket must waterlog slab");
        helper.assertTrue(player.getMainHandItem().is(family.empty()), "Waterlogging must return wooden bucket");
        useBucket(helper, player);
        helper.assertBlockPresent(Blocks.OAK_SLAB, TARGET);
        helper.assertFalse(helper.getBlockState(TARGET).getValue(BlockStateProperties.WATERLOGGED),
            "Pickup must drain slab without removing it");
        helper.assertTrue(player.getMainHandItem().is(family.water()), "Pickup must retain wood type");
        helper.succeed();
    }

    @GameTest(dimension = "minecraft:the_nether")
    public void netherEvaporationReturnsMatchingEmptyBuckets(GameTestHelper helper) {
        Player player = playerAboveTarget(helper, GameType.SURVIVAL);
        for (var family : ModItems.WOODEN_BUCKETS) {
            helper.setBlock(TARGET, Blocks.AIR);
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(family.water()));
            useBucket(helper, player);
            helper.assertBlockPresent(Blocks.AIR, TARGET);
            helper.assertTrue(player.getMainHandItem().is(family.empty()), "Evaporation must preserve wood type");
        }
        helper.succeed();
    }

    @GameTest
    public void dispensersPreserveEveryFilledVariant(GameTestHelper helper) {
        BlockSource source = dispenser(helper);
        for (var family : ModItems.WOODEN_BUCKETS) {
            for (var filled : family.items()) {
                if (filled == family.empty()) continue;
                helper.setBlock(TARGET, Blocks.AIR);
                ItemStack empty = dispense(source, new ItemStack(filled));
                helper.assertTrue(empty.is(family.empty()), "Dispensing must return matching empty bucket");
                helper.assertTrue(helper.getBlockState(TARGET).getFluidState().getType() == filled.getContent(),
                    "Dispenser must place correct fluid");
                ItemStack refilled = dispense(source, empty);
                helper.assertTrue(refilled.is(filled), "Dispenser pickup must preserve wood and fluid type");
                helper.assertBlockPresent(Blocks.AIR, TARGET);
            }
        }
        helper.succeed();
    }

    @GameTest
    public void dispenserDropsOverflowWithoutLosingBuckets(GameTestHelper helper) {
        BlockSource source = dispenser(helper);
        for (int slot = 0; slot < source.blockEntity().getContainerSize(); slot++) {
            source.blockEntity().setItem(slot, new ItemStack(Items.STONE, 64));
        }
        var family = oak();
        source.blockEntity().setItem(0, new ItemStack(family.empty(), 16));
        helper.setBlock(TARGET, Blocks.WATER);
        ItemStack result = dispense(source, source.blockEntity().getItem(0));
        source.blockEntity().setItem(0, result);
        helper.assertTrue(result.is(family.empty()) && result.getCount() == 15,
            "Dispenser must consume exactly one empty bucket");
        helper.assertBlockPresent(Blocks.AIR, TARGET);
        assertDropped(helper, family.water(), 1);
        helper.succeed();
    }

    @GameTest
    public void blockedDispenserPlacementEjectsFilledBucket(GameTestHelper helper) {
        BlockSource source = dispenser(helper);
        helper.setBlock(TARGET, Blocks.STONE);
        ItemStack result = dispense(source, new ItemStack(oak().water()));
        helper.assertTrue(result.isEmpty(), "Blocked placement must eject the filled bucket");
        helper.assertBlockPresent(Blocks.STONE, TARGET);
        assertDropped(helper, oak().water(), 1);
        helper.succeed();
    }

    @GameTest
    public void cauldronsPreserveEveryFilledVariant(GameTestHelper helper) {
        Player player = playerAboveTarget(helper, GameType.SURVIVAL);
        for (var family : ModItems.WOODEN_BUCKETS) {
            for (var filled : family.items()) {
                if (filled == family.empty()) continue;
                helper.setBlock(TARGET, Blocks.CAULDRON);
                player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(filled));
                helper.useBlock(TARGET, player);
                helper.assertBlockPresent(filled.getContent() == Fluids.WATER ? Blocks.WATER_CAULDRON : Blocks.LAVA_CAULDRON, TARGET);
                helper.assertTrue(player.getMainHandItem().is(family.empty()), "Cauldron filling must return matching bucket");
                helper.useBlock(TARGET, player);
                helper.assertBlockPresent(Blocks.CAULDRON, TARGET);
                helper.assertTrue(player.getMainHandItem().is(filled), "Cauldron pickup must preserve wood and fluid type");
            }
        }
        helper.succeed();
    }

    @GameTest
    public void partialAndUnsupportedCauldronsRemainIntact(GameTestHelper helper) {
        Player player = playerAboveTarget(helper, GameType.SURVIVAL);
        for (var family : ModItems.WOODEN_BUCKETS) {
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(family.empty()));
            BlockState partial = Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 2);
            helper.setBlock(TARGET, partial);
            helper.useBlock(TARGET, player);
            helper.assertTrue(helper.getBlockState(TARGET).equals(partial), "Partial cauldron cannot fill a bucket");
            helper.assertTrue(player.getMainHandItem().is(family.empty()), "Rejected pickup must retain bucket");
            if (family.lava() == null) {
                helper.setBlock(TARGET, Blocks.LAVA_CAULDRON);
                helper.useBlock(TARGET, player);
                helper.assertBlockPresent(Blocks.LAVA_CAULDRON, TARGET);
                helper.assertTrue(player.getMainHandItem().is(family.empty()), "Overworld bucket cannot collect cauldron lava");
            }
            helper.setBlock(TARGET, Blocks.POWDER_SNOW_CAULDRON);
            helper.useBlock(TARGET, player);
            helper.assertBlockPresent(Blocks.POWDER_SNOW_CAULDRON, TARGET);
            helper.assertTrue(player.getMainHandItem().is(family.empty()), "Powder snow pickup must remain unsupported");
        }
        helper.succeed();
    }

    @GameTest
    public void submergedCauldronDoesNotConsumeLavaBucket(GameTestHelper helper) {
        Player player = playerAboveTarget(helper, GameType.SURVIVAL);
        helper.setBlock(TARGET, Blocks.CAULDRON);
        helper.setBlock(TARGET.above(), Blocks.WATER);
        for (var family : ModItems.WOODEN_BUCKETS) {
            if (family.lava() == null) continue;
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(family.lava()));
            helper.useBlock(TARGET, player);
            helper.assertBlockPresent(Blocks.CAULDRON, TARGET);
            helper.assertTrue(player.getMainHandItem().is(family.lava()), "Submerged cauldron must retain lava bucket");
        }
        helper.succeed();
    }

    @GameTest(maxTicks = 40)
    public void netherLavaBucketsSmeltAndReturnExtractableContainers(GameTestHelper helper) {
        // Advance real furnace logic synchronously to avoid waiting 200 world ticks.
        int x = 2;
        for (var family : ModItems.WOODEN_BUCKETS) {
            if (family.lava() == null) continue;
            BlockPos pos = new BlockPos(x, 1, 2);
            helper.setBlock(pos, Blocks.FURNACE);
            FurnaceBlockEntity furnace = helper.getBlockEntity(pos, FurnaceBlockEntity.class);
            helper.assertTrue(family.lava().components().get(DataComponents.COOKING_FUEL)
                .equals(Items.LAVA_BUCKET.components().get(DataComponents.COOKING_FUEL)),
                "Nether lava bucket must use vanilla lava fuel duration");
            furnace.setItem(0, new ItemStack(Items.COBBLESTONE));
            furnace.setItem(1, new ItemStack(family.lava()));
            for (int tick = 0; tick < 200; tick++) {
                AbstractFurnaceBlockEntity.serverTick(helper.getLevel(), helper.absolutePos(pos),
                    helper.getBlockState(pos), furnace);
            }
            helper.assertTrue(furnace.getItem(2).is(Items.STONE), "Lava bucket must actually smelt an item");
            helper.assertTrue(furnace.getItem(1).is(family.empty()), "Smelting must return matching empty bucket");
            helper.assertTrue(furnace.canTakeItemThroughFace(1, furnace.getItem(1), Direction.DOWN),
                "Hopper must be allowed to retrieve the returned container");
            furnace.setItem(2, ItemStack.EMPTY);
            helper.setBlock(pos.below(), Blocks.HOPPER);
            x += 2;
        }
        helper.runAfterDelay(10, () -> {
            int hopperX = 2;
            for (var family : ModItems.WOODEN_BUCKETS) {
                if (family.lava() == null) continue;
                BlockPos pos = new BlockPos(hopperX, 1, 2);
                FurnaceBlockEntity furnace = helper.getBlockEntity(pos, FurnaceBlockEntity.class);
                HopperBlockEntity hopper = helper.getBlockEntity(pos.below(), HopperBlockEntity.class);
                helper.assertTrue(furnace.getItem(1).isEmpty(), "Hopper must remove the returned bucket");
                helper.assertTrue(hopper.getItem(0).is(family.empty()), "Hopper must receive matching wood type");
                hopperX += 2;
            }
            helper.succeed();
        });
    }

    @GameTest
    public void taggedRecipesAcceptBucketsAndPreserveRemainders(GameTestHelper helper) {
        for (var family : ModItems.WOODEN_BUCKETS) {
            helper.assertTrue(new ItemStack(family.empty()).is(ConventionalItemTags.EMPTY_BUCKETS),
                "Empty buckets must have the conventional ingredient tag");
            for (var filled : family.items()) {
                helper.assertTrue(new ItemStack(filled).is(ConventionalItemTags.BUCKETS),
                    "All bucket variants must have the conventional bucket tag");
                if (filled == family.empty()) continue;
                CraftingInput input = CraftingInput.of(2, 1, List.of(new ItemStack(filled), new ItemStack(Items.DIRT)));
                var recipe = helper.getLevel().recipeAccess().getRecipeFor(RecipeType.CRAFTING, input, helper.getLevel());
                helper.assertTrue(recipe.isPresent(), "Shared tag recipe must accept each filled variant");
                Item expected = filled.getContent() == Fluids.WATER ? Items.MUD : Items.OBSIDIAN;
                helper.assertTrue(recipe.orElseThrow().value().assemble(input).is(expected), "Tag recipe must produce expected output");
                var remainders = recipe.orElseThrow().value().getRemainingItems(input);
                helper.assertTrue(remainders.getFirst().is(family.empty()) && remainders.getFirst().getCount() == 1,
                    "Crafting must return exactly one matching empty bucket");
            }
        }
        helper.succeed();
    }

    @GameTest
    public void fluidTransfersPreserveFamilyAndCustomNames(GameTestHelper helper) {
        Player player = playerAboveTarget(helper, GameType.SURVIVAL);
        for (var family : ModItems.WOODEN_BUCKETS) {
            for (var filled : family.items()) {
                if (filled == family.empty()) continue;
                ItemStack empty = new ItemStack(family.empty());
                empty.set(DataComponents.CUSTOM_NAME, Component.literal("My bucket"));
                player.setItemInHand(InteractionHand.MAIN_HAND, empty);
                ContainerItemContext context = ContainerItemContext.ofPlayerHand(player, InteractionHand.MAIN_HAND);
                FluidVariant fluid = FluidVariant.of(filled.getContent());
                try (Transaction transaction = Transaction.openOuter()) {
                    helper.assertTrue(storage(context).insert(fluid, FluidConstants.BUCKET, transaction) == FluidConstants.BUCKET,
                        "Transfer must insert one full bucket");
                    transaction.commit();
                }
                helper.assertTrue(player.getMainHandItem().is(filled), "Insertion must preserve wood and fluid type");
                try (Transaction transaction = Transaction.openOuter()) {
                    helper.assertTrue(storage(context).extract(fluid, FluidConstants.BUCKET, transaction) == FluidConstants.BUCKET,
                        "Transfer must extract one full bucket");
                    transaction.commit();
                }
                helper.assertTrue(player.getMainHandItem().is(family.empty()), "Extraction must preserve wood type");
                helper.assertTrue(Component.literal("My bucket").equals(player.getMainHandItem().get(DataComponents.CUSTOM_NAME)),
                    "Fluid transfers must preserve custom name");
            }
        }
        helper.succeed();
    }

    @GameTest
    public void fluidTransferRollbackAndPartialAmountsDoNotConsumeBuckets(GameTestHelper helper) {
        Player player = playerAboveTarget(helper, GameType.SURVIVAL);
        var family = oak();
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(family.empty()));
        ContainerItemContext context = ContainerItemContext.ofPlayerHand(player, InteractionHand.MAIN_HAND);
        FluidVariant water = FluidVariant.of(Fluids.WATER);
        try (Transaction transaction = Transaction.openOuter()) {
            helper.assertTrue(storage(context).insert(water, FluidConstants.BUCKET - 1, transaction) == 0,
                "Partial insertion must be rejected");
            helper.assertTrue(storage(context).insert(water, FluidConstants.BUCKET, transaction) == FluidConstants.BUCKET,
                "Whole insertion must be possible before rollback");
        }
        helper.assertTrue(player.getMainHandItem().is(family.empty()), "Aborted insertion must restore empty bucket");
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(family.water()));
        try (Transaction transaction = Transaction.openOuter()) {
            helper.assertTrue(storage(context).extract(water, FluidConstants.BUCKET - 1, transaction) == 0,
                "Partial extraction must be rejected");
            helper.assertTrue(storage(context).extract(water, FluidConstants.BUCKET, transaction) == FluidConstants.BUCKET,
                "Whole extraction must be possible before rollback");
        }
        helper.assertTrue(player.getMainHandItem().is(family.water()), "Aborted extraction must restore filled bucket");
        helper.succeed();
    }

    @GameTest
    public void fluidTransfersRejectUnsupportedFluids(GameTestHelper helper) {
        Player player = playerAboveTarget(helper, GameType.SURVIVAL);
        for (var family : ModItems.WOODEN_BUCKETS) {
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(family.empty()));
            ContainerItemContext context = ContainerItemContext.ofPlayerHand(player, InteractionHand.MAIN_HAND);
            try (Transaction transaction = Transaction.openOuter()) {
                for (Fluid fluid : new Fluid[]{TAGGED_WATER, TAGGED_LAVA}) {
                    helper.assertTrue(storage(context).insert(FluidVariant.of(fluid), FluidConstants.BUCKET, transaction) == 0,
                        "Transfer must reject custom fluids");
                }
                if (family.lava() == null) {
                    helper.assertTrue(storage(context).insert(FluidVariant.of(Fluids.LAVA), FluidConstants.BUCKET, transaction) == 0,
                        "Overworld bucket must reject lava through transfer API");
                }
                transaction.commit();
            }
            helper.assertTrue(player.getMainHandItem().is(family.empty()), "Rejected transfers must retain empty bucket");
        }
        helper.succeed();
    }

    @GameTest
    public void stackedFluidTransfersRespectContainerOverflowPolicy(GameTestHelper helper) {
        Player player = playerAboveTarget(helper, GameType.SURVIVAL);
        var family = oak();
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(family.empty(), 16));
        ContainerItemContext context = ContainerItemContext.ofPlayerHand(player, InteractionHand.MAIN_HAND);
        try (Transaction transaction = Transaction.openOuter()) {
            helper.assertTrue(storage(context).insert(FluidVariant.of(Fluids.WATER), FluidConstants.BUCKET, transaction) == FluidConstants.BUCKET,
                "Stacked transfer must use inventory space");
            transaction.commit();
        }
        helper.assertTrue(player.getMainHandItem().getCount() == 15 && player.getMainHandItem().is(family.empty()),
            "Stacked transfer must consume exactly one empty bucket");
        helper.assertTrue(player.getInventory().contains(new ItemStack(family.water())), "Filled bucket must enter inventory");
        fillInventory(player);
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(family.empty(), 16));
        ContainerItemContext isolatedSlot = ContainerItemContext.ofSingleSlot(context.getMainSlot());
        try (Transaction transaction = Transaction.openOuter()) {
            helper.assertTrue(storage(isolatedSlot).insert(FluidVariant.of(Fluids.WATER), FluidConstants.BUCKET, transaction) == 0,
                "Inventory-only context without space must reject conversion");
            transaction.commit();
        }
        helper.assertTrue(player.getMainHandItem().is(family.empty()) && player.getMainHandItem().getCount() == 16,
            "Failed inventory conversion must not lose empty buckets");
        try (Transaction transaction = Transaction.openOuter()) {
            helper.assertTrue(storage(context).insert(FluidVariant.of(Fluids.WATER), FluidConstants.BUCKET, transaction) == FluidConstants.BUCKET,
                "Player context must allow overflow drops");
        }
        helper.assertTrue(player.getMainHandItem().getCount() == 16, "Aborted transfer must restore stacked buckets");
        assertDropped(helper, family.water(), 0);
        try (Transaction transaction = Transaction.openOuter()) {
            helper.assertTrue(storage(context).insert(FluidVariant.of(Fluids.WATER), FluidConstants.BUCKET, transaction) == FluidConstants.BUCKET,
                "Committed player transfer must allow overflow drops");
            transaction.commit();
        }
        helper.assertTrue(player.getMainHandItem().getCount() == 15, "Committed transfer must consume exactly one bucket");
        assertDropped(helper, family.water(), 1);
        helper.succeed();
    }

    private static Storage<FluidVariant> storage(ContainerItemContext context) {
        var storage = context.find(FluidStorage.ITEM);
        if (storage == null) throw new AssertionError("Bucket fluid storage must be registered");
        return storage;
    }

    private static WoodenBucketFamily oak() {
        return ModItems.WOODEN_BUCKETS.getFirst();
    }

    private static Player playerAboveTarget(GameTestHelper helper, GameType mode) {
        Player player = helper.makeMockPlayer(mode);
        BlockPos target = helper.absolutePos(TARGET);
        helper.setBlock(TARGET.below(), Blocks.STONE);
        player.snapTo(target.getX() + 0.5, target.getY() + 2, target.getZ() + 0.5, 0, 90);
        mode.updatePlayerAbilities(player.getAbilities());
        return player;
    }

    private static void useBucket(GameTestHelper helper, Player player) {
        InteractionResult result = player.getMainHandItem().getItem().use(helper.getLevel(), player, InteractionHand.MAIN_HAND);
        helper.assertTrue(result instanceof InteractionResult.Success, "Bucket action must succeed");
        if (result instanceof InteractionResult.Success success && success.heldItemTransformedTo() != null) {
            player.setItemInHand(InteractionHand.MAIN_HAND, success.heldItemTransformedTo());
        }
    }

    private static void roundTrip(GameTestHelper helper, Player player, Item empty, Item filled, BlockState fluid) {
        helper.setBlock(TARGET, fluid);
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(empty));
        useBucket(helper, player);
        helper.assertBlockPresent(Blocks.AIR, TARGET);
        helper.assertTrue(player.getMainHandItem().is(filled), "Pickup must return matching filled bucket");
        useBucket(helper, player);
        helper.assertTrue(player.getMainHandItem().is(empty), "Placement must return matching empty bucket");
        helper.assertTrue(helper.getBlockState(TARGET).equals(fluid), "Placement must restore fluid source");
    }

    private static void fillInventory(Player player) {
        for (int slot = 0; slot < player.getInventory().getNonEquipmentItems().size(); slot++) {
            player.getInventory().setItem(slot, new ItemStack(Items.STONE, 64));
        }
        player.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.STONE, 64));
    }

    private static BlockSource dispenser(GameTestHelper helper) {
        helper.setBlock(DISPENSER, Blocks.DISPENSER.defaultBlockState().setValue(DispenserBlock.FACING, Direction.SOUTH));
        return new BlockSource(helper.getLevel(), helper.absolutePos(DISPENSER), helper.getBlockState(DISPENSER),
            helper.getBlockEntity(DISPENSER, DispenserBlockEntity.class));
    }

    private static ItemStack dispense(BlockSource source, ItemStack stack) {
        return DispenserBlock.DISPENSER_REGISTRY.get(stack.getItem()).dispense(source, stack);
    }

    private static void assertDropped(GameTestHelper helper, Item item, int count) {
        int dropped = helper.getLevel().getEntitiesOfClass(ItemEntity.class, helper.getBounds().inflate(1),
            entity -> entity.getItem().is(item)).stream().mapToInt(entity -> entity.getItem().getCount()).sum();
        helper.assertTrue(dropped == count, "Expected " + count + " dropped buckets, found " + dropped);
    }
}
