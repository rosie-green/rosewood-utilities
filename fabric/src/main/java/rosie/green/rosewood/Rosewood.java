package rosie.green.rosewood;

import net.fabricmc.api.ModInitializer;
import rosie.green.rosewood.content.ModBlocks;
import rosie.green.rosewood.content.ModItems;

public class Rosewood implements ModInitializer {
    @Override
    public void onInitialize() {
        ModBlocks.init();
        ModItems.init();
    }

    public static final String MOD_ID = "rosewood_utilities";
}
