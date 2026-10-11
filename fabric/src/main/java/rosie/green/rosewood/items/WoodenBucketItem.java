package rosie.green.rosewood.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import rosie.green.rosewood.content.WoodenBucketFamily;

import java.util.function.Supplier;

public class WoodenBucketItem extends Item {
    private final Fluid contents;
    private final Supplier<WoodenBucketFamily<Item>> variants;

    public WoodenBucketItem(Properties properties, Fluid contents, Supplier<WoodenBucketFamily<Item>> variants) {
        super(properties);

        this.contents = contents;
        this.variants = variants;
    }
}
