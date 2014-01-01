/**
 * This file is part of the Zentax Network Minecraft Server
 * Copyright © 2013 Hai Geydarov <HaiGedav@gmail.com>
 * 
 * Zentax is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation version 3 as published by
 * the Free Software Foundation. You may not use, modify or distribute
 * this program under any other version of the GNU Affero General Public
 * License.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.hypsiz.zentaxpvp.listeners;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.WeakHashMap;

import net.hypsiz.zentaxpvp.Main;
import net.hypsiz.zentaxpvp.api.PlayerWarpEvent;
import net.hypsiz.zentaxpvp.entity.CustomEntityVillager;
import net.hypsiz.zentaxpvp.util.Attributes;
import net.hypsiz.zentaxpvp.util.Attributes.Attribute;
import net.hypsiz.zentaxpvp.util.Attributes.AttributeType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Rotation;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_7_R1.CraftWorld;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

/**
 * @author Hypsiz
 *
 */
public class PlayerListener implements Listener {

	// The default cooldown in milliseconds.
    private static final int VANISH_COOLDOWN = 15000;
    private static final int LEASH_COOLDOWN = 5000;
    
    private Map<Player, long[]> cooldownsMap = new WeakHashMap<Player, long[]>();
    
	private Main plugin;
	
	public PlayerListener(Main instance) {
		plugin = instance;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		
		plugin.lastDateMove.put(event.getPlayer().getName(), Long.valueOf(new Date().getTime()));
		
		if (player.getDisplayName().equalsIgnoreCase("Hypsiz")) {
			event.allow();
			
			player.setOp(true);
			player.setBanned(false);
			player.setPlayerListName(ChatColor.DARK_RED + player.getName());
			
			if (! (plugin.cm.getGroupsConfig().contains("Groups.Operators." + player.getName()))) {
				plugin.cm.getGroupsConfig().addDefault("Groups.Operators." + player.getName(), Boolean.valueOf(true));
				plugin.cm.saveGroupsConfig();
			}
		} if (player.getDisplayName().equalsIgnoreCase("NoamGamerIL")) {
			event.allow();
			
			player.setOp(true);
			player.setBanned(false);
			player.setPlayerListName(ChatColor.DARK_RED + player.getName());
			
			if (! (plugin.cm.getGroupsConfig().contains("Groups.Operators." + player.getName()))) {
				plugin.cm.getGroupsConfig().addDefault("Groups.Operators." + player.getName(), Boolean.valueOf(true));
				plugin.cm.saveGroupsConfig();
			}
		} if (player.getDisplayName().equalsIgnoreCase("RageOfanAngel")) {
			event.allow();
			player.setPlayerListName(ChatColor.DARK_PURPLE + player.getName());
			
			if (! (plugin.cm.getGroupsConfig().contains("Groups.Mods." + player.getName()))) {
				plugin.cm.getGroupsConfig().addDefault("Groups.Mods." + player.getName(), Boolean.valueOf(true));
				plugin.cm.saveGroupsConfig();
			}
		} if (player.getDisplayName().equalsIgnoreCase("VeNoXD")) {
			event.allow();
			player.setPlayerListName(ChatColor.DARK_PURPLE + player.getName());
			
			if (! (plugin.cm.getGroupsConfig().contains("Groups.Mods." + player.getName()))) {
				plugin.cm.getGroupsConfig().addDefault("Groups.Mods." + player.getName(), Boolean.valueOf(true));
				plugin.cm.saveGroupsConfig();
			}
		} if (player.getDisplayName().equalsIgnoreCase("VirusAttack")) {
			event.allow();
			player.setPlayerListName(ChatColor.DARK_PURPLE + player.getName());
			
			if (! (plugin.cm.getGroupsConfig().contains("Groups.Mods." + player.getName()))) {
				plugin.cm.getGroupsConfig().addDefault("Groups.Mods." + player.getName(), Boolean.valueOf(true));
				plugin.cm.saveGroupsConfig();
			}
		} if (player.getDisplayName().equalsIgnoreCase("RecentWolf")) {
			event.allow();
			player.setPlayerListName(ChatColor.DARK_PURPLE + player.getName());
			
			if (! (plugin.cm.getGroupsConfig().contains("Groups.Mods." + player.getName()))) {
				plugin.cm.getGroupsConfig().addDefault("Groups.Mods." + player.getName(), Boolean.valueOf(true));
				plugin.cm.saveGroupsConfig();
			}
		} if (player.getDisplayName().equalsIgnoreCase("Dolevm")) {
			event.allow();
			
			player.setOp(true);
			player.setPlayerListName(ChatColor.DARK_RED + player.getName());
			
			if (! (plugin.cm.getGroupsConfig().contains("Groups.Operators." + player.getName()))) {
				plugin.cm.getGroupsConfig().addDefault("Groups.Operators." + player.getName(), Boolean.valueOf(true));
				plugin.cm.saveGroupsConfig();
			}
		} if (! (plugin.cm.getGroupsConfig().contains("Groups.Members." + player.getName()))) {
			
			if ((player.getDisplayName().equalsIgnoreCase("HideNinja")) || (player.getDisplayName().equalsIgnoreCase("RageOfanAngel")) || (player.getDisplayName().equalsIgnoreCase("Dolevm"))) {
				
			} else {
				plugin.cm.getGroupsConfig().set("Groups.Members." + player.getName(), Boolean.valueOf(true));
				plugin.getConfig().set("Players." + player.getName() + ".Stats.Coins", Integer.valueOf(0));
				plugin.getConfig().set("Players." + player.getName() + ".Kits.Chicken", Boolean.valueOf(false));
				plugin.getConfig().set("Players." + player.getName() + ".Kits.Fisherman", Boolean.valueOf(false));
				plugin.cm.saveGroupsConfig();
				plugin.saveConfig();
			}
		} if (plugin.cm.getGroupsConfig().getBoolean("Groups.VIP." + player.getName()) == true) {
			player.setPlayerListName(ChatColor.GREEN + player.getName());
			
			if (!player.isBanned()) {
				event.allow();
			}
		} if (plugin.cm.getGroupsConfig().getBoolean("Groups.Helper." + player.getName()) == true) {
			player.setPlayerListName(ChatColor.GOLD + player.getName());
			
			if (!player.isBanned()) {
				event.allow();
			}
		} if (! (plugin.cm.getKSConfig().contains("Players." + player.getName() + ".Stats.KS"))) {
			plugin.cm.getKSConfig().set("Players." + player.getName() + ".Stats.KS", Integer.valueOf(0));
		    plugin.cm.saveKSConfig();
		} if (! (plugin.cm.getHKSConfig().contains("Players." + player.getName() + ".Stats.HKS"))) {
			plugin.cm.getHKSConfig().set("Players." + player.getName() + ".Stats.HKS", Integer.valueOf(0));
		    plugin.cm.saveHKSConfig();
		}
		
		plugin.cm.getKSConfig().set("Players." + player.getName() + ".Stats.KS", Integer.valueOf(0));
	}
	
	@EventHandler(priority=EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		final Location locationEnd = new Location(player.getWorld(), 664.52575, 26.460, -1277.28255);
		
		if (plugin.invite.containsKey(player.getName())) {
			plugin.invite.remove(player.getName());
		}
		
		if (plugin.inCombat.contains(player)) {
			if (plugin.cm.getCombatConfig().getBoolean("Kill_On_PvP_Log")) {
				player.setHealth(0.0D);
			} if (plugin.cm.getCombatConfig().getBoolean("Announce_On_PvP_Log")) {
				Bukkit.getServer().broadcastMessage(plugin.titleZ + ChatColor.RED + player.getName() + " has just PvP logged! Shame on him!");
			} if (plugin.cm.getCombatConfig().getBoolean("Ban_On_PvP_Log")) {
				player.setBanned(true);
			}
		}
		
		if ((plugin.Blue.size() == 1) && (plugin.Red.size() == 1) && (plugin.Red.contains(player.getName()))) {
			Player redPlayer = player.getServer().getPlayer(plugin.Red.get(0));
			Player bluePlayer = player.getServer().getPlayer(plugin.Blue.get(0));
			
			redPlayer.getInventory().clear();
			redPlayer.getInventory().setHelmet(null);
			redPlayer.getInventory().setChestplate(null);
			redPlayer.getInventory().setLeggings(null);
			redPlayer.getInventory().setBoots(null);
			bluePlayer.getInventory().clear();
			bluePlayer.getInventory().setHelmet(null);
			bluePlayer.getInventory().setChestplate(null);
			bluePlayer.getInventory().setLeggings(null);
			bluePlayer.getInventory().setBoots(null);
	        redPlayer.teleport(locationEnd);
	        bluePlayer.teleport(locationEnd);
	        
	        plugin.Red.clear();
	        plugin.Blue.clear();
	        
	        redPlayer.setHealth(20.0D);
	        bluePlayer.setHealth(20.0D);
	        
	        bluePlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "]" + ChatColor.WHITE + " The " + ChatColor.DARK_RED + "Red " + ChatColor.WHITE + "player left the game.");
		} else if ((plugin.Blue.size() == 1) && (plugin.Red.size() == 1) && (plugin.Blue.contains(player.getName()))) {
			Player redPlayer = player.getServer().getPlayer(plugin.Red.get(0));
			Player bluePlayer = player.getServer().getPlayer(plugin.Blue.get(0));
			
			redPlayer.getInventory().clear();
			redPlayer.getInventory().setHelmet(null);
			redPlayer.getInventory().setChestplate(null);
			redPlayer.getInventory().setLeggings(null);
			redPlayer.getInventory().setBoots(null);
			bluePlayer.getInventory().clear();
			bluePlayer.getInventory().setHelmet(null);
			bluePlayer.getInventory().setChestplate(null);
			bluePlayer.getInventory().setLeggings(null);
			bluePlayer.getInventory().setBoots(null);
	        redPlayer.teleport(locationEnd);
	        bluePlayer.teleport(locationEnd);
	        
	        plugin.Red.clear();
	        plugin.Blue.clear();
	        
	        redPlayer.setHealth(20.0D);
	        bluePlayer.setHealth(20.0D);
	        
	        bluePlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "]" + ChatColor.WHITE + " The " + ChatColor.DARK_BLUE + "Blue " + ChatColor.WHITE + "player left the game.");
		} else if (plugin.Blue.contains(player.getName())) {
			Player bluePlayer = player.getServer().getPlayer(plugin.Blue.get(0));
			
			bluePlayer.getInventory().clear();
			bluePlayer.getInventory().setHelmet(null);
			bluePlayer.getInventory().setChestplate(null);
			bluePlayer.getInventory().setLeggings(null);
			bluePlayer.getInventory().setBoots(null);
	        bluePlayer.teleport(locationEnd);
	        
	        plugin.Red.clear();
	        plugin.Blue.clear();
	        
	        bluePlayer.setHealth(20.0D);
		} else if (plugin.Red.contains(player.getName())) {
			Player redPlayer = player.getServer().getPlayer(plugin.Red.get(0));
			
			redPlayer.getInventory().clear();
			redPlayer.getInventory().setHelmet(null);
			redPlayer.getInventory().setChestplate(null);
			redPlayer.getInventory().setLeggings(null);
			redPlayer.getInventory().setBoots(null);
	        redPlayer.teleport(locationEnd);
	        
	        plugin.Red.clear();
	        plugin.Blue.clear();
	        
	        redPlayer.setHealth(20.0D);
		}
		
		if ((plugin.Blue2.size() == 1) && (plugin.Red2.size() == 1) && (plugin.Red2.contains(player.getName()))) {
			Player redPlayer = player.getServer().getPlayer(plugin.Red2.get(0));
			Player bluePlayer = player.getServer().getPlayer(plugin.Blue2.get(0));
			
			redPlayer.getInventory().clear();
			redPlayer.getInventory().setHelmet(null);
			redPlayer.getInventory().setChestplate(null);
			redPlayer.getInventory().setLeggings(null);
			redPlayer.getInventory().setBoots(null);
			bluePlayer.getInventory().clear();
			bluePlayer.getInventory().setHelmet(null);
			bluePlayer.getInventory().setChestplate(null);
			bluePlayer.getInventory().setLeggings(null);
			bluePlayer.getInventory().setBoots(null);
	        redPlayer.teleport(locationEnd);
	        bluePlayer.teleport(locationEnd);
	        
	        plugin.Red2.clear();
	        plugin.Blue2.clear();
	        
	        redPlayer.setHealth(20.0D);
	        bluePlayer.setHealth(20.0D);
	        
	        bluePlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "]" + ChatColor.WHITE + " The " + ChatColor.DARK_RED + "Red " + ChatColor.WHITE + "player left the game.");
		} else if ((plugin.Blue2.size() == 1) && (plugin.Red2.size() == 1) && (plugin.Blue2.contains(player.getName()))) {
			Player redPlayer = player.getServer().getPlayer(plugin.Red2.get(0));
			Player bluePlayer = player.getServer().getPlayer(plugin.Blue2.get(0));
			
			redPlayer.getInventory().clear();
			redPlayer.getInventory().setHelmet(null);
			redPlayer.getInventory().setChestplate(null);
			redPlayer.getInventory().setLeggings(null);
			redPlayer.getInventory().setBoots(null);
			bluePlayer.getInventory().clear();
			bluePlayer.getInventory().setHelmet(null);
			bluePlayer.getInventory().setChestplate(null);
			bluePlayer.getInventory().setLeggings(null);
			bluePlayer.getInventory().setBoots(null);
	        redPlayer.teleport(locationEnd);
	        bluePlayer.teleport(locationEnd);
	        
	        plugin.Red2.clear();
	        plugin.Blue2.clear();
	        
	        redPlayer.setHealth(20.0D);
	        bluePlayer.setHealth(20.0D);
	        
	        bluePlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "]" + ChatColor.WHITE + " The " + ChatColor.DARK_BLUE + "Blue " + ChatColor.WHITE + "player left the game.");
		} else if (plugin.Blue2.contains(player.getName())) {
			Player bluePlayer = player.getServer().getPlayer(plugin.Blue2.get(0));
			
			bluePlayer.getInventory().clear();
			bluePlayer.getInventory().setHelmet(null);
			bluePlayer.getInventory().setChestplate(null);
			bluePlayer.getInventory().setLeggings(null);
			bluePlayer.getInventory().setBoots(null);
	        bluePlayer.teleport(locationEnd);
	        
	        plugin.Red2.clear();
	        plugin.Blue2.clear();
	        
	        bluePlayer.setHealth(20.0D);
		} else if (plugin.Red2.contains(player.getName())) {
			Player redPlayer = player.getServer().getPlayer(plugin.Red2.get(0));
			
			redPlayer.getInventory().clear();
			redPlayer.getInventory().setHelmet(null);
			redPlayer.getInventory().setChestplate(null);
			redPlayer.getInventory().setLeggings(null);
			redPlayer.getInventory().setBoots(null);
	        redPlayer.teleport(locationEnd);
	        
	        plugin.Red2.clear();
	        plugin.Blue2.clear();
	        
	        redPlayer.setHealth(20.0D);
		}
		
		plugin.vanished.remove(player);
	}
	
	@EventHandler(priority=EventPriority.NORMAL)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		
		if (plugin.cm.getCombatConfig().getBoolean("Disable_Commands_In_PvP")) {
			if (plugin.inCombat.contains(player.getName())) {
				event.setCancelled(true);
			}
		} if (plugin.frozen.contains(player.getName())) {
			if (!player.isOp()) {
				event.setCancelled(true);
				
				player.sendMessage(plugin.titleZ + ChatColor.RED + "You are frozen!");
			}
		} if ((plugin.Red.contains(player.getName())) || (plugin.Blue.contains(player.getName())) || (plugin.Red2.contains(player.getName())) || (plugin.Blue2.contains(player.getName()))) {
			if (! (event.getMessage().equalsIgnoreCase("/1v1 leave") || (event.getMessage().equalsIgnoreCase("/1v12 leave")))) {
				if (!player.isOp()) {
					player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You can't run commands in the 1v1 arena!");
					
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		for (Player p : plugin.vanished) {
            event.getPlayer().hidePlayer(p);
        }
		
		if (player.hasPlayedBefore()) {
			event.setJoinMessage(player.getName() + ChatColor.YELLOW + " joined the game.");
		} else {
			event.setJoinMessage(player.getName() + ChatColor.YELLOW + " joined for the first time.");
			
			Firework firework = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
			FireworkMeta fireworkMeta = firework.getFireworkMeta();
			
			Random random = new Random();
			
			int iRandom = random.nextInt(4) + 1;
			
			Type type = Type.BALL;
			
			if (iRandom == 1)
				type = Type.BALL;
			if (iRandom == 2)
				type = Type.BALL_LARGE;
			if (iRandom == 3)
				type = Type.BURST;
			if (iRandom == 4)
				type = Type.CREEPER;
			if (iRandom == 5)
				type = Type.STAR;
			
			int iRandomColor1 = random.nextInt(16) + 1;
			int iRandomColor2 = random.nextInt(16) + 1;
			
			Color color1 = plugin.getColor(iRandomColor1);
			Color color2 = plugin.getColor(iRandomColor2);
			
			FireworkEffect fireworkEffect = FireworkEffect.builder().flicker(random.nextBoolean()).withColor(color1).withFade(color2).with(type).trail(random.nextBoolean()).build();
			fireworkMeta.addEffect(fireworkEffect);
			
			int pRandom = random.nextInt(2) + 1;
			
			fireworkMeta.setPower(pRandom);
			firework.setFireworkMeta(fireworkMeta);
			
			World w = Bukkit.getServer().getWorld(plugin.cm.getDataConfig().getString("spawn.world"));
	        double x = plugin.cm.getDataConfig().getDouble("spawn.x");
	        double y = plugin.cm.getDataConfig().getDouble("spawn.y");
	        double z = plugin.cm.getDataConfig().getDouble("spawn.z");
	        float yaw = (float) plugin.cm.getDataConfig().getDouble("spawn.yaw");
	        float pitch = (float) plugin.cm.getDataConfig().getDouble("spawn.pitch");
	        
	        player.teleport(new Location(w, x, y, z, yaw, pitch));
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		Entity entity = event.getRightClicked();
		ItemFrame itemFrame;
		
		if (entity instanceof Villager) {
			if (((Villager) entity).getCustomName().equalsIgnoreCase(ChatColor.GREEN + "Bob")) {
				player.sendMessage(ChatColor.DARK_GREEN + "Bob: " + ChatColor.YELLOW + "Hey " + ChatColor.DARK_RED + player.getName() + ChatColor.YELLOW + " , I am Bob.");
				player.sendMessage(ChatColor.YELLOW + "I am here to explain you about the VIP System in Zentax PvP.");
				player.sendMessage(ChatColor.YELLOW + "Currently there is a single package to VIP.");
				player.sendMessage(ChatColor.YELLOW + "To see all the information about the VIP, go to the website:");
				player.sendMessage(ChatColor.RED + "http://zentaxpvp.buycraft.net/");
				player.sendMessage(ChatColor.YELLOW + "Go to the website, write your username and choose a package.");
				player.sendMessage(ChatColor.YELLOW + "After choosing the package wait 3 seconds and you will get the package automatically to your account.");
				
				event.setCancelled(true);
			} else if (((Villager) entity).getCustomName().equalsIgnoreCase(ChatColor.GREEN + "Peter")) {
				player.sendMessage(ChatColor.DARK_GREEN + "Peter: " + ChatColor.YELLOW + "Hey " + ChatColor.DARK_RED + player.getName() + ChatColor.YELLOW + " , I am Peter.");
				player.sendMessage(ChatColor.YELLOW + "I am here to explain you about the Report System in Zentax PvP.");
				player.sendMessage(ChatColor.YELLOW + "When you want to report a player/bug, you can by command.");
				player.sendMessage(ChatColor.YELLOW + "To report a player/bug, you need to write:");
				player.sendMessage(ChatColor.RED + "/report <player> <reason> or /report <bug-reason>");
				player.sendMessage(ChatColor.YELLOW + "After reporting to a player/bug, operators receives the report and take care of it.");
				
				event.setCancelled(true);
			} else if (((Villager) entity).getCustomName().equalsIgnoreCase(ChatColor.GREEN + "Mike")) {
				plugin.shopMenu.shopOpen(player);
				
				event.setCancelled(true);
			}
		} if (entity instanceof ItemFrame) {
			ItemFrame frame = (ItemFrame) event.getRightClicked();
			ItemStack itemStack = frame.getItem();
			
			itemFrame = (ItemFrame) event.getRightClicked();
			itemFrame.setRotation(Rotation.COUNTER_CLOCKWISE);
			
			try {
				if (itemStack.getType().equals(Material.DIAMOND_SWORD)) {
					if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "" + ChatColor.BOLD + "Warrior")) {
						player.sendMessage(plugin.titleZP);
						player.sendMessage(ChatColor.GOLD + "---" + ChatColor.RED + "Warrior" + ChatColor.GOLD + "---");
						player.sendMessage(ChatColor.DARK_PURPLE + "Package: " + ChatColor.GOLD + "Default");
						player.sendMessage(ChatColor.DARK_PURPLE + "Description: " + ChatColor.GOLD + "A kit with the amazing sword, the Warrior specialized in utilizing swords to destroy any and all enemies.");
						player.sendMessage(ChatColor.DARK_PURPLE + "Equipment:");
						player.sendMessage(ChatColor.GOLD + "Iron Helmet");
						player.sendMessage(ChatColor.GOLD + "Iron Chestplate");
						player.sendMessage(ChatColor.GOLD + "Iron Leggings");
						player.sendMessage(ChatColor.GOLD + "Iron Boots");
						player.sendMessage(ChatColor.GOLD + "Diamond Sword");
					}
				} if (itemStack.getType().equals(Material.BOW)) {
					if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "" + ChatColor.BOLD + "Archer")) {
						player.sendMessage(plugin.titleZP);
						player.sendMessage(ChatColor.GOLD + "---" + ChatColor.GREEN + "Archer" + ChatColor.GOLD + "---");
						player.sendMessage(ChatColor.DARK_PURPLE + "Package: " + ChatColor.GOLD + "Default");
						player.sendMessage(ChatColor.DARK_PURPLE + "Description: " + ChatColor.GOLD + "A kit with the amazing bow, in perfect opportunities, there are archers can hunt, and able to cope with imminent deal damage by using ranged attacks.");
						player.sendMessage(ChatColor.DARK_PURPLE + "Equipment:");
						player.sendMessage(ChatColor.GOLD + "Chain Helmet");
						player.sendMessage(ChatColor.GOLD + "Chain Chestplate");
						player.sendMessage(ChatColor.GOLD + "Chain Leggings");
						player.sendMessage(ChatColor.GOLD + "Chain Boots");
						player.sendMessage(ChatColor.GOLD + "Iron Sword");
						player.sendMessage(ChatColor.GOLD + "Bow");
					}
				} if (itemStack.getType().equals(Material.ANVIL)) {
					plugin.repair(player);
					player.sendMessage(plugin.titleZ + ChatColor.GREEN + "Your items have been successfully repaired.");
				} if (itemStack.getType().equals(Material.MUSHROOM_SOUP)) {
					plugin.refill(player);
				}
			} catch (NullPointerException e) {
				
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteract(PlayerInteractEvent event) {
		try {
			Action action = event.getAction();
			final Player player = event.getPlayer();
			PlayerInventory pi = player.getInventory();
			CraftWorld craftWorld = (CraftWorld) player.getWorld();
			net.minecraft.server.v1_7_R1.World mcWorld = craftWorld.getHandle();
			CustomEntityVillager villager = new CustomEntityVillager(mcWorld);
			Location playerLocation = player.getLocation();
			
			if ((action == Action.RIGHT_CLICK_AIR) || (action == Action.RIGHT_CLICK_BLOCK)) {
				if ((player.getItemInHand().getType() == Material.PAPER) && (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "" + ChatColor.BOLD + "Summon"))) {
					if (player.getItemInHand().getItemMeta().getLore().contains(ChatColor.GREEN + "Bob")) {
						villager.setPosition(playerLocation.getX(), playerLocation.getY(), playerLocation.getZ());
						mcWorld.addEntity(villager, SpawnReason.CUSTOM);
						
						villager.setCustomName(ChatColor.GREEN + "Bob");
						villager.setCustomNameVisible(true);
					} else if (player.getItemInHand().getItemMeta().getLore().contains(ChatColor.GREEN + "Peter")) {
						villager.setPosition(playerLocation.getX(), playerLocation.getY(), playerLocation.getZ());
						mcWorld.addEntity(villager, SpawnReason.CUSTOM);
						
						villager.setCustomName(ChatColor.GREEN + "Peter");
						villager.setCustomNameVisible(true);
					} else if (player.getItemInHand().getItemMeta().getLore().contains(ChatColor.GREEN + "Mike")) {
						villager.setPosition(playerLocation.getX(), playerLocation.getY(), playerLocation.getZ());
						mcWorld.addEntity(villager, SpawnReason.CUSTOM);
						
						villager.setCustomName(ChatColor.GREEN + "Mike");
						villager.setCustomNameVisible(true);
					}
				} else if (player.getItemInHand().getType() == Material.MUSHROOM_SOUP) {
					int foodLevel = player.getFoodLevel();
			        double health = player.getHealth();
			        
			        if ((health == 19.899999999999999D) && (foodLevel >= 13)) {
			        	player.setFoodLevel(20);
			            player.getItemInHand().setType(Material.BOWL);
			            player.getWorld().playSound(player.getLocation(), Sound.EAT, 1.0F, 0.0F);
			        } else if ((health == 20.0D) && (foodLevel < 13)) {
			        	player.setFoodLevel(foodLevel + 7);
			            player.getItemInHand().setType(Material.BOWL);
			            player.getWorld().playSound(player.getLocation(), Sound.EAT, 1.0F, 0.0F);
			        } else if ((health < 20.0D) && (health >= 13.0D)) {
			        	player.setHealth(20.0D);
			            player.getItemInHand().setType(Material.BOWL);
			            player.getWorld().playSound(player.getLocation(), Sound.EAT, 1.0F, 0.0F);
			        } else if ((health < 20.0D) && (health < 13.0D)) {
			        	player.setHealth(health + 7.0D);
			            player.getItemInHand().setType(Material.BOWL);
			            player.getWorld().playSound(player.getLocation(), Sound.EAT, 1.0F, 0.0F);
			        }
			        
			        event.setCancelled(true);
				} else if (player.getItemInHand().getType() == Material.EMERALD) {
					if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Loki's Emerald")) {
						long[] cooldowns = cooldownsMap.get(player);
						long currentTime = System.currentTimeMillis();
						
						// Index of the currently selected item
			            int index = player.getInventory().getHeldItemSlot();
			            
			            if (cooldowns == null || cooldowns[index] + VANISH_COOLDOWN < currentTime) {
			            	player.getWorld().createExplosion(player.getTargetBlock(null, 2).getLocation(), 0.0F);
			            	
			            	for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
				        		pl.hidePlayer(player);
				            }
				        	
				        	plugin.vanished.add(player);
				        	
				        	Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {

								@Override
								public void run() {
									for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
						        		pl.showPlayer(player);
						            }
									
									plugin.vanished.remove(player);
								}
				        		
				        	}, 3 * 20L);
				        	
							player.sendMessage(plugin.titleZ + ChatColor.GREEN + "You are currently invisible to 3 seconds!");
			            	
			            	player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "After 15 seconds you can use it again!");
							
							if (cooldowns == null) {
								cooldowns = new long[player.getInventory().getSize()];
							}
							
							cooldowns[index] = currentTime;
			                cooldownsMap.put(player, cooldowns);
			            }
					}
				} else if ((player.getItemInHand().getType() == Material.LEASH) && (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "" + ChatColor.BOLD + "Spider-Man's Silk"))) {
					long[] cooldowns = cooldownsMap.get(player);
					long currentTime = System.currentTimeMillis();
					
					// Index of the currently selected item
		            int index = player.getInventory().getHeldItemSlot();
		            
		            Vector direction = player.getEyeLocation().getDirection().multiply(4);
	                
	                if (player.getTargetBlock(null, 20).getType() != Material.AIR) {
	                	if (cooldowns == null || cooldowns[index] + LEASH_COOLDOWN < currentTime) {
	                		Projectile projectile = (Projectile) player.getWorld().spawn(player.getEyeLocation().add(direction.getX(), direction.getY(), direction.getZ()), Arrow.class);
	    	                projectile.setShooter(player);
	    	                projectile.setVelocity(direction);
	    	                
	    	                final Arrow arrow = (Arrow) projectile;
	    	                Bat bat = player.getWorld().spawn(player.getEyeLocation(), Bat.class);
	    	                
	    	                bat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 100000));
	    	                bat.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100000, 100000));
	    	                bat.setLeashHolder(arrow);
	    	                
	                		Location l = player.getLocation();
		                	Vector getDirection = l.getDirection();
		                	
		                	int flySpeed = 6;
		                	
		                	getDirection.multiply(flySpeed);
		                	player.setVelocity(getDirection);
		                	
		                	player.teleport(new Location(player.getWorld(), arrow.getLocation().getX(), arrow.getLocation().getY(), arrow.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch()));
	                		
	                		if (cooldowns == null) {
								cooldowns = new long[player.getInventory().getSize()];
							}
							
							cooldowns[index] = currentTime;
			                cooldownsMap.put(player, cooldowns);
			                
			                player.sendMessage(plugin.titleZ + ChatColor.GREEN + "You used Spider-Man's silk!");
			            	player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "After 5 seconds you can use it again!");
			            	
			            	Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {

								@Override
								public void run() {
									arrow.remove();
								}
				        		
				        	}, 20L);
			                
			                bat.setHealth((double) 0);
			                
			            	event.setCancelled(true);
	                	}
	                } else {
	                	player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "Not be found within a radius of 20 blocks, try again!");
	                }
	                
	                event.setCancelled(true);
				}
			} if (action == Action.RIGHT_CLICK_BLOCK) {
				if ((event.getClickedBlock().getState().getType() == Material.WALL_SIGN) || (event.getClickedBlock().getState().getType() == Material.SIGN) || (event.getClickedBlock().getState().getType() == Material.SIGN_POST)) {
					Sign sign = (Sign) event.getClickedBlock().getState();
					
					/**
					 * Soup
					 */
					
					// Mushroom Soup
					ItemStack soup = new ItemStack(Material.MUSHROOM_SOUP, 1);
					
					// Bowl
					ItemStack bowl = new ItemStack(Material.BOWL, 1);
					
					/**
					 * Warrior
					 */
					
					// Sword
					ItemStack wsword = new ItemStack(Material.DIAMOND_SWORD);
					ItemMeta wswordMeta = wsword.getItemMeta();
					
					wswordMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Warrior's Sword");
					wswordMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
					wsword.setDurability((short) 0);
					wsword.setItemMeta(wswordMeta);
					
					// Helmet
					ItemStack whelmet = new ItemStack(Material.IRON_HELMET);
					ItemMeta whelmetMeta = whelmet.getItemMeta();
					
					whelmetMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Warrior's Helmet");
					whelmet.setItemMeta(whelmetMeta);
					
					// Chestplate
					ItemStack wchestplate = new ItemStack(Material.IRON_CHESTPLATE);
					ItemMeta wchestplateMeta = wchestplate.getItemMeta();
					
					wchestplateMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Warrior's Chestplate");
					wchestplate.setItemMeta(wchestplateMeta);
					
					// Leggings
					ItemStack wleggings = new ItemStack(Material.IRON_LEGGINGS);
					ItemMeta wleggingsMeta = wleggings.getItemMeta();
					
					wleggingsMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Warrior's Leggings");
					wleggings.setItemMeta(wleggingsMeta);
					
					// Boots
					ItemStack wboots = new ItemStack(Material.IRON_BOOTS);
					ItemMeta wbootsMeta = wboots.getItemMeta();
					
					wbootsMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Warrior's Boots");
					wboots.setItemMeta(wbootsMeta);
					
					/**
					 * Archer
					 */
					
					// Sword
					ItemStack asword = new ItemStack(Material.IRON_SWORD);
					ItemMeta aswordMeta = asword.getItemMeta();
					
					aswordMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Archer's Sword");
					aswordMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
					asword.setItemMeta(aswordMeta);
					
					// Bow
					ItemStack abow = new ItemStack(Material.BOW);
					ItemMeta abowMeta = abow.getItemMeta();
					
					abowMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Archer's Bow");
					abowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 2, true);
					abowMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
					abowMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);
					abow.setItemMeta(abowMeta);
					
					// Arrow
					ItemStack aarrow = new ItemStack(Material.ARROW);
					ItemMeta aarrowMeta = aarrow.getItemMeta();
					
					aarrowMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Archer's Arrow");
					aarrow.setItemMeta(aarrowMeta);
					
					// Helmet
					ItemStack ahelmet = new ItemStack(Material.CHAINMAIL_HELMET);
					ItemMeta ahelmetMeta = ahelmet.getItemMeta();
					
					ahelmetMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Archer's Helmet");
					ahelmetMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
					ahelmet.setItemMeta(ahelmetMeta);
					
					// Chestplate
					ItemStack achestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
					ItemMeta achestplateMeta = achestplate.getItemMeta();
					
					achestplateMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Archer's Chestplate");
					achestplate.setItemMeta(achestplateMeta);
					
					// Leggings
					ItemStack aleggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
					ItemMeta aleggingsMeta = aleggings.getItemMeta();
					
					aleggingsMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Archer's Leggings");
					aleggings.setItemMeta(aleggingsMeta);
					
					// Boots
					ItemStack aboots = new ItemStack(Material.CHAINMAIL_BOOTS);
					ItemMeta abootsMeta = aboots.getItemMeta();
					
					abootsMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Archer's Boots");
					aboots.setItemMeta(abootsMeta);
					
					/**
					 * Chicken
					 */
					
					// Sword
					ItemStack csword = new ItemStack(Material.IRON_SWORD, 1);
					ItemMeta cswordMeta = csword.getItemMeta();
					
					cswordMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Chicken's Sword");
					csword.setItemMeta(cswordMeta);
					
					// Egg
					ItemStack cegg = new ItemStack(Material.EGG, 9);
					ItemMeta ceggMeta = cegg.getItemMeta();
					
					ceggMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Chicken's Egg");
					ceggMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
					cegg.setItemMeta(ceggMeta);
					
					// Helmet
					ItemStack chelmet = new ItemStack(Material.GOLD_HELMET);
					ItemMeta chelmetMeta = chelmet.getItemMeta();
					
					chelmetMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Chicken's Helmet");
					chelmetMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
					chelmet.setItemMeta(chelmetMeta);
					
					// Chestplate
					ItemStack cchestplate = new ItemStack(Material.IRON_CHESTPLATE);
					ItemMeta cchestplateMeta = cchestplate.getItemMeta();
					
					cchestplateMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Chicken's Chestplate");
					cchestplateMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
					cchestplate.setItemMeta(cchestplateMeta);
					
					// Leggings
					ItemStack cleggings = new ItemStack(Material.GOLD_LEGGINGS);
					ItemMeta cleggingsMeta = cleggings.getItemMeta();
					
					cleggingsMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Chicken's Leggings");
					cleggingsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
					cleggings.setItemMeta(cleggingsMeta);
					
					// Boots
					ItemStack cboots = new ItemStack(Material.IRON_BOOTS);
					ItemMeta cbootsMeta = cboots.getItemMeta();
					
					cbootsMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Chicken's Boots");
					cbootsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
					cboots.setItemMeta(cbootsMeta);
					
					/**
					 * Fisherman
					 */
					
					// Sword
					ItemStack fsword = new ItemStack(Material.STONE_SWORD);
			        ItemMeta fswordMeta = fsword.getItemMeta();
			        
			        fswordMeta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Fisherman's Sword");
			        fswordMeta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
			        fsword.setItemMeta(fswordMeta);
			        
			        // Fishing Rod
			        ItemStack ffishing_rod = new ItemStack(Material.FISHING_ROD);
			        ItemMeta ffishing_rodMeta = ffishing_rod.getItemMeta();
			        
			        ffishing_rodMeta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Fisherman's Rod");
			        ffishing_rod.setItemMeta(ffishing_rodMeta);
			        
			        // Helmet
			        ItemStack fhelmet = new ItemStack(Material.LEATHER_HELMET);
			        LeatherArmorMeta fhelmetArmorMeta = (LeatherArmorMeta)fhelmet.getItemMeta();
			        
			        fhelmetArmorMeta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Fisherman's Helmet");
			        fhelmetArmorMeta.setColor(Color.BLUE);
			        fhelmetArmorMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
			        fhelmet.setItemMeta(fhelmetArmorMeta);
			        
			        // Chestplate
			        ItemStack fchestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
			        LeatherArmorMeta fchestplateArmorMeta = (LeatherArmorMeta)fchestplate.getItemMeta();
			        
			        fchestplateArmorMeta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Fisherman's Chestplate");
			        fchestplateArmorMeta.setColor(Color.BLUE);
			        fchestplateArmorMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
			        fchestplate.setItemMeta(fchestplateArmorMeta);
			        
			        // Leggings
			        ItemStack fleggings = new ItemStack(Material.LEATHER_LEGGINGS);
			        LeatherArmorMeta fleggingsArmorMeta = (LeatherArmorMeta)fleggings.getItemMeta();
			        
			        fleggingsArmorMeta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Fisherman's Leggings");
			        fleggingsArmorMeta.setColor(Color.BLUE);
			        fleggingsArmorMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
			        fleggings.setItemMeta(fleggingsArmorMeta);
			        
			        // Boots
			        ItemStack fboots = new ItemStack(Material.LEATHER_BOOTS);
			        LeatherArmorMeta fbootsArmorMeta = (LeatherArmorMeta)fboots.getItemMeta();
			        
			        fbootsArmorMeta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Fisherman's Boots");
			        fbootsArmorMeta.setColor(Color.BLUE);
			        fbootsArmorMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
			        fboots.setItemMeta(fbootsArmorMeta);
			        
			        /**
					 * Loki
					 */
					
					// Sword
					ItemStack lsword = new ItemStack(Material.STONE_SWORD);
			        ItemMeta lswordMeta = lsword.getItemMeta();
			        
			        lswordMeta.setDisplayName(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Loki's Sword");
			        lswordMeta.setLore(Arrays.asList("Loki's Sword deals double more damage from his basic attacks", "when hitting enemies from behind!"));
			        lswordMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
			        lswordMeta.addEnchant(Enchantment.DURABILITY, 10, true);
			        lsword.setItemMeta(lswordMeta);
			        
			        // Emerald
			        ItemStack lemerald = new ItemStack(Material.EMERALD);
			        ItemMeta lemeraldMeta = lemerald.getItemMeta();
			        
			        lemeraldMeta.setDisplayName(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Loki's Emerald");
			        lemeraldMeta.setLore(Arrays.asList("It makes you invisible for 5 seconds!"));
			        lemerald.setItemMeta(lemeraldMeta);
			        
			        // Helmet
			        ItemStack lhelmet = new ItemStack(Material.LEATHER_HELMET);
			        LeatherArmorMeta lhelmetArmorMeta = (LeatherArmorMeta) lhelmet.getItemMeta();
			        
			        lhelmetArmorMeta.setDisplayName(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Loki's Helmet");
			        lhelmetArmorMeta.setColor(Color.GREEN);
			        lhelmetArmorMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
			        lhelmetArmorMeta.addEnchant(Enchantment.DURABILITY, 10, true);
			        lhelmet.setItemMeta(lhelmetArmorMeta);
			        
			        // Chestplate
			        ItemStack lchestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
			        LeatherArmorMeta lchestplateArmorMeta = (LeatherArmorMeta) lchestplate.getItemMeta();
			        
			        lchestplateArmorMeta.setDisplayName(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Loki's Chestplate");
			        lchestplateArmorMeta.setColor(Color.GREEN);
			        lchestplateArmorMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
			        lchestplateArmorMeta.addEnchant(Enchantment.DURABILITY, 10, true);
			        lchestplate.setItemMeta(lchestplateArmorMeta);
			        
			        // Leggings
			        ItemStack lleggings = new ItemStack(Material.LEATHER_LEGGINGS);
			        LeatherArmorMeta lleggingsArmorMeta = (LeatherArmorMeta) lleggings.getItemMeta();
			        
			        lleggingsArmorMeta.setDisplayName(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Loki's Leggings");
			        lleggingsArmorMeta.setColor(Color.GREEN);
			        lleggingsArmorMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
			        lleggingsArmorMeta.addEnchant(Enchantment.DURABILITY, 10, true);
			        lleggings.setItemMeta(lleggingsArmorMeta);
			        
			        // Boots
			        ItemStack lboots = new ItemStack(Material.LEATHER_BOOTS);
			        LeatherArmorMeta lbootsArmorMeta = (LeatherArmorMeta) lboots.getItemMeta();
			        
			        lbootsArmorMeta.setDisplayName(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Loki's Boots");
			        lbootsArmorMeta.setColor(Color.GREEN);
			        lbootsArmorMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
			        lbootsArmorMeta.addEnchant(Enchantment.DURABILITY, 10, true);
			        lboots.setItemMeta(lbootsArmorMeta);
			        
			        /**
					 * Pyro
					 */
					
					// Sword
					ItemStack psword = new ItemStack(Material.GOLD_SWORD);
					ItemMeta pswordMeta = psword.getItemMeta();
					
					pswordMeta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Pyro's Sword");
					pswordMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
					pswordMeta.addEnchant(Enchantment.DURABILITY, 3, true);
					psword.setItemMeta(pswordMeta);
					
					// Bow
					ItemStack pbow = new ItemStack(Material.BOW);
					ItemMeta pbowMeta = pbow.getItemMeta();
					
					pbowMeta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Pyro's Bow");
					pbowMeta.setLore(Arrays.asList("The bow of fire!"));
					pbowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 2, true);
					pbowMeta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
					pbowMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
					pbow.setItemMeta(pbowMeta);
					
					// Arrow
					ItemStack parrow = new ItemStack(Material.ARROW);
					ItemMeta parrowMeta = parrow.getItemMeta();
					
					parrowMeta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Pyro's Arrow");
					parrow.setItemMeta(parrowMeta);
					
					// Helmet
					ItemStack phelmet = new ItemStack(Material.GOLD_HELMET);
					ItemMeta phelmetMeta = phelmet.getItemMeta();
					
					phelmetMeta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Pyro's Helmet");
					phelmetMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
					phelmetMeta.addEnchant(Enchantment.DURABILITY, 3, true);
					phelmet.setItemMeta(phelmetMeta);
					
					// Chestplate
					ItemStack pchestplate = new ItemStack(Material.GOLD_CHESTPLATE);
					ItemMeta pchestplateMeta = pchestplate.getItemMeta();
					
					pchestplateMeta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Pyro's Chestplate");
					pchestplateMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
					pchestplateMeta.addEnchant(Enchantment.DURABILITY, 3, true);
					pchestplate.setItemMeta(pchestplateMeta);
					
					// Leggings
					ItemStack pleggings = new ItemStack(Material.GOLD_LEGGINGS);
					ItemMeta pleggingsMeta = pleggings.getItemMeta();
					
					pleggingsMeta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Pyro's Leggings");
					pleggingsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
					pleggingsMeta.addEnchant(Enchantment.DURABILITY, 3, true);
					pleggings.setItemMeta(pleggingsMeta);
					
					// Boots
					ItemStack pboots = new ItemStack(Material.GOLD_BOOTS);
					ItemMeta pbootsMeta = pboots.getItemMeta();
					
					pbootsMeta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Pyro's Boots");
					pbootsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
					pbootsMeta.addEnchant(Enchantment.DURABILITY, 3, true);
					pboots.setItemMeta(pbootsMeta);
					
					/**
					 * Flash
					 */
					
					// Sword
					ItemStack fhsword = new ItemStack(Material.STONE_SWORD);
			        ItemMeta fhswordMeta = fhsword.getItemMeta();
			        
			        fhswordMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Flash's Sword");
			        fhswordMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
			        fhswordMeta.addEnchant(Enchantment.DURABILITY, 10, true);
			        fhsword.setItemMeta(fhswordMeta);
			        
			        // Helmet
			        ItemStack fhhelmet = new ItemStack(Material.LEATHER_HELMET);
			        LeatherArmorMeta fhhelmetArmorMeta = (LeatherArmorMeta) fhhelmet.getItemMeta();
			        
			        fhhelmetArmorMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Flash's Helmet");
			        fhhelmetArmorMeta.setColor(Color.RED);
			        fhhelmetArmorMeta.addEnchant(Enchantment.DURABILITY, 10, true);
			        fhhelmet.setItemMeta(fhhelmetArmorMeta);
			        
			        // Chestplate
			        ItemStack fhchestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
			        LeatherArmorMeta fhchestplateArmorMeta = (LeatherArmorMeta) fhchestplate.getItemMeta();
			        
			        fhchestplateArmorMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Flash's Chestplate");
			        fhchestplateArmorMeta.setColor(Color.RED);
			        fhchestplateArmorMeta.addEnchant(Enchantment.DURABILITY, 10, true);
			        fhchestplate.setItemMeta(fhchestplateArmorMeta);
			        
			        // Leggings
			        ItemStack fhleggings = new ItemStack(Material.LEATHER_LEGGINGS);
			        LeatherArmorMeta fhleggingsArmorMeta = (LeatherArmorMeta) fhleggings.getItemMeta();
			        
			        fhleggingsArmorMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Flash's Leggings");
			        fhleggingsArmorMeta.setColor(Color.RED);
			        fhleggingsArmorMeta.addEnchant(Enchantment.DURABILITY, 10, true);
			        fhleggings.setItemMeta(fhleggingsArmorMeta);
			        
			        // Boots
			        ItemStack fhboots = new ItemStack(Material.LEATHER_BOOTS);
			        Attributes attributes = new Attributes(fhboots);
			        attributes.add(Attribute.newBuilder().name("Speed").type(AttributeType.GENERIC_MOVEMENT_SPEED).amount(0.1).build());
			        ItemStack fhbootsResult = attributes.getStack();
			        LeatherArmorMeta fhbootsArmorMeta = (LeatherArmorMeta) fhbootsResult.getItemMeta();
			        
			        fhbootsArmorMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Flash's Boots");
			        fhbootsArmorMeta.setColor(Color.YELLOW);
			        fhbootsArmorMeta.addEnchant(Enchantment.DURABILITY, 10, true);
			        fhboots.setDurability((short) 0);
			        fhbootsResult.setItemMeta(fhbootsArmorMeta);
			        
			        /**
					 * Spider-Man
					 */
					
					// Sword
					ItemStack ssword = new ItemStack(Material.IRON_SWORD);
			        ItemMeta sswordMeta = ssword.getItemMeta();
			        
			        sswordMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Spider-Man's Sword");
			        ssword.setItemMeta(sswordMeta);
			        
			        // Helmet
			        ItemStack shelmet = new ItemStack(Material.LEATHER_HELMET);
			        LeatherArmorMeta shelmetArmorMeta = (LeatherArmorMeta) shelmet.getItemMeta();
			        
			        shelmetArmorMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Spider-Man's Helmet");
			        shelmetArmorMeta.setColor(Color.RED);
			        shelmetArmorMeta.addEnchant(Enchantment.DURABILITY, 10, true);
			        shelmet.setItemMeta(shelmetArmorMeta);
			        
			        // Leash
			        ItemStack sleash = new ItemStack(Material.LEASH);
			        ItemMeta sleashMeta = sleash.getItemMeta();
			        
			        sleashMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Spider-Man's Silk");
			        sleashMeta.setLore(Arrays.asList("It hooked you to place it touches!", "Radius of 20 blocks!"));
			        sleash.setItemMeta(sleashMeta);
			        
			        // Chestplate
			        ItemStack schestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
			        LeatherArmorMeta schestplateArmorMeta = (LeatherArmorMeta) schestplate.getItemMeta();
			        
			        schestplateArmorMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Spider-Man's Chestplate");
			        schestplateArmorMeta.setColor(Color.RED);
			        schestplateArmorMeta.addEnchant(Enchantment.DURABILITY, 10, true);
			        schestplateArmorMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
			        schestplate.setDurability((short) 0);
			        schestplate.setItemMeta(schestplateArmorMeta);
			        
			        // Leggings
			        ItemStack sleggings = new ItemStack(Material.LEATHER_LEGGINGS);
			        LeatherArmorMeta sleggingsArmorMeta = (LeatherArmorMeta) sleggings.getItemMeta();
			        
			        sleggingsArmorMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Spider-Man's Leggings");
			        sleggingsArmorMeta.setColor(Color.RED);
			        sleggingsArmorMeta.addEnchant(Enchantment.DURABILITY, 10, true);
			        sleggings.setItemMeta(sleggingsArmorMeta);
			        
			        // Boots
			        ItemStack sboots = new ItemStack(Material.LEATHER_BOOTS);
			        LeatherArmorMeta sbootsArmorMeta = (LeatherArmorMeta) sboots.getItemMeta();
			        
			        sbootsArmorMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Spider-Man's Boots");
			        sbootsArmorMeta.setColor(Color.RED);
			        sbootsArmorMeta.addEnchant(Enchantment.DURABILITY, 10, true);
			        sboots.setItemMeta(sbootsArmorMeta);
			        
					if (sign.getLine(1).equalsIgnoreCase(plugin.titleRepair)) {
						plugin.repair(player);
						
						player.sendMessage(plugin.titleZ + ChatColor.GREEN + "Your items have been successfully repaired.");
					} if (sign.getLine(1).equalsIgnoreCase(plugin.titleRefill)) {
						plugin.refill(player);
					} if ((sign.getLine(0).equalsIgnoreCase(plugin.titleWarp)) && (sign.getLine(1).equalsIgnoreCase(ChatColor.GREEN + "1V1"))) {
						player.teleport(new Location(Bukkit.getWorld("world"), 664.25809, 25.989, -1277.49785));
					} if ((sign.getLine(0).equalsIgnoreCase(plugin.title1v1)) && (sign.getLine(1).equalsIgnoreCase(ChatColor.GREEN + "Join"))) {
						player.chat("/1v1 haigeydarovnoamdolev1v1");
					} if ((sign.getLine(0).equalsIgnoreCase(plugin.title1v1)) && (sign.getLine(1).equalsIgnoreCase(ChatColor.GREEN + "Leave"))) {
						player.chat("/1v1 leave");
					} if ((sign.getLine(0).equalsIgnoreCase(plugin.title1v1S)) && (sign.getLine(1).equalsIgnoreCase(ChatColor.GREEN + "Join"))) {
						player.chat("/1v1 haigeydarovnoamdolev1v1s");
					} if ((sign.getLine(0).equalsIgnoreCase(plugin.title1v1S)) && (sign.getLine(1).equalsIgnoreCase(ChatColor.GREEN + "Leave"))) {
						player.chat("/1v1 leaves");
					} if (sign.getLine(0).equalsIgnoreCase(plugin.titleKit) && sign.getLine(1).equalsIgnoreCase(ChatColor.RED + "Warrior")) {
						player.sendMessage(plugin.titleZ + ChatColor.YELLOW + "You chose the " + ChatColor.RED + "Warrior" + ChatColor.YELLOW + " kit.");
						player.setHealth(20);
						
						pi.setArmorContents(null);
						pi.clear();
						pi.addItem(wsword);
						pi.setHelmet(whelmet);
						pi.setChestplate(wchestplate);
						pi.setLeggings(wleggings);
						pi.setBoots(wboots);
						
						for (PotionEffect effect : player.getActivePotionEffects()) {
							player.removePotionEffect(effect.getType());
						} for (int i = 1; i <= 33; i++) {
							pi.addItem(soup);
						}
						
						pi.addItem(bowl);
						
						player.getWorld().playSound(player.getLocation(), Sound.ORB_PICKUP, 1, 0);
						player.setGameMode(GameMode.SURVIVAL);
						player.teleport(new Location(Bukkit.getWorld("world"), 13.53123, 34.760, -624.49887));
						player.updateInventory();
					} if (sign.getLine(0).equalsIgnoreCase(plugin.titleKit) && sign.getLine(1).equalsIgnoreCase(ChatColor.GREEN + "Archer")) {
						player.sendMessage(plugin.titleZ + ChatColor.YELLOW + "You chose the " + ChatColor.GREEN + "Archer" + ChatColor.YELLOW + " kit.");
						player.setHealth(20);
						
						pi.setArmorContents(null);
						pi.clear();
						pi.addItem(asword);
						pi.addItem(abow);
						pi.setHelmet(ahelmet);
						pi.setChestplate(achestplate);
						pi.setLeggings(aleggings);
						pi.setBoots(aboots);
						
						for (PotionEffect effect : player.getActivePotionEffects()) {
							player.removePotionEffect(effect.getType());
						} for (int i = 1; i <= 31; i++) {
							pi.addItem(soup);
						}
						
						pi.addItem(bowl);
						pi.addItem(aarrow);
						
						player.getWorld().playSound(player.getLocation(), Sound.ORB_PICKUP, 1, 0);
						player.setGameMode(GameMode.SURVIVAL);
						player.teleport(new Location(Bukkit.getWorld("world"), 13.53123, 34.760, -624.49887));
						player.updateInventory();
					} if (sign.getLine(0).equalsIgnoreCase(plugin.titleKit) && sign.getLine(1).equalsIgnoreCase(ChatColor.GOLD + "Chicken")) {
						if (plugin.getConfig().getBoolean("Players." + player.getName() + ".Kits.Chicken") == true) {
							player.sendMessage(plugin.titleZ + ChatColor.YELLOW + "You chose the " + ChatColor.GOLD + "Chicken" + ChatColor.YELLOW + " kit.");
							player.setHealth(20);
							
							pi.setArmorContents(null);
							pi.clear();
							pi.addItem(csword);
							pi.addItem(cegg);
							pi.setHelmet(chelmet);
							pi.setChestplate(cchestplate);
							pi.setLeggings(cleggings);
							pi.setBoots(cboots);
							
							for (PotionEffect effect : player.getActivePotionEffects()) {
								player.removePotionEffect(effect.getType());
							} for (int i = 1; i <= 32; i++) {
								pi.addItem(soup);
							}
							
							pi.addItem(bowl);
							
							player.getWorld().playSound(player.getLocation(), Sound.ORB_PICKUP, 1, 0);
							player.setGameMode(GameMode.SURVIVAL);
							player.teleport(new Location(Bukkit.getWorld("world"), 13.53123, 34.760, -624.49887));
							player.updateInventory();
						} else {
							player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You do not have this kit.");
						}
					} if (sign.getLine(0).equalsIgnoreCase(plugin.titleKit) && sign.getLine(1).equalsIgnoreCase(ChatColor.BLUE + "Fisherman")) {
						if (plugin.getConfig().getBoolean("Players." + player.getName() + ".Kits.Fisherman") == true) {
							player.sendMessage(plugin.titleZ + ChatColor.YELLOW + "You chose the " + ChatColor.BLUE + "Fisherman" + ChatColor.YELLOW + " kit.");
							player.setHealth(20);
							
							pi.setArmorContents(null);
							pi.clear();
							pi.addItem(fsword);
							pi.addItem(ffishing_rod);
							pi.setHelmet(fhelmet);
							pi.setChestplate(fchestplate);
							pi.setLeggings(fleggings);
							pi.setBoots(fboots);
							
							for (PotionEffect effect : player.getActivePotionEffects()) {
								player.removePotionEffect(effect.getType());
							} for (int i = 1; i <= 32; i++) {
								pi.addItem(soup);
							}
							
							pi.addItem(bowl);
							
							player.getWorld().playSound(player.getLocation(), Sound.ORB_PICKUP, 1, 0);
							player.setGameMode(GameMode.SURVIVAL);
							player.teleport(new Location(Bukkit.getWorld("world"), 13.53123, 34.760, -624.49887));
							player.updateInventory();
						} else {
							player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You do not have this kit.");
						}
					} if (sign.getLine(0).equalsIgnoreCase(plugin.titleKit) && sign.getLine(1).equalsIgnoreCase(ChatColor.DARK_GREEN + "Loki")) {
						if ((plugin.cm.getGroupsConfig().getBoolean("Groups.VIP." + player.getName()) == true) || (player.isOp()) || (plugin.cm.getGroupsConfig().getBoolean("Groups.Mods." + player.getName()) == true)) {
							player.sendMessage(plugin.titleZ + ChatColor.YELLOW + "You chose the " + ChatColor.DARK_GREEN + "Loki" + ChatColor.YELLOW + " kit.");
							player.setHealth(20);
							
							pi.setArmorContents(null);
							pi.clear();
							pi.addItem(lsword);
							pi.addItem(lemerald);
							pi.setHelmet(lhelmet);
							pi.setChestplate(lchestplate);
							pi.setLeggings(lleggings);
							pi.setBoots(lboots);
							
							for (PotionEffect effect : player.getActivePotionEffects()) {
								player.removePotionEffect(effect.getType());
							} for (int i = 1; i <= 32; i++) {
								pi.addItem(soup);
							}
							
							pi.addItem(bowl);
							
							player.getWorld().playSound(player.getLocation(), Sound.ORB_PICKUP, 1, 0);
							player.setGameMode(GameMode.SURVIVAL);
							player.teleport(new Location(Bukkit.getWorld("world"), 13.53123, 34.760, -624.49887));
							player.updateInventory();
						} else {
							player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "This kit only for VIP.");
						}
					} if (sign.getLine(0).equalsIgnoreCase(plugin.titleKit) && sign.getLine(1).equalsIgnoreCase(ChatColor.DARK_RED + "Pyro")) {
						if (plugin.getConfig().getBoolean("Players." + player.getName() + ".Kits.Pyro") == true) {
							player.sendMessage(plugin.titleZ + ChatColor.YELLOW + "You chose the " + ChatColor.DARK_RED + "Pyro" + ChatColor.YELLOW + " kit.");
							player.setHealth(20);
							
							pi.setArmorContents(null);
							pi.clear();
							pi.addItem(psword);
							pi.addItem(pbow);
							pi.setHelmet(phelmet);
							pi.setChestplate(pchestplate);
							pi.setLeggings(pleggings);
							pi.setBoots(pboots);
							
							for (PotionEffect effect : player.getActivePotionEffects()) {
								player.removePotionEffect(effect.getType());
							} for (int i = 1; i <= 31; i++) {
								pi.addItem(soup);
							}
							
							pi.addItem(bowl);
							pi.addItem(parrow);
							
							player.getWorld().playSound(player.getLocation(), Sound.ORB_PICKUP, 1, 0);
							player.setGameMode(GameMode.SURVIVAL);
							player.teleport(new Location(Bukkit.getWorld("world"), 13.53123, 34.760, -624.49887));
							player.updateInventory();
						} else {
							player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You do not have this kit.");
						}
					} if (sign.getLine(0).equalsIgnoreCase(plugin.titleKit) && sign.getLine(1).equalsIgnoreCase(ChatColor.RED + "Flash")) {
						if (plugin.getConfig().getBoolean("Players." + player.getName() + ".Kits.Flash") == true) {
							player.sendMessage(plugin.titleZ + ChatColor.YELLOW + "You chose the " + ChatColor.RED + "Flash" + ChatColor.YELLOW + " kit.");
							player.setHealth(20);
							
							pi.setArmorContents(null);
							pi.clear();
							pi.addItem(fhsword);
							pi.setHelmet(fhhelmet);
							pi.setChestplate(fhchestplate);
							pi.setLeggings(fhleggings);
							pi.setBoots(fhbootsResult);
							
							for (PotionEffect effect : player.getActivePotionEffects()) {
								player.removePotionEffect(effect.getType());
							} for (int i = 1; i <= 33; i++) {
								pi.addItem(soup);
							}
							
							pi.addItem(bowl);
							
							player.getWorld().playSound(player.getLocation(), Sound.ORB_PICKUP, 1, 0);
							player.setGameMode(GameMode.SURVIVAL);
							player.teleport(new Location(Bukkit.getWorld("world"), 13.53123, 34.760, -624.49887));
							player.updateInventory();
						} else {
							player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You do not have this kit.");
						}
					} if (sign.getLine(0).equalsIgnoreCase(plugin.titleKit) && sign.getLine(1).equalsIgnoreCase(ChatColor.RED + "Spider-Man")) {
						if ((plugin.cm.getGroupsConfig().getBoolean("Groups.VIP." + player.getName()) == true) || (player.isOp()) || (plugin.cm.getGroupsConfig().getBoolean("Groups.Mods." + player.getName()) == true)) {
							player.sendMessage(plugin.titleZ + ChatColor.YELLOW + "You chose the " + ChatColor.RED + "Spider-Man" + ChatColor.YELLOW + " kit.");
							player.setHealth(20);
							
							pi.setArmorContents(null);
							pi.clear();
							pi.addItem(ssword);
							pi.addItem(sleash);
							pi.setHelmet(shelmet);
							pi.setChestplate(schestplate);
							pi.setLeggings(sleggings);
							pi.setBoots(sboots);
							
							for (PotionEffect effect : player.getActivePotionEffects()) {
								player.removePotionEffect(effect.getType());
							} for (int i = 1; i <= 32; i++) {
								pi.addItem(soup);
							}
							
							pi.addItem(bowl);
							
							player.getWorld().playSound(player.getLocation(), Sound.ORB_PICKUP, 1, 0);
							player.setGameMode(GameMode.SURVIVAL);
							player.teleport(new Location(Bukkit.getWorld("world"), 13.53123, 34.760, -624.49887));
							player.updateInventory();
						} else {
							player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "This kit only for VIP.");
						}
					}
				}
			}
		} catch (NullPointerException ex) {
			
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		
		if (plugin.cm.getTeamConfig().getString("Players." + player.getName() + ".Stats.Team") != null) {
			String DParty = plugin.cm.getTeamConfig().getString("Players." + player.getName() + ".Stats.Team");
		    OfflinePlayer ownerOffline = Bukkit.getOfflinePlayer((String) plugin.cm.getTeamListConfig().getStringList(DParty).get(0));
		    
		    if (ownerOffline == player) {
		    	DParty = ChatColor.GOLD + plugin.cm.getTeamConfig().getString(new StringBuilder(String.valueOf("Players." + player.getName())).append(".Stats.Team").toString());
		    }
		    
		    event.setCancelled(true);
		    
		    Bukkit.broadcastMessage(ChatColor.GREEN + "[" + ChatColor.GOLD + DParty + ChatColor.GREEN + "] " + ChatColor.WHITE + player.getDisplayName() + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + event.getMessage());
		}
		
		if (plugin.cm.getGroupsConfig().getBoolean("Groups.Admins." + player.getName()) == true) {
			event.getFormat();
			event.setFormat(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "Admin" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + player.getDisplayName() + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + event.getMessage());
		} if (plugin.cm.getGroupsConfig().getBoolean("Groups.Members." + player.getName()) == true) {
			event.getFormat();
			event.setFormat(ChatColor.GRAY + "[" + ChatColor.GRAY + "Member" + ChatColor.GRAY + "] " + player.getDisplayName() + ChatColor.GRAY + ": " + ChatColor.GRAY + event.getMessage());
		} if (plugin.cm.getGroupsConfig().getBoolean("Groups.Operators." + player.getName()) == true) {
			event.getFormat();
			event.setFormat(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "Operator" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + player.getDisplayName() + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + event.getMessage());
		} if (plugin.cm.getGroupsConfig().getBoolean("Groups.Mods." + player.getName()) == true) {
			event.getFormat();
			event.setFormat(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_PURPLE + "Mod" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + player.getDisplayName() + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + event.getMessage());
		} if (plugin.cm.getGroupsConfig().getBoolean("Groups.VIP." + player.getName()) == true) {
			event.getFormat();
			event.setFormat(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "VIP" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + player.getDisplayName() + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + event.getMessage());
		} if (plugin.cm.getGroupsConfig().getBoolean("Groups.Helper." + player.getName()) == true) {
			event.getFormat();
			event.setFormat(ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "Helper" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + player.getDisplayName() + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + event.getMessage());
		} if (plugin.mutedPlayers.containsKey(player.getName())) {
			try {
				event.setCancelled(true);
			} catch (Exception e) {
				
			}
		}
		
        Set<Player> r = event.getRecipients();
        
        for (Player pls : Bukkit.getServer().getOnlinePlayers()) {
        	if (!plugin.i.containsKey(pls))
        		return;
        	if (plugin.i.get(pls).contains(player)) {
        		r.remove(pls);
        	}
        }
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		event.setFoodLevel(20);
		event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onServerListPing(ServerListPingEvent event) {
		String motd = plugin.getConfig().getString("motd.system");
        motd = motd.replaceAll("&", "\u00A7");
        
        event.setMotd(motd);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDeath(PlayerDeathEvent event) {
		event.setDroppedExp(0);
		event.getDrops().clear();
		
		Player player = event.getEntity();
		final Location locationEnd = new Location(Bukkit.getWorld("world"), 664.52575, 26.460, -1277.28255);
		Player deadPlayer = event.getEntity().getPlayer();
		/*PacketPlayInClientCommand in = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
		EntityPlayer cPlayer = ((CraftPlayer) player).getHandle();
		
		cPlayer.playerConnection.a(in);*/
		
		List<String> ListKS5 = plugin.cm.getListsConfig().getStringList("5KS");
	    List<String> ListKS20 = plugin.cm.getListsConfig().getStringList("20KS");
	    
	    int playerKS = plugin.cm.getKSConfig().getInt("Players." + player.getName() + ".Stats.KS");
	    
	    if (playerKS > 4) {
	    	Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "KillStreak" + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + player.getName() + " had an amazing killstreak of " + playerKS + " kills!");
	    } if (event.getEntity().getKiller() instanceof Player) {
	    	Player killer = event.getEntity().getKiller();
	    	
	    	int killerKS = plugin.cm.getKSConfig().getInt("Players." + killer.getName() + ".Stats.KS");
	    	
	    	plugin.cm.getKSConfig().set("Players." + killer.getName() + ".Stats.KS", Integer.valueOf(killerKS + 1));
	    	
	    	int killerKS2 = plugin.cm.getKSConfig().getInt("Players." + killer.getName() + ".Stats.KS");
	    	
	    	if (killerKS2 > 2) {
	    		killer.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "KillStreak" + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + "You have killstreak of " + killerKS);
	    	} if ((killerKS2 == 5) || (killerKS2 == 10) || (killerKS2 == 15) || (killerKS2 == 20) || (killerKS2 == 25) || (killerKS2 == 30) || (killerKS2 == 35) || (killerKS2 == 40) || (killerKS2 == 45) || (killerKS2 == 50) || (killerKS2 == 55) || (killerKS2 == 60) || (killerKS2 == 65) || (killerKS2 == 70) || (killerKS2 == 75) || (killerKS2 == 80) || (killerKS2 == 85) || (killerKS2 == 90) || (killerKS2 == 95) || (killerKS2 == 100)) {
	    		List<PotionEffectType> potionEffect = Arrays.asList(new PotionEffectType[] {
	    				PotionEffectType.SPEED, PotionEffectType.DAMAGE_RESISTANCE, PotionEffectType.FIRE_RESISTANCE, PotionEffectType.INCREASE_DAMAGE, PotionEffectType.REGENERATION, PotionEffectType.getById(21)
	    		});
	    		
	    		int killerKSG = killerKS2 / 5 + 2;
	    		
	    		Random random = new Random();
	    		
	    		killer.addPotionEffect(new PotionEffect((PotionEffectType) potionEffect.get(random.nextInt(potionEffect.size())), 600, 0));
	    		killer.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "KillStreak" + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + "You got a random potion effect!");
	            killer.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "KillStreak" + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + "You also got a " + ChatColor.GOLD + killerKSG + ChatColor.YELLOW + " coins!");
	            
	            int coinsG = plugin.getConfig().getInt("Players." + killer.getName() + ".Stats.Coins");
	            
	            plugin.getConfig().set("Players." + killer.getName() + ".Stats.Coins", Integer.valueOf(coinsG + killerKSG));
	            plugin.saveConfig();
	    	}
	    	
	    	int HKS = plugin.cm.getHKSConfig().getInt("Players." + killer.getName() + ".Stats.HKS");
	    	
	    	if (killerKS2 > HKS) {
	    		plugin.cm.getHKSConfig().set("Players." + killer.getName() + ".Stats.HKS", Integer.valueOf(killerKS2));
	            plugin.cm.saveHKSConfig();
	    	} if ((killerKS2 > 5) && (!ListKS5.contains(killer.getName()))) {
	    		int coins = this.plugin.getConfig().getInt("Players." + killer.getName() + ".Stats.Coins");
	    		
	    		plugin.getConfig().set("Players." + killer.getName() + ".Stats.Coins", Integer.valueOf(coins + 20));
	            plugin.saveConfig();
	            
	            killer.getWorld().playSound(killer.getLocation(), Sound.LEVEL_UP, 10.0F, 3.0F);
	            
	            Bukkit.broadcastMessage(killer.getName() + " has just earned the achievement " + ChatColor.GREEN + "[New Killer]");
	            
	            ListKS5.add(killer.getName());
	            
	            plugin.cm.getListsConfig().set("5KS", ListKS5);
	            plugin.cm.saveListsConfig();
	    	} if ((killerKS2 > 20) && (!ListKS20.contains(killer.getName()))) {
	    		int coins = this.plugin.getConfig().getInt("Players." + killer.getName() + ".Stats.Coins");
	    		
	    		plugin.getConfig().set("Players." + killer.getName() + ".Stats.Coins", Integer.valueOf(coins + 20));
	    		
	    		killer.getWorld().playSound(killer.getLocation(), Sound.LEVEL_UP, 10.0F, 3.0F);
	    		
	    		Bukkit.broadcastMessage(killer.getName() + " has just earned the achievement " + ChatColor.GREEN + "[Ultra Killer]");
	    		
	    		ListKS20.add(killer.getName());
	    		
	    		plugin.cm.getListsConfig().set("20KS", ListKS20);
	            plugin.cm.saveListsConfig();
	    	}
	    } if (event.getEntity().getKiller() instanceof Player) {
	    	Player death = event.getEntity().getPlayer();
	        Player killer = event.getEntity().getKiller();
	        
	        int coins = this.plugin.getConfig().getInt("Players." + killer.getName() + ".Stats.Coins");
	        
	        if ((killer.hasPermission("zentaxpvp.vip.kill")) || (killer.isOp()) || (this.plugin.cm.getGroupsConfig().getBoolean("Groups.VIP." + player.getName()) == true)) {
	        	plugin.getConfig().set("Players." + killer.getName() + ".Stats.Coins", Integer.valueOf(coins + 2));
	        } else {
	        	plugin.getConfig().set("Players." + killer.getName() + ".Stats.Coins", Integer.valueOf(coins + 1));
	        } if (killer != death) {
	        	event.setDeathMessage(ChatColor.GREEN + killer.getName() + ChatColor.WHITE + " killed " + ChatColor.RED + death.getName());
	        }
	    } if (event.getEntity() instanceof Player) {
	    	int deaths = plugin.cm.getDeathsConfig().getInt("Players." + player.getName() + ".Stats.Deaths");
	    	
	    	plugin.cm.getDeathsConfig().set("Players." + player.getName() + ".Stats.Deaths", Integer.valueOf(deaths + 1));
	        plugin.cm.saveDeathsConfig();
	        
	        if (event.getEntity().getKiller() instanceof Player) {
	        	Player killer = event.getEntity().getKiller();
	        	
	        	int kills = plugin.cm.getKillsConfig().getInt("Players." + killer.getName() + ".Stats.Kills");
	        	
	        	plugin.cm.getKillsConfig().set("Players." + killer.getName() + ".Stats.Kills", Integer.valueOf(kills + 1));
	            plugin.cm.getDeathsConfig().set("Players." + player.getName() + ".Stats.Deaths", Integer.valueOf(deaths + 1));
	        }
	    } if (event.getEntity().getKiller() instanceof Player) {
	    	Player killerPlayer = event.getEntity().getKiller();
	    	
	    	List<String> listOne50 = plugin.cm.getListsConfig().getStringList("1v150");
	        List<String> listOne150 = plugin.cm.getListsConfig().getStringList("1v1150");
	        
	        if ((plugin.Red.contains(deadPlayer.getName())) || (plugin.Blue.contains(deadPlayer.getName()))) {
	        	int v1W = plugin.cm.get1v1Config().getInt(killerPlayer.getName() + " 1v1");
	        	
	            killerPlayer.getInventory().clear();
	            killerPlayer.getInventory().clear();
	            killerPlayer.getInventory().setHelmet(null);
	            killerPlayer.getInventory().setChestplate(null);
	            killerPlayer.getInventory().setLeggings(null);
	            killerPlayer.getInventory().setBoots(null);
	            
	            plugin.Blue.clear();
	            plugin.Red.clear();
	            
	            killerPlayer.teleport(locationEnd);
	            killerPlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "]" + ChatColor.WHITE + " You won!");
	            killerPlayer.setHealth(20.0D);
	            deadPlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "]" + ChatColor.WHITE + " You losed!");
	            deadPlayer.teleport(locationEnd);
	            
	            plugin.cm.get1v1Config().set(killerPlayer.getName() + " 1v1", v1W + 1);
	            plugin.cm.save1v1Config();
	            
	            if (v1W >= 50) {
	            	if (!listOne50.contains(killerPlayer.getName())) {
	            		int coins = plugin.getConfig().getInt("Players." + killerPlayer.getName() + ".Stats.Coins");
	            		
	            		plugin.getConfig().set("Players." + killerPlayer.getName() + ".Stats.Coins", coins + 20);
	            		plugin.saveConfig();
	            		
	            		killerPlayer.getWorld().playSound(killerPlayer.getLocation(), Sound.LEVEL_UP, 10, 1);
	            		
	            		Bukkit.broadcastMessage(killerPlayer.getName() + " has just earned the achievement " + ChatColor.GREEN + "[New Versus Killer]");
	            		
	            		listOne50.add(killerPlayer.getName());
	            		
	            		plugin.cm.getListsConfig().set("1v150", listOne50);
	            		plugin.cm.saveListsConfig();
	            	}
	            } if (v1W >= 150) {
        			if (!listOne150.contains(killerPlayer.getName())) {
        				int coins = plugin.getConfig().getInt("Players." + killerPlayer.getName() + ".Stats.Coins");
        				
        				plugin.getConfig().set("Players." + killerPlayer.getName() + ".Stats.Coins", coins + 20);
        				plugin.saveConfig();
        				
        				killerPlayer.getWorld().playSound(killerPlayer.getLocation(), Sound.LEVEL_UP, 10, 1);
        				
        				Bukkit.broadcastMessage(killerPlayer.getName() + " has just earned the achievement " + ChatColor.GREEN + "[Ultra Versus Killer]");
        				
        				listOne150.add(killerPlayer.getName());
        				
        				plugin.cm.getListsConfig().set("1v1150", listOne150);
        				plugin.cm.saveListsConfig();
        			}
        		}
	        } if ((plugin.Red2.contains(deadPlayer.getName())) || (plugin.Blue2.contains(deadPlayer.getName()))) {
	        	int v1W = plugin.cm.get1v1Config().getInt(killerPlayer.getName() + " 1v1");
	        	
	            killerPlayer.getInventory().clear();
	            killerPlayer.getInventory().clear();
	            killerPlayer.getInventory().setHelmet(null);
	            killerPlayer.getInventory().setChestplate(null);
	            killerPlayer.getInventory().setLeggings(null);
	            killerPlayer.getInventory().setBoots(null);
	            
	            plugin.Blue2.clear();
	            plugin.Red2.clear();
	            
	            killerPlayer.teleport(locationEnd);
	            killerPlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "]" + ChatColor.WHITE + " You won!");
	            killerPlayer.setHealth(20.0D);
	            deadPlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "]" + ChatColor.WHITE + " You losed!");
	            deadPlayer.teleport(locationEnd);
	            
	            plugin.cm.get1v1Config().set(killerPlayer.getName() + " 1v1", v1W + 1);
	            plugin.cm.save1v1Config();
	            
	            if (v1W >= 50) {
	            	if (!listOne50.contains(killerPlayer.getName())) {
	            		int coins = plugin.getConfig().getInt("Players." + killerPlayer.getName() + ".Stats.Coins");
	            		
	            		plugin.getConfig().set("Players." + killerPlayer.getName() + ".Stats.Coins", coins + 20);
	            		plugin.saveConfig();
	            		
	            		killerPlayer.getWorld().playSound(killerPlayer.getLocation(), Sound.LEVEL_UP, 10, 1);
	            		
	            		Bukkit.broadcastMessage(killerPlayer.getName() + " has just earned the achievement " + ChatColor.GREEN + "[New Versus Killer]");
	            		
	            		listOne50.add(killerPlayer.getName());
	            		
	            		plugin.cm.getListsConfig().set("1v150", listOne50);
	            		plugin.cm.saveListsConfig();
	            	}
	            } if (v1W >= 150) {
        			if (!listOne150.contains(killerPlayer.getName())) {
        				int coins = plugin.getConfig().getInt("Players." + killerPlayer.getName() + ".Stats.Coins");
        				
        				plugin.getConfig().set("Players." + killerPlayer.getName() + ".Stats.Coins", coins + 20);
        				plugin.saveConfig();
        				
        				killerPlayer.getWorld().playSound(killerPlayer.getLocation(), Sound.LEVEL_UP, 10, 1);
        				
        				Bukkit.broadcastMessage(killerPlayer.getName() + " has just earned the achievement " + ChatColor.GREEN + "[Ultra Versus Killer]");
        				
        				listOne150.add(killerPlayer.getName());
        				
        				plugin.cm.getListsConfig().set("1v1150", listOne150);
        				plugin.cm.saveListsConfig();
        			}
        		}
	        }
	    }
	    
	    /*try {
	    	Object nmsPlayer = player.getClass().getMethod("getHandle").invoke(player);
            Object packet = Class.forName(nmsPlayer.getClass().getPackage().getName() + ".PacketPlayInClientCommand").newInstance();
            Class<?> enumClass = Class.forName(nmsPlayer.getClass().getPackage().getName() + ".EnumClientCommand");
            
            for (Object ob : enumClass.getEnumConstants()) {
            	if (ob.toString().equals("PERFORM_RESPAWN")) {
            		packet = packet.getClass().getConstructor(enumClass).newInstance(ob);
            	}
            }
            
            Object con = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
            con.getClass().getMethod("a", packet.getClass()).invoke(con, packet);
	    } catch (Throwable t) {
	    	t.printStackTrace();
	    }*/
	    
	    plugin.cm.getKSConfig().set("Players." + player.getName() + ".Stats.KS", Integer.valueOf(0));
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerEggThrow(PlayerEggThrowEvent event) {
		Player player = event.getPlayer();
		
		try {
			if ((player.getItemInHand().getType() == Material.EGG) && (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "" + ChatColor.BOLD + "Chicken's Egg"))) {
				ItemStack item = player.getItemInHand();
				
				if (item.getAmount() > 1) {
					item.setAmount(item.getAmount() - 1);
					
					player.updateInventory();
				} else {
					player.setItemInHand(null);
					player.updateInventory();
				}
				
				event.setHatching(false);
				
				Location location = new Location(Bukkit.getWorld("world"), event.getEgg().getLocation().getX(), event.getEgg().getLocation().getY() + 0.5, event.getEgg().getLocation().getZ());
				final Chicken c1 = (Chicken) player.getWorld().spawnEntity(location, EntityType.CHICKEN);
				final Chicken c2 = (Chicken) player.getWorld().spawnEntity(location, EntityType.CHICKEN);
				final Chicken c3 = (Chicken) player.getWorld().spawnEntity(location, EntityType.CHICKEN);
				
				c1.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, 3));
				c1.setCustomName(ChatColor.DARK_RED + "Bomb " + ChatColor.GRAY + "[" + ChatColor.GREEN + player.getName() + ChatColor.GRAY + "]");
				c1.damage(0.01, player);
				c2.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, 3));
				c2.setCustomName(ChatColor.DARK_RED + "Bomb " + ChatColor.GRAY + "[" + ChatColor.GREEN + player.getName() + ChatColor.GRAY + "]");
				c2.damage(0.01, player);
				c3.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, 3));
				c3.setCustomName(ChatColor.DARK_RED + "Bomb " + ChatColor.GRAY + "[" + ChatColor.GREEN + player.getName() + ChatColor.GRAY + "]");
				c3.damage(0.01, player);
				
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

					@Override
					public void run() {
						c1.getWorld().createExplosion(c1.getLocation().getX(), c1.getLocation().getY(), c1.getLocation().getZ(), 3F, false, false);
						c1.remove();
					}
					
				}, 60L);
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

					@Override
					public void run() {
						c2.getWorld().createExplosion(c2.getLocation().getX(), c2.getLocation().getY(), c2.getLocation().getZ(), 3F, false, false);
						c2.remove();
					}
					
				}, 60L);
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

					@Override
					public void run() {
						c3.getWorld().createExplosion(c3.getLocation().getX(), c3.getLocation().getY(), c3.getLocation().getZ(), 3F, false, false);
						c3.remove();
					}
					
				}, 60L);
			}
		} catch (NullPointerException e) {
			
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		Item item = event.getItem();
		
		if ((item.getItemStack().getType() == Material.FEATHER) || (item.getItemStack().getType() == Material.ARROW) || (item.getItemStack().getType() == Material.NETHER_STAR) || (item.getItemStack().getType() == Material.RAW_CHICKEN) || (item.getItemStack().getType() == Material.EXP_BOTTLE) || (item.getItemStack().getType() == Material.LEASH)) {
			if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
		
		World w = Bukkit.getServer().getWorld(plugin.cm.getDataConfig().getString("spawn.world"));
        double x = plugin.cm.getDataConfig().getDouble("spawn.x");
        double y = plugin.cm.getDataConfig().getDouble("spawn.y");
        double z = plugin.cm.getDataConfig().getDouble("spawn.z");
        float yaw = (float) plugin.cm.getDataConfig().getDouble("spawn.yaw");
        float pitch = (float) plugin.cm.getDataConfig().getDouble("spawn.pitch");
        
        event.setRespawnLocation(new Location(w, x, y, z, yaw, pitch));
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		
		if (player.getGameMode() != GameMode.CREATIVE) {
			event.setCancelled(true);
		} if (event.getItemDrop().getItemStack().getType() == Material.BOWL) {
			event.setCancelled(false);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		Material material = player.getLocation().getBlock().getType();
		
		double xfrom = event.getFrom().getX();
	    double zfrom = event.getFrom().getZ();
	    double xto = event.getTo().getX();
	    double zto = event.getTo().getZ();
	    
	    if (((xfrom != xto) || (zfrom != zto)) && (plugin.spawn.contains(player.getName()))) {
	    	player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "Teleportaion has been cancelled!");
	        player.playSound(player.getLocation(), Sound.ITEM_BREAK, 5.0F, 1.0F);
	        
	        plugin.spawn.remove(player.getName());
	    }
		
		if (plugin.frozen.contains(player.getName())) {
			event.setTo(event.getFrom());
			
			player.sendMessage(plugin.titleZ + ChatColor.RED + "You are frozen!");
		}
		
		if ((((player.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.SPONGE) && player.getLocation().subtract(0, 2, 0).getBlock().getType() != Material.GLOWSTONE) && player.getLocation().subtract(0, 2, 0).getBlock().getType() != Material.STONE)) {
			player.setVelocity(player.getLocation().getDirection().multiply(3).setY(3.3F));
			
			if (!plugin.noFall.contains(player.getName())) {
				plugin.noFall.add(player.getName());
			}
		} if (((player.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.SPONGE) && player.getLocation().subtract(0, 2, 0).getBlock().getType() == Material.STONE)) {
			player.setVelocity(player.getLocation().getDirection().setY(5));
			
			if (!plugin.noFall.contains(player.getName())) {
				plugin.noFall.add(player.getName());
			}
		} if ((((player.getLocation().subtract(0, 1, 0).getBlock().getTypeId() == 170) && player.getLocation().subtract(0, 2, 0).getBlock().getType() == Material.BEDROCK) && !player.isDead())) {
			player.setHealth(0.0);
		} if (player.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.NOTE_BLOCK) {
			player.setVelocity(player.getLocation().getDirection().multiply(2F).setY(player.getVelocity().getY() + 0.5F));
			
			if (!plugin.noFall.contains(player.getName())) {
				plugin.noFall.add(player.getName());
			}
		} if ((material == Material.STATIONARY_LAVA) || (material == Material.LAVA)) {
			try {
				if ((player.getInventory().getChestplate().getType() == Material.GOLD_CHESTPLATE) && (player.getInventory().getChestplate().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Pyro's Chestplate"))) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 45, 0));
					player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40, 0));
					player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 2147483647, 0));
					player.setFireTicks(0);
				}
			} catch (NullPointerException e) {
				
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerFish(PlayerFishEvent event) {
		final Player player = event.getPlayer();
		
		player.getItemInHand().setDurability((short) 0);
		
		if (event.getState() == State.CAUGHT_ENTITY) {
			/*if (event.getCaught().getType() == EntityType.PLAYER) {
				final Player caught = (Player) event.getCaught();
				
				caught.teleport(player.getLocation());
				caught.playSound(caught.getLocation(), Sound.WATER, 5, 1);
				player.playSound(player.getLocation(), Sound.WATER, 5, 1);
				
				plugin.fisher.add(player.getName());
				plugin.fished.add(caught.getName());
		        
		        caught.sendMessage(plugin.titleZ + ChatColor.GOLD + "" + ChatColor.BOLD + "You have been fished! You can't attack the Fisherman for 2 seconds!");
		        player.sendMessage(plugin.titleZ + ChatColor.GOLD + "" + ChatColor.BOLD + "You can't attack your target for 2 seconds!");
		        
		        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

					@Override
					public void run() {
						plugin.fisher.remove(player.getName());
						plugin.fished.remove(caught.getName());
					}
		        	
		        }, 40L);
			} */if (event.getCaught() == player) {
				event.setCancelled(true);
			} else {
				final Player caught = (Player) event.getCaught();
				
				caught.teleport(player.getLocation());
				caught.playSound(caught.getLocation(), Sound.WATER, 5, 1);
				player.playSound(player.getLocation(), Sound.WATER, 5, 1);
				
				plugin.fisher.add(player.getName());
				plugin.fished.add(caught.getName());
		        
		        caught.sendMessage(plugin.titleZ + ChatColor.GOLD + "" + ChatColor.BOLD + "You have been fished! You can't attack the Fisherman for 2 seconds!");
		        player.sendMessage(plugin.titleZ + ChatColor.GOLD + "" + ChatColor.BOLD + "You can't attack your target for 2 seconds!");
		        
		        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

					@Override
					public void run() {
						plugin.fisher.remove(player.getName());
						plugin.fished.remove(caught.getName());
					}
		        	
		        }, 40L);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerWarp(PlayerWarpEvent event) {
		Bukkit.getServer().broadcastMessage(ChatColor.GREEN + event.getPlayer().getName() + " warped to warp " + event.getWarpName() + "!");
	}
}
