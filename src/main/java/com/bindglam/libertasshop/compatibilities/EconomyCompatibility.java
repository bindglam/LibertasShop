package com.bindglam.libertasshop.compatibilities;

import java.math.BigDecimal;
import java.util.UUID;

public interface EconomyCompatibility extends Compatibility {
    BigDecimal getBalance(UUID uuid);

    void setBalance(UUID uuid, BigDecimal balance);
}
