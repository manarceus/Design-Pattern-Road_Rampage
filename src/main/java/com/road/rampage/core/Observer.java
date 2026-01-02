package com.road.rampage.core;

import com.road.rampage.core.EventType;

public interface Observer {
    void onNotify(EventType event, Object data);
}
