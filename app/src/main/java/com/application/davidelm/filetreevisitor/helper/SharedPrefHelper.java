package com.application.davidelm.filetreevisitor.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.lang.ref.WeakReference;

public class SharedPrefHelper {
    private static SharedPreferences sharedPref;

    public enum SharedPrefKeysEnum { TREE_NODE }

    public SharedPrefHelper(WeakReference<Context> ctx) {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx.get());
    }

    /**
     * @param key
     * @param defValue
     * @return
     */
    public Object getValue(SharedPrefKeysEnum key, Object defValue) {
        return sharedPref.getString(key.name(), (String) defValue);
//        if (defValue instanceof Boolean) {
//            return sharedPref.getBoolean(key.name(), (boolean) defValue);
//        }
//        if (defValue instanceof Integer) {
//            return sharedPref.getInt(key.name(), (int) defValue);
//        }
//        if (defValue instanceof String) {
//            return sharedPref.getString(key.name(), defValue.toString());
//        }
//        return null;
    }

    /**
     * @param key
     * @param value
     */
    public void setValue(SharedPrefKeysEnum key, Object value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key.name(), (String) value);
        editor.apply();
        //TODO check type
//        if (value instanceof Boolean) {
//            editor.putBoolean(key.name(), (boolean) value);
//        } else if (value instanceof Integer) {
//            editor.putInt(key.name(), (int) value);
//        } else if (value instanceof String) {
//            editor.putString(key.name(), "bla");
//        }
    }
}