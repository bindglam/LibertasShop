package com.bindglam.libertasshop.compatibilities;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ItemCompatibility extends Compatibility {
    @Nullable ItemStack getCustomItem(String id);

    @Nullable String getCustomItemId(@NotNull ItemStack itemStack);

    boolean isCustomItem(@NotNull ItemStack itemStack);
}
