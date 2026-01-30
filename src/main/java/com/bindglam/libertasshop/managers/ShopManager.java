package com.bindglam.libertasshop.managers;

import com.bindglam.libertasshop.registry.ShopLoader;
import com.bindglam.libertasshop.registry.ShopRegistry;

public final class ShopManager implements Managerial, Reloadable {
    private final ShopRegistry registry = new ShopRegistry();
    private final ShopLoader loader = new ShopLoader(registry);

    @Override
    public void start() {
        this.loader.loadAll();
    }

    @Override
    public void end() {
        this.registry.clear();
    }

    @Override
    public void reload() {
        this.end();
        this.start();
    }

    public ShopRegistry registry() {
        return registry;
    }
}
