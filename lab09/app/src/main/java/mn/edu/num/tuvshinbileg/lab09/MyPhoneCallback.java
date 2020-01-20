package mn.edu.num.tuvshinbileg.lab09;

import android.content.Context;
import android.os.Environment;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyPhoneCallback extends PhoneStateListener {
    File externalStorageDir = Environment.getExternalStorageDirectory();
    private Context context;
    private static String fileName = "phonestate.txt";
    private static final String LOG_TAG = "PhoneCallback";
    public MyPhoneCallback(Context context){
        this.context=context;
    }

    @Override
    public void onCallForwardingIndicatorChanged(boolean cfi) {
        super.onCallForwardingIndicatorChanged(cfi);
        String msg = "onCallForwardingIndicatorChanged:" + cfi;

    }
    @Override
    public void onCellLocationChanged(CellLocation location) {
        super.onCellLocationChanged(location);

        String message = "";
        if (location instanceof GsmCellLocation) {
            GsmCellLocation gcLoc = (GsmCellLocation) location;
            message += "onCellLocationChanged: GsmCellLocation " + gcLoc + "\n";


        } else if (location instanceof CdmaCellLocation) {
            CdmaCellLocation cdmLoc = (CdmaCellLocation) location;
            message += "onCellLocationChanged: CdmaCellLocation " + cdmLoc + "\n";


        }
        try {
            appendStrToFile(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCallStateChanged(int state, String phoneNumber) {
        super.onCallStateChanged(state, phoneNumber);

        String message = callStateToString(state) + "phoneNumber: " + phoneNumber;
        Log.i(LOG_TAG, message);
    }

    private String callStateToString(int state) {
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                return "\nonCallStateChanged: CALL_STATE_IDLE, ";
            case TelephonyManager.CALL_STATE_RINGING:
                return "\nonCallStateChanged: CALL_STATE_RINGING, ";
            case TelephonyManager.CALL_STATE_OFFHOOK:
                return "\nonCallStateChanged: CALL_STATE_OFFHOOK, ";
            default:
                return "\nUNKNOWN_STATE: " + state + ", ";
        }
    }

    private String serviceStateToString(int serviceState) {
        switch (serviceState) {
            case ServiceState.STATE_IN_SERVICE:
                return "STATE_IN_SERVICE";
            case ServiceState.STATE_OUT_OF_SERVICE:
                return "STATE_OUT_OF_SERVICE";
            case ServiceState.STATE_EMERGENCY_ONLY:
                return "STATE_EMERGENCY_ONLY";
            case ServiceState.STATE_POWER_OFF:
                return "STATE_POWER_OFF";
            default:
                return "UNKNOWN_STATE";
        }
    }

    @Override
    public void onServiceStateChanged(ServiceState serviceState) {
        super.onServiceStateChanged(serviceState);
        String message = "onServiceStateChanged: " + serviceState + "\n";
        message += "onServiceStateChanged: getOperatorNumeric " + serviceState.getOperatorNumeric() + "\n";
        message += "onServiceStateChanged: getIsManualSelection " + serviceState.getIsManualSelection() + "\n";
        message += "onServiceStateChanged: getRoaming " + serviceState.getRoaming() + "\n";
        message += "onServiceStateChanged: " + serviceStateToString(serviceState.getState());
        Log.i(LOG_TAG, message);
        try {
            appendStrToFile(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDataActivity(int direction) {
        super.onDataActivity(direction);
        String msg = "";
        switch (direction) {
            case TelephonyManager.DATA_ACTIVITY_NONE:
                Log.i(LOG_TAG, "onDataActivity: DATA_ACTIVITY_NONE");
                msg += "onDataActivity: DATA_ACTIVITY_NONE";
                break;
            case TelephonyManager.DATA_ACTIVITY_IN:
                Log.i(LOG_TAG, "onDataActivity: DATA_ACTIVITY_IN");
                msg += "onDataActivity: DATA_ACTIVITY_IN";
                break;
            case TelephonyManager.DATA_ACTIVITY_OUT:
                Log.i(LOG_TAG, "onDataActivity: DATA_ACTIVITY_OUT");
                msg += "onDataActivity: DATA_ACTIVITY_OUT";
                break;
            case TelephonyManager.DATA_ACTIVITY_INOUT:
                Log.i(LOG_TAG, "onDataActivity: DATA_ACTIVITY_INOUT");
                msg += "onDataActivity: DATA_ACTIVITY_INOUT";
                break;
            case TelephonyManager.DATA_ACTIVITY_DORMANT:
                Log.i(LOG_TAG, "onDataActivity: DATA_ACTIVITY_DORMANT");
                msg += "onDataActivity: DATA_ACTIVITY_DORMANT";
                break;
            default:
                break;

        }


    }



    public void appendStrToFile(String str) throws IOException {
        Log.i(LOG_TAG,"apendStrToFIle");
       // Log.i(LOG_TAG, String.valueOf(context.getFilesDir()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss ");
        String currentDate = sdf.format(new Date());
        BufferedWriter out = null;
        try {
            // Open given file in append mode.
            out = new BufferedWriter(
                    new FileWriter(fileName, true));
            out.write(currentDate + ":" + str);
            out.write("\n");
    //        Toast.makeText(context,"Saved to"+context.getFilesDir() +"/" +fileName,Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            System.out.println("exception occoured" + e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
    public void saveToFile(String str){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss ");
        String currentDate = sdf.format(new Date());

        FileOutputStream fos=null;
        try{
            fos=context.openFileOutput(fileName,Context.MODE_APPEND|Context.MODE_PRIVATE);
            String write=currentDate +":" +str;
            fos.write(write.getBytes());

            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
