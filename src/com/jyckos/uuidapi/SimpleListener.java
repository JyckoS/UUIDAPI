package com.jyckos.uuidapi;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SimpleListener implements Listener {
	private UUIDAPI m;
	public SimpleListener(UUIDAPI m) {
		this.m = m;
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p =e.getPlayer();
		this.m.getUUIDStorage().registerData(p);
	}
}
