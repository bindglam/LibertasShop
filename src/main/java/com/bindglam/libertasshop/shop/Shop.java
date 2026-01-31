package com.bindglam.libertasshop.shop;

import com.bindglam.libertasshop.shop.item.ShopItem;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

public interface Shop {
    String id();

    String displayName();

    // Compatibility for Citizens
    @Nullable Integer npcId();

    @Unmodifiable
    List<ShopItem> items();

    void registerItem(ShopItem item);
}
