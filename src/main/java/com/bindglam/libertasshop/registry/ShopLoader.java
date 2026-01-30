package com.bindglam.libertasshop.registry;

import com.bindglam.libertasshop.LibertasShopPlugin;
import com.bindglam.libertasshop.compatibilities.ItemsAdderCompatibility;
import com.bindglam.libertasshop.shop.ShopImpl;
import com.bindglam.libertasshop.shop.item.ImmutableShopItem;
import com.bindglam.libertasshop.utils.ImmutableItemStack;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Objects;

public final class ShopLoader {
    private static final File SHOPS_FOLDER = new File("plugins/LibertasShop/shops");

    private final ShopRegistry registry;

    public ShopLoader(ShopRegistry registry) {
        this.registry = registry;
    }

    public void loadAll() {
        if(!SHOPS_FOLDER.exists())
            SHOPS_FOLDER.mkdirs();

        for(File configFile : SHOPS_FOLDER.listFiles()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);

            for (String id : config.getKeys(false)) {
                load(Objects.requireNonNull(config.getConfigurationSection(id)));
            }
        }
    }

    public void load(ConfigurationSection config) {
        String id = config.getName();

        ShopImpl shop = new ShopImpl(id, config.getString("display-name"));

        ConfigurationSection itemsConfig = Objects.requireNonNull(config.getConfigurationSection("items"));
        for(int i = 0; i < 1000; i++) {
            if(!itemsConfig.contains(String.valueOf(i))) break;
            ConfigurationSection itemConfig = Objects.requireNonNull(itemsConfig.getConfigurationSection(String.valueOf(i)));

            String stackId = Objects.requireNonNull(itemConfig.getString("stack"));
            boolean itemsadder = false;
            if(stackId.startsWith("(itemsadder)")) {
                stackId = stackId.substring("(itemsadder)".length());
                itemsadder = true;
            }
            ItemStack stack;
            if(itemsadder)
                stack = LibertasShopPlugin.getInstance().getCompatibilityManager().getCompatibility(ItemsAdderCompatibility.class)
                        .orElseThrow().getItem(stackId);
            else
                stack = new ItemStack(Objects.requireNonNull(Registry.MATERIAL.get(Objects.requireNonNull(NamespacedKey.fromString(stackId)))));

            double price = itemConfig.getDouble("price");

            shop.registerItem(new ImmutableShopItem(ImmutableItemStack.of(stack), price)); // TODO : 시세 변동
        }

        registry.registerShop(id, shop);
    }
}
