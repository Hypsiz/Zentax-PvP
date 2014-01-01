package net.hypsiz.zentaxpvp.util;

import java.util.Arrays;

import net.hypsiz.zentaxpvp.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.Plugin;

public class ShopMenu implements Listener {

	private Inventory inv;
    private ItemStack c;
    private Main plugin;
    
    public ShopMenu(Plugin p, Main instance) {
    	inv = Bukkit.getServer().createInventory(null, 18, ChatColor.GOLD + "" + ChatColor.BOLD + "Shop " + ChatColor.RESET + "" + ChatColor.WHITE + "-" + ChatColor.GREEN + "" + ChatColor.BOLD + " Kits");
    	
    	c = Chicken(ChatColor.GOLD + "Chicken");
    	
    	inv.setItem(0, c);
    	
    	plugin = instance;
    	
    	Bukkit.getServer().getPluginManager().registerEvents(this, p);
    }
    
    private ItemStack Chicken(String name) {
    	ItemStack egg = new ItemStack(Material.EGG);
    	ItemMeta eggMeta = egg.getItemMeta();
    	eggMeta.setDisplayName(name);
    	eggMeta.setLore(Arrays.asList(ChatColor.GRAY + "Chicken" + ChatColor.DARK_GRAY + " | " + ChatColor.YELLOW + "300 coins"));
    	egg.setItemMeta(eggMeta);
    	
    	return egg;
    }
    
    public void show(Player p) {
    	p.openInventory(inv);
    }
    
    @SuppressWarnings("deprecation")
	public boolean shopOpen(Player player) {
		int coins = plugin.getConfig().getInt("Players." + player.getName() + ".Stats.Coins");
		
		ItemStack light_gray_stained_glass_pane = new ItemStack(160, 1, (byte) 8);
		ItemMeta light_gray_stained_glass_paneMeta = light_gray_stained_glass_pane.getItemMeta();
		light_gray_stained_glass_paneMeta.setDisplayName(ChatColor.AQUA + "Soon!");
		light_gray_stained_glass_pane.setItemMeta(light_gray_stained_glass_paneMeta);
		
		ItemStack gold_ingot = new ItemStack(Material.GOLD_INGOT);
		ItemMeta gold_ingotMeta = gold_ingot.getItemMeta();
		gold_ingotMeta.setDisplayName(ChatColor.BLUE + "Your currently coins: " + ChatColor.DARK_GREEN + coins);
		gold_ingot.setItemMeta(gold_ingotMeta);
		
		ItemStack egg = new ItemStack(Material.EGG);
    	ItemMeta eggMeta = egg.getItemMeta();
    	eggMeta.setDisplayName(ChatColor.GOLD + "Chicken");
    	
    	ItemStack fishing_rod = new ItemStack(Material.FISHING_ROD);
    	ItemMeta fishing_rodMeta = fishing_rod.getItemMeta();
    	fishing_rodMeta.setDisplayName(ChatColor.BLUE + "Fisherman");
    	
    	ItemStack blaze_powder = new ItemStack(Material.BLAZE_POWDER);
    	ItemMeta blaze_powderMeta = blaze_powder.getItemMeta();
    	blaze_powderMeta.setDisplayName(ChatColor.DARK_RED + "Pyro");
    	
    	ItemStack leather_boots = new ItemStack(Material.LEATHER_BOOTS);
    	LeatherArmorMeta leather_bootsArmorMeta = (LeatherArmorMeta) leather_boots.getItemMeta();
    	leather_bootsArmorMeta.setDisplayName(ChatColor.RED + "Flash");
    	
    	if (plugin.getConfig().getBoolean("Players." + player.getName() + ".Kits.Chicken") == true) {
    		eggMeta.setLore(Arrays.asList(ChatColor.DARK_RED + "You own this kit!"));
    	} else {
    		eggMeta.setLore(Arrays.asList(ChatColor.GRAY + "Chicken" + ChatColor.DARK_GRAY + " | " + ChatColor.YELLOW + "300 coins"));
    	}
    	
    	if (plugin.getConfig().getBoolean("Players." + player.getName() + ".Kits.Fisherman") == true) {
    		fishing_rodMeta.setLore(Arrays.asList(ChatColor.DARK_RED + "You own this kit!"));
    	} else {
    		fishing_rodMeta.setLore(Arrays.asList(ChatColor.GRAY + "Fisherman" + ChatColor.DARK_GRAY + " | " + ChatColor.YELLOW + "340 coins"));
    	}
    	
    	if (plugin.getConfig().getBoolean("Players." + player.getName() + ".Kits.Pyro") == true) {
    		blaze_powderMeta.setLore(Arrays.asList(ChatColor.DARK_RED + "You own this kit!"));
    	} else {
    		blaze_powderMeta.setLore(Arrays.asList(ChatColor.GRAY + "Pyro" + ChatColor.DARK_GRAY + " | " + ChatColor.YELLOW + "400 coins"));
    	}
    	
    	if (plugin.getConfig().getBoolean("Players." + player.getName() + ".Kits.Flash") == true) {
    		leather_bootsArmorMeta.setLore(Arrays.asList(ChatColor.DARK_RED + "You own this kit!"));
    		leather_bootsArmorMeta.setColor(Color.YELLOW);
    	} else {
    		leather_bootsArmorMeta.setLore(Arrays.asList(ChatColor.GRAY + "Flash" + ChatColor.DARK_GRAY + " | " + ChatColor.YELLOW + "350 coins"));
    		leather_bootsArmorMeta.setColor(Color.YELLOW);
    	}
    	
    	egg.setItemMeta(eggMeta);
    	fishing_rod.setItemMeta(fishing_rodMeta);
    	blaze_powder.setItemMeta(blaze_powderMeta);
    	leather_boots.setItemMeta(leather_bootsArmorMeta);
		
		inv = Bukkit.createInventory(player, 18, ChatColor.GOLD + "" + ChatColor.BOLD + "Shop " + ChatColor.RESET + "" + ChatColor.WHITE + "-" + ChatColor.GREEN + "" + ChatColor.BOLD + " Kits");
		
		inv.clear();
		inv.setItem(0, egg);
		inv.setItem(1, fishing_rod);
		inv.setItem(2, blaze_powder);
		inv.setItem(3, leather_boots);
		inv.setItem(4, light_gray_stained_glass_pane);
		inv.setItem(5, light_gray_stained_glass_pane);
		inv.setItem(6, light_gray_stained_glass_pane);
		inv.setItem(7, light_gray_stained_glass_pane);
		inv.setItem(8, gold_ingot);
		inv.setItem(9, light_gray_stained_glass_pane);
		inv.setItem(10, light_gray_stained_glass_pane);
		inv.setItem(11, light_gray_stained_glass_pane);
		inv.setItem(12, light_gray_stained_glass_pane);
		inv.setItem(13, light_gray_stained_glass_pane);
		inv.setItem(14, light_gray_stained_glass_pane);
		inv.setItem(15, light_gray_stained_glass_pane);
		inv.setItem(16, light_gray_stained_glass_pane);
		inv.setItem(17, light_gray_stained_glass_pane);
		
		player.openInventory(inv);
		
		return false;
	}
    
    @SuppressWarnings("deprecation")
	@EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
    	Player player = (Player) event.getWhoClicked();
    	int coins = plugin.getConfig().getInt("Players." + player.getName() + ".Stats.Coins");
    	
    	if (!event.getInventory().getName().equalsIgnoreCase(inv.getName()))
    		return;
    	if (event.getCurrentItem().getItemMeta() == null)
    		return;
    	
    	if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Chicken")) {
    		event.setCancelled(true);
    		
    		boolean chicken = plugin.getConfig().getBoolean("Players." + player.getName() + ".Kits.Chicken");
    		
    		if (chicken == false) {
    			if (coins >= 300) {
    				plugin.getConfig().set("Players." + player.getName() + ".Stats.Coins", coins - 300);
    				
    				player.sendMessage(plugin.titleZ + ChatColor.GREEN + "Buying this kit has been successful!");
    				
    				plugin.getConfig().set("Players." + player.getName() + ".Kits.Chicken", Boolean.valueOf(true));
    				
    				plugin.saveConfig();
    				
    				player.closeInventory();
    			} else {
    				player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You don't have enough coins to purchase this kit.");
    				
    				player.closeInventory();
    			}
    		} else {
    			player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You have already purchased this kit.");
    		}
    	} if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Fisherman")) {
    		event.setCancelled(true);
    		
    		boolean fisherman = plugin.getConfig().getBoolean("Players." + player.getName() + ".Kits.Fisherman");
    		
    		if (fisherman == false) {
    			if (coins >= 300) {
    				plugin.getConfig().set("Players." + player.getName() + ".Stats.Coins", coins - 300);
    				
    				player.sendMessage(plugin.titleZ + ChatColor.GREEN + "Buying this kit has been successful!");
    				
    				plugin.getConfig().set("Players." + player.getName() + ".Kits.Fisherman", Boolean.valueOf(true));
    				
    				plugin.saveConfig();
    				
    				player.closeInventory();
    			} else {
    				player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You don't have enough coins to purchase this kit.");
    				
    				player.closeInventory();
    			}
    		} else {
    			player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You have already purchased this kit.");
    		}
    	} if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Pyro")) {
    		event.setCancelled(true);
    		
    		boolean pyro = plugin.getConfig().getBoolean("Players." + player.getName() + ".Kits.Pyro");
    		
    		if (pyro == false) {
    			if (coins >= 400) {
    				plugin.getConfig().set("Players." + player.getName() + ".Stats.Coins", coins - 400);
    				
    				player.sendMessage(plugin.titleZ + ChatColor.GREEN + "Buying this kit has been successful!");
    				
    				plugin.getConfig().set("Players." + player.getName() + ".Kits.Pyro", Boolean.valueOf(true));
    				
    				plugin.saveConfig();
    				
    				player.closeInventory();
    			} else {
    				player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You don't have enough coins to purchase this kit.");
    				
    				player.closeInventory();
    			}
    		} else {
    			player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You have already purchased this kit.");
    		}
    	} if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Flash")) {
    		event.setCancelled(true);
    		
    		boolean flash = plugin.getConfig().getBoolean("Players." + player.getName() + ".Kits.Flash");
    		
    		if (flash == false) {
    			if (coins >= 350) {
    				plugin.getConfig().set("Players." + player.getName() + ".Stats.Coins", coins - 350);
    				
    				player.sendMessage(plugin.titleZ + ChatColor.GREEN + "Buying this kit has been successful!");
    				
    				plugin.getConfig().set("Players." + player.getName() + ".Kits.Flash", Boolean.valueOf(true));
    				
    				plugin.saveConfig();
    				
    				player.closeInventory();
    			} else {
    				player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You don't have enough coins to purchase this kit.");
    				
    				player.closeInventory();
    			}
    		} else {
    			player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You have already purchased this kit.");
    		}
    	}
    	
    	event.setCancelled(true);
    	
    	player.updateInventory();
    }
}
