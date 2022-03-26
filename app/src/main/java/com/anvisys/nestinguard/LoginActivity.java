package com.anvisys.nestinguard;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.anvisys.nestinguard.Common.Profile;
import com.anvisys.nestinguard.Common.Session;
import com.anvisys.nestinguard.Common.ApplicationConstants;
import com.anvisys.nestinguard.Common.Society;
import com.anvisys.nestinguard.Common.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin,btnRent;
    EditText password,txtLogin;
    String strResponse,strUserID, strFirstName,strLastName,  strLogin, strEmailId,strMobile;
    String strUser_Mobile="", strUser_Login="", strUser_Password="";
    ProgressDialog prgDialog;
    boolean IsGuard = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            txtLogin =  findViewById(R.id.txtLogin);
            password =  findViewById(R.id.edittxtPassword);
            btnLogin =  findViewById(R.id.btnLogin);
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Validate();
                }
            });
        }
        catch (Exception ex)
        {

        }
    }

    private void Validate()
    {
        try {
            if (!Utility.IsConnected(getApplicationContext()))
            {
                Toast.makeText(getApplicationContext(),"Not connected to internet, Please connect", Toast.LENGTH_LONG).show();
            }
            else {
                strUser_Login = txtLogin.getText().toString();

                boolean numeric = true;

                numeric = strUser_Login.matches("-?\\d+(\\.\\d+)?");


                if (numeric) {
                    strUser_Mobile = strUser_Login;
                    strUser_Login = "";
                }

                strUser_Password = password.getText().toString();
                if ((strUser_Mobile.length() < 5 && strUser_Login.length() < 5) || strUser_Password.length() < 3) {
                    Toast.makeText(getApplicationContext(), " Incorrect Mobile No or Password", Toast.LENGTH_LONG).show();
                } else {
                    prgDialog = ProgressDialog.show(LoginActivity.this, "Login", "Please Wait....");
                    // ValidateUser();

                    UserValidation();
                }
            }
        }
        catch (Exception ex)
        {
            prgDialog.dismiss();
            AlertDialog.Builder builder= new AlertDialog.Builder(LoginActivity.this);
            builder.setTitle("Service not available temporarily : Try Again ");
            builder.setNeutralButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            dialog.cancel();
                            LoginActivity.this.finish();
                            Log.e("info", "NO");
                        }

                    });

            AlertDialog Alert = builder.create();
            Alert.show();
        }
    }


    private void UserValidation()
    {
        String url = ApplicationConstants.APP_SERVER_URL + "/api/User/IsValid";
        String reqBody = "{\"Mobile\":\""+ strUser_Mobile+"\",\"Email\":\""+ strUser_Login +"\",\"Password\":\""+ strUser_Password + "\",\"RegistrationID\":\"\"}";

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
                                user.MOB_NUMBER = userData.getString("MobileNo");
                                user.UserID = userData.getString("UserID");
                                user.E_MAIL = userData.getString("EmailId");
                                user.Gender = userData.getString("Gender");
                                user.ParentName = userData.getString("Parentname");
                                user.password = strUser_Password;
                                user.LOCATION = userData.getString("Address");
                                JSONArray flatArray =  response.getJSONArray("SocietyUser");
                                int x = flatArray.length();
                                if (x == 0) {
                                    Toast.makeText(getApplicationContext(),"No User Role Assigned", Toast.LENGTH_LONG);

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
                                        Intent mainInent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(mainInent);
                                        LoginActivity.this.finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(), "User is not Guard.", Toast.LENGTH_LONG).show();

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

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Contact Admin..",Toast.LENGTH_LONG).show();

                }
            });

            RetryPolicy rPolicy = new DefaultRetryPolicy(0,-1,0);
            jsObjRequest.setRetryPolicy(rPolicy);
            queue.add(jsObjRequest);
            //-------------------------------------------------------------------------------------------------
        }
        catch (JSONException jex)
        {
            Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_LONG).show();
        }

    }



}
