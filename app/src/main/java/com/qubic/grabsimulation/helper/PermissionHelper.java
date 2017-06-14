package com.qubic.grabsimulation.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.qubic.grabsimulation.R;
import com.qubic.grabsimulation.view.custom.CustomDialog;
import com.qubic.grabsimulation.view.custom.WarningDialog;


public class PermissionHelper {

    public enum Permission {
        LOCATION(android.Manifest.permission.ACCESS_FINE_LOCATION),
        READ_PHONE_STATE(Manifest.permission.READ_PHONE_STATE);

        private String permission;

        Permission(String permission) {
            this.permission = permission;
        }

        public String getPermission() {
            return permission;
        }
    }

    public static boolean isPermissionGranted(Context context, Permission permission) {
        return ContextCompat.checkSelfPermission(context, permission.getPermission()) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkAndRequestPermission(final Activity activity, Permission permission, CharSequence message, int requestCode) {
        if (!isPermissionGranted(activity, permission)) {
            //This will be executed if user check the Never ask Again in Permission Request
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                showDialogToPermissionSetting(activity, message, requestCode);
                return false;
            }

            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_PHONE_STATE)) {
                showDialogToPermissionSetting(activity, message, requestCode);
                return false;
            }

            // Ask for Permission
            String[] perm = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE};
            ActivityCompat.requestPermissions(activity, perm, requestCode);
            return false;
        }
        return true;
    }

    public static boolean checkAndRequestPermission(final Fragment fragment, Permission permission, CharSequence message, int requestCode) {
        if (!isPermissionGranted(fragment.getContext(), permission)) {
            // This will be executed if user check the Never ask Again in Permission Request
            if (!fragment.shouldShowRequestPermissionRationale(permission.getPermission())) {
                showDialogToPermissionSetting(fragment, message, requestCode);
                return false;
            }

            // Ask for Permission
            String[] perm = {permission.getPermission()};
            fragment.requestPermissions(perm, requestCode);
            return false;
        }
        return true;
    }

    private static void showDialogToPermissionSetting(final Activity activity, CharSequence message, final int requestCode) {
        CustomDialog alertDialog = new CustomDialog(activity);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.coloredPositiveButton(true);
        alertDialog.setTextGravityCenter(true);
        alertDialog.setPositiveButton(activity.getString(R.string.settings), new WarningDialog.OnClickListener() {
            @Override
            public void onClick() {
                activity.startActivityForResult(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + activity.getPackageName())), requestCode);
            }
        });
        alertDialog.setNegativeButton(activity.getString(R.string.close), new WarningDialog.OnClickListener() {
            @Override
            public void onClick() {
                Intent i = activity.getPackageManager()
                        .getLaunchIntentForPackage( activity.getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(i);
            }
        });
        alertDialog.show();
    }

    private static void showDialogToPermissionSetting(final Fragment fragment, CharSequence message, final int requestCode) {
        CustomDialog alertDialog = new CustomDialog(fragment.getActivity());
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.coloredPositiveButton(true);
        alertDialog.setTextGravityCenter(true);
        alertDialog.setPositiveButton(fragment.getString(R.string.settings), new WarningDialog.OnClickListener() {
            @Override
            public void onClick() {
                fragment.startActivityForResult(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + fragment.getActivity().getPackageName())), requestCode);
            }
        });
        alertDialog.setNegativeButton(fragment.getString(R.string.close), new WarningDialog.OnClickListener() {
            @Override
            public void onClick() {

            }
        });
        alertDialog.show();
    }

}