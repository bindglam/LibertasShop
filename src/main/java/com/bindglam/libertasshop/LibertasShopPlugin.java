package com.bindglam.libertasshop;

import com.bindglam.libertasshop.managers.CommandManager;
import com.bindglam.libertasshop.managers.CompatibilityManager;
import com.bindglam.libertasshop.managers.ShopManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class LibertasShopPlugin extends JavaPlugin {
    private static LibertasShopPlugin instance;

    private final CommandManager commandManager = new CommandManager();
    private final CompatibilityManager compatibilityManager = new CompatibilityManager();
    private final ShopManager shopManager = new ShopManager();

    @Override
    public void onEnable() {
        instance = this;

        this.commandManager.start();
        this.compatibilityManager.start();
        this.shopManager.start();
    }

    @Override
    public void onDisable() {
        this.commandManager.end();
        this.compatibilityManager.end();
        this.shopManager.end();
    }

    public void reload() {
        this.shopManager.reload();
    }

    public CompatibilityManager getCompatibilityManager() {
        return this.compatibilityManager;
    }

    public ShopManager getShopManager() {
        return shopManager;
    }

    public static LibertasShopPlugin getInstance() {
        return instance;
    }
}
