package rosie.green.rosewood.content;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.Nullable;

public record WoodenBucketFamily<T>(
    T empty,
    T water,
    @Nullable T lava
) {
    @SuppressWarnings("NullableProblems")
    public static WoodenBucketFamily<ResourceKey<Item>> create(ResourceKey<Item> empty, boolean lavaResistant) {
        return new WoodenBucketFamily<>(
            empty,
            empty.dependent(empty.registryKey(), path -> path.replace("_bucket", "_water_bucket")),
            lavaResistant ? empty.dependent(empty.registryKey(), path -> path.replace("_bucket", "_lava_bucket")) : null
        );
    }

    public T last() {
        return lava != null ? lava : water;
    }
}
