/**
 * This file is part of the Zentax PvP Minecraft Server
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

import net.hypsiz.zentaxpvp.Main;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fish;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Hypsiz
 *
 */
public class EntityListener implements Listener {

	private Main plugin;
	
	public EntityListener(Main instance) {
		plugin = instance;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		Entity entity = event.getEntity();
		
		if (entity instanceof Villager) {
			if (((Villager) entity).getCustomName().equalsIgnoreCase(ChatColor.GREEN + "Bob") || ((Villager) entity).getCustomName().equalsIgnoreCase(ChatColor.GREEN + "Peter") || ((Villager) entity).getCustomName().equalsIgnoreCase(ChatColor.GREEN + "Mike")) {
				event.setCancelled(true);
			}
		} if (event.getDamager() instanceof Arrow) {
			Arrow arrow = (Arrow) event.getDamager();
			LivingEntity shooter = arrow.getShooter();
			
			if (shooter instanceof Player) {
				if (event.getEntity() == shooter) {
					if ((((Player) shooter).getItemInHand().getType() == Material.BOW) && (((Player) shooter).getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "" + ChatColor.BOLD + "Archer's Bow"))) {
						event.setCancelled(true);
					} if ((((Player) shooter).getItemInHand().getType() == Material.BOW) && (((Player) shooter).getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Pyro's Bow"))) {
						event.setCancelled(true);
					}
				}
			}
		} if (event.getEntity() instanceof Player) {
			if (event.getDamager() instanceof Fish) {
				Player player = (Player) event.getEntity();
				
				if (plugin.fished.contains(player.getName())) {
					event.setCancelled(true);
				}
			} if (event.getDamager() instanceof Player) {
				Player damager = (Player) event.getDamager();
		        Player player = (Player) event.getEntity();
		        
		        if ((plugin.fisher.contains(damager.getName()) && (plugin.fished.contains(player.getName())) || (plugin.fisher.contains(player.getName())) && (plugin.fished.contains(damager.getName())))) {
		        	event.setCancelled(true);
		        }
			}
		} if ((event.getDamager() instanceof Player) && (event.getEntity() instanceof Player)) {
			Player player = (Player) event.getEntity();
			Player damager = (Player) event.getDamager();
			
			double angle = Math.abs(Math.toDegrees(player.getEyeLocation().getDirection().angle(player.getLocation().getDirection())));
			
			if (angle < 45.0D) {
				if ((damager.getItemInHand().getType() == Material.IRON_SWORD) && (damager.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Loki's Sword"))) {
					event.setDamage(event.getDamage() * 2);
					damager.sendMessage("LOL");
				}
			}
		} if ((event.getEntityType() == EntityType.ITEM_FRAME) || (event.getEntityType() == EntityType.PAINTING)) {
			if ((event.getEntityType() == EntityType.ITEM_FRAME) && (event.getDamager() instanceof Player)) {
				Player player = (Player) event.getDamager();
				
				if (player.getGameMode() != GameMode.CREATIVE) {
					event.setCancelled(true);
				}
			} if ((event.getEntityType() == EntityType.ITEM_FRAME) && (event.getDamager() instanceof Arrow)) {
				Player player = (Player) event.getDamager();
				
				if (player.getGameMode() != GameMode.CREATIVE) {
					event.setCancelled(true);
				}
			}
		}
		
		if (((event.getDamager() instanceof Player)) && ((event.getEntity() instanceof Player)) && (!(event.getDamager() instanceof Arrow))) {
			final Player player = (Player) event.getEntity();
			final Player damager = (Player) event.getDamager();
			
			if (!plugin.inCombat.contains(damager)) {
				plugin.inCombat.add(damager);
				plugin.inCombat.add(player);
				
				//damager.sendMessage(plugin.titleZP + ChatColor.RED + "You are now in combat with " + entity.getName());
				//player.sendMessage(plugin.titleZP + ChatColor.RED + "You are now in combat with " + damager.getName());
				
				this.plugin.getServer().getScheduler().runTaskLaterAsynchronously(this.plugin, new Runnable() {

					@Override
					public void run() {
						//damager.sendMessage(plugin.titleZP + ChatColor.RED + "You are no longer in combat!");
						//player.sendMessage(plugin.titleZP + ChatColor.RED + "You are no longer in combat!");
						
						plugin.inCombat.remove(damager);
						plugin.inCombat.remove(player);
					}
					
				}, plugin.cm.getCombatConfig().getLong("Time_In_Combat") * 20L);
			}
		} if (((event.getDamager() instanceof Arrow)) && ((event.getEntity() instanceof Player))) {
			Projectile proj = (Projectile) event.getDamager();
			LivingEntity shooter = proj.getShooter();
			
			if (((proj instanceof Arrow)) && ((shooter instanceof Player))) {
				final Player attacker = (Player) shooter;
				final Player player = (Player) event.getEntity();
				
				if (!plugin.inCombat.contains(attacker)) {
					plugin.inCombat.add(attacker);
					plugin.inCombat.add(player);
					
					//attacker.sendMessage(plugin.titleZP + ChatColor.RED + "You are now in combat with " + entity.getName());
					//player.sendMessage(plugin.titleZP + ChatColor.RED + "You are now in combat with " + attacker.getName());
					
					plugin.getServer().getScheduler().runTaskLaterAsynchronously(this.plugin, new Runnable() {

						@Override
						public void run() {
							//attacker.sendMessage(plugin.titleZP + ChatColor.RED + "You are no longer in combat!");
							//player.sendMessage(plugin.titleZP + ChatColor.RED + "You are no longer in combat!");
							
							plugin.inCombat.remove(attacker);
							plugin.inCombat.remove(player);
						}
						
					}, plugin.cm.getCombatConfig().getLong("Time_In_Combat") * 20L);
				}
			}
		} if (((event.getDamager() instanceof Player)) && ((event.getEntityType() == EntityType.ITEM_FRAME)) && (!(event.getDamager() instanceof Arrow))) {
			Player damager = (Player) event.getDamager();
			
			if (damager.getGameMode() != GameMode.CREATIVE) {
				event.setCancelled(true);
			}
		} if ((event.getEntity() instanceof Player) && (event.getDamager() instanceof Player)) {
			Player player = (Player) event.getEntity();
		    Player damager = (Player) event.getDamager();
		    
		    if (plugin.Team(player, damager)) {
		    	event.setCancelled(true);
		    }
		} if ((event.getEntity() instanceof Player) && (event.getDamager() instanceof Arrow)) {
			Player player = (Player) event.getEntity();
		    Arrow arrow = (Arrow) event.getDamager();
		    
		    if (arrow.getShooter() instanceof Player) {
		    	Player damager = (Player) arrow.getShooter();
		    	
		    	if (plugin.Team(player, damager)) {
		    		event.setCancelled(true);
		    	}
		    }
		} if ((event.getEntity() instanceof Player) && (event.getDamager() instanceof Fish)) {
			Player player = (Player) event.getEntity();
		    Fish fish = (Fish) event.getDamager();
		    
		    if (fish.getShooter() instanceof Player) {
		    	Player damager = (Player) fish.getShooter();
		    	
		    	if (plugin.Team(player, damager)) {
		    		event.setCancelled(true);
		    	}
		    }
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			if (event.getCause() == DamageCause.FALL) {
				try {
					Player player = (Player) event.getEntity();
					
					if ((player.getInventory().getBoots().getType() == Material.IRON_BOOTS) && (player.getInventory().getBoots().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "" + ChatColor.BOLD + "Chicken's Boots"))) {
						event.setCancelled(true);
					} else if ((player.getInventory().getChestplate().getType() == Material.LEATHER_CHESTPLATE) && (player.getInventory().getChestplate().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "" + ChatColor.BOLD + "Spider-Man's Chestplate"))) {
						event.setCancelled(true);
					}
				} catch (NullPointerException e) {
					
				}
			}
			
			if (plugin.noFall.contains(((Player) event.getEntity()).getName())) {
				if (event.getCause() == DamageCause.FALL) {
					plugin.noFall.remove(((Player) event.getEntity()).getName());
					
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityRegainHealth(EntityRegainHealthEvent event) {
		if (event.getEntity() instanceof Player) {
			if (!(((Player) event.getEntity()).hasPotionEffect(PotionEffectType.REGENERATION))) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onProjectileHit(ProjectileHitEvent event) {
		Entity entity = event.getEntity();
		
		if (entity instanceof Arrow) {
			final Arrow arrow = (Arrow) entity;
			Entity shooter = arrow.getShooter();
			
			if (shooter instanceof Player) {
				final Player player = (Player) shooter;
				
				if ((player.getItemInHand().getType() == Material.BOW) && (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "" + ChatColor.BOLD + "Archer's Bow"))) {
					arrow.remove();
					
					player.getWorld().playSound(arrow.getLocation(), Sound.DIG_GRAVEL, 1, 1);
				} if ((player.getItemInHand().getType() == Material.BOW) && (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Pyro's Bow"))) {
					arrow.remove();
					
					player.getWorld().playSound(arrow.getLocation(), Sound.DIG_GRAVEL, 1, 1);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDeath(EntityDeathEvent event) {
		event.setDroppedExp(0);
		event.getDrops().clear();
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onItemSpawn(ItemSpawnEvent event) {
		if (event.getEntity().getItemStack().getType() == Material.BOWL == true) {
			event.getEntity().remove();
		} if (event.getEntity().getItemStack().getType() == Material.LEASH == true) {
			event.getEntity().remove();
		}
	}
}
