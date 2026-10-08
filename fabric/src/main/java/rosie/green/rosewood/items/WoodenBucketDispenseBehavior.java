package rosie.green.rosewood.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;

public class WoodenBucketDispenseBehavior extends DefaultDispenseItemBehavior {
    @Override
    protected ItemStack execute(BlockSource source, ItemStack stack) {
        BlockPos target = source.pos().relative(source.state().getValue(DispenserBlock.FACING));
        var level = source.level();
        WoodenBucketItem bucket = (WoodenBucketItem) stack.getItem();
        if (bucket.getContent() != Fluids.EMPTY) {
            if (bucket.emptyContents(null, level, target, null)) {
                return consumeWithRemainder(source, stack, new ItemStack(bucket.getEmptyBucket()));
            }
        } else {
            BlockState state = level.getBlockState(target);
            WoodenBucketItem filledBucket = bucket.getPickupResult(state.getFluidState());
            if (filledBucket != null
                && state.getBlock() instanceof BucketPickup pickup
                && !pickup.pickupBlock(null, level, target, state).isEmpty()) {
                level.gameEvent(null, GameEvent.FLUID_PICKUP, target);
                return consumeWithRemainder(source, stack, new ItemStack(filledBucket));
            }
        }
        return super.execute(source, stack);
    }
}
