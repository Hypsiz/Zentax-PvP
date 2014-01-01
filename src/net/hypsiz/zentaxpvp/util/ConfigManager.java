package net.hypsiz.zentaxpvp.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import net.hypsiz.zentaxpvp.Main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {

	private FileConfiguration groupsConfig = null;
	private File groupsConfigFile = null;
	
	private FileConfiguration listsConfig = null;
	private File listsConfigFile = null;
	
	private FileConfiguration ksConfig = null;
	private File ksConfigFile = null;
	
	private FileConfiguration hksConfig = null;
	private File hksConfigFile = null;
	
	private FileConfiguration killsConfig = null;
	private File killsConfigFile = null;
	
	private FileConfiguration deathsConfig = null;
	private File deathsConfigFile = null;
	
	private FileConfiguration combatConfig = null;
	private File combatConfigFile = null;
	
	private FileConfiguration reportsConfig = null;
	private File reportsConfigFile = null;
	
	private FileConfiguration dataConfig = null;
	private File dataConfigFile = null;
	
	private FileConfiguration onev1Config = null;
	private File onev1ConfigFile = null;
	
	private FileConfiguration teamConfig = null;
	private File teamConfigFile = null;
	
	private FileConfiguration teamListConfig = null;
	private File teamListConfigFile = null;
	
	private Main plugin;
	
	public ConfigManager(Main instance) {
		plugin = instance;
	}
	
	public void reloadGroupsConfig() {
	    if (groupsConfigFile == null) {
	    	groupsConfigFile = new File(plugin.getDataFolder(), "groups.yml");
	    }
	    
	    groupsConfig = YamlConfiguration.loadConfiguration(groupsConfigFile);
	    
	    // Look for defaults in the jar
	    InputStream defConfigStream = this.plugin.getResource("groups.yml");
	    
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        
	        groupsConfig.setDefaults(defConfig);
	    }
	}
	
	public FileConfiguration getGroupsConfig() {
	    if (groupsConfig == null) {
	    	reloadGroupsConfig();
	    }
	    return groupsConfig;
	}
	
	public void saveGroupsConfig() {
	    if (groupsConfig == null || groupsConfigFile == null) {
	    	return;
	    }
	    
	    try {
	        getGroupsConfig().save(groupsConfigFile);
	    } catch (IOException e) {
	        plugin.getLogger().log(Level.SEVERE, "Could not save config to " + groupsConfigFile, e);
	    }
	}
	
	public void reloadListsConfig() {
	    if (listsConfigFile == null) {
	    	listsConfigFile = new File(plugin.getDataFolder(), "lists.yml");
	    }
	    
	    listsConfig = YamlConfiguration.loadConfiguration(listsConfigFile);
	    
	    // Look for defaults in the jar
	    InputStream defConfigStream = this.plugin.getResource("lists.yml");
	    
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        
	        listsConfig.setDefaults(defConfig);
	    }
	}
	
	public FileConfiguration getListsConfig() {
	    if (listsConfig == null) {
	    	reloadListsConfig();
	    }
	    return listsConfig;
	}
	
	public void saveListsConfig() {
	    if (listsConfig == null || listsConfigFile == null) {
	    	return;
	    }
	    
	    try {
	        getListsConfig().save(listsConfigFile);
	    } catch (IOException e) {
	        plugin.getLogger().log(Level.SEVERE, "Could not save config to " + listsConfigFile, e);
	    }
	}
	
	public void reloadKSConfig() {
	    if (ksConfigFile == null) {
	    	ksConfigFile = new File(plugin.getDataFolder(), "ks.yml");
	    }
	    
	    ksConfig = YamlConfiguration.loadConfiguration(ksConfigFile);
	    
	    // Look for defaults in the jar
	    InputStream defConfigStream = this.plugin.getResource("ks.yml");
	    
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        
	        ksConfig.setDefaults(defConfig);
	    }
	}
	
	public FileConfiguration getKSConfig() {
	    if (ksConfig == null) {
	    	reloadKSConfig();
	    }
	    return ksConfig;
	}
	
	public void saveKSConfig() {
	    if (ksConfig == null || ksConfigFile == null) {
	    	return;
	    }
	    
	    try {
	        getKSConfig().save(ksConfigFile);
	    } catch (IOException e) {
	        plugin.getLogger().log(Level.SEVERE, "Could not save config to " + ksConfigFile, e);
	    }
	}
	
	public void reloadHKSConfig() {
	    if (hksConfigFile == null) {
	    	hksConfigFile = new File(plugin.getDataFolder(), "hks.yml");
	    }
	    
	    hksConfig = YamlConfiguration.loadConfiguration(ksConfigFile);
	    
	    // Look for defaults in the jar
	    InputStream defConfigStream = this.plugin.getResource("hks.yml");
	    
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        
	        hksConfig.setDefaults(defConfig);
	    }
	}
	
	public FileConfiguration getHKSConfig() {
	    if (hksConfig == null) {
	    	reloadHKSConfig();
	    }
	    return hksConfig;
	}
	
	public void saveHKSConfig() {
	    if (hksConfig == null || hksConfigFile == null) {
	    	return;
	    }
	    
	    try {
	        getHKSConfig().save(hksConfigFile);
	    } catch (IOException e) {
	        plugin.getLogger().log(Level.SEVERE, "Could not save config to " + hksConfigFile, e);
	    }
	}
	
	public void reloadKillsConfig() {
	    if (killsConfigFile == null) {
	    	killsConfigFile = new File(plugin.getDataFolder(), "kills.yml");
	    }
	    
	    killsConfig = YamlConfiguration.loadConfiguration(killsConfigFile);
	    
	    // Look for defaults in the jar
	    InputStream defConfigStream = this.plugin.getResource("kills.yml");
	    
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        
	        killsConfig.setDefaults(defConfig);
	    }
	}
	
	public FileConfiguration getKillsConfig() {
	    if (killsConfig == null) {
	    	reloadKillsConfig();
	    }
	    return killsConfig;
	}
	
	public void saveKillsConfig() {
	    if (killsConfig == null || killsConfigFile == null) {
	    	return;
	    }
	    
	    try {
	        getKillsConfig().save(killsConfigFile);
	    } catch (IOException e) {
	        plugin.getLogger().log(Level.SEVERE, "Could not save config to " + killsConfigFile, e);
	    }
	}
	
	public void reloadDeathsConfig() {
	    if (deathsConfigFile == null) {
	    	deathsConfigFile = new File(plugin.getDataFolder(), "deaths.yml");
	    }
	    
	    deathsConfig = YamlConfiguration.loadConfiguration(deathsConfigFile);
	    
	    // Look for defaults in the jar
	    InputStream defConfigStream = this.plugin.getResource("deaths.yml");
	    
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        
	        deathsConfig.setDefaults(defConfig);
	    }
	}
	
	public FileConfiguration getDeathsConfig() {
	    if (deathsConfig == null) {
	    	reloadDeathsConfig();
	    }
	    return deathsConfig;
	}
	
	public void saveDeathsConfig() {
	    if (deathsConfig == null || deathsConfigFile == null) {
	    	return;
	    }
	    
	    try {
	        getDeathsConfig().save(deathsConfigFile);
	    } catch (IOException e) {
	        plugin.getLogger().log(Level.SEVERE, "Could not save config to " + deathsConfigFile, e);
	    }
	}
	
	public void reloadCombatConfig() {
	    if (combatConfigFile == null) {
	    	combatConfigFile = new File(plugin.getDataFolder(), "combat.yml");
	    }
	    
	    combatConfig = YamlConfiguration.loadConfiguration(combatConfigFile);
	    
	    // Look for defaults in the jar
	    InputStream defConfigStream = this.plugin.getResource("combat.yml");
	    
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        
	        combatConfig.setDefaults(defConfig);
	    }
	}
	
	public FileConfiguration getCombatConfig() {
	    if (combatConfig == null) {
	    	reloadCombatConfig();
	    }
	    return combatConfig;
	}
	
	public void saveCombatConfig() {
	    if (combatConfig == null || combatConfigFile == null) {
	    	return;
	    }
	    
	    try {
	        getCombatConfig().save(combatConfigFile);
	    } catch (IOException e) {
	        plugin.getLogger().log(Level.SEVERE, "Could not save config to " + combatConfigFile, e);
	    }
	}
	
	public void reloadReportsConfig() {
	    if (reportsConfigFile == null) {
	    	reportsConfigFile = new File(plugin.getDataFolder(), "reports.yml");
	    }
	    
	    reportsConfig = YamlConfiguration.loadConfiguration(reportsConfigFile);
	 
	    // Look for defaults in the jar
	    InputStream defConfigStream = plugin.getResource("reports.yml");
	    
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        reportsConfig.setDefaults(defConfig);
	    }
	}
	
	public FileConfiguration getReportsConfig() {
	    if (reportsConfig == null) {
	        reloadReportsConfig();
	    }
	    
	    return reportsConfig;
	}
	
	public void saveReportsConfig() {
	    if (reportsConfig == null || reportsConfigFile == null) {
	        return;
	    } try {
	        getReportsConfig().save(reportsConfigFile);
	    } catch (IOException ex) {
	    	plugin.getLogger().log(Level.SEVERE, "Could not save config to " + reportsConfigFile, ex);
	    }
	}
	
	public void reloadDataConfig() {
	    if (dataConfigFile == null) {
	    	dataConfigFile = new File(plugin.getDataFolder(), "data.yml");
	    }
	    
	    dataConfig = YamlConfiguration.loadConfiguration(dataConfigFile);
	 
	    // Look for defaults in the jar
	    InputStream defConfigStream = plugin.getResource("data.yml");
	    
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        dataConfig.setDefaults(defConfig);
	    }
	}
	
	public FileConfiguration getDataConfig() {
	    if (dataConfig == null) {
	        reloadDataConfig();
	    }
	    
	    return dataConfig;
	}
	
	public void saveDataConfig() {
	    if (dataConfig == null || dataConfigFile == null) {
	        return;
	    } try {
	        getDataConfig().save(dataConfigFile);
	    } catch (IOException ex) {
	    	plugin.getLogger().log(Level.SEVERE, "Could not save config to " + dataConfigFile, ex);
	    }
	}
	
	public void reload1v1Config() {
	    if (onev1ConfigFile == null) {
	    	onev1ConfigFile = new File(plugin.getDataFolder(), "1v1.yml");
	    }
	    
	    onev1Config = YamlConfiguration.loadConfiguration(onev1ConfigFile);
	 
	    // Look for defaults in the jar
	    InputStream defConfigStream = plugin.getResource("1v1.yml");
	    
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        onev1Config.setDefaults(defConfig);
	    }
	}
	
	public FileConfiguration get1v1Config() {
	    if (onev1Config == null) {
	        reload1v1Config();
	    }
	    
	    return onev1Config;
	}
	
	public void save1v1Config() {
	    if (onev1Config == null || onev1ConfigFile == null) {
	        return;
	    } try {
	        get1v1Config().save(onev1ConfigFile);
	    } catch (IOException ex) {
	    	plugin.getLogger().log(Level.SEVERE, "Could not save config to " + onev1ConfigFile, ex);
	    }
	}
	
	public void reloadTeamConfig() {
	    if (teamConfigFile == null) {
	    	teamConfigFile = new File(plugin.getDataFolder(), "team.yml");
	    }
	    
	    teamConfig = YamlConfiguration.loadConfiguration(teamConfigFile);
	 
	    // Look for defaults in the jar
	    InputStream defConfigStream = plugin.getResource("team.yml");
	    
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        teamConfig.setDefaults(defConfig);
	    }
	}
	
	public FileConfiguration getTeamConfig() {
	    if (teamConfig == null) {
	        reloadTeamConfig();
	    }
	    
	    return teamConfig;
	}
	
	public void saveTeamConfig() {
	    if (teamConfig == null || teamConfigFile == null) {
	        return;
	    } try {
	        getTeamConfig().save(teamConfigFile);
	    } catch (IOException ex) {
	    	plugin.getLogger().log(Level.SEVERE, "Could not save config to " + teamConfigFile, ex);
	    }
	}
	
	public void reloadTeamListConfig() {
	    if (teamListConfigFile == null) {
	    	teamListConfigFile = new File(plugin.getDataFolder(), "teamlist.yml");
	    }
	    
	    teamListConfig = YamlConfiguration.loadConfiguration(teamListConfigFile);
	 
	    // Look for defaults in the jar
	    InputStream defConfigStream = plugin.getResource("teamlist.yml");
	    
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        teamListConfig.setDefaults(defConfig);
	    }
	}
	
	public FileConfiguration getTeamListConfig() {
	    if (teamListConfig == null) {
	        reloadTeamListConfig();
	    }
	    
	    return teamListConfig;
	}
	
	public void saveTeamListConfig() {
	    if (teamListConfig == null || teamListConfigFile == null) {
	        return;
	    } try {
	        getTeamListConfig().save(teamListConfigFile);
	    } catch (IOException ex) {
	    	plugin.getLogger().log(Level.SEVERE, "Could not save config to " + teamListConfigFile, ex);
	    }
	}
}
