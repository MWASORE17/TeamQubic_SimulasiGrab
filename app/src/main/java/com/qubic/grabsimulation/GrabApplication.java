package com.qubic.grabsimulation;

import android.app.Application;

import java.util.Date;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by christopher on 4/14/17.
 */

public class GrabApplication extends Application {
    public void onCreate(){
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(4)
                .migration(new Migration())
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
    private class Migration implements RealmMigration {

        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
            RealmSchema schema = realm.getSchema();
            if (oldVersion == 0) {
                schema.get("User")
                        .addField("username", String.class)
                        .addField("phoneNumber", String.class)
                        .addField("password", String.class);
                oldVersion++;
            }
            if (oldVersion == 1){
                schema.get("User")
                        .addField("credit", Double.class);
                oldVersion++;
            }

        }
    }
}
