package com.bindglam.libertasshop.utils;

import org.bukkit.inventory.ItemStack;

public final class ImmutableItemStack {
    private final ItemStack itemStack;

    private ImmutableItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack get() {
        return itemStack.clone();
    }

    public static ImmutableItemStack of(ItemStack itemStack) {
        return new ImmutableItemStack(itemStack);
    }
}
