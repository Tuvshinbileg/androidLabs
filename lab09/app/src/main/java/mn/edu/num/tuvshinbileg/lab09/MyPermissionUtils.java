package mn.edu.num.tuvshinbileg.lab09;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
public class MyPermissionUtils {
    public static boolean hasPermission(Context activity, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return (PackageManager.PERMISSION_GRANTED == activity.checkSelfPermission(permission));
        }
        return false;
    }


    public static Boolean canAccessCoarseLocation(Activity activity) {
        return (MyPermissionUtils.hasPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION));
    }

    public static Boolean canReadPhoneState(Activity activity) {
        return (MyPermissionUtils.hasPermission(activity, Manifest.permission.READ_PHONE_STATE));
    }
}
