package rosie.green.rosewood.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class CraftingPad extends Item {
    public CraftingPad(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        player.openMenu(new SimpleMenuProvider(((id, inv, ply) -> createMenu(id, inv, ply, hand)), this.getName(player.getItemInHand(hand))));

        return InteractionResult.SUCCESS_SERVER;
    }

    private CraftingMenu createMenu(int containerId, Inventory inventory, Player player, InteractionHand hand) {
        return new CraftingPadMenu(containerId, inventory, ContainerLevelAccess.create(player.level(), player.blockPosition()), hand);
    }
}
