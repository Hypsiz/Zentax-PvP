/**
 * 
 */
package net.hypsiz.zentaxpvp.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;

/**
 * @author Hai
 *
 */
public class HangingListener implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void onHangingPlace(HangingPlaceEvent event) {
		Player player = event.getPlayer();
		
		if (player.getGameMode() != GameMode.CREATIVE) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onHangingBreakByEntity(HangingBreakByEntityEvent event) {
		if ((event.getRemover() instanceof Player) || (event.getRemover() instanceof Arrow) || (event.getRemover() instanceof Projectile)) {
			Player player = (Player) event.getRemover();
			
			if (player.getGameMode() != GameMode.CREATIVE) {
				if (event.getEntity().getType() == EntityType.ITEM_FRAME) {
					event.setCancelled(true);
				}
				
				if (event.getEntity().getType() == EntityType.PAINTING) {
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onHangingBreak(HangingBreakEvent event) {
		Player player = (Player) event.getEntity();
		
		if (player.getGameMode() != GameMode.CREATIVE) {
			if (event.getEntity().getType() == EntityType.ITEM_FRAME) {
				event.setCancelled(true);
			}
		}
	}
}
