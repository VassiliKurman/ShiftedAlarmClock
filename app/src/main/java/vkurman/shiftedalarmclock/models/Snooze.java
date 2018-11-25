/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package vkurman.shiftedalarmclock.models;

import android.arch.persistence.room.ColumnInfo;

/**
 * Snooze
 * Created by Vassili Kurman on 24/11/2018.
 * Version 1.0
 */
public class Snooze {
    /**
     * Intervals between repeats.
     */
    public static final int[] INTERVALS = new int[]{5, 10, 15, 30};
    /**
     * Alarm repeats before stop.
     */
    public static final int[] REPEATS = new int[]{3, 5, Integer.MAX_VALUE};

    @ColumnInfo(name = "enabled")
    private boolean snoozeEnabled;

    @ColumnInfo(name = "interval")
    private int snoozeInterval;

    @ColumnInfo(name = "snooze_repeat")
    private int snoozeRepeat;

    public Snooze() {
        this.snoozeEnabled = false;
        this.snoozeInterval = INTERVALS[0];
        this.snoozeRepeat = REPEATS[0];
    }

    public boolean isSnoozeEnabled() {
        return snoozeEnabled;
    }

    public void setSnoozeEnabled(boolean snoozeEnabled) {
        this.snoozeEnabled = snoozeEnabled;
    }

    public int getSnoozeInterval() {
        return snoozeInterval;
    }

    public void setSnoozeInterval(int snoozeInterval) {
        if (snoozeInterval <= 0)
            throw new IllegalArgumentException("Interval should be more than 0");
        this.snoozeInterval = snoozeInterval;
    }

    public int getSnoozeRepeat() {
        return snoozeRepeat;
    }

    public void setSnoozeRepeat(int snoozeRepeat) {
        if (snoozeRepeat <= 0)
            throw new IllegalArgumentException("Repeat should be more than 0");
        this.snoozeRepeat = snoozeRepeat;
    }
}