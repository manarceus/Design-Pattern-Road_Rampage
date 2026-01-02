package com.road.rampage.world;

import com.road.rampage.core.GameLogger;

public enum LaneIndex {
    LEFT(0), MIDDLE(1), RIGHT(2);

    public final int idx;
    LaneIndex(int idx) { this.idx = idx; }

    public LaneIndex left()  {
        LaneIndex res = idx == 0 ? this : values()[idx - 1];
        GameLogger.getInstance().info("LaneIndex.left: from=" + this + " to=" + res + " (idx=" + idx + ")");
        return res;
    }
    public LaneIndex right() {
        LaneIndex res = idx == 2 ? this : values()[idx + 1];
        GameLogger.getInstance().info("LaneIndex.right: from=" + this + " to=" + res + " (idx=" + idx + ")");
        return res;
    }
}
