package rosie.green.rosewood.items;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

public class ScytheItem extends Item {
    private final int range;

    public ScytheItem(Properties properties, int range) {
        super(properties);
        this.range = range;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockState state = level.getBlockState(context.getClickedPos());
        Block block = state.getBlock();

        if (block instanceof CropBlock crop && crop.isMaxAge(state)) {
            if (!(level instanceof ServerLevel serverLevel)) {
                return InteractionResult.CONSUME;
            }

            ItemStack handStack = context.getItemInHand();
            ServerPlayer player = context.getPlayer() instanceof ServerPlayer serverPlayer ? serverPlayer : null;

            BlockPos dropPos = (player != null ? player.getOnPos() : context.getClickedPos()).above();

            Object2IntMap<Item> drops = harvestCrops(
                serverLevel,
                player,
                handStack,
                context.getHand().asEquipmentSlot(),
                crop,
                context.getClickedPos(),
                Math.min(handStack.getMaxDamage() - handStack.getDamageValue(), range)
            );

            dropItems(serverLevel, dropPos, drops);

            return InteractionResult.SUCCESS_SERVER;
        }

        return super.useOn(context);
    }

    private void dropItems(ServerLevel level, BlockPos pos, Object2IntMap<Item> drops) {
        for (var entry : drops.object2IntEntrySet()) {
            Item item = entry.getKey();
            int count = entry.getIntValue();

            if (item.builtInRegistryHolder().is(ItemTags.VILLAGER_PLANTABLE_SEEDS)) {
                count = Math.max(1, (int) (0.75 * count));
            }

            while (count > 0) {
                int dropAmount = Math.min(count, 64);

                Block.popResource(level, pos, new ItemStack(item, dropAmount));

                count -= dropAmount;
            }
        }
    }

    private static void findNeighbouringCrops(ServerLevel level, BlockState expectedState, BlockPos pos, Consumer<BlockPos> consumer) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos neighbourPos = pos.relative(direction);

            if (level.getBlockState(neighbourPos) == expectedState) {
                consumer.accept(neighbourPos);
            }
        }
    }

    private static BlockPos.TraversalNodeStatus harvestCrop(
        ServerLevel level,
        BlockState newState,
        @Nullable ServerPlayer player,
        ItemStack handStack,
        Object2IntOpenHashMap<Item> drops,
        BlockPos pos
    ) {
        BlockState state = level.getBlockState(pos);
        BlockEntity entity = level.getBlockEntity(pos);

        if (level.setBlock(pos, newState, Block.UPDATE_ALL)) {
            for (ItemStack stack : Block.getDrops(state, level, pos, entity, player, handStack)) {
                drops.mergeInt(stack.getItem(), stack.getCount(), Integer::sum);
            }

            state.spawnAfterBreak(level, pos, handStack, true, player);
        }

        return BlockPos.TraversalNodeStatus.ACCEPT;
    }

    private Object2IntMap<Item> harvestCrops(
        ServerLevel level,
        @Nullable ServerPlayer player,
        ItemStack handStack,
        EquipmentSlot handSlot,
        CropBlock crop,
        BlockPos pos,
        int maxBlocks
    ) {
        var drops = new Object2IntOpenHashMap<Item>();
        BlockState harvestState = crop.getStateForAge(crop.getMaxAge());

        var blocksHarvested = BlockPos.breadthFirstTraversal(
            pos, Integer.MAX_VALUE, maxBlocks,
            (currentPos, consumer) -> ScytheItem.findNeighbouringCrops(level, harvestState, currentPos, consumer),
            (currentPos) -> ScytheItem.harvestCrop(level, crop.getStateForAge(0), player, handStack, drops, currentPos)
        );

        if (player != null) {
            player.awardStat(Stats.BLOCK_MINED.get(crop), blocksHarvested);
        }

        if (player == null || !player.hasInfiniteMaterials()) {
            handStack.hurtAndBreak(blocksHarvested, level, player, stack -> {
                if (player != null) {
                    player.onEquippedItemBroken(stack, handSlot);
                }
            });
        }

        return drops;
    }
}
