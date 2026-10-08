package rosie.green.rosewood.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NullMarked;
import rosie.green.rosewood.registration.ModBlocks;

import java.util.concurrent.CompletableFuture;

@NullMarked
public class BlockTagGenerator extends FabricTagsProvider.BlockTagsProvider {
    public BlockTagGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        var mineableWithPickaxe = tag(BlockTags.MINEABLE_WITH_PICKAXE);
        var needsStoneTool = tag(BlockTags.NEEDS_STONE_TOOL);

        add(mineableWithPickaxe, ModBlocks.REINFORCED_STONE);
        add(needsStoneTool, ModBlocks.REINFORCED_STONE);
    }

    private void add(TagAppender<Block> tag, Block block) {
        tag.add(block.builtInRegistryHolder().key());
    }
}
