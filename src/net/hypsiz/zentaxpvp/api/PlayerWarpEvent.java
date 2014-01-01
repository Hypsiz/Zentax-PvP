package net.hypsiz.zentaxpvp.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerWarpEvent extends Event {

	Player p;
    Location l;
    String n;
    
    public PlayerWarpEvent(Player p, Location l, String n) {
    	this.p = p;
        this.l = l;
        this.n = n;
    }
    
    public Player getPlayer() {
    	return p;
    }
    
    public Location getWarpLocation() {
    	return l;
    }
    
    public String getWarpName() {
    	return n;
    }
    
    private static final HandlerList handlers = new HandlerList();
    
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
