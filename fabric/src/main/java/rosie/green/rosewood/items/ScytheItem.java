package rosie.green.rosewood.items;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.CropBlock;
import org.jspecify.annotations.Nullable;

public class ScytheItem extends Item {
    private final int range;

    public ScytheItem(Properties properties, int range) {
        super(properties);
        this.range = range;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return super.useOn(context);
    }

    private void dropItems(ServerLevel level, BlockPos pos, Object2IntMap<Item> drops) {
    }

    private Object2IntMap<Item> harvestCrops(
        ServerLevel level,
        @Nullable ServerPlayer player,
        ItemStack handStack,
        EquipmentSlot handSlot,
        CropBlock crop,
        BlockPos pos,
        int maxBlocks
    ) {
        return new Object2IntOpenHashMap<>();
    }
}
