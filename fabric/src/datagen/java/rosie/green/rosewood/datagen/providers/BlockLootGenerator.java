package rosie.green.rosewood.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;
import rosie.green.rosewood.registration.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class BlockLootGenerator extends FabricBlockLootSubProvider {
    public BlockLootGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    public void generate() {
        dropSelf(ModBlocks.REINFORCED_STONE);
    }
}
