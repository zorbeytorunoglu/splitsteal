package tr.thelegend.splitsteal.commands;

import java.util.ArrayList;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tr.thelegend.splitsteal.Main;
import tr.thelegend.splitsteal.configuration.SettingsHandler;
import tr.thelegend.splitsteal.objects.SSArena;

public class Splitsteal implements CommandExecutor {
	
	private SettingsHandler config;
	private Main plugin;
	
	public Splitsteal(Main plugin) {
		this.plugin=plugin;
		this.config=plugin.getSettingsHandler();
	}
	
	public boolean onCommand(CommandSender gonderen, Command komut, String label, String[] args) {
		
		if (komut.getName().equalsIgnoreCase("splitsteal")) {
			
			if (args.length==0) {
				if (!gonderen.hasPermission("splitsteal.main")) {
					gonderen.sendMessage(config.getNoPerm());
					return false;
				}
				
				for (String s:config.getCommandsList()) {
					gonderen.sendMessage(s);
				}
				
				return true;
				
			}
			
			if (args[0].equalsIgnoreCase("send")) {
				
				if (!gonderen.hasPermission("splitsteal.send")) {
					gonderen.sendMessage(config.getNoPerm());
					return false;
				}
				
				if (args.length!=5) {
					
					gonderen.sendMessage(config.getSendUsage());
					return false;
					
				}
				
				Player hdf1=Bukkit.getPlayer(args[1]);
				
				if (hdf1==null) {
					gonderen.sendMessage(config.playerNotFound());
					return false;
				}
				
				Player hdf2=Bukkit.getPlayer(args[2]);
				if (hdf2==null) {
					gonderen.sendMessage(config.playerNotFound());
					return false;
				}
				
				String arenaName=args[3];
				
				if (!plugin.getUtil().doesExists(arenaName)) {
					gonderen.sendMessage(config.getArenaNotFound());
					return false;
				}
				
				SSArena arena=plugin.getUtil().getArena(arenaName);
				
				if (arena.isOccupied()) {
					gonderen.sendMessage(config.getArenaOccupied());
					return false;
				}
				
				if (!plugin.getUtil().isArenaComplete(arena)) {
					
					gonderen.sendMessage(config.getArenaNotComplete());
					return false;
					
				}
				
				Integer amount=0;
				
				try {
					amount=Integer.parseInt(args[4]);
				} catch(NumberFormatException e) { 
			        gonderen.sendMessage(config.getInvalidNumber());
			        return false;
			    } catch(NullPointerException e) {
			        gonderen.sendMessage(config.getInvalidNumber());
			    	return false;
			    }
				
				arena.setAmount(amount);
				
				arena.setFirstPlayer(hdf1);
				arena.setSecondPlayer(hdf2);
				
				plugin.getUtil().startSplitShare(arena);
				
				return true;
				
			}
			
			if (args[0].equalsIgnoreCase("arena")) {
				
				if (!gonderen.hasPermission("splitsteal.arena")) {
					gonderen.sendMessage(config.getNoPerm());
					return false;
				}
				
				if (args.length!=2) {
					
					gonderen.sendMessage(config.getArenaUsage());
					return false;
					
				}
				
				SSArena arena=plugin.getUtil().getArena(args[1]);
				
				if (arena==null) {
					gonderen.sendMessage(config.getArenaNotFound());
					return false;
				}
				
				for (String s:config.getArenaCheck()) {
					
					s=s.replace("%arena_name%", arena.getName());
					
					s=arena.getPos1()==null ? s.replace("%position1%", config.getNotSet()) : s.replace("%position1%", config.getSet());
					
					s=arena.getPos2()==null ? s.replace("%position2%", config.getNotSet()) : s.replace("%position2%", config.getSet());
					
					s=arena.getResultSpot1()==null ? s.replace("%result1%", config.getNotSet()) : s.replace("%result1%", config.getSet());
					
					s=arena.getResultSpot2()==null ? s.replace("%result2%", config.getNotSet()) : s.replace("%result2%", config.getSet());
					
					s=arena.getSpawn()==null ? s.replace("%spawn%", config.getNotSet()) : s.replace("%spawn%", config.getSet());
					
					if (arena.getChooseButtons()!=null) {
						s=arena.getChooseButtons().size()==0 ? s.replace("%buttons%", 0+"") : s.replace("%buttons%", arena.getChooseButtons().size()+"");
					} else {
						s=s.replace("%buttons%", 0+"");
					}
					
					s=arena.getPos1()!=null &&
							arena.getPos2()!=null &&
							arena.getResultSpot1()!=null && 
							arena.getResultSpot2()!=null ?
									s.replace("%active%", config.getActive()) : 
										s.replace("%active%", config.getNotActive());
						
					gonderen.sendMessage(s);
					
				}
				
				return true;
				
			}
			
			if (args[0].equalsIgnoreCase("setup")) {
				
				if (args.length==1) {
					if (!gonderen.hasPermission("splitsteal.main")) {
						gonderen.sendMessage(config.getNoPerm());
						return false;
					}
					
					for (String s:config.getCommandsList()) {
						gonderen.sendMessage(s);
					}
					
					return true;
				}
				
				if (args[1].equalsIgnoreCase("create")) {
					
					if (!gonderen.hasPermission("splitsteal.setup.create")) {
						gonderen.sendMessage(config.getNoPerm());
						return false;
					}
					
					if (args.length!=3) {
						
						gonderen.sendMessage(config.getSetupCreateUsage());
						return false;
						
					}
					
					String arenaName=args[2];
					
					if (plugin.getUtil().doesExists(arenaName)) {
						gonderen.sendMessage(config.getNotUniqueName());
						return false;
					}
					
					SSArena arena=new SSArena(arenaName);
					
					plugin.getArenas().add(arena);
					
					for (String s:config.getArenaCreated()) {
						gonderen.sendMessage(s.replace("%arena_name%", arena.getName()));
					}
					
					return true;
					
				}
				
				if (args[1].equalsIgnoreCase("delete")) {
					
					if (!gonderen.hasPermission("splitsteal.setup.delete")) {
						gonderen.sendMessage(config.getNoPerm());
						return false;
					}
					
					if (args.length!=3) {
						
						gonderen.sendMessage(config.getDeleteUsage());
						return false;
						
					}
					
					String arenaName=args[2];
					
					if (!plugin.getUtil().doesExists(arenaName)) {
						gonderen.sendMessage(config.getArenaNotFound());
						return false;
					}
					
					SSArena arena=plugin.getUtil().getArena(arenaName);
					
					gonderen.sendMessage(config.getDeleted().replace("%arena_name%", arena.getName()));
					
					plugin.getUtil().deleteArena(arena);
					
					arena=null;
					
					return true;
					
				}
				
				if (args[1].equalsIgnoreCase("setpos1")) {
					
					if (!gonderen.hasPermission("splitsteal.setup.setpos1")) {
						gonderen.sendMessage(config.getNoPerm());
						return false;
					}
					
					if (!(gonderen instanceof Player)) {
						gonderen.sendMessage(config.getOnlyInGamme());
						return false;
					}
					
					if (args.length!=3) {
						gonderen.sendMessage(config.getPos1Usage());
						return false;
					}
					
					String arenaName=args[2];
					
					if (!plugin.getUtil().doesExists(arenaName)) {
						gonderen.sendMessage(config.getArenaNotFound());
						return false;
					}
					
					Player player=(Player)gonderen;
					
					Location loc=player.getLocation();
					
					SSArena arena=plugin.getUtil().getArena(arenaName);
					
					arena.setPos1(loc);
					
					gonderen.sendMessage(config.getPos1Set().replace("%arena_name%", arena.getName()));
					
					return true;
					
				}
				
				if (args[1].equalsIgnoreCase("setspawn")) {
					
					if (!gonderen.hasPermission("splitsteal.setup.setspawn")) {
						gonderen.sendMessage(config.getNoPerm());
						return false;
					}
					
					if (!(gonderen instanceof Player)) {
						gonderen.sendMessage(config.getOnlyInGamme());
						return false;
					}
					
					if (args.length!=3) {
						gonderen.sendMessage(config.getPos1Usage());
						return false;
					}
					
					String arenaName=args[2];
					
					if (!plugin.getUtil().doesExists(arenaName)) {
						gonderen.sendMessage(config.getArenaNotFound());
						return false;
					}
					
					Player player=(Player)gonderen;
					
					Location loc=player.getLocation();
					
					SSArena arena=plugin.getUtil().getArena(arenaName);
					
					arena.setSpawn(loc);
					
					gonderen.sendMessage(config.getSpawnSet().replace("%arena_name%", arena.getName()));
					
					return true;
					
				}
				
				if (args[1].equalsIgnoreCase("setpos2")) {
					
					if (!gonderen.hasPermission("splitsteal.setup.setpos2")) {
						gonderen.sendMessage(config.getNoPerm());
						return false;
					}
					
					if (!(gonderen instanceof Player)) {
						gonderen.sendMessage(config.getOnlyInGamme());
						return false;
					}
					
					if (args.length!=3) {
						gonderen.sendMessage(config.getPos1Usage());
						return false;
					}
					
					String arenaName=args[2];
					
					if (!plugin.getUtil().doesExists(arenaName)) {
						gonderen.sendMessage(config.getArenaNotFound());
						return false;
					}
					
					Player player=(Player)gonderen;
					
					Location loc=player.getLocation();
					
					SSArena arena=plugin.getUtil().getArena(arenaName);
					
					arena.setPos2(loc);
					
					gonderen.sendMessage(config.getPos2Set().replace("%arena_name%", arena.getName()));
					
					return true;
					
				}
				
				if (args[1].equalsIgnoreCase("setresult1")) {
					
					if (!gonderen.hasPermission("splitsteal.setup.setresult1")) {
						gonderen.sendMessage(config.getNoPerm());
						return false;
					}
					
					if (!(gonderen instanceof Player)) {
						gonderen.sendMessage(config.getOnlyInGamme());
						return false;
					}
					
					if (args.length!=3) {
						gonderen.sendMessage(config.getSetResult1Usage());
						return false;
					}
					
					String arenaName=args[2];
					
					if (!plugin.getUtil().doesExists(arenaName)) {
						gonderen.sendMessage(config.getArenaNotFound());
						return false;
					}
					
					Player player=(Player)gonderen;
					
					Block targetBlock = player.getTargetBlock((Set<Material>) null, 10);
					
					if (targetBlock==null || targetBlock.getType()==Material.AIR) {
						
						player.sendMessage(config.getInvalidBlock());
						return false;
						
					}
					
					SSArena arena=plugin.getUtil().getArena(arenaName);
					
					arena.setResultSpot1(targetBlock.getLocation());
					
					targetBlock.setType(Material.AIR);
					
					gonderen.sendMessage(config.getResult1Set().replace("%arena_name%", arena.getName()));
					
					return true;
					
				}
				
				if (args[1].equalsIgnoreCase("setresult2")) {
					
					if (!gonderen.hasPermission("splitsteal.setup.setresult2")) {
						gonderen.sendMessage(config.getNoPerm());
						return false;
					}
					
					if (!(gonderen instanceof Player)) {
						gonderen.sendMessage(config.getOnlyInGamme());
						return false;
					}
					
					if (args.length!=3) {
						gonderen.sendMessage(config.getSetResult2Usage());
						return false;
					}
					
					String arenaName=args[2];
					
					if (!plugin.getUtil().doesExists(arenaName)) {
						gonderen.sendMessage(config.getArenaNotFound());
						return false;
					}
					
					Player player=(Player)gonderen;
					
					Block targetBlock = player.getTargetBlock((Set<Material>) null, 10);
					
					if (targetBlock==null || targetBlock.getType()==Material.AIR) {
						
						player.sendMessage(config.getInvalidBlock());
						return false;
						
					}
					
					SSArena arena=plugin.getUtil().getArena(arenaName);
					
					arena.setResultSpot2(targetBlock.getLocation());
					
					targetBlock.setType(Material.AIR);
					
					gonderen.sendMessage(config.getResult2Set().replace("%arena_name%", arena.getName()));
					
					return true;
					
				}
				
				if (args[1].equalsIgnoreCase("addbutton")) {
					
					if (!gonderen.hasPermission("splitsteal.setup.addbutton")) {
						gonderen.sendMessage(config.getNoPerm());
						return false;
					}
					
					if (!(gonderen instanceof Player)) {
						gonderen.sendMessage(config.getOnlyInGamme());
						return false;
					}
					
					if (args.length!=3) {
						gonderen.sendMessage(config.getSetResult2Usage());
						return false;
					}
					
					String arenaName=args[2];
					
					if (!plugin.getUtil().doesExists(arenaName)) {
						gonderen.sendMessage(config.getArenaNotFound());
						return false;
					}
					
					Player player=(Player)gonderen;
					
					Block targetBlock = player.getTargetBlock((Set<Material>) null, 10);
					
					if (targetBlock==null || targetBlock.getType()==Material.AIR) {
						
						player.sendMessage(config.getInvalidBlock());
						return false;
						
					}
					
					SSArena arena=plugin.getUtil().getArena(arenaName);
					
					if (arena.getChooseButtons()==null) {
						ArrayList<Location> buttons=new ArrayList<>();
						buttons.add(targetBlock.getLocation());
						arena.setChooseButtons(buttons);
					} else {
						arena.getChooseButtons().add(targetBlock.getLocation());
					}
							
					gonderen.sendMessage(config.getButtonAdded().replace("%arena_name%", arena.getName()));
					
					return true;
					
				}
				
			}
			
			
			
			return false;
			
		}
		
		return false;
		
	}
	
}
