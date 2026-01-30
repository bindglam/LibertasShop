package com.bindglam.libertasshop.compatibilities;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface ItemCompatibility extends Compatibility {
    @Nullable ItemStack getItem(String id);
}
