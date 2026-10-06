package rosie.green.rosewood.registration;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import rosie.green.rosewood.Rosewood;

import java.util.function.Function;

public class ModBlocks {
    private ModBlocks() {
        throw new IllegalStateException("Cannot instantiate an utility class.");
    }

    private static ResourceKey<Block> createKey(String name) {
        return ResourceKey.create(Registries.BLOCK, Rosewood.id(name));
    }

    private static <T extends Block> T register(
        String name,
        Function<Block.Properties, T> constructor,
        Block.Properties properties
    ) {
        var key = createKey(name);

        return Registry.register(
            BuiltInRegistries.BLOCK,
            key,
            constructor.apply(properties.setId(key))
        );
    }

    public static final Block REINFORCED_STONE = register(
        "reinforced_stone",
        RotatedPillarBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).mapColor(MapColor.STONE).sound(SoundType.STONE)
    );

    @SuppressWarnings("unused")
    public static void init() {

    }
}
