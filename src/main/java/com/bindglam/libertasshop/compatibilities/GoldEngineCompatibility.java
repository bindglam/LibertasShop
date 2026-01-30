package com.bindglam.libertasshop.compatibilities;

import com.bindglam.goldengine.GoldEngine;
import com.bindglam.goldengine.account.Account;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public final class GoldEngineCompatibility implements EconomyCompatibility {
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

    @Override
    public BigDecimal getBalance(UUID uuid) {
        Account account;
        try {
            account = Objects.requireNonNull(GoldEngine.instance().accountManager().getAccount(uuid).get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return account.balance();
    }

    @Override
    public void setBalance(UUID uuid, BigDecimal balance) {
        Account account;
        try {
            account = Objects.requireNonNull(GoldEngine.instance().accountManager().getAccount(uuid).get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        account.balance(balance);
    }
}
