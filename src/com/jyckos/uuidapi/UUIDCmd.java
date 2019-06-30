package com.jyckos.uuidapi;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UUIDCmd implements CommandExecutor {
	private UUIDAPI m;
	public UUIDCmd(UUIDAPI m) {
		this.m = m;
	}
	@Override
	public boolean onCommand(CommandSender snd, Command cmd, String lbl, String[] args) {
		if (!snd.hasPermission("uuidapi.admin")) {
			return true;
		}
		redo(snd, args);
		return true;
	}
	private void redo(CommandSender snd, String[] args) {
		if (args.length == 0) {
			sendMsg(snd, "&b&lUUIDAPI");
			sendMsg(snd, "&7/uuidapi &fgetName <UUID>");
			sendMsg(snd, "&7/uuidapi &fgetUUID <Name>");
			return;
		}
		switch (args[0].toLowerCase()) {
		default:
		{
			sendMsg(snd, "&b&lUUIDAPI");
			sendMsg(snd, "&7/uuidapi &fgetName <UUID>");
			sendMsg(snd, "&7/uuidapi &fgetUUID <Name>");
			return;
		}
		case "getname":
		{
			if (args.length < 2) {
				sendMsg(snd, "&7Please input the uuid.");
				return;
			}
			String uuid = args[1];
			UUID name = null;
			try {
				name = UUID.fromString(uuid);
			} catch (IllegalArgumentException e) {
				sendMsg(snd, "&cUUID form is invalid.");
				return;
			}
			String nm = UUIDAPI.getName(name);
			if (nm == null) {
				sendMsg(snd, "&cName not found.");
				return;
			}
			sendMsg(snd, "Name from &7&o" + args[1] + " &ris &b" + nm);
			return;
		}
		case "getuuid":
		{
			if (args.length < 2) {
				sendMsg(snd, "&7Please input the name.");
				return;
			}
			String name = args[1];
			UUID uu = UUIDAPI.getUUID(name);
			if (uu == null) {
				sendMsg(snd, "&cUUID not found.");
				return;
			}		
			sendMsg(snd, args[1] + "'s UUID: &7&o" +uu.toString());
			return;
		}
		}
	}
	private void sendMsg(CommandSender snd, String msg) {
		snd.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}
}
