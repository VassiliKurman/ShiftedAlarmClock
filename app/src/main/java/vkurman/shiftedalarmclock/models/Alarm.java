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
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Alarm
 * Created by Vassili Kurman on 18/11/2018.
 * Version 1.0
 */
@Entity
public class Alarm {

    /**
     * Minimum value for volume
     */
    private static final int MIN_VOLUME = 100;
    /**
     * Maximum value for volume
     */
    private static final int MAX_VOLUME = 100;

    /**
     * Alarm id
     */
    @PrimaryKey(autoGenerate = true)
    private int id;

    /**
     * Name for alarm
     */
    @ColumnInfo(name = "name")
    private String name;

    /**
     * Indicator that alarm is activated
     */
    @ColumnInfo(name = "active")
    private boolean active;
    /**
     * Indicator if alarm is repeatable
     */
    @ColumnInfo(name = "repeat")
    private boolean repeat;
    /**
     * Alarm tone
     */
    @ColumnInfo(name = "tone")
    private String tone;
    /**
     * Volume of alarm
     */
    @ColumnInfo(name = "volume")
    private int volume;
    /**
     * Flag to gradually increase the volume
     */
    @ColumnInfo(name = "gradually_increase")
    private boolean graduallyIncreaseVolume;

    /**
     * Say time loud
     */
    @ColumnInfo(name = "say_time")
    private boolean sayTime;

    /**
     * Alarm pattern
     */
    @Embedded
    private Pattern pattern;

    /**
     * Indicator if snooze option to use in alarm
     */
    @Embedded
    private Snooze snooze;
    /**
     * Flag to use vibration
     */
    @Embedded
    private Vibration vibration;

    /**
     * Default constructor.
     */
    @Ignore
    public Alarm() {

        this.vibration = new Vibration();
    }

    /**
     * Constructor for Room.
     * @param id - int
     * @param name - String
     * @param active - boolean
     * @param repeat - boolean
     * @param tone - String
     * @param volume - int
     * @param graduallyIncreaseVolume - boolean
     * @param sayTime - boolean
     * @param pattern - Pattern
     * @param snooze - Snooze
     * @param vibration - Vibration
     */
    public Alarm(int id, String name, boolean active, boolean repeat, String tone, int volume,
                 boolean graduallyIncreaseVolume, boolean sayTime, Pattern pattern,
                 Snooze snooze, Vibration vibration) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.repeat = repeat;
        this.tone = tone;
        this.volume = volume;
        this.graduallyIncreaseVolume = graduallyIncreaseVolume;
        this.sayTime = sayTime;
        this.pattern = pattern;
        this.snooze = snooze;
        this.vibration = vibration;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getVolume() {
        return volume;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public boolean isSayTime() {
        return sayTime;
    }

    public Snooze getSnooze() {
        return snooze;
    }

    public String getTone() {
        return tone;
    }

    public boolean isGraduallyIncreaseVolume() {
        return graduallyIncreaseVolume;
    }

    public Vibration getVibration() {
        return vibration;
    }

    public Alarm setPattern(Pattern pattern) {
        if(pattern == null)
            throw new IllegalArgumentException("Pattern is null");

        this.pattern = pattern;

        return this;
    }

    public Alarm setId(int id) {
        this.id = id;
        return this;
    }

    public Alarm setVolume(int volume) {
        this.volume = volume < MIN_VOLUME ? MIN_VOLUME : volume > MAX_VOLUME ? MAX_VOLUME : volume;
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

    public Alarm setSayTime(boolean sayTime) {
        this.sayTime = sayTime;
        return this;
    }

    public Alarm setSnooze(Snooze snooze) {
        if (snooze == null)
            throw new IllegalArgumentException("Snooze not provided");
        this.snooze = snooze;
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

    public Alarm setVibration(Vibration vibration) {
        if (vibration == null)
            throw new IllegalArgumentException("Vibration not provided");
        this.vibration = vibration;
        return this;
    }

    public Alarm setPattern(boolean[] pattern) {
        if (pattern == null)
            throw new IllegalArgumentException("Pattern not provided");
        this.pattern = new Pattern(null, pattern);
        return this;
    }

    public Alarm setName(String name) {
        this.name = name;
        return this;
    }
}