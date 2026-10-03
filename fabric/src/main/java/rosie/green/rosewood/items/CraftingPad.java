package rosie.green.rosewood.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class CraftingPad extends Item {
    public CraftingPad(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            player.openMenu(new SimpleMenuProvider(this::createMenu, this.getName(player.getItemInHand(hand))));
        }

        return InteractionResult.SUCCESS_SERVER;
    }

    private CraftingMenu createMenu(int containerId, Inventory inventory, final Player player) {
        return new CraftingMenu(containerId, inventory);
    }
}
