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

import android.support.annotation.NonNull;

import java.util.Calendar;

/**
 * Pattern - pattern fo {@link Alarm}
 * Created by Vassili Kurman on 18/11/2018.
 * Version 1.0
 */
public class Pattern {
    /**
     * Pattern start date
     */
    private Calendar patternStart;
    /**
     * Pattern description
     */
    private boolean[] pattern;

    /**
     * For no argument constructor 7 day pattern will be used from
     * Monday to Sunday.
     */
    public Pattern() {
        pattern = new boolean[7];
    }

    /**
     * Custom pattern with custom number of days.
     *
     * @param pattern - boolean[]
     */
    public Pattern(boolean[] pattern) {
        this.pattern = pattern;
    }

    /**
     * Sets pattern start date. If argument is null, than start date is now.
     *
     * @param date - Calendar
     * @return Pattern
     */
    public Pattern setPatternStart(Calendar date) {
        if (date == null)
            patternStart = Calendar.getInstance();
        else
            this.patternStart = date;

        return this;
    }

    /**
     * Returns pattern start date and time
     *
     * @return Calendar
     */
    public Calendar getPatternStart() {
        return patternStart;
    }

    /**
     * Returns pattern.
     *
     * @return boolean[]
     */
    public boolean[] getPattern() {
        return pattern;
    }

    /**
     * Sets pattern, argument should be not null.
     *
     * @param pattern - not null
     * @return Pattern
     */
    public Pattern setPattern(@NonNull boolean[] pattern) {
        this.pattern = pattern;

        return this;
    }

    /**
     * Change patterns individual value
     *
     * @param index - int as index in pattern array
     * @param on - true or false
     * @return Pattern
     */
    public Pattern setPatternValue(int index, boolean on) {
        if(index < 0 || index >= pattern.length)
            throw new IllegalArgumentException("Index is not valid!");

        this.pattern[index] = on;

        return this;
    }

    /**
     * Retrieve individual pattern value
     *
     * @param index - int as index in "0" based pattern.
     * @return boolean
     */
    public boolean getPatternValue(int index) {
        if(index < 0 || index >= pattern.length)
            throw new IllegalArgumentException("Wrong index provided");

        return pattern[index];
    }
}