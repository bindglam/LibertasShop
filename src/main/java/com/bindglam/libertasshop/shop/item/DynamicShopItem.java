package com.bindglam.libertasshop.shop.item;

import com.bindglam.libertasshop.utils.ImmutableItemStack;

public final class DynamicShopItem implements ShopItem {
    private final ImmutableItemStack stack;
    private double price;

    public DynamicShopItem(ImmutableItemStack stack, double price) {
        this.stack = stack;
        this.price = price;
    }

    @Override
    public ImmutableItemStack stack() {
        return this.stack;
    }

    @Override
    public double price() {
        return this.price;
    }

    public void price(double price) {
        this.price = price;
    }
}
