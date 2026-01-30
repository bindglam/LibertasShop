package com.bindglam.libertasshop.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

public final class ItemBuilder {
    private final ItemStack itemStack;

    private ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    private ItemBuilder(Material material) {
        this(new ItemStack(material));
    }

    public ItemBuilder displayName(Component name) {
        this.itemStack.editMeta((meta) -> meta.displayName(name));
        return this;
    }

    public ItemBuilder lore(List<Component> lore) {
        this.itemStack.editMeta((meta) -> meta.lore(lore));
        return this;
    }

    public ItemBuilder lore(Component... lore) {
        return this.lore(Arrays.stream(lore).toList());
    }

    public <T, Z> ItemBuilder pdc(NamespacedKey key, PersistentDataType<T, Z> type, Z value) {
        this.itemStack.editMeta((meta) -> meta.getPersistentDataContainer().set(key, type, value));
        return this;
    }

    public ItemStack build() {
        return this.itemStack;
    }

    public static ItemBuilder of(ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }

    public static ItemBuilder of(Material material) {
        return new ItemBuilder(material);
    }
}
