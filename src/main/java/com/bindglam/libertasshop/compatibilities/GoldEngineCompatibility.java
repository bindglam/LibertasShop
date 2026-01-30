package com.bindglam.libertasshop.compatibilities;

import com.bindglam.goldengine.GoldEngine;
import com.bindglam.goldengine.account.Account;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.Objects;

public final class GoldEngineCompatibility implements Compatibility {
    @Override
    public void start() {
    }

    @Override
    public void end() {
    }

    @Override
    public String requiredPlugin() {
        return "GoldEngine";
    }

    public BigDecimal getBalance(Player player) {
        Account account = Objects.requireNonNull(GoldEngine.instance().accountManager().getOnlineAccount(player.getUniqueId()));
        return account.balance();
    }

    public void setBalance(Player player, BigDecimal balance) {
        Account account = Objects.requireNonNull(GoldEngine.instance().accountManager().getOnlineAccount(player.getUniqueId()));
        account.balance(balance);
    }
}
