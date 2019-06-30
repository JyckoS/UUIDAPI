package com.jyckos.uuidapi;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class UUIDStorage {
	private UUIDAPI m;
	private HashMap<String, UUID> uuids = new HashMap<String, UUID>();
	private HashMap<String, String> names = new HashMap<String, String>();
                   // UUID, String
	public UUIDStorage(UUIDAPI m) {
		this.m = m;
		this.loadUUID();
		this.loadNames();
		this.loadAll();

		new BukkitRunnable() {
			@Override
			public void run() {
				saveNames();
				saveUUID();
			}
		}.runTaskTimerAsynchronously(this.m, 7200L, 7200L);
	}
	public UUID getUUID(String name) {
		return uuids.get(name.toLowerCase());
	}
	public String getName(UUID uuid) {
		return names.get(uuid.toString());
	}
	public void loadAll() {
		new BukkitRunnable() {
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					uuids.put(p.getName(), p.getUniqueId());
				}
			}
		}.runTaskAsynchronously(this.m);
	}
	public void registerData(Player p) {
		UUID uuid = p.getUniqueId();
		String name = p.getName();
		this.uuids.put(name.toLowerCase(), uuid);
		this.names.put(uuid.toString(), name);
	}
	public void loadUUID() {
		File f = new File(this.m.getDataFolder(), "uuid.yml");
		if (!f.exists()) return;
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
		ConfigurationSection uuidz = yml.getConfigurationSection("uuid");
		for (String name : uuidz.getKeys(false)) {
			UUID uuid = UUID.fromString(uuidz.getString(name));
			this.uuids.put(name.toLowerCase(), uuid);
		}
	}
	public void loadNames() {
		File f2 = new File(this.m.getDataFolder(), "names.yml");
		if (!f2.exists()) return;
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(f2);
		ConfigurationSection names = yml.getConfigurationSection("names");
		for (String uuid : names.getKeys(false)) {
			UUID uu = UUID.fromString(uuid);
			String name = names.getString(uuid);
			this.names.put(uu.toString(), name);
		}
	}
	public void saveNames() {
		File f = new File(this.m.getDataFolder(), "names.yml");
		YamlConfiguration yml = new YamlConfiguration();
		ConfigurationSection uuids = yml.createSection("names");
		for (String uuid : this.names.keySet()) {
			String name = this.names.get(uuid);
			uuids.set(uuid, name);
		}
		try {
			yml.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void saveUUID() {
		File f = new File(this.m.getDataFolder(), "uuid.yml");
		YamlConfiguration yml = new YamlConfiguration();
		ConfigurationSection uuids = yml.createSection("uuid");
		for (String str : this.uuids.keySet()) {
			UUID uu = this.uuids.get(str);
			String uuid = uu.toString();
			uuids.set(str, uuid);
		}
		try {
			yml.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
