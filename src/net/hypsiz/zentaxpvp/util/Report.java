/**
 * 
 */
package net.hypsiz.zentaxpvp.util;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.hypsiz.zentaxpvp.Main;

/**
 * @author Hai
 *
 */
public class Report {

	public static ArrayList<String> reports = new ArrayList<String>();
	
	private static String head = ChatColor.WHITE + "--------------------";
	private static String space = ChatColor.WHITE + "| ";
	
	public static void addReport(CommandSender sender, String s) {
		reports.add(sender.getName() + " : " + s);
	}
	
	public static void loadReports(Main instance) {
		for (String s : instance.cm.getReportsConfig().getStringList("report"))
			reports.add(s);
	}
	
	public static void readReports(CommandSender sender) {
		sendMessage(sender, head);
		
		for (String s : reports) {
			sendMessage(sender, s);
		}
		
		sendMessage(sender, head);
	}
	
	public static void sendMessage(CommandSender sender, String s) {
		sender.sendMessage(s);
	}
	
	public static void clearReports() {
		reports.clear();
	}
	
	public static void saveReports(Main instance) {
		instance.cm.getReportsConfig().set("report", reports);
		instance.cm.saveReportsConfig();
	}
	
	public static String getString(String[] args) {
		String msg = "";
		
		for (int i = 0; i < args.length; i++) {
			msg = msg + args[i] + " ";
		}
		
		return msg;
	}
	
	public static void sendReportsToAdmin(CommandSender sender, String s) {
		for (Player p : Bukkit.getOnlinePlayers())
			if ((p.hasPermission("report.read")) || (p.isOp()))
				p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "REPORT" + ChatColor.DARK_GRAY + "] " + ChatColor.GOLD + sender.getName() + " : " + s);
	}
	
	public static void sendHelp(CommandSender sender) {
		sendMessage(sender, head);
		
		sendMessage(sender, space + ChatColor.YELLOW + "/report - Report player/issue to admins!");
		sendMessage(sender, space + ChatColor.YELLOW + "/read - Read all reports.");
		sendMessage(sender, space + ChatColor.YELLOW + "/read clear - Clear all reports!");
		sendMessage(sender, head);
	}
}
