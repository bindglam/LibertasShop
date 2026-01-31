package com.bindglam.libertasshop.shop.item;

import com.bindglam.libertasshop.utils.ImmutableItemStack;

public interface ShopItem {
    ImmutableItemStack stack();

    Value value();
}
