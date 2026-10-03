package rosie.green.rosewood;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;
import rosie.green.rosewood.registration.ModItems;

public class Rosewood implements ModInitializer {
    @Override
    public void onInitialize() {
        ModItems.init();
    }

    public static final String MOD_ID = "rosewood_utilities";

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
