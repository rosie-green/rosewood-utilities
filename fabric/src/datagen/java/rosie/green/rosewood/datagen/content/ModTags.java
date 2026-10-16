package rosie.green.rosewood.datagen.content;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.NullMarked;
import rosie.green.rosewood.content.ModIds;

@NullMarked
public final class ModTags {
    private ModTags() {
        throw new IllegalStateException("Cannot instantiate a utility class.");
    }

    private static TagKey<Item> item(String name) {
        return TagKey.create(Registries.ITEM, ModIds.id(name));
    }

    public static final TagKey<Item> SCYTHES = item("scythes");

    public static final TagKey<Item> OAK_BARK = item("oak_bark");
    public static final TagKey<Item> SPRUCE_BARK = item("spruce_bark");
    public static final TagKey<Item> BIRCH_BARK = item("birch_bark");
    public static final TagKey<Item> JUNGLE_BARK = item("jungle_bark");
    public static final TagKey<Item> ACACIA_BARK = item("acacia_bark");
    public static final TagKey<Item> DARK_OAK_BARK = item("dark_oak_bark");
    public static final TagKey<Item> MANGROVE_BARK = item("mangrove_bark");
    public static final TagKey<Item> CHERRY_BARK = item("cherry_bark");
    public static final TagKey<Item> PALE_OAK_BARK = item("pale_oak_bark");
    public static final TagKey<Item> POPLAR_BARK = item("poplar_bark");
    public static final TagKey<Item> CRIMSON_BARK = item("crimson_bark");
    public static final TagKey<Item> WARPED_BARK = item("warped_bark");
}
