package tr.thelegend.splitshare.configuration;

import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

public class Container {
	
	public final String noPerm;
	public final String onlyInGame;
	public final String playerNotFound;
	public final List<String> commands;
	public final String setupCreateUsage;
	public final String notUniqueName;
	public final List<String> arenaCreated;
	public final String arenaDoesntExists;
	public final String pos1Set;
	public final String setPos1Usage;
	public final String pos2Set;
	public final String setPos2Usage;
	public final String setResult1Usage;
	public final String result1Set;
	public final String setResult2Usage;
	public final String result2Set;
	public final String invalidBlock;
	public final String arenaUsage;
	public final List<String> arenaCheck;
	public final String set;
	public final String notSet;
	public final String active;
	public final String notActive;
	public final String deleteUsage;
	public final String deleted;
	public final String sendUsage;
	public final String invalidNumber;
	public final String arenaOccupied;
	public final String teleportSound;
	public final String menuTitle;
	public final Integer menuSize;
	public final ConfigurationSection menuItemsSet;
	public final boolean startTitleEnabled;
	public final String startTitle;
	public final String startSubtitle;
	public final boolean startMsgEnabled;
	public final List<String> startMsg;
	public final int ssEndIn;
	public final String menuOpenSound;
	public final String butttonAdded;
	public final String stealSound;
	public final String splitSound;
	public final String stealChosen;
	public final String splitChosen;
	public final boolean changeOfHeart;
	public final String noChangeOfHeart;
	public final String noChangeOfHeartSound;
	public final String splitBlock;
	public final String stealBlock;
	public final String resultSplitSound;
	public final String resultStealSound;
	public final String setSpawnUsage;
	public final String spawnSet;
	public final List<String> commandWhitelist;
	public final String commandBlocked;
	public final String youCantDoIt;
	public final String canceledNoChoice;
	public final String noChoice;
	public final int countdownStart;
	public final String countdownSound;
	public final String choseSplit;
	public final String choseSteal;
	public final String arenaNotComplete;
	public final String bothSteal;
	public final String bothSplit;
	public final String youStole;
	public final String heStole;
	
	public Container(Resource file) {
		
		this.noPerm=file.getString("messages.no-perm");
		this.onlyInGame=file.getString("messages.only-in-game");
		this.playerNotFound=file.getString("messages.player-not-found");
		this.commands=file.getStringList("messages.commands");
		this.setupCreateUsage=file.getString("messages.create-usage");
		this.notUniqueName=file.getString("messages.not-unique-name");
		this.arenaCreated=file.getStringList("messages.arena-created");
		this.arenaDoesntExists=file.getString("messages.arena-not-found");
		this.pos1Set=file.getString("messages.pos1-set");
		this.setPos1Usage=file.getString("messages.setpos1-usage");
		this.pos2Set=file.getString("messages.pos2-set");
		this.setPos2Usage=file.getString("messages.setpos2-usage");
		this.setResult1Usage=file.getString("messages.set-result1-usage");
		this.setResult2Usage=file.getString("messages.set-result2-usage");
		this.result1Set=file.getString("messages.result1-set");
		this.result2Set=file.getString("messages.result2-set");
		this.invalidBlock=file.getString("messages.invalid-block");
		this.arenaUsage = file.getString("messages.arena-usage");
		this.arenaCheck=file.getStringList("messages.arena-check");
		this.set=file.getString("messages.set");
		this.notSet=file.getString("messages.not-set");
		this.active=file.getString("messages.active");
		this.notActive=file.getString("messages.not-active");
		this.deleteUsage=file.getString("messages.delete-usage");
		this.deleted=file.getString("messages.deleted");
		this.sendUsage=file.getString("messages.send-usage");
		this.invalidNumber=file.getString("messages.invalid-number");
		this.arenaOccupied=file.getString("messages.arena-occupied");
		this.teleportSound=file.getString("sounds.teleport");
		this.menuTitle=file.getString("menus.name");
		this.menuSize=file.getInt("menus.size");
		this.menuItemsSet=file.getConfigurationSection("menus.items");
		this.startTitleEnabled=file.getBoolean("start.title.enabled");
		this.startTitle=file.getString("start.title.title");
		this.startSubtitle=file.getString("start.title.subtitle");
		this.startMsgEnabled=file.getBoolean("start.message.enabled");
		this.startMsg=file.getStringList("start.message.message");
		this.ssEndIn=file.getInt("ss-end-in");
		this.menuOpenSound=file.getString("sounds.menu-open");
		this.butttonAdded=file.getString("messages.button-added");
		this.splitSound=file.getString("sounds.split");
		this.stealSound=file.getString("sounds.steal");
		this.splitChosen=file.getString("messages.chosen-split");
		this.stealChosen=file.getString("messages.chosen-steal");
		this.changeOfHeart=file.getBoolean("change-of-heart-enabled");
		this.noChangeOfHeart=file.getString("messages.nochangeofheart");
		this.noChangeOfHeartSound=file.getString("sounds.nochangeofheart");
		this.splitBlock=file.getString("blocks.split");
		this.stealBlock=file.getString("blocks.steal");
		this.resultSplitSound=file.getString("sounds.result-split");
		this.resultStealSound=file.getString("sounds.result-steal");
		this.setSpawnUsage=file.getString("messages.set-spawn-usage");
		this.spawnSet=file.getString("messages.spawn-set");
		this.commandWhitelist=file.getStringList("command-whitelist");
		this.commandBlocked=file.getString("messages.command-blocked");
		this.youCantDoIt=file.getString("messages.youcantdoit");
		this.canceledNoChoice=file.getString("messages.canceled-nochoice");
		this.noChoice=file.getString("messages.nochoice");
		this.countdownStart=file.getInt("countdown.start-under");
		this.countdownSound=file.getString("countdown.sound");
		this.choseSplit=file.getString("messages.chose-split");
		this.choseSteal=file.getString("messages.chose-steal");
		this.arenaNotComplete=file.getString("messages.arena-not-complete");
		this.bothSteal=file.getString("messages.both-steal");
		this.bothSplit=file.getString("messages.both-split");
		this.youStole=file.getString("messages.you-stole");
		this.heStole=file.getString("messages.he-stole");
		
	}

}