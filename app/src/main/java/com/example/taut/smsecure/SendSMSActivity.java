package com.example.taut.smsecure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendSMSActivity extends AppCompatActivity {

    Button sendBtn;
    EditText txtphoneNo;
    EditText txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);
        sendBtn = (Button) findViewById(R.id.btnSendSMS);
        txtphoneNo = (EditText) findViewById(R.id.editTextPhoneNo);
        txtMessage = (EditText) findViewById(R.id.editTextSMS);

        txtphoneNo.setText(getIntent().getStringExtra("phoneNo"));
        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendSMSMessage();
            }
        });


    }
    protected void sendSMSMessage()
    {
        Log.v("Send SMS", "");
        String phoneNo = txtphoneNo.getText().toString();
        String message = txtMessage.getText().toString();
        String encryptedMessage = Encrypter.encrypt(phoneNo,message);
        Log.v("enc",encryptedMessage);
       // Log.v("enc",obj.decrypt(encryptedMessage));
        try
        {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, encryptedMessage, null, null);
            Toast t = Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG);
            t.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
            t.show();
            startActivity(new Intent(SendSMSActivity.this, MainActivity.class));
        }
        catch (Exception e)
        {
            Toast t = Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG);
            t.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,0,0);
            t.show(); e.printStackTrace();
        }
    }
}
