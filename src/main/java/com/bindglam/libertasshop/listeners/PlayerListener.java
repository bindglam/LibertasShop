package com.bindglam.libertasshop.listeners;

import com.bindglam.libertasshop.LibertasShopPlugin;
import com.bindglam.libertasshop.compatibilities.NPCCompatibility;
import com.bindglam.libertasshop.gui.ShopGui;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.Objects;

public final class PlayerListener implements Listener {
    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        NPCCompatibility npcCompat = (NPCCompatibility) LibertasShopPlugin.getInstance().getCompatibilityManager().getCompatibility(compat -> compat instanceof NPCCompatibility)
                .orElse(null);
        if(npcCompat == null) return;
        Integer npcId = npcCompat.getNpcId(entity);
        if(npcId == null) return;

        LibertasShopPlugin.getInstance().getShopManager().registry().entries().forEach(shop -> {
            if(Objects.equals(shop.npcId(), npcId)) {
                player.openInventory(new ShopGui(shop).getInventory());
            }
        });
    }
}
