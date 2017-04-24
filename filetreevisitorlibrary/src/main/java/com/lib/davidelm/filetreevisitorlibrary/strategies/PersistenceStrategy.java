package com.lib.davidelm.filetreevisitorlibrary.strategies;

import android.content.Context;

import java.lang.ref.WeakReference;


public class PersistenceStrategy {
    private final WeakReference<Context> context;
    public PersistenceStrategyInterface strategy;
    public enum PersistenceType {SHARED_PREF, FIREBASE}

    public PersistenceStrategy(WeakReference<Context> context, PersistenceType type) {
        this.context = context;
        setStrategy(type);
    }

    public PersistenceStrategyInterface getStrategy() {
        return strategy;
    }

    void setStrategy(PersistenceType type) {
        switch (type.name()) {
            case "SHARED_PREF":
                this.strategy = new SharedPrefPersistence(context);
                break;
            case "FIREBASE":
                this.strategy = new FirebasePersistence();
                break;
        }
    }

}
