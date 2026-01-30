package com.bindglam.libertasshop.compatibilities;

import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.inventory.ItemStack;
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

    public @Nullable ItemStack getItem(String id) {
        CustomStack stack = CustomStack.getInstance(id);
        if(stack == null) return null;
        return stack.getItemStack();
    }
}
