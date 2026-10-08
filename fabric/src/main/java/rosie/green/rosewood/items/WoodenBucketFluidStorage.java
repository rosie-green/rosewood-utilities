package rosie.green.rosewood.items;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.EmptyItemFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.FullItemFluidStorage;
import net.minecraft.world.level.material.Fluids;

import java.util.List;

/** Transactional, whole-bucket transfers that preserve the wood family. */
public final class WoodenBucketFluidStorage {
    private WoodenBucketFluidStorage() {}

    public static void register(List<WoodenBucketFamily> families) {
        for (var family : families) {
            for (var filled : family.items()) {
                if (filled.getContent() == Fluids.EMPTY) continue;
                FluidStorage.combinedItemApiProvider(family.empty()).register(context ->
                    new EmptyItemFluidStorage(context, filled, filled.getContent(), FluidConstants.BUCKET));
                FluidStorage.combinedItemApiProvider(filled).register(context ->
                    new FullItemFluidStorage(context, family.empty(), FluidVariant.of(filled.getContent()),
                        FluidConstants.BUCKET));
            }
        }
    }
}
