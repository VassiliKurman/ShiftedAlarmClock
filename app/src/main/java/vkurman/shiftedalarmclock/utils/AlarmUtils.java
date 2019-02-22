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
package vkurman.shiftedalarmclock.utils;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import vkurman.shiftedalarmclock.models.Pattern;

/**
 * AlarmUtils
 * Created by Vassili Kurman on 18/11/2018.
 * Version 1.0
 */
public class AlarmUtils {

    /**
     * Bundle argument name for Calendar
     */
    public static final String ARG_CALENDAR = "Calendar";
    /**
     * Bundle argument name for Pattern
     */
    public static final String ARG_PATTERN = "Pattern";
    /**
     * Action add fragment
     */
    public static final int ACTION_ADD_FRAGMENT = 1;
    /**
     * Action add fragment
     */
    public static final int ACTION_REPLACE_FRAGMENT = 2;

    /**
     * Return String representation of alarm time in format "HH:MM".
     *
     * @param cal - Calendar
     * @return String
     */
    public static String formatTime(Calendar cal) {
        if(cal == null) {
            return null;
        }
        return formatTime(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
    }

    /**
     * Return String representation of alarm time in format "HH:MM".
     *
     * @param hourOfDay - int
     * @param minute - int
     * @return String
     */
    public static String formatTime(int hourOfDay, int minute) {
        return String.format(Locale.getDefault(), "%02d", hourOfDay)
                + ":" + String.format(Locale.getDefault(), "%02d", minute);
    }

    /**
     * Return String representation of alarm date in format "dd MMM YYYY".
     *
     * @param cal - Calendar
     * @return String
     */
    public static String formatDate(Calendar cal) {
        if(cal == null) {
            return null;
        }
        return cal.get(Calendar.DAY_OF_MONTH) + " "
                + new SimpleDateFormat("MMM", Locale.getDefault()).format(cal.getTime()) + " "
                + cal.get(Calendar.YEAR);
    }

    /**
     * Return String representation of alarm date in format "dd MM YYYY".
     *
     * @param cal - Calendar
     * @return String
     */
    public static String formatShortDate(Calendar cal) {
        if(cal == null) {
            return null;
        }
        return cal.get(Calendar.DAY_OF_MONTH) + " "
                + cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()) + " "
                + cal.get(Calendar.YEAR);
    }

    /**
     * Return String representation of alarm date in format "dd MMM YYYY".
     *
     * @param year - int
     * @param month - int
     * @param day - int
     * @return String
     */
    public static String formatDate(int year, int month, int day) {
        return String.format(Locale.getDefault(), "%02d", day)
                + " " + new DateFormatSymbols().getShortMonths()[month]
                + " " + String.format(Locale.getDefault(), "%04d", year);
    }

    /**
     * Return String representation of alarm pattern to display in UI.
     *
     * @param pattern - Pattern
     * @return String
     */
    public static String formatPattern(Pattern pattern) {
        if(pattern == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for(boolean on: pattern.getPattern()) {
            sb.append(on ? "[+] " : "[-] ");
        }
        return sb.toString();
    }

    /**
     * Return String representation of alarm pattern to pass between activities.
     *
     * @param pattern - Pattern
     * @return String
     */
    public static String formatPatternToString(Pattern pattern) {
        if(pattern == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for(boolean on: pattern.getPattern()) {
            sb.append(on ? "1 " : "0 ");
        }
        return sb.toString();
    }

    /**
     * Return String representation of alarm pattern to pass between activities.
     *
     * @param pattern - Pattern
     * @return String
     */
    public static String formatPatternToString(boolean[] pattern) {
        if(pattern == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for(boolean on: pattern) {
            sb.append(on ? "1 " : "0 ");
        }
        return sb.toString();
    }

    /**
     * Return boolean[] representation of alarm pattern passed between activities.
     *
     * @param pattern - Pattern
     * @return String
     */
    public static boolean[] formatStringToPattern(String pattern) {
        if(pattern == null) {
            return null;
        }
        String[] pat = pattern.split(" ");
        boolean[] bool = new boolean[pat.length];
        for(int i = 0; i < pat.length; i++) {
            bool[i] = pat[i].equals("1");
        }
        return bool;
    }
}