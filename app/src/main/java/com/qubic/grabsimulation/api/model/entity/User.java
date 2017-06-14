package com.qubic.grabsimulation.api.model.entity;

import android.content.Context;

import com.qubic.grabsimulation.api.model.ObjectInterface;
import com.qubic.grabsimulation.helper.RealmHelper;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject implements ObjectInterface {

    public static User currentUser = null;
    @PrimaryKey

    private String username;
    private String phoneNumber;
    private String password;
    private Double credit;
    private int _id = 1;
    private int id;

    public User() {
    }


    private static ArrayList<User> users = new ArrayList<>();

    public User(String username, String password, String phoneNumber) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.credit = 0.0;
        this.id = _id;
        _id++;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static void addNewUser(User user) {
        users.add(user);
    }

    public interface OnFetchUserCallback {
        void onSuccess(User user);
    }

    public interface OnSetUserCallback {
        void onSuccess();
    }

    public static void setCurrentUser(Context context, final User user) {
        setCurrentUser(context, user, null);
    }

    public static boolean isRegistered(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static User getMatchUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public static void setCurrentUser(Context context, final User user, final OnSetUserCallback onSetUserCallback) {
        currentUser = user;
        RealmHelper.getRealm(new RealmHelper.OnRealmLoaded() {
            @Override
            public void onSuccess(Realm realm) {
                if (null == onSetUserCallback) {
                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.copyToRealmOrUpdate(user);
                        }
                    });
                } else {
                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.copyToRealmOrUpdate(user);
                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            onSetUserCallback.onSuccess();
                        }
                    });
                }
            }
        });

    }


    public static User getCurrentUser() {
        return currentUser;
    }
}
