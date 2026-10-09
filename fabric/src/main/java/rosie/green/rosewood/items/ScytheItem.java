package rosie.green.rosewood.items;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
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
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
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

        if (!(block instanceof CropBlock crop) || !crop.isMaxAge(state)) {
            return super.useOn(context);
        }

        if (!(level instanceof ServerLevel serverLevel)) {
            return InteractionResult.SUCCESS;
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

    private static void dropItems(ServerLevel level, BlockPos pos, Object2IntMap<Item> drops) {
        for (var entry : drops.object2IntEntrySet()) {
            var template = new ItemStack(entry.getKey());
            int maxStackSize = template.getMaxStackSize();
            int count = entry.getIntValue();

            if (template.is(ConventionalItemTags.SEEDS)) {
                count = Math.max(1, 3 * count / 4);
            }

            while (count > 0) {
                int dropAmount = Math.min(count, maxStackSize);

                Block.popResource(level, pos, template.copyWithCount(dropAmount));

                count -= dropAmount;
            }
        }
    }

    private static void findNeighbouringCrops(ServerLevel level, BlockState matureState, BlockPos pos, Consumer<BlockPos> consumer) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos neighbourPos = pos.relative(direction);

            if (level.getBlockState(neighbourPos) == matureState) {
                consumer.accept(neighbourPos);
            }
        }
    }

    private static BlockPos.TraversalNodeStatus harvestCrop(
        ServerLevel level,
        BlockState replantedState,
        @Nullable ServerPlayer player,
        ItemStack handStack,
        Object2IntMap<Item> drops,
        BlockPos pos
    ) {
        BlockState state = level.getBlockState(pos);
        BlockEntity entity = level.getBlockEntity(pos);

        if (!level.setBlock(pos, replantedState, Block.UPDATE_ALL)) {
            return BlockPos.TraversalNodeStatus.SKIP;
        }

        for (ItemStack stack : Block.getDrops(state, level, pos, entity, player, handStack)) {
            drops.mergeInt(stack.getItem(), stack.getCount(), Integer::sum);
        }

        state.spawnAfterBreak(level, pos, handStack, true, player);
        level.gameEvent(GameEvent.BLOCK_CHANGE, Vec3.atCenterOf(pos), GameEvent.Context.of(player, replantedState));

        return BlockPos.TraversalNodeStatus.ACCEPT;
    }

    private static Object2IntMap<Item> harvestCrops(
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

        int blocksHarvested = BlockPos.breadthFirstTraversal(
            pos, Integer.MAX_VALUE, maxBlocks,
            (currentPos, consumer) -> ScytheItem.findNeighbouringCrops(level, harvestState, currentPos, consumer),
            (currentPos) -> ScytheItem.harvestCrop(level, crop.getStateForAge(0), player, handStack, drops, currentPos)
        );

        if (player != null) {
            player.awardStat(Stats.BLOCK_MINED.get(crop), blocksHarvested);
        }

        handStack.hurtAndBreak(blocksHarvested, level, player, stack -> {
            if (player != null) {
                player.onEquippedItemBroken(stack, handSlot);
            }
        });

        return drops;
    }
}
