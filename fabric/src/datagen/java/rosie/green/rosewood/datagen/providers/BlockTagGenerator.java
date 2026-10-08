package rosie.green.rosewood.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NullMarked;
import rosie.green.rosewood.datagen.helpers.TagBuilder;
import rosie.green.rosewood.registration.ModBlocks;

import java.util.concurrent.CompletableFuture;

@NullMarked
public class BlockTagGenerator extends FabricTagsProvider.BlockTagsProvider {
    public BlockTagGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.REINFORCED_STONE);
        tag(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.REINFORCED_STONE);
    }

    @Override
    protected TagBuilder<Block> tag(TagKey<Block> tag) {
        return new TagBuilder<>(super.tag(tag), this::getBlockName);
    }

    @Override
    protected TagBuilder<Block> tag(TagKey<Block> tag, boolean replace) {
        return new TagBuilder<>(super.tag(tag, replace), this::getBlockName);
    }

    private ResourceKey<Block> getBlockName(Block block) {
        return block.builtInRegistryHolder().key();
    }
}
