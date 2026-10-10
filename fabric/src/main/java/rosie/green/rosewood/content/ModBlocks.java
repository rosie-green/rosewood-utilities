package rosie.green.rosewood.content;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.references.BlockItemId;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Function;

public class ModBlocks {
    private ModBlocks() {
        throw new IllegalStateException("Cannot instantiate an utility class.");
    }

    private static <T extends Block> T register(
        BlockItemId id,
        Function<Block.Properties, T> constructor,
        Block.Properties properties
    ) {
        return Registry.register(
            BuiltInRegistries.BLOCK,
            id.block(),
            constructor.apply(properties.setId(id.block()))
        );
    }

    public static final Block REINFORCED_STONE = register(
        ModIds.REINFORCED_STONE,
        RotatedPillarBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).mapColor(MapColor.STONE).sound(SoundType.STONE)
    );

    public static void init() {

    }
}
