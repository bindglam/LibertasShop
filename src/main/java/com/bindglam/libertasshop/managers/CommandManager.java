package com.bindglam.libertasshop.managers;

import com.bindglam.libertasshop.LibertasShopPlugin;
import com.bindglam.libertasshop.gui.ShopGui;
import com.bindglam.libertasshop.shop.Shop;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.PlayerArgument;
import dev.jorel.commandapi.arguments.TextArgument;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

import java.util.Objects;

public final class CommandManager implements Managerial {
    @Override
    public void start() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(LibertasShopPlugin.getInstance()));

        new CommandAPICommand("shop")
                .withPermission(CommandPermission.OP)
                .withSubcommands(
                        new CommandAPICommand("reload")
                                .executes((sender, args) -> {
                                    sender.sendMessage(Component.text("Reloading...").color(NamedTextColor.YELLOW));

                                    LibertasShopPlugin.getInstance().reload();

                                    sender.sendMessage(Component.text("Reloaded!").color(NamedTextColor.GREEN));
                                }),
                        new CommandAPICommand("open")
                                .withArguments(new PlayerArgument("player"), new TextArgument("shop"))
                                .executes((sender, args) -> {
                                    Player player = (Player) Objects.requireNonNull(args.get("player"));
                                    Shop shop = LibertasShopPlugin.getInstance().getShopManager().registry().getShop((String) Objects.requireNonNull(args.get("shop")));

                                    player.openInventory(new ShopGui(player, shop).getInventory());
                                })
                )
                .register();

        CommandAPI.onEnable();
    }

    @Override
    public void end() {
        CommandAPI.onDisable();
    }
}
