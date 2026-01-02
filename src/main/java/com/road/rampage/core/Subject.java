package com.road.rampage.core;

import com.road.rampage.core.EventType;

public interface Subject {
    void addObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers(EventType event, Object data);
}
