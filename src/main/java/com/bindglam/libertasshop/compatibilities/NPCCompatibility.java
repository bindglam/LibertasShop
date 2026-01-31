package com.bindglam.libertasshop.compatibilities;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

public interface NPCCompatibility extends Compatibility {
    @Nullable Integer getNpcId(Entity entity);
}
