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

import java.util.Calendar;

/**
 * Alarm
 * Created by Vassili Kurman on 18/11/2018.
 * Version 1.0
 */
public class Alarm {
    /**
     * Alarm id
     */
    private Long id;
    /**
     * Name for alarm
     */
    private String name;
    /**
     * Alarm pattern
     */
    private Pattern pattern;
    /**
     * Alarm date and time
     */
    private Calendar date;
    /**
     * Indicator that alarm is activated
     */
    private boolean active;
    /**
     * indicator if alarm is repeatable
     */
    private boolean repeat;
    /**
     * Indicator if snooze option to use in alarm
     */
    private boolean snooze;
    /**
     * Minutes to snooze alarm
     */
    private int snoozeMinutes;
    /**
     * How many time alarm to snooze
     */
    private int snoozeTimes;
    /**
     * Alarm tone
     */
    private String tone;
    /**
     * Flag to gradually increase the volume
     */
    private boolean graduallyIncreaseVolume;
    /**
     * Flag to use vibration
     */
    private boolean vibration;

    public Pattern getPattern() {
        return pattern;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Calendar getDate() {
        return date;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public boolean isSnooze() {
        return snooze;
    }

    public int getSnoozeMinutes() {
        return snoozeMinutes;
    }

    public int getSnoozeTimes() {
        return snoozeTimes;
    }

    public String getTone() {
        return tone;
    }

    public boolean isGraduallyIncreaseVolume() {
        return graduallyIncreaseVolume;
    }

    public boolean isVibration() {
        return vibration;
    }

    public Alarm setPattern(Pattern pattern) {
        if(pattern == null)
            throw new IllegalArgumentException("Pattern is null");

        this.pattern = pattern;

        return this;
    }

    public Alarm setId(long id) {
        this.id = id;

        return this;
    }

    public Alarm setDate(Calendar date) {
        if(date == null)
            throw new IllegalArgumentException("Date is null");

        this.date = date;

        return this;
    }

    public Alarm setActive(boolean active) {
        this.active = active;

        return this;
    }

    public Alarm setRepeat(boolean repeat) {
        this.repeat = repeat;

        return this;
    }

    public Alarm setSnooze(boolean snooze) {
        this.snooze = snooze;

        return this;
    }

    public Alarm setSnoozeMinutes(int snoozeMinutes) {
        if (snoozeMinutes <= 0)
            throw new IllegalArgumentException("Snooze time should be more than 0");

        this.snoozeMinutes = snoozeMinutes;

        return this;
    }

    public Alarm setSnoozeTimes(int snoozeTimes) {
        this.snoozeTimes = snoozeTimes;

        return this;
    }

    public Alarm setTone(String tone) {
        if (tone == null)
            throw new IllegalArgumentException("Tone not provided");

        this.tone = tone;

        return this;
    }

    public Alarm setGraduallyIncreaseVolume(boolean graduallyIncreaseVolume) {
        this.graduallyIncreaseVolume = graduallyIncreaseVolume;

        return this;
    }

    public Alarm setVibration(boolean vibration) {
        this.vibration = vibration;

        return this;
    }

    public Alarm setPattern(boolean[] pattern) {
        if (pattern == null)
            throw new IllegalArgumentException("Pattern not provided");

        this.pattern = new Pattern(pattern);

        return this;
    }

    public Alarm setName(String name) {
        this.name = name;

        return this;
    }
}