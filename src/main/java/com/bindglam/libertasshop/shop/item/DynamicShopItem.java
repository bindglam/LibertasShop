package com.bindglam.libertasshop.shop.item;

import com.bindglam.libertasshop.utils.ImmutableItemStack;

public final class DynamicShopItem implements ShopItem {
    private final ImmutableItemStack stack;
    private Value value;

    public DynamicShopItem(ImmutableItemStack stack, Value value) {
        this.stack = stack;
        this.value = value;
    }

    @Override
    public ImmutableItemStack stack() {
        return this.stack;
    }

    @Override
    public Value value() {
        return this.value;
    }

    public void value(Value value) {
        this.value = value;
    }
}
