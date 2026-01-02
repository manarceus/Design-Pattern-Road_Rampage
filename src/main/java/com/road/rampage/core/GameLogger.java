package com.road.rampage.core;

import org.apache.logging.log4j.LogManager;

public class GameLogger {

    private static final GameLogger INSTANCE = new GameLogger();
    private final org.apache.logging.log4j.Logger log = LogManager.getLogger("RoadRampage");

    private GameLogger() {}

    public static GameLogger getInstance() {
        return INSTANCE;
    }

    public void info(String msg) {
        log.info(msg);
    }

    public void stateChange(String who, String from, String to) {
        log.info("STATE {} {} - {}", who, from, to);
    }

    public void decorator(String msg) {
        log.info("DECORATOR {}", msg);
    }
}
