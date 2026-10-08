package rosie.green.rosewood.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import org.jspecify.annotations.NullMarked;
import rosie.green.rosewood.registration.ModIds;

import java.util.concurrent.CompletableFuture;

@NullMarked
public class BlockTagGenerator extends FabricTagsProvider.BlockTagsProvider {
    public BlockTagGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        builder(BlockTags.MINEABLE_WITH_PICKAXE).add(ModIds.REINFORCED_STONE);
        builder(BlockTags.NEEDS_STONE_TOOL).add(ModIds.REINFORCED_STONE);
    }
}
