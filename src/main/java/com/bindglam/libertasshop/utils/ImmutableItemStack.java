package com.bindglam.libertasshop.utils;

import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public final class ImmutableItemStack {
    private final Supplier<ItemStack> itemStack;

    private ImmutableItemStack(Supplier<ItemStack> itemStack) {
        this.itemStack = itemStack;
    }

    private ImmutableItemStack(ItemStack itemStack) {
        this.itemStack = () -> itemStack;
    }

    public ItemStack get() {
        return itemStack.get();
    }

    public static ImmutableItemStack of(Supplier<ItemStack> itemStack) {
        return new ImmutableItemStack(itemStack);
    }

    public static ImmutableItemStack of(ItemStack itemStack) {
        return new ImmutableItemStack(itemStack);
    }
}
