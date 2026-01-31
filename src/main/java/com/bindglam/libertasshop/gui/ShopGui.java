package com.bindglam.libertasshop.gui;

import com.bindglam.libertasshop.LibertasShopPlugin;
import com.bindglam.libertasshop.compatibilities.EconomyCompatibility;
import com.bindglam.libertasshop.compatibilities.ItemCompatibility;
import com.bindglam.libertasshop.shop.Shop;
import com.bindglam.libertasshop.shop.item.ShopItem;
import com.bindglam.libertasshop.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ShopGui implements InventoryHolder, Listener {
    private static final int PER_PAGE_MAX_ITEMS = 9*5; // 페이지당 표시 가능 아이템 수
    private static final int PREVIOUS_PAGE_BTN = 9*5+0;
    private static final int NEXT_PAGE_BTN = 9*5+8;
    private static final NamespacedKey ITEM_INDEX_KEY = new NamespacedKey(LibertasShopPlugin.getInstance(), "item_index");
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,###");

    private final EconomyCompatibility economyCompat = (EconomyCompatibility) LibertasShopPlugin.getInstance().getCompatibilityManager().getCompatibility(compat -> compat instanceof EconomyCompatibility)
            .orElseThrow(() -> new IllegalStateException("There is no currency plugin"));
    private final ItemCompatibility itemCompat = (ItemCompatibility) LibertasShopPlugin.getInstance().getCompatibilityManager().getCompatibility(compat -> compat instanceof ItemCompatibility)
            .orElse(null);

    private final Shop shop;
    private final Inventory inventory;
    private final int updateTask;

    private int page = 0;

    public ShopGui(Shop shop) {
        this.shop = shop;

        this.inventory = Bukkit.createInventory(this, 9*6, Component.text(this.shop.displayName()));

        this.updateTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(LibertasShopPlugin.getInstance(),
                this::update, 1L, 20L); // 초당 업데이트(for 실시간 시세 변동)

        Bukkit.getPluginManager().registerEvents(this, LibertasShopPlugin.getInstance());
    }

    private void update() {
        this.inventory.clear();

        for(int i = this.page * PER_PAGE_MAX_ITEMS; i < (this.page + 1) * PER_PAGE_MAX_ITEMS; i++) {
            if(i >= this.shop.items().size()) break;

            ShopItem item = this.shop.items().get(i);
            ItemStack stack = item.stack().get();

            List<Component> lore = stack.lore() == null ? new ArrayList<>() : new ArrayList<>(Objects.requireNonNull(stack.lore()));
            lore.add(Component.empty());
            if(Double.compare(item.value().buyPrice(), 0.0) > 0)
                lore.add(Component.text("구매 가격 : " + DECIMAL_FORMAT.format(item.value().buyPrice()) + "원").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            if(Double.compare(item.value().sellPrice(), 0.0) > 0)
                lore.add(Component.text("판매 가격 : " + DECIMAL_FORMAT.format(item.value().sellPrice()) + "원").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
            lore.add(Component.empty());

            this.inventory.setItem(i - (this.page * PER_PAGE_MAX_ITEMS),
                    ItemBuilder.of(stack)
                            .lore(lore)
                            .pdc(ITEM_INDEX_KEY, PersistentDataType.INTEGER, i)
                            .build());
        }

        // 메모리 누수 방지
        ItemStack footer = ItemBuilder.of(Material.GRAY_STAINED_GLASS_PANE)
                .displayName(Component.empty())
                .lore(Component.text("현재 페이지 : " + (this.page+1)).color(NamedTextColor.GRAY))
                .build();
        for(int i = 0; i < 9; i++) {
            this.inventory.setItem(9 * 5 + i, footer);
        }

        this.inventory.setItem(PREVIOUS_PAGE_BTN, ItemBuilder.of(Material.ARROW)
                .displayName(Component.text("이전").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false))
                .lore(Component.text("현재 페이지 : " + (this.page+1)).color(NamedTextColor.GRAY))
                .build());
        this.inventory.setItem(NEXT_PAGE_BTN, ItemBuilder.of(Material.ARROW)
                .displayName(Component.text("다음").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false))
                .lore(Component.text("현재 페이지 : " + (this.page+1)).color(NamedTextColor.GRAY))
                .build());
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(!this.isMe(event.getInventory())) return;
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        event.setCancelled(true);

        if(event.getRawSlot() == PREVIOUS_PAGE_BTN) {
            this.page = Math.max(0, this.page - 1);

            player.playSound(player, Sound.UI_BUTTON_CLICK, 1f, 1.5f);

            this.update();
        } else if(event.getRawSlot() == NEXT_PAGE_BTN) {
            this.page++;

            player.playSound(player, Sound.UI_BUTTON_CLICK, 1f, 1.5f);

            this.update();
        } else if(clickedItem != null && clickedItem.getItemMeta().getPersistentDataContainer().has(ITEM_INDEX_KEY)) {
            ShopItem item = this.shop.items().get(Objects.requireNonNull(clickedItem.getItemMeta().getPersistentDataContainer().get(ITEM_INDEX_KEY, PersistentDataType.INTEGER)));

            ItemStack stack = item.stack().get();

            BigDecimal balance = economyCompat.getBalance(player.getUniqueId());

            if(event.isLeftClick()) {
                if(Double.compare(item.value().buyPrice(), 0.0) <= 0)
                    return;

                BigDecimal price;
                if(event.isLeftClick())
                    price = BigDecimal.valueOf(item.value().buyPrice());
                else
                    price = BigDecimal.valueOf(item.value().sellPrice());

                if(event.isShiftClick()) {
                    price = price.multiply(BigDecimal.valueOf(64));
                    stack.setAmount(64);
                }

                if(player.getInventory().firstEmpty() == -1) {
                    player.sendMessage(Component.text("인벤토리가 꽉 찼습니다!").color(NamedTextColor.RED));
                    player.playSound(player, Sound.ENTITY_VILLAGER_NO, 0.5f, 2f);
                    return;
                }

                if (balance.compareTo(price) < 0) {
                    player.sendMessage(Component.text("돈이 부족합니다!").color(NamedTextColor.RED));
                    player.playSound(player, Sound.BLOCK_ANVIL_PLACE, 0.5f, 2f);
                    return;
                }

                economyCompat.setBalance(player.getUniqueId(), balance.subtract(price));

                player.getInventory().addItem(stack);
                player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1f, 1.5f);
            } else if(event.isRightClick()) {
                if(Double.compare(item.value().sellPrice(), 0.0) <= 0)
                    return;

                String shopItemCustomItemId = null;
                if(itemCompat != null)
                    shopItemCustomItemId = itemCompat.getCustomItemId(stack);

                int soldCnt = 1;
                if(event.isShiftClick())
                    soldCnt = 64;

                for(int i = 0; i < soldCnt; i++) {
                    for (ItemStack itemStack : player.getInventory()) {
                        if (itemStack == null) continue;

                        String customItemId = null;
                        if (itemCompat != null)
                            customItemId = itemCompat.getCustomItemId(itemStack);

                        if (shopItemCustomItemId == null && customItemId == null) {
                            if (itemStack.getType() == stack.getType()) {
                                itemStack.setAmount(itemStack.getAmount() - 1);
                                economyCompat.setBalance(player.getUniqueId(), economyCompat.getBalance(player.getUniqueId())
                                        .add(BigDecimal.valueOf(item.value().sellPrice())));
                                break;
                            }
                        }
                    }
                }

                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.2f);
            }
        }
    }

    @SuppressWarnings("all")
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if(!this.isMe(event.getInventory())) return;

        // 최적화 (메모리 누수 가능성 제거)
        Bukkit.getScheduler().cancelTask(this.updateTask);
        HandlerList.unregisterAll(this);

        // IMPORTANT : 아래 updateInventory 호출이 없으면, Gui의 아이템을 쌔빌 수 있는 버그가 있음!
        Bukkit.getScheduler().scheduleSyncDelayedTask(LibertasShopPlugin.getInstance(), player::updateInventory, 1L);
    }

    // 구식의 인벤토리 이름 비교는 ㅗ
    private boolean isMe(Inventory other) {
        return other.getHolder(false) == this;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }
}
