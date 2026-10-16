package rosie.green.rosewood.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import rosie.green.rosewood.content.ModItems;

public class CraftingPadMenu extends CraftingMenu {
    private final InteractionHand hand;

    public CraftingPadMenu(int containerId, Inventory inventory, ContainerLevelAccess access, InteractionHand hand) {
        super(containerId, inventory, access);
        this.hand = hand;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.getItemInHand(hand).is(ModItems.CRAFTING_PAD);
    }
}
