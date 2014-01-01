package net.hypsiz.zentaxpvp.listeners;

import net.hypsiz.zentaxpvp.Main;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignListener implements Listener {

	private Main plugin;
	
	public SignListener(Main instance) {
		plugin = instance;
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onSignChange(SignChangeEvent event) {
		Player player = event.getPlayer();
		
		if ((player.isOp()) || (player.hasPermission("zentaxpvp.sign.change"))) {
			if (event.getLine(0).equalsIgnoreCase("[main]")) {
				event.setLine(0, plugin.titleWarp);
				event.setLine(1, ChatColor.GREEN + "Main");
			} if (event.getLine(0).equalsIgnoreCase("[1v1]")) {
				event.setLine(0, plugin.titleWarp);
				event.setLine(1, ChatColor.GREEN + "1V1");
			} if (event.getLine(0).equalsIgnoreCase("[1v1j]")) {
				event.setLine(0, plugin.title1v1);
				event.setLine(1, ChatColor.GREEN + "Join");
			} if (event.getLine(0).equalsIgnoreCase("[1v1l]")) {
				event.setLine(0, plugin.title1v1);
				event.setLine(1, ChatColor.GREEN + "Leave");
			} if (event.getLine(0).equalsIgnoreCase("[1v1js]")) {
				event.setLine(0, plugin.title1v1S);
				event.setLine(1, ChatColor.GREEN + "Join");
			} if (event.getLine(0).equalsIgnoreCase("[1v1ls]")) {
				event.setLine(0, plugin.title1v1S);
				event.setLine(1, ChatColor.GREEN + "Leave");
			} if (event.getLine(0).equalsIgnoreCase("[welzp]")) {
				event.setLine(0, ChatColor.WHITE + "Welcome to");
				event.setLine(1, plugin.titleZentaxP);
				event.setLine(2, ChatColor.GRAY + "[" + ChatColor.BLACK + "KitPvP" + ChatColor.GRAY + "]");
			} if (event.getLine(0).equalsIgnoreCase("[refill]")) {
				event.setLine(0, "");
				event.setLine(1, plugin.titleRefill);
			} if (event.getLine(0).equalsIgnoreCase("[repair]")) {
				event.setLine(0, "");
				event.setLine(1, plugin.titleRepair);
			} if (event.getLine(0).equalsIgnoreCase("[warrior]")) {
				event.setLine(0, plugin.titleKit);
				event.setLine(1, ChatColor.RED + "Warrior");
			} if (event.getLine(0).equalsIgnoreCase("[archer]")) {
				event.setLine(0, plugin.titleKit);
				event.setLine(1, ChatColor.GREEN + "Archer");
			} if (event.getLine(0).equalsIgnoreCase("[chicken]")) {
				event.setLine(0, plugin.titleKit);
				event.setLine(1, ChatColor.GOLD + "Chicken");
			} if (event.getLine(0).equalsIgnoreCase("[fisherman]")) {
				event.setLine(0, plugin.titleKit);
				event.setLine(1, ChatColor.BLUE + "Fisherman");
			} if (event.getLine(0).equalsIgnoreCase("[loki]")) {
				event.setLine(0, plugin.titleKit);
				event.setLine(1, ChatColor.DARK_GREEN + "Loki");
			} if (event.getLine(0).equalsIgnoreCase("[pyro]")) {
				event.setLine(0, plugin.titleKit);
				event.setLine(1, ChatColor.DARK_RED + "Pyro");
			} if (event.getLine(0).equalsIgnoreCase("[flash]")) {
				event.setLine(0, plugin.titleKit);
				event.setLine(1, ChatColor.RED + "Flash");
			} if (event.getLine(0).equalsIgnoreCase("[spiderm]")) {
				event.setLine(0, plugin.titleKit);
				event.setLine(1, ChatColor.RED + "Spider-Man");
			}
		}
	}
}
