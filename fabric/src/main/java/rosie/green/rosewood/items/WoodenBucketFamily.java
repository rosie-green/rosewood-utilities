package rosie.green.rosewood.items;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import org.jspecify.annotations.Nullable;
import rosie.green.rosewood.registration.ModIds;

import java.util.List;

/** Owns registration and the empty/filled items for one wood family. */
public final class WoodenBucketFamily {
    private final TagKey<Item> logs;
    private final WoodenBucketItem empty;
    private final WoodenBucketItem water;
    private final @Nullable WoodenBucketItem lava;
    private final List<WoodenBucketItem> items;

    public WoodenBucketFamily(String wood, TagKey<Item> logs) {
        this(wood, logs, false);
    }

    public WoodenBucketFamily(String wood, TagKey<Item> logs, boolean supportsLava) {
        this.logs = logs;
        empty = register(wood + "_bucket", Fluids.EMPTY, properties(supportsLava).stacksTo(16));
        water = register(wood + "_water_bucket", Fluids.WATER,
            properties(supportsLava).stacksTo(1).craftRemainder(empty));
        lava = supportsLava ? register(wood + "_lava_bucket", Fluids.LAVA,
            properties(true).stacksTo(1).craftRemainder(empty)
                .cookingFuel(ContextIntProviders.COOKING_TIME_LAVA_BUCKET)) : null;
        items = lava == null ? List.of(empty, water) : List.of(empty, water, lava);
    }

    private static Item.Properties properties(boolean fireResistant) {
        Item.Properties properties = new Item.Properties();
        return fireResistant ? properties.fireResistant() : properties;
    }

    private WoodenBucketItem register(String name, Fluid content, Item.Properties properties) {
        ResourceKey<Item> key = ModIds.item(name);
        return Registry.register(BuiltInRegistries.ITEM, key,
            new WoodenBucketItem(content, properties.setId(key), this));
    }

    public TagKey<Item> logs() {
        return logs;
    }

    public WoodenBucketItem empty() {
        return empty;
    }

    public WoodenBucketItem water() {
        return water;
    }

    public @Nullable WoodenBucketItem lava() {
        return lava;
    }

    public List<WoodenBucketItem> items() {
        return items;
    }
}
