package com.road.rampage.core;

public class DebugObserver implements Observer {
    @Override
    public void onNotify(EventType event, Object data) {
        GameLogger.getInstance().info("[DebugObserver] Event=" + event + " data=" + (data != null ? data.toString() : "<null>"));
    }
}

