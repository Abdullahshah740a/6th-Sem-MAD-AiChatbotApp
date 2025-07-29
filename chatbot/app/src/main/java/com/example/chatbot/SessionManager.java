package com.example.chatbot;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "userSession";
    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final String USER_EMAIL = "userEmail";

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void createLoginSession(String email) {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(USER_EMAIL, email);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(IS_LOGGED_IN, false);
    }

    public String getUserEmail() {
        return prefs.getString(USER_EMAIL, null);
    }

    public void logoutUser() {
        editor.clear();
        editor.apply();
    }
}
