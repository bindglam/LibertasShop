package com.bindglam.libertasshop.managers;

import com.bindglam.libertasshop.compatibilities.Compatibility;
import com.bindglam.libertasshop.compatibilities.GoldEngineCompatibility;
import com.bindglam.libertasshop.compatibilities.ItemsAdderCompatibility;
import org.bukkit.Bukkit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public final class CompatibilityManager implements Managerial {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompatibilityManager.class);

    private final List<Compatibility> compatibilities = List.of(new ItemsAdderCompatibility(), new GoldEngineCompatibility());

    private final List<Compatibility> enabledCompatibilities = new ArrayList<>();

    @Override
    public void start() {
        compatibilities.stream().filter(compat -> Bukkit.getPluginManager().isPluginEnabled(compat.requiredPlugin()))
                .forEach(enabledCompatibilities::add);

        enabledCompatibilities.forEach(compat -> {
            LOGGER.info("{} hooked!", compat.requiredPlugin());

            compat.start();
        });
    }

    @Override
    public void end() {
        enabledCompatibilities.forEach(Compatibility::end);
        enabledCompatibilities.clear();
    }

    public Optional<Compatibility> getCompatibility(Predicate<Compatibility> predicate) {
        for(Compatibility compat : enabledCompatibilities) {
            if(predicate.test(compat)) {
                return Optional.of(compat);
            }
        }
        return Optional.empty();
    }

    public <T extends Compatibility> Optional<T> getCompatibility(Class<T> clazz) {
        for(Compatibility compat : enabledCompatibilities) {
            if(clazz.isInstance(compat)) {
                return Optional.of(clazz.cast(compat));
            }
        }
        return Optional.empty();
    }
}
