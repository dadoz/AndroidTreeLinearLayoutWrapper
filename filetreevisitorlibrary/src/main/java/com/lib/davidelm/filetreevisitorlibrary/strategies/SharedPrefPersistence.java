package com.lib.davidelm.filetreevisitorlibrary.strategies;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.lib.davidelm.filetreevisitorlibrary.models.TreeNode;

import java.lang.ref.WeakReference;

public class SharedPrefPersistence implements PersistenceStrategyInterface {
    private static final String TAG = "SharedPrefStrategy";
    private static SharedPreferences sharedPref;

    /**
     * reading on local storage
     */
    @Override
    public TreeNode getPersistentNode() {
        Log.e(TAG, "hey get node");
        try {
            return new Gson().fromJson(getValue(SharedPrefPersistence
                    .SharedPrefKeysEnum.TREE_NODE, null).toString(), TreeNode.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * saving on local storage
     */

    @Override
    public void setPersistentNode(TreeNode node) {
        Log.e(TAG, new Gson().toJson(node));
        setValue(SharedPrefPersistence.SharedPrefKeysEnum.TREE_NODE,
                new Gson().toJson(node));

    }

    public enum SharedPrefKeysEnum { TREE_NODE }

    public SharedPrefPersistence(WeakReference<Context> ctx) {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx.get());
    }

    /**
     * @param key
     * @param defValue
     * @return
     */
    private Object getValue(SharedPrefKeysEnum key, Object defValue) {
        String temp = sharedPref.getString(key.name(), (String) defValue);
        Log.e(TAG, temp);
        return temp;
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
    private void setValue(SharedPrefKeysEnum key, Object value) {
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