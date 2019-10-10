package com.decima.lab.easyquiz.util;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Shakil Ahmed Shaj on 10-Oct-19.
 * shakilahmedshaj@gmail.com
 */
public class Prefs {

    private SharedPreferences preferences;

    public Prefs(Activity activity) {
        this.preferences = activity.getPreferences(activity.MODE_PRIVATE);
    }

    public void saveHighScore(int score) {

        int lastScore = preferences.getInt("high_score", 0);

        if (score > lastScore) {
            preferences.edit().putInt("high_score", score).apply();
        }

    }

    public int getHighScore() {
        return preferences.getInt("high_score", 0);
    }

    public  void setState(int index) {
        preferences.edit().putInt("index_state", index).apply();
    }

    public int getState() {
        return preferences.getInt("index_state", 0);
    }


}
