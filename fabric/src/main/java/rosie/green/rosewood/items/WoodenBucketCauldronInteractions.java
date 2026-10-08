package rosie.green.rosewood.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.cauldron.CauldronInteractions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.List;

public final class WoodenBucketCauldronInteractions {
    private WoodenBucketCauldronInteractions() {}

    public static void register(List<WoodenBucketFamily> families) {
        for (var family : families) {
            CauldronInteractions.WATER.put(family.empty(), (state, level, pos, player, hand, stack) ->
                CauldronInteractions.fillBucket(state, level, pos, player, hand, stack,
                    new ItemStack(family.water()), s -> s.getValue(LayeredCauldronBlock.LEVEL) == 3,
                    SoundEvents.BUCKET_FILL));

            WoodenBucketItem lava = family.lava();
            if (lava != null) {
                CauldronInteractions.LAVA.put(family.empty(), (state, level, pos, player, hand, stack) ->
                    CauldronInteractions.fillBucket(state, level, pos, player, hand, stack,
                        new ItemStack(lava), s -> true, SoundEvents.BUCKET_FILL_LAVA));
            }

            // Vanilla filled buckets can replace any cauldron contents.
            for (CauldronInteraction.Dispatcher dispatcher : List.of(CauldronInteractions.EMPTY,
                CauldronInteractions.WATER, CauldronInteractions.LAVA, CauldronInteractions.POWDER_SNOW)) {
                dispatcher.put(family.water(), (state, level, pos, player, hand, stack) ->
                    emptyBucket(level, pos, player, hand, stack, family.empty(),
                        Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3),
                        SoundEvents.BUCKET_EMPTY));
                if (lava != null) {
                    dispatcher.put(lava, (state, level, pos, player, hand, stack) -> {
                        if (level.getFluidState(pos.above()).is(FluidTags.WATER)) {
                            return InteractionResult.CONSUME;
                        }
                        return emptyBucket(level, pos, player, hand, stack, family.empty(),
                            Blocks.LAVA_CAULDRON.defaultBlockState(), SoundEvents.BUCKET_EMPTY_LAVA);
                    });
                }
            }
        }
    }

    private static InteractionResult emptyBucket(Level level, BlockPos pos, Player player, InteractionHand hand,
                                                  ItemStack stack, WoodenBucketItem empty,
                                                  BlockState newState, SoundEvent sound) {
        if (!level.isClientSide()) {
            var usedItem = stack.getItem();
            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(empty)));
            player.awardStat(Stats.FILL_CAULDRON);
            player.awardStat(Stats.ITEM_USED.get(usedItem));
            level.setBlockAndUpdate(pos, newState);
            level.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
        }
        return InteractionResult.SUCCESS;
    }
}
