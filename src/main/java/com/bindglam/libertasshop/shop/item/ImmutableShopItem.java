package com.bindglam.libertasshop.shop.item;

import com.bindglam.libertasshop.utils.ImmutableItemStack;

public record ImmutableShopItem(
        ImmutableItemStack stack,
        Value value
) implements ShopItem {
}
