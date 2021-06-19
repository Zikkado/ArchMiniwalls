package me.arch.nms;

import java.lang.reflect.Constructor;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class ActionBarAPI {
	
	public static void sendActionBar(Player player, String message) {

		message = ChatColor.translateAlternateColorCodes('&', message);

		Class<?> chatSerializer = NMS.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0];
		Class<?> chatComponent = NMS.getNMSClass("IChatBaseComponent");
		Class<?> packetActionbar = NMS.getNMSClass("PacketPlayOutChat");

		try {

			Constructor<?> ConstructorActionbar = packetActionbar.getDeclaredConstructor(chatComponent, byte.class);
			Object actionbar = chatSerializer.getMethod("a", String.class).invoke(chatSerializer, "{\"text\": \"" + message + "\"}");
			Object packet = ConstructorActionbar.newInstance(actionbar, (byte) 2);
			NMS.sendPacket(player, packet);

		} catch (Exception ex) {

			ex.printStackTrace();
		}
	}
}
