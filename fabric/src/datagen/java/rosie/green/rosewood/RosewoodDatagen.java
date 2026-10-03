package rosie.green.rosewood;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.jspecify.annotations.NullMarked;
import rosie.green.rosewood.providers.ItemTagGenerator;
import rosie.green.rosewood.providers.ModelGenerator;
import rosie.green.rosewood.providers.RecipeGeneratorRunner;

@NullMarked
public class RosewoodDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(ModelGenerator::new);
        pack.addProvider(RecipeGeneratorRunner::new);
        pack.addProvider(ItemTagGenerator::new);
    }

    @Override
    public String getEffectiveModId() {
        return Rosewood.MOD_ID;
    }
}
