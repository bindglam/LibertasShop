package com.bindglam.libertasshop.shop;

import com.bindglam.libertasshop.shop.item.ShopItem;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.LinkedList;
import java.util.List;

public class ShopImpl implements Shop {
    private final String id;
    private final String displayName;
    private final Integer npcId;

    private final List<ShopItem> items = new LinkedList<>();

    public ShopImpl(String id, String displayName, Integer npcId) {
        this.id = id;
        this.displayName = displayName;
        this.npcId = npcId;
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public String displayName() {
        return this.displayName;
    }

    @Override
    public @Nullable Integer npcId() {
        return this.npcId;
    }

    @Override
    public @Unmodifiable List<ShopItem> items() {
        return List.copyOf(items);
    }

    @Override
    public void registerItem(ShopItem item) {
        items.add(item);
    }
}
