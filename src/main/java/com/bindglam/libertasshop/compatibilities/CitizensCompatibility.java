package com.bindglam.libertasshop.compatibilities;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class CitizensCompatibility implements NPCCompatibility {
    @Override
    public void start() {
    }

    @Override
    public void end() {
    }

    @Override
    public String requiredPlugin() {
        return "Citizens";
    }

    @Override
    public @Nullable Integer getNpcId(Entity entity) {
        NPC npc = CitizensAPI.getNPCRegistry().getNPC(entity);
        if(npc == null) return null;
        return npc.getId();
    }
}
