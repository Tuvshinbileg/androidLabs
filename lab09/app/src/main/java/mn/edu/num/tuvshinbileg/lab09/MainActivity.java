package mn.edu.num.tuvshinbileg.lab09;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private static final int MY_PERMISSION_READ_PHONE_STATE = 100;
    private static final String[] PERMISSIONS = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS = 1;
    TelephonyManager tm;
    EditText etTo;
    EditText etMssg;
    String phoneNumber;
    String msgBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etTo = (EditText) findViewById(R.id.etTo);
        etMssg = (EditText) findViewById(R.id.etMessages);
        checkPermissions();
        checkSmsReceivePermissions();
        registerReceiver(new MyBroadCastReceiver(), new IntentFilter(Intent.ACTION_POWER_CONNECTED));
        registerReceiver(new MyBroadCastReceiver(), new IntentFilter(Intent.ACTION_POWER_DISCONNECTED));
        registerReceiver(new MyBroadCastReceiver(), new IntentFilter(Intent.ACTION_BOOT_COMPLETED));
        registerReceiver(new MyBroadCastReceiver(), new IntentFilter(Intent.ACTION_TIME_TICK));

    }

    public void simManager() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        String IMEINumber = tm.getDeviceId();
        String subscriberID=tm.getDeviceId();
        String SIMSerialNumber=tm.getSimSerialNumber();
        String networkCountryISO=tm.getNetworkCountryIso();
        String SIMCountryISO=tm.getSimCountryIso();
        String softwareVersion=tm.getDeviceSoftwareVersion();
        String voiceMailNumber=tm.getVoiceMailNumber();

    }

    public void callPhoneManager() {

        tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        tm.listen(new MyPhoneCallback(MainActivity.this),
                PhoneStateListener.LISTEN_CALL_FORWARDING_INDICATOR | PhoneStateListener.LISTEN_CELL_INFO
                        | PhoneStateListener.LISTEN_CALL_STATE | PhoneStateListener.LISTEN_CELL_LOCATION
                        | PhoneStateListener.LISTEN_DATA_ACTIVITY | PhoneStateListener.LISTEN_SERVICE_STATE);


    }


    public void sendMessage(View view) {
        SmsManager m = SmsManager.getDefault();
        // m.sendTextMessage()
        // ;
        Log.i("SMS", "ONSEND");
        sendSMSMessage();
    }

    public void sendSMSMessage() {
        phoneNumber = etTo.getText().toString();
        msgBody = etMssg.getText().toString();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("SEND MSG","SENT MSG");
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNumber, null, msgBody, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            case MY_PERMISSION_READ_PHONE_STATE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhoneManager();

                } else {
                    Toast.makeText(getApplicationContext(), "PHONE_STATE failed", Toast.LENGTH_LONG).show();
                }
            }
            case MY_PERMISSIONS_REQUEST_RECEIVE_SMS:{
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){

                    Toast.makeText(this,"permited receive",Toast.LENGTH_LONG).show();
                }else{

                    Toast.makeText(this,"failed receive",Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    private void checkPermissions() {
        // Checks the Android version of the device.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Boolean canWriteExternalStorage = MyPermissionUtils.canReadPhoneState(this);
            Boolean canReadExternalStorage = MyPermissionUtils.canAccessCoarseLocation(this);
            if (!canWriteExternalStorage || !canReadExternalStorage) {
                requestPermissions(PERMISSIONS, MY_PERMISSION_READ_PHONE_STATE);
            } else {
                // Permission was granted.
                callPhoneManager();
            }
        } else {
            // Version is below Marshmallow.
            callPhoneManager();
        }
    }

    private void checkSmsReceivePermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
            }
        }
    }
    public void createFile(String fileName,String msg){
        File root=new File(Environment.getExternalStorageState(),"MyFolder");
        if(!root.exists()){
                root.mkdir();
        }
        File filepath=new File(root,fileName);
        FileWriter writer= null;
        try {
            writer = new FileWriter(filepath);

            writer.append(msg);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
