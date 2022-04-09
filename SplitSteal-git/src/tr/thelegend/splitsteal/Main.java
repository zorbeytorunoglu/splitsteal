package tr.thelegend.splitsteal;

import java.util.ArrayList;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;
import tr.thelegend.splitsteal.commands.Splitsteal;
import tr.thelegend.splitsteal.configuration.Container;
import tr.thelegend.splitsteal.configuration.Menu;
import tr.thelegend.splitsteal.configuration.Resource;
import tr.thelegend.splitsteal.configuration.SettingsHandler;
import tr.thelegend.splitsteal.listeners.Choose;
import tr.thelegend.splitsteal.listeners.Protection;
import tr.thelegend.splitsteal.objects.SSArena;

public class Main extends JavaPlugin {
	
	private Resource config;
	private Resource data;
	
	private SettingsHandler settingsHandler;
	private Container container;
	
	private ArrayList<SSArena> arenas;
	
	private static Main plugin;
	private static Util util;
	private static Menu menu;
	
	private Economy econ=null;
	
	public void onEnable() {
		
		plugin=this;
		util=new Util(this);
		config=new Resource(this,"config.yml");
		config.load();
		data=new Resource(this,"data.yml");
		data.load();
		container=new Container(config);
		settingsHandler=new SettingsHandler(container, plugin);
		menu=new Menu(this);
		
		arenas=util.loadArenas(data);
		
		getCommand("splitsteal").setExecutor(new Splitsteal(this));
		getServer().getPluginManager().registerEvents(new Choose(this), this);
		getServer().getPluginManager().registerEvents(new Protection(this), this);
		
		getServer().getLogger().info("========== [ SPLIT or STEAL ] ==========");
		getServer().getLogger().info("If you need help: discord.mc-market.com");
		getServer().getLogger().info("========== [ SPLIT or STEAL ] ==========");
		
		if (!setupEconomy()) {
			getServer().getLogger().severe("[SplitSteal] We need Vault and a proper economy to work. Disabling.");
			this.setEnabled(false);
		}
		
	}
	
	public void onDisable() {
		
		util.saveArenas(arenas, data);
		
	}
	
	public Resource getResource() {
		return config;
	}
	
	public Main getPlugin() {
		return plugin;
	}

	public SettingsHandler getSettingsHandler() {
		return settingsHandler;
	}
	
	public ArrayList<SSArena> getArenas() {
		return arenas;
	}
	
	public Util getUtil() {
		return util;
	}
	
	public Resource getData() {
		return data;
	}

	public Menu getMenu() {
		return menu;
	}
	
	public Economy getEconomy() {
		return econ;
	}
	
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
}
