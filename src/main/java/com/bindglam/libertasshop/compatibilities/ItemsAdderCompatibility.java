package com.bindglam.libertasshop.compatibilities;

import dev.lone.itemsadder.api.CustomStack;
import net.kyori.adventure.key.Key;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public final class ItemsAdderCompatibility implements Compatibility {
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

    public @Nullable ItemStack getItem(Key key) {
        return CustomStack.getInstance(key.toString()).getItemStack();
    }
}
