package com.qubic.grabsimulation.helper;

import android.util.Log;

import io.realm.Realm;

public class RealmHelper {

    public interface OnRealmLoaded {
        void onSuccess(Realm realm);
    }

    static public void getRealm(OnRealmLoaded onRealmLoaded) {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            Log.d("realm", "getInstance helper");
            onRealmLoaded.onSuccess(realm);
        } finally {
            if (realm != null) {
                realm.close();
                Log.d("realm", "close helper");
            }
        }
    }
}
