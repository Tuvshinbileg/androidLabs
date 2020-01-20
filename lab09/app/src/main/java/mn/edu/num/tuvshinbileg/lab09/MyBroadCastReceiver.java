package mn.edu.num.tuvshinbileg.lab09;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

public class MyBroadCastReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = ".BroadcastReceiver";
    String msg;
    String phone;

    private final static String fileName = "deviceInfo.txt";

    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMessage[] messages;
        String str = "";
        Log.i(TAG, "Intent Received:" + intent.getAction());
        BatteryManager bm = (BatteryManager) context.getSystemService(context.BATTERY_SERVICE);
        switch (intent.getAction()) {
            case SMS_RECEIVED:
                Bundle databundle = intent.getExtras();
                if (databundle != null) {
                    Object[] mypdu = (Object[]) databundle.get("pdus");
                    messages = new SmsMessage[mypdu.length];
                    for (int i = 0; i < mypdu.length; i++) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            String format = databundle.getString("format");
                            messages[i] = SmsMessage.createFromPdu((byte[]) mypdu[i], format);

                        } else {
                            messages[i] = SmsMessage.createFromPdu((byte[]) mypdu[i]);

                        }
                        msg = messages[i].getMessageBody();
                        phone = messages[i].getOriginatingAddress();
                    }
                    Toast.makeText(context, "Message :" + msg + "\n Number:" + phone, Toast.LENGTH_LONG).show();
                }
                break;
            case Intent.ACTION_POWER_CONNECTED:
                Log.i(TAG, "POWER CONNECTED");
                saveToFile("POWER CONNECTED", context);
                Toast.makeText(context, "Power connected", Toast.LENGTH_SHORT).show();
                try {
                    appendStrToFile("POWER CONNECTED",context);
                    saveToFile("POWER CONNECTED",context);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case Intent.ACTION_POWER_DISCONNECTED:
                Toast.makeText(context, "Power disconnected", Toast.LENGTH_SHORT).show();
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                try {
                    appendStrToFile("POWER DISCONNECTED,LEVEL"+level, context);
                    saveToFile("POWER DISCONNECTED 1", context);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "LEVEL " + level);
                break;
            case Intent.ACTION_BOOT_COMPLETED:
                Log.i(TAG, "BOot completed");
                Toast.makeText(context, "Boot completed", Toast.LENGTH_SHORT).show();
                break;
            case Intent.ACTION_TIME_CHANGED: {

                //Write to File
            }

            break;
            case Intent.ACTION_TIME_TICK: {
                Log.i(TAG, "TIME_TICK");
                Calendar calendar = Calendar.getInstance();
                int minute = calendar.get(Calendar.MINUTE);
                Log.i(TAG, "MINUTE" + minute);
                if (minute % 5 == 0) {
                    Toast.makeText(context, "minute%5", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "Minute%5");
                    saveToFile("MINUTE%5",context);
                    try {
                        appendStrToFile("MINUTE TICK"+minute, context);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;

            default:
                break;
        }

    }
// /data/user/0/mn.edu.num.tuvshinbileg.lab09/files
    public void appendStrToFile(String str, Context context) throws IOException {
      Log.i(TAG,"apendStrToFIle");
      Log.i(TAG, String.valueOf(context.getFilesDir()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss ");
        String currentDate = sdf.format(new Date());
        BufferedWriter out = null;
        try {
            // Open given file in append mode.
            out = new BufferedWriter(
                    new FileWriter(fileName, true));
            out.write(currentDate + ":" + str);
            out.write("\n");
           Toast.makeText(context,"Saved to"+context.getFilesDir() +"/" +fileName,Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            System.out.println("exception occoured" + e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
    public void saveToFile(String str,Context context){
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
