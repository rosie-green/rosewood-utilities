package rosie.green.rosewood.items;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class CraftingPadMenu extends CraftingMenu {
    public CraftingPadMenu(int containerId, Inventory inventory, ContainerLevelAccess access) {
        super(containerId, inventory, access);
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
