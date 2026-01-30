package com.bindglam.libertasshop.registry;

import com.bindglam.libertasshop.shop.Shop;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class ShopRegistry {
    private final Map<String, Shop> registry = new HashMap<>();

    public ShopRegistry() {
    }

    public @Nullable Shop getShop(String id) {
        return this.registry.get(id);
    }

    public void registerShop(String id, Shop shop) {
        if(this.registry.containsKey(id))
            throw new IllegalStateException("Already registered");
        this.registry.put(id, shop);
    }

    public void clear() {
        this.registry.clear();
    }
}
