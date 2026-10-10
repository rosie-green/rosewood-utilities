package rosie.green.rosewood.content;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.Nullable;

public record WoodenBucketFamily<Value>(
    Value empty,
    Value water,
    @Nullable Value lava
) {
    public static WoodenBucketFamily<ResourceKey<Item>> create(ResourceKey<Item> empty, boolean lavaResistant) {
        if (lavaResistant) {
            return new WoodenBucketFamily<>(
                empty,
                empty.dependent(empty.registryKey(), path -> path.replace("_bucket", "_water_bucket")),
                empty.dependent(empty.registryKey(), path -> path.replace("_bucket", "_lava_bucket"))
            );
        } else {
            //noinspection NullableProblems
            return new WoodenBucketFamily<>(
                empty,
                empty.dependent(empty.registryKey(), path -> path.replace("_bucket", "_water_bucket")),
                null
            );
        }
    }

    public Value last() {
        return lava != null ? lava : water;
    }
}
