package com.jyckos.uuidapi;

import java.util.UUID;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;

public class UUIDAPI extends JavaPlugin {
	private boolean enabled = false;
	private static UUIDAPI instance;
	@Override
	public void onEnable() {
		instance = this;
		if (enabled) return;
		enabled = true;
		this.getServer().getPluginManager().registerEvents(new SimpleListener(this), this);
		this.getCommand("uuidapi").setExecutor(new UUIDCmd(this));
		this.UUIDStorage = new UUIDStorage(this);
	}
	@Override
	public void onDisable() {
		this.UUIDStorage.saveNames();
		this.UUIDStorage.saveUUID();
	}
	public static UUID getUUID(String name) {
		return instance.getUUIDStorage().getUUID(name);
	}
	public static String getName(UUID uuid) {
		return instance.getUUIDStorage().getName(uuid);
	}
	public static UUIDAPI getInstance() {
		return instance;
	}
	private @Getter UUIDStorage UUIDStorage = null;
}
