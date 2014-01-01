package net.hypsiz.zentaxpvp.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import net.hypsiz.zentaxpvp.Main;
import net.hypsiz.zentaxpvp.util.Report;

public class PlayerCommands implements CommandExecutor {

	private Main plugin;
	
	public PlayerCommands(Main instance) {
		plugin = instance;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if (cmd.getName().equalsIgnoreCase("coins")) {
			// Make sure the sender is a player.
			if (!(sender instanceof Player)) {
				sender.sendMessage("Only players can use this command.");
				
				return true;
			} else if (sender instanceof Player) {
				Player player = (Player) sender;
				
				int coins = plugin.getConfig().getInt("Players." + sender.getName() + ".Stats.Coins");
			    
		    	sender.sendMessage(plugin.titleZP);
		        sender.sendMessage(ChatColor.BLUE + "Your currently coins: " + ChatColor.DARK_GREEN + coins);
		        
		        if (args.length >= 1) {
		        	Player target = Bukkit.getPlayer(args[1]);
		        	int value = Integer.parseInt(args[2]);
		        	
		        	if (args[0].equalsIgnoreCase("give")) {
		        		if (plugin.getConfig().getInt("Players." + player.getName() + ".Stats.Coins") < value) {
		        			player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You do not have enough money to give.");
		        			
		        			return true;
		        		}
		        		
		        		plugin.getConfig().set("Players." + player.getName() + ".Stats.Coins", plugin.getConfig().getInt("Players." + player.getName() + ".Stats.Coins") - value);
		        		plugin.getConfig().set("Players." + target.getName() + ".Stats.Coins", plugin.getConfig().getInt("Players." + target.getName() + ".Stats.Coins") + value);
		        		
		        		player.sendMessage(plugin.titleZ + ChatColor.GOLD + "You gave " + ChatColor.DARK_GREEN + "$" + args[2] + ChatColor.GOLD + "'s to the " + ChatColor.GREEN + target.getName() + ChatColor.GOLD + ".");
						target.sendMessage(plugin.titleZ + ChatColor.GOLD + "You got " + ChatColor.DARK_GREEN + "$" + args[2] + ChatColor.GOLD + "'s from the " + ChatColor.GREEN + player.getName() + ChatColor.GOLD + ".");
						
						plugin.saveConfig();
						
						return true;
		        	} if (args[0].equalsIgnoreCase("set")) {
		        		if (player.isOp()) {
		        			plugin.getConfig().set("Players." + target.getName() + ".Stats.Coins", value);
			        		
			        		player.sendMessage(plugin.titleZ + ChatColor.GOLD + "You set " + ChatColor.GREEN + target.getName() + ChatColor.GOLD + "'s money to " + ChatColor.DARK_GREEN + "$" + value + ChatColor.GOLD + ".");
			        		target.sendMessage(plugin.titleZ + ChatColor.GOLD + "Your money was set to " + ChatColor.DARK_GREEN + "$" + value + ChatColor.BLUE + " by " + ChatColor.GREEN + player.getName() + ChatColor.GOLD + ".");
		        		} else {
		        			player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You don't have permission to use this command.");
		        		}
		        		
		        		return true;
		        	}
		        	
		        	return true;
		        }
		        
		        return true;
			}
	        
	        return true;
	    } if (cmd.getName().equalsIgnoreCase("report")) {
	    	if (!(sender instanceof Player)) {
				sender.sendMessage("Only players can use this command.");
				
				return true;
			}
	    	
			if (args.length == 0) {
				sender.sendMessage(plugin.titleZ + ChatColor.YELLOW + "To report a player/bug, you need to write: /report <player> <reason> or /report <bug-reason>");
			} else {
				Report.addReport(sender, Report.getString(args));
				Report.saveReports(plugin);
				Report.sendReportsToAdmin(sender, Report.getString(args));
				Report.sendMessage(sender, plugin.titleZ + ChatColor.GREEN + "Report sent!");
			}
			
			return true;
		} if (cmd.getName().equalsIgnoreCase("stats")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Only players can use this command.");
				
				return true;
			}
			
			Player player = (Player) sender;
			
		    int kills = plugin.cm.getKillsConfig().getInt("Players." + player.getName() + ".Stats.Kills");
		    int deaths = plugin.cm.getDeathsConfig().getInt("Players." + player.getName() + ".Stats.Deaths");
		    int coins = plugin.getConfig().getInt("Players." + player.getName() + ".Stats.Coins");
		    
		    double dkills = plugin.cm.getKillsConfig().getInt("Players." + player.getName() + ".Stats.Kills");
		    double ddeaths = plugin.cm.getDeathsConfig().getInt("Players." + player.getName() + ".Stats.Deaths");
		    double kd = dkills / ddeaths;
		    
		    if (args.length == 0) {
		    	player.sendMessage(plugin.titleZP);
		        player.sendMessage(ChatColor.BLUE + "Your Statistics:");
		        player.sendMessage(ChatColor.GREEN + "Kills: " + ChatColor.DARK_GREEN + kills);
		        player.sendMessage(ChatColor.GREEN + "Deaths: " + ChatColor.DARK_GREEN + deaths);
		        player.sendMessage(ChatColor.GREEN + "K/D Ratio: " + ChatColor.DARK_GREEN + kd);
		        player.sendMessage(ChatColor.GREEN + "Coins: " + ChatColor.DARK_GREEN + coins);
		    } if (args.length > 0) {
		    	if (!args[0].equalsIgnoreCase("reset")) {
		    		OfflinePlayer oTarget = player.getServer().getOfflinePlayer(args[0]);
		    		
		    		int killsTarget = plugin.cm.getKillsConfig().getInt("Players." + oTarget.getName() + ".Stats.Kills");
				    int deathsTarget = plugin.cm.getDeathsConfig().getInt("Players." + oTarget.getName() + ".Stats.Deaths");
				    int coinsTarget = plugin.getConfig().getInt("Players." + oTarget.getName() + ".Stats.Coins");
				    
				    double dkillsTarget = plugin.cm.getKillsConfig().getInt("Players." + oTarget.getName() + ".Stats.Kills");
				    double ddeathsTarget = plugin.cm.getDeathsConfig().getInt("Players." + oTarget.getName() + ".Stats.Deaths");
				    double kdTarget = dkillsTarget / ddeathsTarget;
				    
				    player.sendMessage(plugin.titleZP);
			        player.sendMessage(ChatColor.BLUE + oTarget.getName() + "'s Statistics:");
			        player.sendMessage(ChatColor.GREEN + "Kills: " + ChatColor.DARK_GREEN + killsTarget);
			        player.sendMessage(ChatColor.GREEN + "Deaths: " + ChatColor.DARK_GREEN + deathsTarget);
			        player.sendMessage(ChatColor.GREEN + "K/D Ratio: " + ChatColor.DARK_GREEN + kdTarget);
			        player.sendMessage(ChatColor.GREEN + "Coins: " + ChatColor.DARK_GREEN + coinsTarget);
		    	} else {
		    		if ((plugin.cm.getGroupsConfig().getBoolean("Groups.VIP." + player.getName()) == true) || player.isOp()) {
		    			plugin.cm.getKillsConfig().set("Players." + player.getName() + ".Stats.Kills", Integer.valueOf(0));
			    		plugin.cm.getDeathsConfig().set("Players." + player.getName() + ".Stats.Deaths", Integer.valueOf(0));
			    		
			    		player.sendMessage(ChatColor.GREEN + "Your statistics has been reset.");
		    		} else {
		    			player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "To reset your stats, you should be VIP.");
		    		}
		    	}
		    }
		    
		    return true;
		} if (cmd.getName().equalsIgnoreCase("top10")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Only players can use this command.");
				
				return true;
			}
			
			if (args.length != 1) {
				sender.sendMessage(plugin.titleZP);
				sender.sendMessage(ChatColor.GREEN + "Available Top10 Commands:");
				sender.sendMessage(ChatColor.RED + "/top10 kills");
				/*sender.sendMessage(ChatColor.RED + "/top10 deaths");
				sender.sendMessage(ChatColor.RED + "/top10 kd");*/
		        
		        return true;
			} if (args.length > 0) {
				Player player = (Player) sender;
				
				if (args[0].equalsIgnoreCase("kills")) {
					plugin.topKills(player);
					
					return true;
				}
			}
			
			return true;
		} if (cmd.getName().equalsIgnoreCase("ignore")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Only players can use this command.");
				
				return true;
			}
			
			if (args.length == 0) {
				sender.sendMessage(plugin.titleZ + ChatColor.RED + "Please specify a target!");
                
				return true;
			}
			
			Player target = Bukkit.getServer().getPlayer(args[0]);
			
			if (target == null) {
				sender.sendMessage(plugin.titleZ + ChatColor.RED + "Could not find target " + args[0] + "!");
				
				return true;
			} else {
				Player player = (Player) sender;
				
				if (plugin.i.get(player) == null) {
					ArrayList<Player> al = new ArrayList<Player>();
					al.add(target);
					plugin.i.put(player, al);
					
					sender.sendMessage(plugin.titleZ + ChatColor.GREEN + "You are now ignoring " + target.getName() + "!");
					
					return true;
				} if (plugin.i.get(player).contains(target)) {
					ArrayList<Player> al = plugin.i.get(player);
					al.remove(target);
					plugin.i.put(player, al);
					
					sender.sendMessage(plugin.titleZ + ChatColor.GREEN + "You are no longer ignoring " + target.getName() + "!");
					
					return true;
				} if (!plugin.i.get(player).contains(target)) {
					ArrayList<Player> al = plugin.i.get(player);
					al.add(target);
					plugin.i.put(player, al);
					
					sender.sendMessage(plugin.titleZ + ChatColor.GREEN + "You are now ignoring " + target.getName() + "!");
					
					return true;
				}
			}
			
			return true;
		} if (cmd.getName().equalsIgnoreCase("spawn")) {
			if (plugin.cm.getDataConfig().getConfigurationSection("spawn") == null) {
				sender.sendMessage(plugin.titleZ + ChatColor.RED + "The spawn has not yet been set!");
				
                return true;
			}
			
			final World w = Bukkit.getServer().getWorld(plugin.cm.getDataConfig().getString("spawn.world"));
	        final double x = plugin.cm.getDataConfig().getDouble("spawn.x");
	        final double y = plugin.cm.getDataConfig().getDouble("spawn.y");
	        final double z = plugin.cm.getDataConfig().getDouble("spawn.z");
	        final float yaw = (float) plugin.cm.getDataConfig().getDouble("spawn.yaw");
	        final float pitch = (float) plugin.cm.getDataConfig().getDouble("spawn.pitch");
            
            final Location l = new Location(w, x, y, z, yaw, pitch);
            
            final Player player = (Player) sender;
            
            sender.sendMessage(plugin.titleZ + ChatColor.GOLD + "Teleport will apply in " + ChatColor.BLUE + "3 " + ChatColor.GOLD + "seconds. Don't move!");
            
            plugin.spawn.add(player.getName());
            
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {

				@Override
				public void run() {
					if (plugin.spawn.contains(player.getName())) {
						plugin.spawn.remove(player.getName());
						
						player.teleport(l);
			            player.getInventory().setArmorContents(null);
			            player.getInventory().clear();
			            player.setFireTicks(0);
			            
			            for (PotionEffect effect : player.getActivePotionEffects()) {
			            	player.removePotionEffect(effect.getType());
			            }
			            
			            player.sendMessage(plugin.titleZ + ChatColor.GOLD + "Teleportation applied!");
					}
				}
            	
            }, 3 * 20L);
            
            return true;
		} if (cmd.getName().equalsIgnoreCase("1v1")) {
			final Location MainP = new Location(Bukkit.getWorld("world"), 664.50818, 16.000, -1206.59975);
			final Location locationEnd = new Location(Bukkit.getWorld("world"), 664.52575, 26.460, -1277.28255);
			final Location locationBlue = new Location(Bukkit.getWorld("world"), 665.51799, 14.359, -1254.13440);
	        final Location locationRed = new Location(Bukkit.getWorld("world"), 665.51437, 14.359, -1219.30000);
	        final Location locationBlue1 = new Location(Bukkit.getWorld("world"), 638.49850, 21.460, -1272.52693);
	        final Location locationRed1 = new Location(Bukkit.getWorld("world"), 648.30420, 21.474, -1282.27131);
	        
			if (args.length < 1) {
				sender.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "]" + ChatColor.WHITE + " 1v1 Arena Plugin!");
				
				return true;
			} if (args[0].equalsIgnoreCase("haigeydarovnoamdolev1v1")) {
				if (args.length < 2) {
					Player player = (Player) sender;
					
					if ((plugin.Red.contains(player.getName())) || (plugin.Blue.contains(player.getName()))) {
						player.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "]" + ChatColor.WHITE + " You cannot join when you're inside.");
					} else if ((plugin.Red.size() == 1) && (plugin.Blue.size() == 1)) {
						player.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "]" + ChatColor.WHITE + " A match is now on, please wait..");
					} else if ((plugin.Red.size() == 0) && (plugin.Blue.size() == 0)) {
						player.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "]" + ChatColor.WHITE + " You are " + ChatColor.DARK_RED + "Red" + ChatColor.WHITE + "!");
						player.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "]" + ChatColor.WHITE + " Please wait for another player to join.");
						player.getInventory().clear();
						
				        plugin.Red.add(player.getName());
				        
				        player.teleport(MainP);
				        player.getInventory().setHelmet(null);
				        player.getInventory().setChestplate(null);
				        player.getInventory().setLeggings(null);
				        player.getInventory().setBoots(null);
					} else if (plugin.Red.size() == 1) {
						player.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "]" + ChatColor.WHITE + " You are " + ChatColor.DARK_BLUE + "Blue" + ChatColor.WHITE + "!");
						
						plugin.Blue.add(player.getName());
						
						player.getInventory().clear();
						player.teleport(MainP);
						player.getInventory().setHelmet(null);
						player.getInventory().setChestplate(null);
						player.getInventory().setLeggings(null);
						player.getInventory().setBoots(null);
					} if ((plugin.Red.size() == 1) && (plugin.Blue.size() == 1)) {
						final Player bluePlayer = player.getServer().getPlayer(plugin.Blue.get(0));
				        final Player redPlayer = player.getServer().getPlayer(plugin.Red.get(0));
				        
				        if ((plugin.Red.contains(player.getName())) || (plugin.Blue.contains(player.getName()))) {
				        	redPlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + bluePlayer.getName() + " is " + ChatColor.DARK_BLUE + "Blue" + ChatColor.WHITE + "!");
				        	bluePlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + redPlayer.getName() + " is " + ChatColor.DARK_RED + "Red" + ChatColor.WHITE + "!");
				        	redPlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + "Starting in " + ChatColor.BLUE + "5 " + ChatColor.WHITE + "seconds..");
				        	bluePlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + "Starting in " + ChatColor.BLUE + "5 " + ChatColor.WHITE + "seconds..");
				        	
				        	plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {

				        		int count = 5;
				        		
								public void run() {
									if (count != -1) {
										if (count != 0) {
											count--;
											
											if (count == 0) {
												if ((plugin.Red.size() == 1) && (plugin.Blue.size() == 1)) {
													redPlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + "Battle begin!");
													bluePlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + "Battle begin!");
											        redPlayer.setHealth(20);
											        bluePlayer.setHealth(20);
											        redPlayer.getInventory().clear();
											        redPlayer.setHealth(20);
											        redPlayer.setFoodLevel(20);
											        
											        for (PotionEffect effect : redPlayer.getActivePotionEffects())
											        	redPlayer.removePotionEffect(effect.getType());
											        
											        /**
											         * Kit
											         */
											        
											        // Sword
											        ItemStack diamond_sword = new ItemStack(Material.DIAMOND_SWORD);
											        diamond_sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
											        
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
													
													redPlayer.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
													redPlayer.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
													redPlayer.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
													redPlayer.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
													redPlayer.getInventory().addItem(diamond_sword);
													redPlayer.getInventory().setItem(35, bowl);
													
													for (int i = 0; i < 33; i++) {
														redPlayer.getInventory().addItem(soup);
													}
													
													redPlayer.updateInventory();
											        bluePlayer.getInventory().clear();
											        bluePlayer.setHealth(20);
											        bluePlayer.setFoodLevel(20);
											        
											        for (PotionEffect effect : bluePlayer.getActivePotionEffects())
											        	bluePlayer.removePotionEffect(effect.getType());
											        
											        bluePlayer.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
											        bluePlayer.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
											        bluePlayer.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
											        bluePlayer.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
											        bluePlayer.getInventory().addItem(diamond_sword);
											        bluePlayer.getInventory().setItem(35, bowl);
											        
											        for (int i = 0; i < 33; i++) {
											        	bluePlayer.getInventory().addItem(soup);
											        }
											        
											        bluePlayer.updateInventory();
											        bluePlayer.teleport(locationBlue);
											        redPlayer.teleport(locationRed);
												} else {
													if (plugin.Red.size() == 1) {
														redPlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + "The " + ChatColor.DARK_BLUE + "Blue " + ChatColor.WHITE + "player left the game.");
										                
														plugin.Red.clear();
										                plugin.Blue.clear();
										                
										                redPlayer.teleport(locationEnd);
													} if (plugin.Blue.size() == 1) {
														bluePlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + "The " + ChatColor.DARK_RED + "Red " + ChatColor.WHITE + "player left the game.");
														
														plugin.Red.clear();
										                plugin.Blue.clear();
										                
										                bluePlayer.teleport(locationEnd);
													}
												}
											}
										}
									}
								}
				        		
				        	}, 0L, 20L);
				        }
					}
				}
			} if (args[0].equalsIgnoreCase("leave")) {
				if (args.length < 2) {
					Player player = (Player) sender;
					
					if (! (plugin.Blue.contains(player.getName()) || (plugin.Red.contains(player.getName())))) {
						player.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + "You aren't in the arena!");
					} if ((plugin.Red.contains(player.getName())) && (plugin.Blue.size() == 1)) {
						Player bluePlayer = player.getServer().getPlayer(plugin.Blue.get(0));
		                Player redPlayer = player.getServer().getPlayer(plugin.Red.get(0));
		                
		                Inventory blueInventory = bluePlayer.getInventory();
		                Inventory redInventory = redPlayer.getInventory();
		                
		                plugin.Red.clear();
		                plugin.Blue.clear();
		                
		                bluePlayer.setHealth(20);
		                redPlayer.setHealth(20);
		                bluePlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + "The other side left the battlefield.");
		                redPlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + "You left the battlefield.");
		                bluePlayer.teleport(locationEnd);
		                redPlayer.teleport(locationEnd);
		                
		                blueInventory.clear();
		                redInventory.clear();
		                
		                redPlayer.setHealth(20);
		                bluePlayer.setHealth(20);
		                redPlayer.getInventory().setHelmet(null);
		                redPlayer.getInventory().setChestplate(null);
		                redPlayer.getInventory().setLeggings(null);
		                redPlayer.getInventory().setBoots(null);
		                bluePlayer.getInventory().setHelmet(null);
		                bluePlayer.getInventory().setChestplate(null);
		                bluePlayer.getInventory().setLeggings(null);
		                bluePlayer.getInventory().setBoots(null);
					} if ((plugin.Blue.contains(player.getName())) && (plugin.Red.size() == 1)) {
						Player bluePlayer = player.getServer().getPlayer(plugin.Blue.get(0));
		                Player redPlayer = player.getServer().getPlayer(plugin.Red.get(0));
		                
		                Inventory blueInventory = bluePlayer.getInventory();
		                Inventory redInventory = redPlayer.getInventory();
		                
		                plugin.Red.clear();
		                plugin.Blue.clear();
		                
		                redPlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + "The other side left the battlefield.");
		                bluePlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + "You left the battlefield.");
		                bluePlayer.teleport(locationEnd);
		                redPlayer.teleport(locationEnd);
		                
		                blueInventory.clear();
		                
		                redPlayer.setHealth(20);
		                bluePlayer.setHealth(20);
		                
		                redInventory.clear();
		                
		                redPlayer.getInventory().setHelmet(null);
		                redPlayer.getInventory().setChestplate(null);
		                redPlayer.getInventory().setLeggings(null);
		                redPlayer.getInventory().setBoots(null);
		                bluePlayer.getInventory().setHelmet(null);
		                bluePlayer.getInventory().setChestplate(null);
		                bluePlayer.getInventory().setLeggings(null);
		                bluePlayer.getInventory().setBoots(null);
	                } if (plugin.Red.contains(player.getName())) {
	                	Player redPlayer = player.getServer().getPlayer(plugin.Red.get(0));
	                    Inventory redInventory = redPlayer.getInventory();
	                    
	                    plugin.Red.clear();
	                    
	                    redPlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + "You left the battlefield.");
	                    redPlayer.teleport(new Location(Bukkit.getWorld("world"), 648.36361, 19.335, -1237.03088));
	                    
	                    redInventory.clear();
	                    
	                    redPlayer.setHealth(20);
	                    
	                    player.getInventory().setHelmet(null);
	                    player.getInventory().setChestplate(null);
	                    player.getInventory().setLeggings(null);
	                    player.getInventory().setBoots(null);
	                } if (plugin.Blue.contains(player.getName())) {
	                	Player bluePlayer = player.getServer().getPlayer(plugin.Blue.get(0));
	                    Inventory blueInventory = bluePlayer.getInventory();
	                    
	                    plugin.Blue.clear();
	                    
	                    bluePlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + "You left the battlefield.");
	                    bluePlayer.teleport(locationEnd);
	                    
	                    blueInventory.clear();
	                    
	                    bluePlayer.setHealth(20);
	                    
	                    player.getInventory().setHelmet(null);
	                    player.getInventory().setChestplate(null);
	                    player.getInventory().setLeggings(null);
	                    player.getInventory().setBoots(null);
	                }
				}
			} if (args[0].equalsIgnoreCase("haigeydarovnoamdolev1v1s")) {
				if (args.length < 2) {
					Player player = (Player) sender;
					
					if ((plugin.Red2.contains(player.getName())) || (plugin.Blue2.contains(player.getName()))) {
						player.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "]" + ChatColor.WHITE + " You cannot join when you're inside.");
					} else if ((plugin.Red2.size() == 1) && (plugin.Blue2.size() == 1)) {
						player.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "]" + ChatColor.WHITE + " A match is now on, please wait..");
					} else if ((plugin.Red2.size() == 0) && (plugin.Blue2.size() == 0)) {
						player.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "]" + ChatColor.WHITE + " You are " + ChatColor.DARK_RED + "Red" + ChatColor.WHITE + "!");
						player.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "]" + ChatColor.WHITE + " Please wait for another player to join.");
						player.getInventory().clear();
						
				        plugin.Red2.add(player.getName());
				        
				        player.teleport(MainP);
				        player.getInventory().setHelmet(null);
				        player.getInventory().setChestplate(null);
				        player.getInventory().setLeggings(null);
				        player.getInventory().setBoots(null);
					} else if (plugin.Red2.size() == 1) {
						player.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "]" + ChatColor.WHITE + " You are " + ChatColor.DARK_BLUE + "Blue" + ChatColor.WHITE + "!");
						
						plugin.Blue2.add(player.getName());
						
						player.getInventory().clear();
						player.teleport(MainP);
						player.getInventory().setHelmet(null);
						player.getInventory().setChestplate(null);
						player.getInventory().setLeggings(null);
						player.getInventory().setBoots(null);
					} if ((plugin.Red2.size() == 1) && (plugin.Blue2.size() == 1)) {
						final Player bluePlayer = player.getServer().getPlayer(plugin.Blue2.get(0));
				        final Player redPlayer = player.getServer().getPlayer(plugin.Red2.get(0));
				        
				        if ((plugin.Red2.contains(player.getName())) || (plugin.Blue2.contains(player.getName()))) {
				        	redPlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + bluePlayer.getName() + " is " + ChatColor.DARK_BLUE + "Blue" + ChatColor.WHITE + "!");
				        	bluePlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + redPlayer.getName() + " is " + ChatColor.DARK_RED + "Red" + ChatColor.WHITE + "!");
				        	redPlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + "Starting in " + ChatColor.BLUE + "5 " + ChatColor.WHITE + "seconds..");
				        	bluePlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + "Starting in " + ChatColor.BLUE + "5 " + ChatColor.WHITE + "seconds..");
				        	
				        	plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {

				        		int count = 5;
				        		
								public void run() {
									if (count != -1) {
										if (count != 0) {
											count--;
											
											if (count == 0) {
												if ((plugin.Red2.size() == 1) && (plugin.Blue2.size() == 1)) {
													redPlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + "Battle begin!");
													bluePlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + "Battle begin!");
											        redPlayer.setHealth(20);
											        bluePlayer.setHealth(20);
											        redPlayer.getInventory().clear();
											        redPlayer.setHealth(20);
											        redPlayer.setFoodLevel(20);
											        
											        for (PotionEffect effect : redPlayer.getActivePotionEffects())
											        	redPlayer.removePotionEffect(effect.getType());
											        
											        /**
											         * Kit
											         */
											        
											        // Sword
											        ItemStack diamond_sword = new ItemStack(Material.DIAMOND_SWORD);
											        diamond_sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
											        
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
													
													redPlayer.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
													redPlayer.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
													redPlayer.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
													redPlayer.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
													redPlayer.getInventory().addItem(diamond_sword);
													redPlayer.getInventory().setItem(35, bowl);
													
													for (int i = 0; i < 33; i++) {
														redPlayer.getInventory().addItem(soup);
													}
													
													redPlayer.updateInventory();
											        bluePlayer.getInventory().clear();
											        bluePlayer.setHealth(20);
											        bluePlayer.setFoodLevel(20);
											        
											        for (PotionEffect effect : bluePlayer.getActivePotionEffects())
											        	bluePlayer.removePotionEffect(effect.getType());
											        
											        bluePlayer.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
											        bluePlayer.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
											        bluePlayer.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
											        bluePlayer.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
											        bluePlayer.getInventory().addItem(diamond_sword);
											        bluePlayer.getInventory().setItem(35, bowl);
											        
											        for (int i = 0; i < 33; i++) {
											        	bluePlayer.getInventory().addItem(soup);
											        }
											        
											        bluePlayer.updateInventory();
											        bluePlayer.teleport(locationBlue1);
											        redPlayer.teleport(locationRed1);
												} else {
													if (plugin.Red2.size() == 1) {
														redPlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + "The " + ChatColor.DARK_BLUE + "Blue " + ChatColor.WHITE + "player left the game.");
										                
														plugin.Red2.clear();
										                plugin.Blue2.clear();
										                
										                redPlayer.teleport(locationEnd);
													} if (plugin.Blue2.size() == 1) {
														bluePlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + "The " + ChatColor.DARK_RED + "Red " + ChatColor.WHITE + "player left the game.");
														
														plugin.Red2.clear();
										                plugin.Blue2.clear();
										                
										                bluePlayer.teleport(locationEnd);
													}
												}
											}
										}
									}
								}
				        		
				        	}, 0L, 20L);
				        }
					}
				}
			} if (args[0].equalsIgnoreCase("leaves")) {
				if (args.length < 2) {
					Player player = (Player) sender;
					
					if (! (plugin.Blue2.contains(player.getName()) || (plugin.Red2.contains(player.getName())))) {
						player.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + "You aren't in the arena!");
					} if ((plugin.Red2.contains(player.getName())) && (plugin.Blue2.size() == 1)) {
						Player bluePlayer = player.getServer().getPlayer(plugin.Blue2.get(0));
		                Player redPlayer = player.getServer().getPlayer(plugin.Red2.get(0));
		                
		                Inventory blueInventory = bluePlayer.getInventory();
		                Inventory redInventory = redPlayer.getInventory();
		                
		                plugin.Red2.clear();
		                plugin.Blue2.clear();
		                
		                bluePlayer.setHealth(20);
		                redPlayer.setHealth(20);
		                bluePlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + "The other side left the battlefield.");
		                redPlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + "You left the battlefield.");
		                bluePlayer.teleport(locationEnd);
		                redPlayer.teleport(locationEnd);
		                
		                blueInventory.clear();
		                redInventory.clear();
		                
		                redPlayer.setHealth(20);
		                bluePlayer.setHealth(20);
		                redPlayer.getInventory().setHelmet(null);
		                redPlayer.getInventory().setChestplate(null);
		                redPlayer.getInventory().setLeggings(null);
		                redPlayer.getInventory().setBoots(null);
		                bluePlayer.getInventory().setHelmet(null);
		                bluePlayer.getInventory().setChestplate(null);
		                bluePlayer.getInventory().setLeggings(null);
		                bluePlayer.getInventory().setBoots(null);
					} if ((plugin.Blue2.contains(player.getName())) && (plugin.Red2.size() == 1)) {
						Player bluePlayer = player.getServer().getPlayer(plugin.Blue2.get(0));
		                Player redPlayer = player.getServer().getPlayer(plugin.Red2.get(0));
		                
		                Inventory blueInventory = bluePlayer.getInventory();
		                Inventory redInventory = redPlayer.getInventory();
		                
		                plugin.Red2.clear();
		                plugin.Blue2.clear();
		                
		                redPlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + "The other side left the battlefield.");
		                bluePlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + "You left the battlefield.");
		                bluePlayer.teleport(locationEnd);
		                redPlayer.teleport(locationEnd);
		                
		                blueInventory.clear();
		                
		                redPlayer.setHealth(20);
		                bluePlayer.setHealth(20);
		                
		                redInventory.clear();
		                
		                redPlayer.getInventory().setHelmet(null);
		                redPlayer.getInventory().setChestplate(null);
		                redPlayer.getInventory().setLeggings(null);
		                redPlayer.getInventory().setBoots(null);
		                bluePlayer.getInventory().setHelmet(null);
		                bluePlayer.getInventory().setChestplate(null);
		                bluePlayer.getInventory().setLeggings(null);
		                bluePlayer.getInventory().setBoots(null);
	                } if (plugin.Red2.contains(player.getName())) {
	                	Player redPlayer = player.getServer().getPlayer(plugin.Red2.get(0));
	                    Inventory redInventory = redPlayer.getInventory();
	                    
	                    plugin.Red2.clear();
	                    
	                    redPlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + "You left the battlefield.");
	                    redPlayer.teleport(new Location(Bukkit.getWorld("world"), 654.47251, 21.600, -1277.53350));
	                    
	                    redInventory.clear();
	                    
	                    redPlayer.setHealth(20);
	                    
	                    player.getInventory().setHelmet(null);
	                    player.getInventory().setChestplate(null);
	                    player.getInventory().setLeggings(null);
	                    player.getInventory().setBoots(null);
	                } if (plugin.Blue2.contains(player.getName())) {
	                	Player bluePlayer = player.getServer().getPlayer(plugin.Blue2.get(0));
	                    Inventory blueInventory = bluePlayer.getInventory();
	                    
	                    plugin.Blue2.clear();
	                    
	                    bluePlayer.sendMessage(ChatColor.YELLOW + "[" + ChatColor.RED + "1V1" + ChatColor.YELLOW + "] " + ChatColor.WHITE + "You left the battlefield.");
	                    bluePlayer.teleport(new Location(Bukkit.getWorld("world"), 654.47251, 21.600, -1277.53350));
	                    
	                    blueInventory.clear();
	                    
	                    bluePlayer.setHealth(20);
	                    
	                    player.getInventory().setHelmet(null);
	                    player.getInventory().setChestplate(null);
	                    player.getInventory().setLeggings(null);
	                    player.getInventory().setBoots(null);
	                }
				}
			}
			
			return true;
		} if (cmd.getName().equalsIgnoreCase("team")) {
			Player player = (Player) sender;
			
			if (args.length < 1) {
				sender.sendMessage(plugin.titleZP);
				sender.sendMessage(ChatColor.GREEN + "Available Team Commands:");
		        sender.sendMessage(ChatColor.RED + "/team create <team-name>");
		        sender.sendMessage(ChatColor.RED + "/team invite <player-name>");
		        sender.sendMessage(ChatColor.RED + "/team accept <player-name>");
		        sender.sendMessage(ChatColor.RED + "/team leave");
		        sender.sendMessage(ChatColor.RED + "/team kick <player-name>");
		        sender.sendMessage(ChatColor.RED + "/team close");
		        sender.sendMessage(ChatColor.DARK_RED + "*Capital letters required!");
			} if (args.length > 0) {
				if (args[0].equalsIgnoreCase("create")) {
					if ((plugin.cm.getGroupsConfig().getBoolean("Groups.VIP." + player.getName()) == true) || (plugin.cm.getGroupsConfig().getBoolean("Groups.Mods." + player.getName()) == true) || (plugin.cm.getGroupsConfig().getBoolean("Groups.Admins." + player.getName()) == true) || (player.isOp())) {
						if (sender instanceof Player) {
							if (args.length > 1) {
								String teamName = args[1];
								
								if (plugin.cm.getTeamConfig().getString("Players." + player.getName() + ".Stats.Team") == null) {
									if (plugin.cm.getTeamListConfig().getStringList(teamName).isEmpty()) {
										if (teamName.length() < 9) {
											player.sendMessage(plugin.titleZ + ChatColor.WHITE + "You created a " + ChatColor.GOLD + teamName + ChatColor.WHITE + " team.");
											
											List<String> teamList = plugin.cm.getTeamListConfig().getStringList(teamName);
											teamList.add(player.getName());
											
											plugin.cm.getTeamListConfig().set(teamName, teamList);
											plugin.cm.getTeamConfig().set("Players." + player.getName() + ".Stats.Team", teamName);
											
											plugin.cm.saveTeamConfig();
											plugin.cm.saveTeamListConfig();
										} else {
											player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "Team name must be lower than 9 letters.");
										}
									} else {
										player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "There is already a team with this name.");
									}
								} else {
									player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You already in a team.");
								}
							} else {
								player.sendMessage(plugin.titleZ + ChatColor.RED + "/team create <team-name>");
							}
						} else {
							sender.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You cannot use this command, if you are not a player.");
						}
					} else {
						sender.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "Only V.I.P have permission to use this command.");
						
						return true;
					}
				} if (args[0].equalsIgnoreCase("invite")) {
					String playerTeam;
			        OfflinePlayer teamAdmin;
			        
		        	if (sender instanceof Player) {
		        		if (args.length > 1) {
		        			OfflinePlayer invitedOffline = Bukkit.getServer().getOfflinePlayer(args[1]);
		        			
		        			if ((invitedOffline.isOnline()) && (invitedOffline != player)) {
		        				if (plugin.cm.getTeamConfig().getString("Players." + player.getName() + ".Stats.Team") != null) {
		        					Player invitedPlayer = Bukkit.getServer().getPlayer(args[1]);
		        					
		        					if (plugin.cm.getTeamConfig().getString("Players." + invitedPlayer.getName() + ".Stats.Team") == null) {
		        						playerTeam = plugin.cm.getTeamConfig().getString("Players." + player.getName() + ".Stats.Team");
		        						teamAdmin = Bukkit.getOfflinePlayer((String) plugin.cm.getTeamListConfig().getStringList(playerTeam).get(0));
		        						
		        						if (teamAdmin == player) {
		        							if (!plugin.invite.containsKey(invitedPlayer.getName())) {
		        								if (plugin.cm.getTeamListConfig().getStringList(playerTeam).size() < 11) {
		        									player.sendMessage(plugin.titleZ + ChatColor.WHITE + "Invitation sent to " + ChatColor.GREEN + invitedPlayer.getName() + ChatColor.WHITE + ".");
		        									invitedPlayer.sendMessage(plugin.titleZ + ChatColor.GREEN + player.getName() + ChatColor.WHITE + ", sent you a request to join " + ChatColor.GOLD + playerTeam + ChatColor.WHITE + " team.");
		        									invitedPlayer.sendMessage(plugin.titleZ + ChatColor.WHITE + "To accept your invitation, you need to write: " + ChatColor.RED + "/team accept " + playerTeam + ChatColor.WHITE + ".");
		        									invitedPlayer.sendMessage(plugin.titleZ + ChatColor.WHITE + "If you don't want to accept, rejoin the server.");
		        									
		        									plugin.invite.put(invitedPlayer.getName(), playerTeam);
		        								} else {
		        									player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You can't have more than 10 people on your team.");
		        								}
		        							} else {
		        								player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "This player taking another invitation.");
		        							}
		        						} else {
		        							player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You don't own any team!");
		        						}
		        					} else {
		        						player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "The current player is already in a team.");
		        					}
		        				} else {
		        					player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You don't have a team.");
		        				}
		        			} else {
		        				player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "Player isn't online or you tried to invite yourself.");
		        			}
		        		} else {
		        			player.sendMessage(plugin.titleZ + ChatColor.RED + "/team invite <player-name>");
		        		}
		        	} else {
		        		sender.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You cannot use this command, if you are not a player.");
		        	}
		        } if (args[0].equalsIgnoreCase("accept")) {
		        	int playerTeam;
			        int teamAdmin;
			        
		        	if (sender instanceof Player) {
		        		if (args.length > 1) {
		        			if (plugin.invite.containsKey(player.getName())) {
		        				OfflinePlayer teamAdminOffline = Bukkit.getServer().getOfflinePlayer((String) plugin.cm.getTeamListConfig().getStringList((String) plugin.invite.get(player.getName())).get(0));
		        				
		        				if (teamAdminOffline.isOnline()) {
		        					Player[] arrayOfPlayer;
		        					teamAdmin = (arrayOfPlayer = Bukkit.getOnlinePlayers()).length;
		        					
		        					for (playerTeam = 0; playerTeam < teamAdmin; playerTeam++) {
		        						Player arrayPlayer = arrayOfPlayer[playerTeam];
		        						
		        						if (plugin.cm.getTeamListConfig().getStringList((String) plugin.invite.get(player.getName())).contains(arrayPlayer.getName())) {
		        							arrayPlayer.sendMessage(plugin.titleZ + ChatColor.GREEN + player.getName() + ChatColor.WHITE + " joined to your team!");
		        						}
		        					}
		        					
		        					player.sendMessage(plugin.titleZ + ChatColor.WHITE + "You joined to " + ChatColor.GOLD + (String) plugin.invite.get(player.getName()) + ChatColor.WHITE + "!");
		        					
		        					List<String> teamL = plugin.cm.getTeamListConfig().getStringList((String) plugin.invite.get(player.getName()));
		        					
		        					plugin.cm.getTeamConfig().set("Players." + player.getName() + ".Stats.Team", plugin.invite.get(player.getName()));
		        					plugin.cm.saveTeamConfig();
		        					
		        					teamL.add(player.getName());
		        					
		        					plugin.cm.getTeamListConfig().set((String) plugin.invite.get(player.getName()), teamL);
		        					plugin.cm.saveTeamListConfig();
		        					plugin.invite.remove(player.getName());
		        				} else {
		        					player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "The team owner is not online, so you can't join the team.");
		        				}
		        			} else {
		        				player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You aren't taking any invitation right now.");
		        			}
		        		} else {
		        			player.sendMessage(plugin.titleZ + ChatColor.RED + "/team accept <player-name>");
		        		}
		        	} else {
		        		sender.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You cannot use this command, if you are not a player.");
		        	}
		        } if (args[0].equalsIgnoreCase("leave")) {
		        	if (sender instanceof Player) {
		        		if (plugin.cm.getTeamConfig().getString("Players." + player.getName() + ".Stats.Team") != null) {
		        			String playerTeam = plugin.cm.getTeamConfig().getString("Players." + player.getName() + ".Stats.Team");
		        			Player teamAdmin = Bukkit.getPlayer((String) plugin.cm.getTeamListConfig().getStringList(playerTeam).get(0));
		        			
		        			if (teamAdmin != player) {
		        				plugin.cm.getTeamConfig().set("Players." + player.getName() + ".Stats.Team", null);
		        				
		        				List<String> teamL = plugin.cm.getTeamListConfig().getStringList(playerTeam);
		        				teamL.remove(player.getName());
		        				
		        				plugin.cm.getTeamListConfig().set(playerTeam, teamL);
		        				
		        				player.sendMessage(plugin.titleZ + ChatColor.WHITE + "You left your team.");
		        				
		        				plugin.cm.saveTeamConfig();
		        				plugin.cm.saveTeamListConfig();
		        				
		        				for (Player arrayPlayer : Bukkit.getOnlinePlayers()) {
		        					if (plugin.cm.getTeamListConfig().getStringList(playerTeam).contains(arrayPlayer.getName())) {
		        						arrayPlayer.sendMessage(plugin.titleZ + ChatColor.DARK_RED + player.getName() + ChatColor.WHITE + ", left your team!");
		        					}
		        				}
		        			} else {
		        				player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You are the team leader, you can't leave the team.");
		        				player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "If you want to close the team, you need to write: " + ChatColor.RED + "/party close" + ChatColor.DARK_RED + ".");
		        			}
		        		} else {
		        			player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You aren't in a team dumbass.");
		        		}
		        	} else {
		        		sender.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You cannot use this command, if you are not a player.");
		        	}
		        }
		        
		        Object teamL;
		        
		        if (args[0].equalsIgnoreCase("kick")) {
		        	if (sender instanceof Player) {
		        		if (args.length > 1) {
		        			OfflinePlayer kickedOffline = Bukkit.getServer().getOfflinePlayer(args[1]);
		        			
		        			if (plugin.cm.getTeamConfig().getString("Players." + player.getName() + ".Stats.Team") != null) {
		        				String teamName = plugin.cm.getTeamConfig().getString("Players." + player.getName() + ".Stats.Team");
		                        String teamLeader = (String) plugin.cm.getTeamListConfig().getStringList(teamName).get(0);
		                        OfflinePlayer teamLeaderPlayer = Bukkit.getOfflinePlayer(teamLeader);
		                        
		                        if (teamLeaderPlayer == player) {
		                        	if (kickedOffline != player) {
		                        		if (plugin.cm.getTeamListConfig().getStringList(teamName).contains(kickedOffline.getName())) {
		                        			plugin.cm.getTeamConfig().set("Players." + kickedOffline.getName() + ".Stats.Team", null);
		                        			
		                        			teamL = plugin.cm.getTeamListConfig().getStringList(teamName);
		                        			((List<?>) teamL).remove(kickedOffline.getName());
		                        			
		                        			plugin.cm.getTeamListConfig().set(teamName, teamL);
		                        			
		                        			plugin.cm.saveTeamConfig();
		                        			plugin.cm.saveTeamListConfig();
		                        			
		                        			if (kickedOffline.isOnline()) {
		                        				Player kickedPlayer = Bukkit.getServer().getPlayer(args[1]);
		                                        kickedPlayer.sendMessage(plugin.titleZ + ChatColor.WHITE + "You got kicked from " + ChatColor.GOLD + teamName + ChatColor.WHITE + "!");
		                        			}
		                        			
		                        			for (Object aPlayer : Bukkit.getOnlinePlayers()) {
		                        				if (plugin.cm.getTeamListConfig().getStringList(teamName).contains(((Player) aPlayer).getName())) {
		                        					((Player) aPlayer).sendMessage(plugin.titleZ + ChatColor.DARK_RED + kickedOffline.getName() + ChatColor.WHITE + " got kicked from your team.");
		                        				}
		                        			}
		                        		} else {
		                        			player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "This player isn't in your team.");
		                        		}
		                        	} else {
		                        		player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You can't kick yourself.");
		                        	}
		                        } else {
		                        	player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You aren't the team leader!");
		                        }
		        			} else {
		        				player.sendMessage(plugin.titleZ + ChatColor.RED + "/team kick <player-name>");
		        			}
		        		} else {
		        			sender.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You cannot use this command, if you are not a player.");
		        		}
		        	}
		        } if (args[0].equalsIgnoreCase("close")) {
		        	if (sender instanceof Player) {
		        		if (plugin.cm.getTeamConfig().getString("Players." + player.getName() + ".Stats.Team") != null) {
		        			String teamName = plugin.cm.getTeamConfig().getString("Players." + player.getName() + ".Stats.Team");
		                    OfflinePlayer teamLeader = Bukkit.getOfflinePlayer((String) plugin.cm.getTeamListConfig().getStringList(teamName).get(0));
		                    
		                    if (teamLeader == player) {
		                    	for (OfflinePlayer oPlayer : Bukkit.getOfflinePlayers()) {
		                    		if (plugin.cm.getTeamListConfig().getStringList(teamName).contains(oPlayer.getName())) {
		                    			if (oPlayer.isOnline()) {
		                    				Player oPlayerClose = (Player) oPlayer;
		                    				
		                    				oPlayerClose.sendMessage(plugin.titleZ + ChatColor.WHITE + "Your team has closed!");
		                    			}
		                    			
		                    			plugin.cm.getTeamConfig().set("Players." + oPlayer.getName() + ".Stats.Team", null);
		                    		}
		                    	}
		                    	
		                    	plugin.cm.getTeamListConfig().getStringList(teamName).clear();
		                    	plugin.cm.getTeamListConfig().set(teamName, null);
		                        plugin.cm.saveTeamConfig();
		                        plugin.cm.saveTeamListConfig();
		                    } else {
		                    	player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You aren't the team leader.");
		                    }
		        		} else {
		        			player.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You don't have a team.");
		        		}
		        	} else {
		        		sender.sendMessage(plugin.titleZ + ChatColor.DARK_RED + "You cannot use this command, if you are not a player.");
		        	}
		        }
			}
			
			return true;
		}
	    
		return false;
	}
}
