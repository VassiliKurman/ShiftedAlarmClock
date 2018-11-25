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

import android.arch.persistence.room.Ignore;
import android.content.Context;
import android.os.Vibrator;

/**
 * Vibration
 * Created by Vassili Kurman on 24/11/2018.
 * Version 1.0
 */
public class Vibration {

    /**
     * Pattern description:
     * 0 - starting without delay
     * vibrate for 100 milliseconds
     * sleep for 1000 milliseconds
     */
    public static final long[] PATTERN_SIMPLE = new long[]{0, 100, 1000};
    public static final long[] PATTERN_SIMPLE2 = new long[]{0, 100, 1000, 300, 200, 100, 500, 200, 100};

    public static final int REPEAT_ONCE = -1;
    public static final int REPEAT_INDEFINITELY = 0;

    private boolean vibrationEnabled;
    private String vibrationName;
    private long[] vibrationPattern;

    @Ignore
    public Vibration() {
        this("Default", PATTERN_SIMPLE, false);
    }

    public Vibration(String vibrationName, long[] vibrationPattern, boolean vibrationEnabled) {
        this.vibrationName = vibrationName;
        this.vibrationPattern = vibrationPattern;
        this.vibrationEnabled = vibrationEnabled;
    }

    public String getVibrationName() {
        return vibrationName;
    }

    public void setVibrationName(String vibrationName) {
        this.vibrationName = vibrationName;
    }

    public boolean isVibrationEnabled() {
        return vibrationEnabled;
    }

    public void setVibrationEnabled(boolean vibrationEnabled) {
        this.vibrationEnabled = vibrationEnabled;
    }

    public long[] getVibrationPattern() {
        return vibrationPattern;
    }

    public void setVibrationPattern(long[] vibrationPattern) {
        this.vibrationPattern = vibrationPattern;
    }

    public void start(Context context, int repeat) {
        // Getting instance of Vibrator from current context
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        // Checking if vibrator exists on device and pattern is set
        if(vibrator == null || !vibrator.hasVibrator() || vibrationPattern == null) {
            return;
        }

        switch(repeat) {
            case REPEAT_ONCE:
                vibrator.vibrate(vibrationPattern, REPEAT_ONCE);
                stop(context);
                break;
            case REPEAT_INDEFINITELY:
                vibrator.vibrate(vibrationPattern, REPEAT_INDEFINITELY);
                break;
            default:
                throw new IllegalArgumentException("Invalid 'repeat' parameter!");
        }
    }

    /**
     * Stops Vibrator.
     */
    public void stop(Context context) {
        // Getting instance of Vibrator from current context
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if(vibrator == null) {
            return;
        }
        vibrator.cancel();
    }
}