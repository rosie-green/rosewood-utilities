package rosie.green.rosewood.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.jspecify.annotations.NullMarked;
import rosie.green.rosewood.Rosewood;
import rosie.green.rosewood.datagen.providers.*;

@NullMarked
public final class RosewoodDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(ModelGenerator::new);
        pack.addProvider(RecipeGeneratorRunner::new);
        var blockTagGenerator = pack.addProvider(BlockTagGenerator::new);
        pack.addProvider((output, registries) -> new ItemTagGenerator(output, registries, blockTagGenerator));
        pack.addProvider(BlockLootGenerator::new);
    }

    @Override
    public String getEffectiveModId() {
        return Rosewood.MOD_ID;
    }
}
