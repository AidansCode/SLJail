package com.aidanmurphey.spacelampjail;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class JailListeners implements Listener {

	private Jail jail;

	public JailListeners() {
		this.jail = Jail.getInstance();
	}

	private boolean isEntityPlayer(Entity e) {
		return e.getType() == EntityType.PLAYER;
	}

	@EventHandler
	public void onPickupItem(EntityPickupItemEvent e) {
		if (!isEntityPlayer(e.getEntity())) return;
		Player p = (Player) e.getEntity();

		if (jail.isPlayerJailed(p.getUniqueId())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onDropItem(EntityDropItemEvent e) {
		if (!isEntityPlayer(e.getEntity())) return;
		Player p = (Player) e.getEntity();

		if (jail.isPlayerJailed(p.getUniqueId())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();

		if (jail.isPlayerJailed(p.getUniqueId())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();

		if (jail.isPlayerJailed(p.getUniqueId())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if (!isEntityPlayer(e.getEntity())) return;
		Player p = (Player) e.getEntity();

		if (jail.isPlayerJailed(p.getUniqueId())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if (!isEntityPlayer(e.getDamager())) return;
		Player p = (Player) e.getDamager();

		if (jail.isPlayerJailed(p.getUniqueId())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (jail.isPlayerJailed(p.getUniqueId())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		if (jail.isPlayerJailed(p.getUniqueId())) {
			jail.checkPlayerShouldBeJailed(p.getUniqueId());
		}
	}

}
