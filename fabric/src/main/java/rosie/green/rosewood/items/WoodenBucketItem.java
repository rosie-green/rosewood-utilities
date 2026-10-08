package rosie.green.rosewood.items;

import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jspecify.annotations.Nullable;

/** A reusable wooden bucket; Nether wood variants can also collect lava. */
public class WoodenBucketItem extends BucketItem {
    private final WoodenBucketFamily family;

    public WoodenBucketItem(Fluid content, Properties properties, WoodenBucketFamily family) {
        super(content, properties);
        this.family = family;
    }

    public WoodenBucketItem getEmptyBucket() {
        return family.empty();
    }

    /** Resolve the filled variant before pickup so unsupported fluids remain intact. */
    public @Nullable WoodenBucketItem getPickupResult(FluidState fluid) {
        if (getContent() != Fluids.EMPTY || !fluid.isSource()) {
            return null;
        }
        if (fluid.getType() == Fluids.WATER) {
            return family.water();
        }
        if (fluid.getType() == Fluids.LAVA) {
            return family.lava();
        }
        return null;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (getContent() != Fluids.EMPTY) {
            // Reuse vanilla fluid placement, waterlogging, evaporation, sounds and permissions.
            InteractionResult result = super.use(level, player, hand);
            if (result instanceof InteractionResult.Success success
                && success.heldItemTransformedTo() != null
                && success.heldItemTransformedTo().is(Items.BUCKET)) {
                return success.heldItemTransformedTo(new ItemStack(getEmptyBucket()));
            }
            return result;
        }

        ItemStack stack = player.getItemInHand(hand);
        BlockHitResult hit = getPlayerPOVHitResult(level, player, getFluidContext());
        if (hit.getType() != HitResult.Type.BLOCK) {
            return InteractionResult.PASS;
        }

        BlockPos pos = hit.getBlockPos();
        if (!level.mayInteract(player, pos)
            || !player.mayUseItemAt(pos.relative(hit.getDirection()), hit.getDirection(), stack)) {
            return InteractionResult.FAIL;
        }

        BlockState state = level.getBlockState(pos);
        WoodenBucketItem filledBucket = getPickupResult(state.getFluidState());
        if (filledBucket == null || !(state.getBlock() instanceof BucketPickup pickup)) {
            return InteractionResult.FAIL;
        }

        ItemStack taken = pickup.pickupBlock(player, level, pos, state);
        if (taken.isEmpty()) {
            return InteractionResult.FAIL;
        }

        ItemStack filled = new ItemStack(filledBucket);
        player.awardStat(Stats.ITEM_USED.get(this));
        pickup.getPickupSound().ifPresent(sound -> player.playSound(sound, 1.0F, 1.0F));
        level.gameEvent(player, GameEvent.FLUID_PICKUP, pos);
        ItemStack result = ItemUtils.createFilledResult(stack, player, filled);
        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.FILLED_BUCKET.trigger(serverPlayer, filled);
        }
        return InteractionResult.SUCCESS.heldItemTransformedTo(result);
    }
}
