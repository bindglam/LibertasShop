package com.bindglam.libertasshop.compatibilities;

import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ItemsAdderCompatibility implements ItemCompatibility {
    @Override
    public void start() {
    }

    @Override
    public void end() {
    }

    @Override
    public String requiredPlugin() {
        return "ItemsAdder";
    }

    public @Nullable ItemStack getCustomItem(String id) {
        CustomStack stack = CustomStack.getInstance(id);
        if(stack == null) return null;
        return stack.getItemStack();
    }

    @Override
    public @Nullable String getCustomItemId(@NotNull ItemStack itemStack) {
        CustomStack stack = CustomStack.byItemStack(itemStack);
        if(stack == null) return null;
        return stack.getNamespacedID();
    }

    @Override
    public boolean isCustomItem(@NotNull ItemStack itemStack) {
        return CustomStack.byItemStack(itemStack) != null;
    }
}
