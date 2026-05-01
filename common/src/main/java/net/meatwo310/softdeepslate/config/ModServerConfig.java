package net.meatwo310.softdeepslate.config;

import java.util.List;

public interface ModServerConfig {
    double miningSpeed();
    List<? extends String> blocks();
}
