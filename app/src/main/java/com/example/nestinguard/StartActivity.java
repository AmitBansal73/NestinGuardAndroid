package com.example.nestinguard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.nestinguard.Common.ApplicationConstants;
import com.example.nestinguard.Common.Profile;
import com.example.nestinguard.Common.Session;
import com.example.nestinguard.Common.Society;
import com.example.nestinguard.Common.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class StartActivity extends AppCompatActivity {

    private static final int INTERNET_PERMISSION_REQUEST_CODE = 1;
    Profile myProfile;
    TextView txtError;
    boolean IsGuard = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        txtError = findViewById(R.id.txtError);

        txtError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartActivity.this.finish();
            }
        });

        myProfile = Session.GetUser(getApplicationContext());



        checkNetworkPermission();
    }

    private void checkNetworkPermission()
    {
        if ((ContextCompat.checkSelfPermission(StartActivity.this, android.Manifest.permission.ACCESS_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED)
                &&(ContextCompat.checkSelfPermission(StartActivity.this, android.Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED))
        {
            ActivityCompat.requestPermissions(StartActivity.this, new String[]{android.Manifest.permission.ACCESS_WIFI_STATE}, INTERNET_PERMISSION_REQUEST_CODE);
        }

        else
        {
            MoveToMainActivity();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case INTERNET_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    MoveToMainActivity();

                } else {

                    StartActivity.this.finish();

                }
                break;

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void Validation(final String MobileNo,final String Password)
    {
        String url = ApplicationConstants.APP_SERVER_URL + "/api/User/IsValid";
        String reqBody = "{\"Mobile\":\""+ MobileNo+"\",\"Email\":\"\",\"Password\":\""+ Password + "\",\"RegistrationID\":\"\"}";

        try{
            JSONObject jsRequest = new JSONObject(reqBody);
            //-------------------------------------------------------------------------------------------------
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url,jsRequest, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {

                        String result  = response.getString("result");

                        if(result.matches("Fail"))
                        {
                            Toast.makeText(getApplicationContext(),"Contact Admin..",Toast.LENGTH_LONG).show();
                            LoginFailedDialog();
                        }
                        else
                        {
                            JSONObject userData = response.getJSONObject("UserData");

                            String strFirstName = userData.get("FirstName").toString();
                            if (!strFirstName.matches("")) {
                                Profile user = new Profile();
                                user.NAME = userData.getString("FirstName") + " " + userData.getString("LastName");
                                user.Last_Name = userData.getString("LastName");
                                user.First_Name = userData.getString("FirstName");
                                user.MOB_NUMBER = MobileNo;
                                user.UserID = userData.getString("UserID");
                                user.E_MAIL = userData.getString("EmailId");
                                user.Gender = userData.getString("Gender");
                                user.ParentName = userData.getString("Parentname");
                                user.password = Password;
                                user.LOCATION = userData.getString("Address");

                                JSONArray flatArray =  response.getJSONArray("SocietyUser");
                                int x = flatArray.length();

                                if (x == 0) {
                                    Toast.makeText(getApplicationContext(),"", Toast.LENGTH_LONG);
                                    txtError.setVisibility(View.VISIBLE);
                                    txtError.setText("No User Role Assigned. click here to exit!");
                                } else {

                                    for (int i = 0; i < x; i++) {
                                        JSONObject flatObject = flatArray.getJSONObject(i);
                                        String RoleType = flatObject.getString("Type");

                                        if(RoleType.equalsIgnoreCase("Guard"))
                                        {
                                            Society society = new Society();
                                            society.SocietyId = flatObject.getInt("SocietyID");
                                            society.SocietyName = flatObject.getString("SocietyName");
                                            Session.AddUser(getApplicationContext(),user);
                                            Session.AddSociety(getApplicationContext(),society);
                                            IsGuard = true;
                                            break;
                                        }
                                    }

                                    if(IsGuard)
                                    {
                                        Intent mainInent = new Intent(StartActivity.this, MainActivity.class);
                                        startActivity(mainInent);
                                        StartActivity.this.finish();
                                    }
                                    else
                                    {
                                        txtError.setVisibility(View.VISIBLE);
                                        txtError.setText("User is not Guard. click here to exit!");
                                    }



                                }
                                Session.AddUser(getApplicationContext(), user);

                            } else {

                                Toast.makeText(getApplicationContext(), "Login Failed..", Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                    catch (JSONException jex)
                    {

                        Toast.makeText(getApplicationContext(),"Contact Admin..",Toast.LENGTH_LONG).show();
                        LoginFailedDialog();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    LoginFailedDialog();
                }
            });

            RetryPolicy rPolicy = new DefaultRetryPolicy(0,-1,0);
            jsObjRequest.setRetryPolicy(rPolicy);
            queue.add(jsObjRequest);
            //-------------------------------------------------------------------------------------------------
        }
        catch (JSONException jex)
        {
            LoginFailedDialog();
            //Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_LONG).show();
        }

    }

    private void LoginFailedDialog()
    {
        AlertDialog.Builder builder= new AlertDialog.Builder(StartActivity.this);
        builder.setTitle("Login Failed: Try Again ");
        builder.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.cancel();
                        StartActivity.this.finish();

                    }

                });
        builder.setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Session.LogOff(getApplicationContext());
                Intent LoginIntent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(LoginIntent);
                StartActivity.this.finish();
            }
        });
        AlertDialog Alert = builder.create();

        Alert.show();
    }


    private void MoveToMainActivity()
    {
        try {
            if (Utility.IsConnected(this)) {

                myProfile = Session.GetUser(this);
                if (myProfile == null || myProfile.MOB_NUMBER.matches("")) {
                    Intent LoginIntent = new Intent(StartActivity.this, LoginActivity.class);
                    startActivity(LoginIntent);
                    StartActivity.this.finish();
                } else {
                    Validation(myProfile.MOB_NUMBER,myProfile.password);
                }
            } else {

                Toast.makeText(this, "No Internet connection", Toast.LENGTH_LONG).show();
                txtError.setVisibility(View.VISIBLE);
                txtError.setText("No Network Connection. click here to exit!");
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "Unable to start Application, Contact Admin", Toast.LENGTH_LONG).show();
        }


    }


}
