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
package net.hypsiz.zentaxpvp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.hypsiz.zentaxpvp.api.EnforcerEvent;
import net.hypsiz.zentaxpvp.api.PlayerWarpEvent;
import net.hypsiz.zentaxpvp.commands.PlayerCommands;
import net.hypsiz.zentaxpvp.entity.CustomEntityType;
import net.hypsiz.zentaxpvp.listeners.BlockListener;
import net.hypsiz.zentaxpvp.listeners.EntityListener;
import net.hypsiz.zentaxpvp.listeners.HangingListener;
import net.hypsiz.zentaxpvp.listeners.PlayerListener;
import net.hypsiz.zentaxpvp.listeners.SignListener;
import net.hypsiz.zentaxpvp.util.ConfigManager;
import net.hypsiz.zentaxpvp.util.Manager;
import net.hypsiz.zentaxpvp.util.Report;
import net.hypsiz.zentaxpvp.util.ShopMenu;
import net.hypsiz.zentaxpvp.util.Type;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

/**
 * @author Hypsiz
 *
 */
public final class Main extends JavaPlugin {

	public String titleZ = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_PURPLE + "Zentax" + ChatColor.DARK_GRAY + "] ";
	public String titleZP = ChatColor.GREEN + "-={" + ChatColor.DARK_PURPLE + " Zentax PvP " + ChatColor.GREEN + "}=-";
	public String titleZentaxP = ChatColor.DARK_PURPLE + "ZentaxPvP";
	public static String titleZPS = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_PURPLE + "ZentaxPvP" + ChatColor.DARK_GRAY + "] ";
	public String titleKit = ChatColor.GRAY + "[" + ChatColor.BLACK + "Kit" + ChatColor.GRAY + "]";
	public String titleWarp = ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "Warp" + ChatColor.DARK_GRAY + "]";
	public String title1v1 = ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "1v1" + ChatColor.DARK_GRAY + "]";
	public String title1v1S = ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "1v1S" + ChatColor.DARK_GRAY + "]";
	public String titleRepair = ChatColor.DARK_GRAY + "[" + ChatColor.BLACK + "Repair" + ChatColor.DARK_GRAY + "]";
	public String titleRefill = ChatColor.DARK_GRAY + "[" + ChatColor.BLACK + "Refill" + ChatColor.DARK_GRAY + "]";
	public ArrayList<Player> inCombat = new ArrayList<Player>();
	public ArrayList<String> frozen = new ArrayList<String>();
	public ArrayList<String> fished = new ArrayList<String>();
	public ArrayList<String> fisher = new ArrayList<String>();
	public ArrayList<String> noFall = new ArrayList<String>();
	public ArrayList<Player> vanished = new ArrayList<Player>();
	public ArrayList<String> spawn = new ArrayList<String>();
	public ArrayList<String> Red = new ArrayList<String>();
	public ArrayList<String> Blue = new ArrayList<String>();
	public ArrayList<String> Red2 = new ArrayList<String>();
	public ArrayList<String> Blue2 = new ArrayList<String>();
	public Map<String, String> lastCoords = new HashMap<String, String>();
	public Map<String, Long> lastDateMove = new HashMap<String, Long>();
	public HashMap<Player, ArrayList<Player>> i = Manager.getInstance().getIgnore();
	public HashMap<String, String> invite = new HashMap<String, String>();
	public final HashMap<String, ArrayList<Block>> mutedPlayers = new HashMap<String, ArrayList<Block>>();
	public ConfigManager cm = new ConfigManager(this);
	public ShopMenu shopMenu;
	public static int currentLine = 0;
	public static int task = 0;
	public static int running = 1;
	public static long interavel = 1200L;
	
	@Override
	public void onEnable() {
		registerCommands();
		registerEvents();
		registerConfigs();
		
		try {
			CustomEntityType.registerEntities();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		shopMenu = new ShopMenu(this, this);
		
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				try {
					Main.broadcastMessage("plugins/ZentaxPvP/messages.txt");
				} catch (IOException localIOException) {
					
				}
			}
			
		}, 0L, interavel * 5L);
		
		/*getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				Player[] players = getServer().getOnlinePlayers();
				
				for (Player p : players) {
					String playerName = p.getName();
					long dateLast = ((Long) lastDateMove.get(playerName)).longValue();
					long r = new Date().getTime() - dateLast;
					
					if (r > 250000L) {
						p.kickPlayer("[Zentax] You was AFK kicked.");
						
						getServer().getLogger().info(playerName + " was AFK kicked.");
					}
				}
			}
			
		}, 400L, 400L);*/
	}
	
	@Override
	public void onDisable() {
		registerConfigs();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("summonp")) {
			// Make sure the sender is a player.
			if (!(sender instanceof Player)) {
				sender.sendMessage("Only players can use this command.");
				
				return true;
			}
			
			if ((cm.getGroupsConfig().getBoolean("Groups.Admins." + sender.getName()) == true) || (sender.isOp())) {
				Player player = (Player) sender;
				
				ItemStack paper = new ItemStack(Material.PAPER, 1);
				ItemMeta paperMeta = paper.getItemMeta();
				
				paperMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Summon");
				
				// Make sure that the player specified exactly one argument
				if (args.length != 1) {
					sender.sendMessage(titleZ + ChatColor.YELLOW + "To get a paper summons, you need to write: /summonp <summon-name>");
		            // When onCommand() returns false, the help message associated with that command is displayed.
		            return true;
		        }
				
				if (args[0].equalsIgnoreCase("list")) {
					sender.sendMessage(titleZP);
					sender.sendMessage(ChatColor.YELLOW + "bob, peter, mike.");
				} else if (args[0].equalsIgnoreCase("bob")) {
					paperMeta.setLore(Arrays.asList(ChatColor.GREEN + "Bob"));
					paper.setItemMeta(paperMeta);
					
					player.getInventory().addItem(paper);
				} else if (args[0].equalsIgnoreCase("peter")) {
					paperMeta.setLore(Arrays.asList(ChatColor.GREEN + "Peter"));
					paper.setItemMeta(paperMeta);
					
					player.getInventory().addItem(paper);
				} else if (args[0].equalsIgnoreCase("mike")) {
					paperMeta.setLore(Arrays.asList(ChatColor.GREEN + "Mike"));
					paper.setItemMeta(paperMeta);
					
					player.getInventory().addItem(paper);
				} else {
					sender.sendMessage(titleZ + ChatColor.WHITE + args[0] + ChatColor.DARK_RED + " is not available.");
				}
			} else {
				sender.sendMessage(titleZ + ChatColor.DARK_RED + "You do not have permission to use this command.");
	            
	            return true;
			}
			
			return true;
		} if (cmd.getName().equalsIgnoreCase("get")) {
			// Make sure the sender is a player.
			if (!(sender instanceof Player)) {
				sender.sendMessage("Only players can use this command.");
				
				return true;
			}
			
			if ((cm.getGroupsConfig().getBoolean("Groups.Admins." + sender.getName()) == true) || (sender.isOp())) {
				Player player = (Player) sender;
				
				ItemStack diamond_sword = new ItemStack(Material.DIAMOND_SWORD, 1);
				ItemStack bow = new ItemStack(Material.BOW, 1);
				ItemMeta diamond_swordMeta = diamond_sword.getItemMeta();
				ItemMeta bowMeta = bow.getItemMeta();
				
				diamond_swordMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Warrior");
				bowMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Archer");
				
				// Make sure that the player specified exactly one argument
				if (args.length != 1) {
					sender.sendMessage(titleZ + ChatColor.YELLOW + "To get an item, you need to write: /get <item-name>");
		            // When onCommand() returns false, the help message associated with that command is displayed.
		            return true;
		        }
				
				if (args[0].equalsIgnoreCase("list")) {
					sender.sendMessage(titleZP);
					sender.sendMessage(ChatColor.YELLOW + "warrior, archer.");
				} else if (args[0].equalsIgnoreCase("warrior")) {
					diamond_sword.setItemMeta(diamond_swordMeta);
					
					player.getInventory().addItem(diamond_sword);
				} else if (args[0].equalsIgnoreCase("archer")) {
					bow.setItemMeta(bowMeta);
					
					player.getInventory().addItem(bow);
				} else {
					sender.sendMessage(titleZ + ChatColor.WHITE + args[0] + ChatColor.DARK_RED + " is not available.");
				}
			} else {
				sender.sendMessage(titleZ + ChatColor.DARK_RED + "You do not have permission to use this command.");
			}
			
			return true;
		} if (cmd.getName().equalsIgnoreCase("setmotd")) {
			if ((cm.getGroupsConfig().getBoolean("Groups.Admins." + sender.getName()) == true) || (sender.isOp())) {
				if (args.length == 0) {
	                sender.sendMessage(titleZ + ChatColor.RED + "Please specify a message!");
	                
	                return true;
				}
				
				StringBuilder str = new StringBuilder();
				
				for (int i = 0; i < args.length; i++) {
	                str.append(args[i] + " ");
				}
				
				String motd = str.toString();
				
		        getConfig().set("motd.system", motd);
		        saveConfig();
		        
		        String system = getConfig().getString("motd.system");
		        system = system.replaceAll("&", "§");
		        
		        sender.sendMessage(ChatColor.GREEN + "MOTD set to: " + system);
		        
		        return true;
			} else {
				sender.sendMessage(titleZ + ChatColor.DARK_RED + "You do not have permission to use this command.");
	            
	            return true;
			}
		} if (cmd.getName().equalsIgnoreCase("gm")) {
			// Your game mode has been updated!
			
			if ((cm.getGroupsConfig().getBoolean("Groups.Mods." + sender.getName()) == true) || (sender.isOp())) {
				if (args.length != 1) {
					sender.sendMessage(titleZ + ChatColor.RED + "Please specify a gamemode!");
		            // When onCommand() returns false, the help message associated with that command is displayed.
		            return true;
		        } if (args.length == 1) {
		        	Player player = (Player) sender;
		        	if (args[0].equalsIgnoreCase("0")) {
		        		player.setGameMode(GameMode.SURVIVAL);
		        	} if (args[0].equalsIgnoreCase("1")) {
		        		player.setGameMode(GameMode.CREATIVE);
		        	} if (args[0].equalsIgnoreCase("2")) {
		        		player.setGameMode(GameMode.ADVENTURE);
		        	}
		        }
			} else {
				sender.sendMessage(titleZ + ChatColor.DARK_RED + "You do not have permission to use this command.");
	            
	            return true;
			}
			
			return true;
		} if (cmd.getName().equalsIgnoreCase("read")) {
			if ((cm.getGroupsConfig().getBoolean("Groups.Mods." + sender.getName()) == true) || (sender.isOp())) {
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("clear")) {
						Report.clearReports();
						Report.saveReports(this);
						Report.sendMessage(sender, titleZ + ChatColor.GREEN + "Reports has been cleared!");
					}
				} else {
					sender.sendMessage(titleZP);
					
					Report.readReports(sender);
				}
			} else {
				Report.sendMessage(sender, titleZ + ChatColor.DARK_RED + "You do not have permission to read reports.");
				
				return true;
			}
			
			return true;
		} if (cmd.getName().equalsIgnoreCase("stopbroadcast")) {
			if ((cm.getGroupsConfig().getBoolean("Groups.Admins." + sender.getName()) == true) || (sender.isOp())) {
				if (running == 1) {
					Bukkit.getServer().getScheduler().cancelTask(task);
					
					sender.sendMessage(titleZ + ChatColor.GREEN + "Cancelled broadcasts.");
			        
			        running = 0;
			        
			        return true;
				}
				
				sender.sendMessage(titleZ + ChatColor.DARK_RED + "They aren't running!");
				
				return true;
			} else {
				sender.sendMessage(titleZ + ChatColor.DARK_RED + "You do not have permission to use this command.");
	            
	            return true;
			}
		} if (cmd.getName().equalsIgnoreCase("startbroadcast")) {
			if ((cm.getGroupsConfig().getBoolean("Groups.Admins." + sender.getName()) == true) || (sender.isOp())) {
				if (running == 1) {
					sender.sendMessage(titleZ + ChatColor.DARK_RED + "They are still running!");
			        
			        return true;
				}
				
				task = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

					@Override
					public void run() {
						try {
							Main.broadcastMessage("plugins/ZentaxPvP/messages.txt");
						} catch (IOException localIOException) {
							
						}
					}
					
				}, 0L, interavel * 5L);
				
				sender.sendMessage(titleZ + ChatColor.GREEN + "Started broadcasts.");
				
				running = 1;
				
				return true;
			} else {
				sender.sendMessage(titleZ + ChatColor.DARK_RED + "You do not have permission to use this command.");
	            
	            return true;
			}
		} if (cmd.getName().equalsIgnoreCase("setwarp")) {
			if ((cm.getGroupsConfig().getBoolean("Groups.Admins." + sender.getName()) == true) || (sender.isOp())) {
				if (args.length == 0) {
					sender.sendMessage(titleZ + ChatColor.RED + "Please specify a name!");
					
                    return true;
				}
				
				Player player = (Player) sender;
				
				cm.getDataConfig().set("warps." + args[0] + ".world", player.getLocation().getWorld().getName());
				cm.getDataConfig().set("warps." + args[0] + ".x", player.getLocation().getX());
				cm.getDataConfig().set("warps." + args[0] + ".y", player.getLocation().getY());
				cm.getDataConfig().set("warps." + args[0] + ".z", player.getLocation().getZ());
				cm.saveDataConfig();
				
				sender.sendMessage(ChatColor.GREEN + "Set warp " + args[0] + "!");
			} else {
				sender.sendMessage(titleZ + ChatColor.DARK_RED + "You do not have permission to use this command.");
	            
	            return true;
			}
			
			return true;
		} if (cmd.getName().equalsIgnoreCase("warp")) {
			if ((cm.getGroupsConfig().getBoolean("Groups.Mods." + sender.getName()) == true) || (sender.isOp())) {
				if (args.length == 0) {
					sender.sendMessage(titleZ + ChatColor.RED + "Please specify a name!");
					
                    return true;
				}
				
				if (cm.getDataConfig().getConfigurationSection("warps." + args[0]) == null) {
					sender.sendMessage(titleZ + ChatColor.RED + "Warp " + args[0] + " does not exist!");
					
					return true;
				}
				
				Player player = (Player) sender;
				World w = Bukkit.getServer().getWorld(cm.getDataConfig().getString("warps." + args[0] + ".world"));
				
	            double x = cm.getDataConfig().getDouble("warps." + args[0] + ".x");
	            double y = cm.getDataConfig().getDouble("warps." + args[0] + ".y");
	            double z = cm.getDataConfig().getDouble("warps." + args[0] + ".z");
	            
	            Location l = new Location(w, x, y, z);
	            
	            player.teleport(l);
	            
	            Bukkit.getServer().getPluginManager().callEvent(new PlayerWarpEvent(player, l, args[0]));
	            
	            player.sendMessage(titleZ + ChatColor.GREEN + "Teleported to " + args[0] + "!");
			} else {
				sender.sendMessage(titleZ + ChatColor.DARK_RED + "You do not have permission to use this command.");
	            
	            return true;
			}
			
			return true;
		} if (cmd.getName().equalsIgnoreCase("delwarp")) {
			if ((cm.getGroupsConfig().getBoolean("Groups.Admins." + sender.getName()) == true) || (sender.isOp())) {
				if (args.length == 0) {
					sender.sendMessage(titleZ + ChatColor.RED + "Please specify a name!");
					
                    return true;
				}
				
				if (cm.getDataConfig().getConfigurationSection("warps." + args[0]) == null) {
					sender.sendMessage(titleZ + ChatColor.RED + "Warp " + args[0] + " does not exist!");
					
                    return true;
				}
				
				cm.getDataConfig().set("warps." + args[0], null);
	            cm.saveDataConfig();
	            
	            sender.sendMessage(titleZ + ChatColor.GREEN + "Removed warp " + args[0] + "!");
			} else {
				sender.sendMessage(titleZ + ChatColor.DARK_RED + "You do not have permission to use this command.");
	            
	            return true;
			}
			
			return true;
		} if (cmd.getName().equalsIgnoreCase("ci")) {
			if ((cm.getGroupsConfig().getBoolean("Groups.Mods." + sender.getName()) == true) || (sender.isOp())) {
				Player player = (Player) sender;
				
				player.getInventory().clear();
				player.getInventory().setArmorContents(null);
				
	            sender.sendMessage(titleZ + ChatColor.GREEN + "Inventory cleared!");
	            
	            return true;
			} else {
				sender.sendMessage(titleZ + ChatColor.DARK_RED + "You do not have permission to use this command.");
	            
	            return true;
			}
		} if (cmd.getName().equalsIgnoreCase("freeze")) {
			if ((cm.getGroupsConfig().getBoolean("Groups.Mods." + sender.getName()) == true) || (sender.isOp())) {
				if (args.length != 1) {
					sender.sendMessage(titleZ + ChatColor.RED + "Please specify a target!");
					
					return true;
				}
				
				Player target = Bukkit.getServer().getPlayer(args[0]);
				
				if (target == null) {
					sender.sendMessage(titleZ + ChatColor.RED + "Could not find target " + args[0] + "!");
					
					return true;
				} if (frozen.contains(target.getName())) {
					frozen.remove(target.getName());
					
					sender.sendMessage(titleZ + ChatColor.GREEN + target.getName() + " has been unfrozen!");
					
					return true;
				}
				
				frozen.add(target.getName());
				
	            sender.sendMessage(titleZ + ChatColor.GREEN + target.getName() + " has been frozen!");
			} else {
				sender.sendMessage(titleZ + ChatColor.DARK_RED + "You do not have permission to use this command.");
	            
	            return true;
			}
			
			return true;
		} if (cmd.getName().equalsIgnoreCase("mban")) {
			if ((cm.getGroupsConfig().getBoolean("Groups.Mods." + sender.getName()) == true) || (sender.isOp())) {
				if (args.length < 2) {
					sender.sendMessage(titleZ + ChatColor.RED + "/ban <player> <reason>");
					
					return true;
				}
				
				Player target = Bukkit.getServer().getPlayer(args[0]);
				
				if (target == null) {
					sender.sendMessage(titleZ + ChatColor.RED + "Could not find player " + args[0] + "!");
					
					return true;
				}
				
				target.kickPlayer(titleZ + ChatColor.RED + "You have been banned!");
		        target.setBanned(true);
		        
		        Bukkit.getServer().getPluginManager().callEvent(new EnforcerEvent(target, Type.BAN));
		        Bukkit.getServer().broadcastMessage(titleZ + ChatColor.YELLOW + target.getName() + " has been banned by " + sender.getName() + "!");
			} else {
				sender.sendMessage(titleZ + ChatColor.DARK_RED + "You do not have permission to use this command.");
	            
	            return true;
			}
			
			return true;
		} if (cmd.getName().equalsIgnoreCase("warn")) {
			if ((cm.getGroupsConfig().getBoolean("Groups.Mods." + sender.getName()) == true) || (sender.isOp())) {
				if (args.length < 2) {
					sender.sendMessage(titleZ + ChatColor.RED + "/warn <player> <reason>");
					
	                return true;
				}
				
				final Player target = Bukkit.getServer().getPlayer(args[0]);
			       
		        if (target == null) {
		                sender.sendMessage(titleZ + ChatColor.RED + "Could not find target " + args[0]);
		                return true;
		        }
		        
		        String msg = "";
		        
		        for (int i = 1; i < args.length; i++) {
		        	msg += args[i] + " ";
		        }
		        
		        Object level = this.getConfig().get(target.getName());
		        
		        if (level == null) {
		        	target.sendMessage(titleZ + ChatColor.RED + msg);
		        	
		        	getConfig().set(target.getName(), Integer.valueOf(1));
		        	saveConfig();
		        	
		        	return true;
		        }
		        
		        int l = Integer.parseInt(level.toString());
		        
		        if (l == 1) {
		        	target.kickPlayer(titleZ + ChatColor.RED + msg);
		        	
		        	getConfig().set(target.getName(), Integer.valueOf(2));
		        	saveConfig();
		        	
		        	return true;
		        }
		        
		        if (l == 2) {
		        	target.kickPlayer(titleZ + ChatColor.RED + msg);
		        	target.setBanned(true);
		        	
		        	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

		        		@Override
		        		public void run() {
		        			target.setBanned(false);
		        		}
		        		
		        	}, 5 * 20L);
		        	
		        	return true;
		        }
		        
		        return true;
			} else {
				sender.sendMessage(titleZ + ChatColor.DARK_RED + "You do not have permission to use this command.");
	            
	            return true;
			}
		} if (cmd.getName().equalsIgnoreCase("reloadConfigs")) {
			if ((cm.getGroupsConfig().getBoolean("Groups.Admins." + sender.getName()) == true) || (sender.isOp())) {
				cm.reloadCombatConfig();
				cm.reloadDataConfig();
				cm.reloadDeathsConfig();
				cm.reloadGroupsConfig();
				cm.reloadHKSConfig();
				cm.reloadKillsConfig();
				cm.reloadListsConfig();
				cm.reloadReportsConfig();
				
				sender.sendMessage(titleZ + ChatColor.GREEN + "All configs reloaded successfully!");
			} else {
				sender.sendMessage(titleZ + ChatColor.DARK_RED + "You do not have permission to use this command.");
	            
	            return true;
			}
			
			return true;
		} if (cmd.getName().equalsIgnoreCase("reloadMainConfig")) {
			if ((cm.getGroupsConfig().getBoolean("Groups.Admins." + sender.getName()) == true) || (sender.isOp())) {
				reloadConfig();
				
				sender.sendMessage(titleZ + ChatColor.GREEN + "Main config reloaded successfully!");
			} else {
				sender.sendMessage(titleZ + ChatColor.DARK_RED + "You do not have permission to use this command.");
	            
	            return true;
			}
			
			return true;
		} if (cmd.getName().equalsIgnoreCase("kick")) {
			if ((cm.getGroupsConfig().getBoolean("Groups.Mods." + sender.getName()) == true) || (sender.isOp())) {
				if (args.length == 0) {
	                sender.sendMessage(titleZ + ChatColor.RED + "Please specify a player!");
	                
	                return true;
				}
				
				Player target = Bukkit.getServer().getPlayer(args[0]);
				
				if (target == null) {
	                sender.sendMessage(titleZ + ChatColor.RED + "Could not find target " + args[0] + "!");
	                return true;
				}
				
				target.kickPlayer(titleZ + ChatColor.RED + "You have been kicked!");
				
				Bukkit.getServer().getPluginManager().callEvent(new EnforcerEvent(target, Type.KICK));
				Bukkit.getServer().broadcastMessage(titleZ + ChatColor.YELLOW + target.getName() + " has been kicked by " + sender.getName() + "!");
			} else {
				sender.sendMessage(titleZ + ChatColor.DARK_RED + "You do not have permission to use this command.");
	            
	            return true;
			}
			
			return true;
		} if (cmd.getName().equalsIgnoreCase("vanish")) {
			if ((cm.getGroupsConfig().getBoolean("Groups.Mods." + sender.getName()) == true) || (sender.isOp())) {
				Player player = (Player) sender;
				
		        if (!vanished.contains(player)) {
		        	for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
		        		pl.hidePlayer(player);
		            }
		        	
		        	vanished.add(player);
		        	sender.sendMessage(titleZ + ChatColor.GREEN + "You have been vanished!");
		        	
		        	return true;
		        } else {
		        	for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
		        		pl.showPlayer(player);
		            }
		        	
		        	vanished.remove(player);
		        	sender.sendMessage(titleZ + ChatColor.GREEN + "You have been unvanished!");
		        	
		        	return true;
		        }
			} else {
				sender.sendMessage(titleZ + ChatColor.DARK_RED + "You do not have permission to use this command.");
	            
	            return true;
			}
		} if (cmd.getName().equalsIgnoreCase("setspawn")) {
			if ((cm.getGroupsConfig().getBoolean("Groups.Admins." + sender.getName()) == true) || (sender.isOp())) {
				Player player = (Player) sender;
				
				cm.getDataConfig().set("spawn.world", player.getWorld().getName());
				cm.getDataConfig().set("spawn.x", Double.valueOf(player.getLocation().getX()));
				cm.getDataConfig().set("spawn.y", Double.valueOf(player.getLocation().getY()));
				cm.getDataConfig().set("spawn.z", Double.valueOf(player.getLocation().getZ()));
				cm.getDataConfig().set("spawn.yaw", Float.valueOf(player.getLocation().getYaw()));
				cm.getDataConfig().set("spawn.pitch", Float.valueOf(player.getLocation().getPitch()));
				cm.saveDataConfig();
	            
	            sender.sendMessage(titleZ + ChatColor.GREEN + "Spawn set!");
			} else {
				sender.sendMessage(titleZ + ChatColor.DARK_RED + "You do not have permission to use this command.");
	            
	            return true;
			}
			
			return true;
		} if (cmd.getName().equalsIgnoreCase("head")) {
			if ((cm.getGroupsConfig().getBoolean("Groups.Mods." + sender.getName()) == true) || (sender.isOp())) {
				Player player = (Player) sender;
				
				ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
				
				SkullMeta Smeta = (SkullMeta) skull.getItemMeta();
		        Smeta.setOwner(player.getName());
		        Smeta.setDisplayName(ChatColor.GREEN + player.getName() + "'s Head!");
		        skull.setItemMeta(Smeta);
		       
		        player.getInventory().addItem(skull);
			} else {
				sender.sendMessage(titleZ + ChatColor.DARK_RED + "You do not have permission to use this command.");
	            
	            return true;
			}
			
			return true;
		} if (cmd.getName().equalsIgnoreCase("vip")) {
			if (!(sender instanceof Player)) {
				if (args.length > 0) {
					OfflinePlayer oTarget = Bukkit.getServer().getOfflinePlayer(args[0]);
					
					cm.getGroupsConfig().set("Groups.VIP." + oTarget.getName(), Boolean.valueOf(true));
					cm.saveGroupsConfig();
					
					int coins = getConfig().getInt("Players." + oTarget.getName() + ".Stats.Coins");
					
					getConfig().set("Players." + oTarget.getName() + ".Stats.Coins", coins + 300);
					saveConfig();
					
					return true;
				} else {
					sender.sendMessage(titleZ + ChatColor.DARK_RED + "Error");
				}
			} else {
				sender.sendMessage(titleZ + ChatColor.DARK_RED + "Only console can use this command!");
				
				return true;
			}
			
			return true;
		} if (cmd.getName().equalsIgnoreCase("rvip")) {
			if (!(sender instanceof Player)) {
				if (args.length > 0) {
					OfflinePlayer oTarget = Bukkit.getServer().getOfflinePlayer(args[0]);
					
					cm.getGroupsConfig().set("Groups.VIP." + oTarget.getName(), Boolean.valueOf(false));
					cm.saveGroupsConfig();
					
					return true;
				}
			} else {
				sender.sendMessage(titleZ + ChatColor.DARK_RED + "Only console can use this command!");
				
				return true;
			}
			
			return true;
		} if (cmd.getName().equalsIgnoreCase("oinv")) {
			if ((cm.getGroupsConfig().getBoolean("Groups.Mods." + sender.getName()) == true) || (sender.isOp())) {
				Player player = (Player) sender;
				
				if (args.length == 0) {
	                sender.sendMessage(titleZ + ChatColor.RED + "Please specify a player!");
	                
	                return true;
				}
				
				Player target = Bukkit.getServer().getPlayer(args[0]);
				
				if (target == null) {
	                sender.sendMessage(titleZ + ChatColor.RED + "Could not find target " + args[0] + "!");
	                
	                return true;
				}
				
				player.openInventory(target.getInventory());
			} else {
				sender.sendMessage(titleZ + ChatColor.DARK_RED + "You do not have permission to use this command.");
	            
	            return true;
			}
			
			return true;
		} if (cmd.getName().equalsIgnoreCase("helper")) {
			if (!(sender instanceof Player)) {
				if (args.length > 0) {
					OfflinePlayer oTarget = Bukkit.getServer().getOfflinePlayer(args[0]);
					
					cm.getGroupsConfig().set("Groups.Helper." + oTarget.getName(), Boolean.valueOf(true));
					cm.saveGroupsConfig();
					
					return true;
				} else {
					sender.sendMessage(titleZ + ChatColor.DARK_RED + "Error");
				}
			} else {
				sender.sendMessage(titleZ + ChatColor.DARK_RED + "Only console can use this command!");
				
				return true;
			}
			
			return true;
		} if (cmd.getName().equalsIgnoreCase("rhelper")) {
			if (!(sender instanceof Player)) {
				if (args.length > 0) {
					OfflinePlayer oTarget = Bukkit.getServer().getOfflinePlayer(args[0]);
					
					cm.getGroupsConfig().set("Groups.Helper." + oTarget.getName(), Boolean.valueOf(false));
					cm.saveGroupsConfig();
					
					return true;
				}
			} else {
				sender.sendMessage(titleZ + ChatColor.DARK_RED + "Only console can use this command!");
				
				return true;
			}
			
			return true;
		} if (cmd.getName().equalsIgnoreCase("tpa")) {
			Player player = (Player) sender;
			
			if (!(sender instanceof Player)) {
				if (args.length > 0) {
					sender.sendMessage(titleZ + ChatColor.DARK_RED + "Only player can use this command!");
					
					return true;
				}
			} else {
				if ((cm.getGroupsConfig().getBoolean("Groups.Mods." + sender.getName()) == true) || (sender.isOp())) {
					for (Player players : Bukkit.getServer().getOnlinePlayers()) {
						players.teleport(player.getLocation());
						
						players.getInventory().setArmorContents(null);
						players.getInventory().clear();
			            
			            for (PotionEffect effect : players.getActivePotionEffects()) {
			            	players.removePotionEffect(effect.getType());
			            }
			            
			            players.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 64));
					}
				} else {
					sender.sendMessage(titleZ + ChatColor.DARK_RED + "You do not have permission to use this command.");
		            
		            return true;
				}
				
				return true;
			}
			
			return true;
		} if (cmd.getName().equalsIgnoreCase("mod")) {
			if (!(sender instanceof Player)) {
				if (args.length > 0) {
					OfflinePlayer oTarget = Bukkit.getServer().getOfflinePlayer(args[0]);
					
					cm.getGroupsConfig().set("Groups.Mods." + oTarget.getName(), Boolean.valueOf(true));
					cm.saveGroupsConfig();
					
					return true;
				} else {
					sender.sendMessage(titleZ + ChatColor.DARK_RED + "Error");
				}
			} else {
				sender.sendMessage(titleZ + ChatColor.DARK_RED + "Only console can use this command!");
				
				return true;
			}
			
			return true;
		} if (cmd.getName().equalsIgnoreCase("rmod")) {
			if (!(sender instanceof Player)) {
				if (args.length > 0) {
					OfflinePlayer oTarget = Bukkit.getServer().getOfflinePlayer(args[0]);
					
					cm.getGroupsConfig().set("Groups.Mods." + oTarget.getName(), Boolean.valueOf(false));
					cm.saveGroupsConfig();
					
					return true;
				}
			} else {
				sender.sendMessage(titleZ + ChatColor.DARK_RED + "Only console can use this command!");
				
				return true;
			}
			
			return true;
		} if (cmd.getName().equalsIgnoreCase("beacon")) {
			Player player = (Player) sender;
			
			if ((cm.getGroupsConfig().getBoolean("Groups.Mods." + sender.getName()) == true) || (sender.isOp())) {
				createBeacon(player.getLocation());
			} else {
				sender.sendMessage(titleZ + ChatColor.DARK_RED + "You don't have permission to use this command.");
			}
			
			return true;
		} if (cmd.getName().equalsIgnoreCase("mtp")) {
			Player player = (Player) sender;
			
			if ((cm.getGroupsConfig().getBoolean("Groups.Mods." + sender.getName()) == true) || (sender.isOp())) {
				Player target = Bukkit.getServer().getPlayer(args[0]);
				
				if (args.length == 0) {
					player.sendMessage(titleZ + ChatColor.RED + "Please specify a player.");
					
					return true;
				} else if (args.length == 1) {
					player.teleport(target.getLocation());
				} else if (args.length == 2) {
					Player target1 = player.getServer().getPlayer(args[1]);
					target.teleport(target1.getLocation());
				}
				
				if (target == null) {
					player.sendMessage(titleZ + ChatColor.RED + "Could not find player " + args[0] + "!");
					
					return true;
				}
				
				player.teleport(target.getLocation());
				
	            return true;
			} else {
				sender.sendMessage(titleZ + ChatColor.DARK_RED + "You don't have permission to use this command.");
			}
			
			return true;
		} 
		
		return false;
	}
	
	public void registerCommands() {
		getCommand("coins").setExecutor(new PlayerCommands(this));
		getCommand("report").setExecutor(new PlayerCommands(this));
		getCommand("stats").setExecutor(new PlayerCommands(this));
		getCommand("top10").setExecutor(new PlayerCommands(this));
		getCommand("ignore").setExecutor(new PlayerCommands(this));
		getCommand("spawn").setExecutor(new PlayerCommands(this));
		getCommand("1v1").setExecutor(new PlayerCommands(this));
		getCommand("team").setExecutor(new PlayerCommands(this));
	}
	
	public void registerEvents() {
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		getServer().getPluginManager().registerEvents(new EntityListener(this), this);
		getServer().getPluginManager().registerEvents(new BlockListener(), this);
		getServer().getPluginManager().registerEvents(new SignListener(this), this);
		getServer().getPluginManager().registerEvents(new HangingListener(), this);
	}
	
	public void registerConfigs() {
		cm.getCombatConfig().addDefault("Time_In_Combat", Integer.valueOf(5));
		cm.getCombatConfig().addDefault("Kill_On_PvP_Log", Boolean.valueOf(true));
		cm.getCombatConfig().addDefault("Ban_On_PvP_Log", Boolean.valueOf(false));
		cm.getCombatConfig().addDefault("Announce_On_PvP_Log", Boolean.valueOf(true));
		cm.getCombatConfig().addDefault("Disable_Commands_In_PvP", Boolean.valueOf(true));
		cm.getCombatConfig().options().copyDefaults(true);
		cm.saveCombatConfig();
		cm.saveKSConfig();
		cm.saveHKSConfig();
		cm.saveKillsConfig();
		cm.saveDeathsConfig();
		cm.saveListsConfig();
		cm.saveGroupsConfig();
		cm.saveReportsConfig();
		cm.saveDataConfig();
		cm.saveTeamConfig();
		cm.saveTeamListConfig();
		saveDefaultConfig();
	}
	
	public Color getColor(int i) {
		Color color = null;
		
		if (i == 0) {
			color = Color.BLACK;
		} if (i == 1) {
			color = Color.BLUE;
		} if (i == 2) {
			color = Color.GREEN;
		} if (i == 3) {
			color = Color.AQUA;
		} if (i == 4) {
			color = Color.RED;
		} if (i == 5) {
			color = Color.PURPLE;
		} if (i == 6) {
			color = Color.GRAY;
		} if (i == 7) {
			color = Color.YELLOW;
		} if (i == 8) {
			color = Color.WHITE;
		} if (i == 9) {
			color = Color.TEAL;
		} if (i == 10) {
			color = Color.FUCHSIA;
		} if (i == 11) {
			color = Color.LIME;
		} if (i == 12) {
			color = Color.NAVY;
		} if (i == 13) {
			color = Color.MAROON;
		} if (i == 14) {
			color = Color.ORANGE;
		} if (i == 15) {
			color = Color.SILVER;
		} if (i == 16) {
			color = Color.OLIVE;
		}
		
		return color;
	}
	
	@SuppressWarnings("deprecation")
	public void repair(Player player) {
		PlayerInventory pi = player.getInventory();
		
		for (int i = 0; i <= 36; i++) {
			try {
				pi.getItem(i).setDurability((short) 0);
			} catch (Exception localException) {
				
			}
		}
		
	    pi.getBoots().setDurability((short) 0);
	    pi.getChestplate().setDurability((short) 0);
	    pi.getHelmet().setDurability((short) 0);
	    pi.getLeggings().setDurability((short) 0);
	    
	    player.updateInventory();
	}
	
	@SuppressWarnings("deprecation")
	public void refill(Player player) {
		Inventory inv = Bukkit.createInventory(player, 54, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Refill");
	    inv.clear();
	    
	    /**
		 * Soup
		 */
		
		// Mushroom Soup
		ItemStack soup = new ItemStack(Material.MUSHROOM_SOUP, 1);
		ItemMeta soupMeta = soup.getItemMeta();
		
		soupMeta.setDisplayName(ChatColor.DARK_PURPLE + "Mushroom Stew");
		soup.setItemMeta(soupMeta);
		
		// Bowl
		ItemStack bowl = new ItemStack(Material.BOWL, 1);
		ItemMeta bowlMeta = bowl.getItemMeta();
		
		bowlMeta.setDisplayName(ChatColor.DARK_PURPLE + "Bowl");
		bowl.setItemMeta(bowlMeta);
		
	    for (int i = 0; i < 54; i++) {
	    	inv.addItem(new ItemStack[] {
	    			soup
	    	});
	    }
	    
	    player.updateInventory();
	    player.openInventory(inv);
	}
	
	@SuppressWarnings("resource")
	public static void broadcastMessage(String fileName) throws IOException {
		FileInputStream fs = new FileInputStream(fileName);
	    BufferedReader br = new BufferedReader(new InputStreamReader(fs));
	    
	    for (int i = 0; i < currentLine; i++) {
	    	br.readLine();
	    }
	    
	    String line = br.readLine();
	    line = line.replaceAll("&0", ChatColor.BLACK + "");
	    line = line.replaceAll("&1", ChatColor.DARK_BLUE + "");
	    line = line.replaceAll("&2", ChatColor.DARK_GREEN + "");
	    line = line.replaceAll("&3", ChatColor.DARK_AQUA + "");
	    line = line.replaceAll("&4", ChatColor.DARK_RED + "");
	    line = line.replaceAll("&5", ChatColor.DARK_PURPLE + "");
	    line = line.replaceAll("&6", ChatColor.GOLD + "");
	    line = line.replaceAll("&7", ChatColor.GRAY + "");
	    line = line.replaceAll("&8", ChatColor.DARK_GRAY + "");
	    line = line.replaceAll("&9", ChatColor.BLUE + "");
	    line = line.replaceAll("&a", ChatColor.GREEN + "");
	    line = line.replaceAll("&b", ChatColor.AQUA + "");
	    line = line.replaceAll("&c", ChatColor.RED + "");
	    line = line.replaceAll("&d", ChatColor.LIGHT_PURPLE + "");
	    line = line.replaceAll("&e", ChatColor.YELLOW + "");
	    line = line.replaceAll("&f", ChatColor.WHITE + "");
	    line = line.replaceAll("&k", ChatColor.MAGIC + "");
	    line = line.replaceAll("&l", ChatColor.BOLD + "");
	    line = line.replaceAll("&m", ChatColor.STRIKETHROUGH + "");
	    line = line.replaceAll("&n", ChatColor.UNDERLINE + "");
	    line = line.replaceAll("&o", ChatColor.ITALIC + "");
	    line = line.replaceAll("&r", ChatColor.RESET + "");
	    
	    Bukkit.getServer().broadcastMessage(titleZPS + ChatColor.WHITE + line);
	    
	    LineNumberReader lnr = new LineNumberReader(new FileReader(new File(fileName)));
	    lnr.skip(9223372036854775807L);
	    
	    int lastLine = lnr.getLineNumber();
	    
	    if (currentLine + 1 == lastLine + 1) {
	    	currentLine = 0;
	    } else {
	    	currentLine += 1;
	    }
	}
	
	public void topKills(Player player) {
		Map<String, Integer> scoreMap = new HashMap<String, Integer>();
	    List<String> finalScore = new ArrayList<String>();
	    
	    for (OfflinePlayer oPlayer : Bukkit.getOfflinePlayers()) {
	    	if (!oPlayer.isBanned()) {
	    		scoreMap.put(oPlayer.getName(), Integer.valueOf(cm.getKillsConfig().getInt("Players." + oPlayer.getName() + ".Stats.Kills")));
		    }
	    }
	    
	    int topScore;
	    
	    for (int i = 0; i < 10; i++) {
	    	String topName = "";
	    	
	    	topScore = 0;
	    	
	    	for (String playerName : scoreMap.keySet()) {
	    		int myScore = ((Integer) scoreMap.get(playerName)).intValue();
	    		
	    		if (myScore > topScore) {
	    			topName = playerName;
	    			topScore = myScore;
	    		}
	    	}
	    	
	    	if (topName.equals("")) {
	    		break;
	    	}
	    	
	    	scoreMap.remove(topName);
	    	
	    	int position = i + 1;
	    	int kills = cm.getKillsConfig().getInt("Players." + topName + ".Stats.Kills");
	    	
	    	String finalString = ChatColor.GREEN + "[" + ChatColor.DARK_GREEN + position + ChatColor.GREEN + "] " + ChatColor.WHITE + topName + " " + ChatColor.DARK_GREEN + ": " + ChatColor.WHITE + kills;
	    	finalScore.add(finalString);
	    }
	    
	    List<String> myTop5 = finalScore;
	    
	    for (String s : myTop5) {
	    	player.sendMessage(s);
	    }
	}
	
	public boolean Team(Player p1, Player p2) {
		if ((cm.getTeamConfig().getString("Players." + p1.getName() + ".Stats.Team") != null) && (cm.getTeamConfig().getString("Players." + p2.getName() + ".Stats.Team") != null)) {
			String vT = cm.getTeamConfig().getString("Players." + p1.getName() + ".Stats.Team");
		    String dT = cm.getTeamConfig().getString("Players." + p2.getName() + ".Stats.Team");
		    
		    List<String> vList = cm.getTeamListConfig().getStringList(vT);
		    List<String> dList = cm.getTeamListConfig().getStringList(dT);
		    
		    OfflinePlayer VLeader = Bukkit.getOfflinePlayer((String) vList.get(0));
		    OfflinePlayer DLeader = Bukkit.getOfflinePlayer((String) dList.get(0));
		    
		    if (VLeader == DLeader) {
		    	return true;
		    }
		}
		
		return false;
	}
	
	public static void createBeacon(Location location) {
		int x = location.getBlockX();
	    int y = location.getBlockY() - 30;
	    int z = location.getBlockZ();
	    
	    World world = location.getWorld();
	    
	    world.getBlockAt(x, y, z).setType(Material.BEACON);
	    
	    for (int i = 0; i <= 29; ++i)
	    	world.getBlockAt(x, (y + 1) + i, z).setType(Material.GLASS);
	    for (int xPoint = x-1; xPoint <= x+1 ; xPoint++) {
	    	for (int zPoint = z-1 ; zPoint <= z+1; zPoint++) {
	    		world.getBlockAt(xPoint, y-1, zPoint).setType(Material.IRON_BLOCK);
	    	}
	    }
	}
}
