package com.anvisys.nestinguard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.anvisys.nestinguard.Common.Session;
import com.anvisys.nestinguard.Common.ApplicationConstants;
import com.anvisys.nestinguard.Common.Society;
import com.anvisys.nestinguard.Common.Utility;
import com.anvisys.nestinguard.Common.Visitor;
//import com.example.nestinguard.R;

import org.json.JSONObject;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private EditText inputcode1,inputcode2,inputcode3,inputcode4;
    TextView txtValidate,txtVisitorName,txtVisitorMobile,txtAddress,txtPurpose,txtStartTime,txtEndTime,txtHostName,txtHostMobile;
    TextView txtFlatNumber,txtFlatIntercom,txtMessage,txtMessagechek;
    EditText txtOTP;
    ProgressBar progressBar;
    Society society;
    View viewVisitor;
    Button btnCheckIn;
    Visitor visitor;
    String OTP;
    Button VerifyButton;
    //for Draw navigation


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle("Visitor Verification");
        actionBar.show();
        txtValidate = findViewById(R.id.txtValidate);
        //for otp
        VerifyButton=findViewById(R.id.verify_btn);

        txtOTP = findViewById(R.id.txtOTP);
        society = Session.GetSociety(getApplicationContext());
        viewVisitor = findViewById(R.id.viewVisitor);
        txtMessagechek=findViewById(R.id.txtMessagechek_in);
        txtVisitorName = findViewById(R.id.txtVisitorName);
        txtVisitorMobile = findViewById(R.id.txtVisitorMobile);
        txtAddress = findViewById(R.id.txtAddress);
        txtPurpose = findViewById(R.id.txtPurpose);
        txtStartTime = findViewById(R.id.txtStartTime);
        txtEndTime = findViewById(R.id.txtEndTime);
        txtHostName = findViewById(R.id.txtHostName);
        txtHostMobile = findViewById(R.id.txtHostMobile);
        txtFlatNumber = findViewById(R.id.txtFlatNumber);
        txtFlatIntercom = findViewById(R.id.txtFlatIntercom);
        btnCheckIn = findViewById(R.id.btnCheckIn);


        txtMessage = findViewById(R.id.txtMessage);
        progressBar = findViewById(R.id.progressBar);
        //for otp
        inputcode1=findViewById(R.id.inputcode1);
        inputcode2=findViewById(R.id.inputcode2);
        inputcode3=findViewById(R.id.inputcode3);
        inputcode4=findViewById(R.id.inputcode4);
        setOtpInputs();
        txtValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateOTP();
            }
        });

        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckInVisitor();
            }
        });
        VerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateOTP();

            }
        });
    }





   private void ValidateOTP()
    {
        txtMessagechek.setVisibility(View.GONE);

        OTP =inputcode1.getText().toString()+
                inputcode2.getText().toString()+
                inputcode3.getText().toString()+
                inputcode4.getText().toString();
        progressBar.setVisibility(View.VISIBLE);


        String url = ApplicationConstants.APP_SERVER_URL+ "/api/Visitor/Code";

        try{
            String reqBody = "{\"VisitorCode\":\""+ OTP +"\", \"SocietyID\":\""+ society.SocietyId +"\"}";

            JSONObject jsRequest = new JSONObject(reqBody);
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest jsArrayRequest = new JsonObjectRequest(Request.Method.POST, url, jsRequest, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jObj) {
                    progressBar.setVisibility(View.GONE);
                    try {
                      Integer visitorID =  jObj.getInt("VisitorId");

                      if(visitorID!=0)
                      {
                          viewVisitor.setVisibility(View.VISIBLE);
                          txtMessage.setVisibility(View.GONE);
                          btnCheckIn.setVisibility(View.VISIBLE);
                          visitor = new Visitor();
                          visitor.RequestId = jObj.getInt("RequestId");
                          visitor.VisitorName = jObj.getString("VisitorName");
                          visitor.VisitorMobile = jObj.getString("VisitorMobile");
                          visitor.VisitorAddress = jObj.getString("VisitorAddress");
                          visitor.VisitPurpose = jObj.getString("VisitPurpose");
                          visitor.FlatNumber = jObj.getString("Flat");
                          visitor.VisitPurpose = jObj.getString("VisitPurpose");
                          visitor.StartTime = jObj.getString("StartTime");
                          visitor.EndTime = jObj.getString("EndTime");
                          visitor.ActualInTime = jObj.getString("ActualInTime");
                          visitor.ExpectedEndTime = jObj.getString("EndTime");
                          visitor.FirstName = jObj.getString("FirstName");
                          visitor.LastName = jObj.getString("LastName");
                          visitor.ResidentMobile = jObj.getString("ResidentMobile");
                          visitor.Type = jObj.getString("Type");

                          Date StartTime = Utility.DBStringToLocalDate(visitor.StartTime);
                          Date EndTime = Utility.DBStringToLocalDate(visitor.EndTime);

                          txtVisitorName.setText(visitor.VisitorName);
                          txtVisitorMobile.setText(visitor.VisitorMobile);
                          txtAddress.setText(visitor.VisitorAddress);
                          txtPurpose.setText(visitor.VisitPurpose);

                          txtStartTime.setText(Utility.DateToDisplayDateTime(StartTime) );
                          txtEndTime.setText(Utility.DateToDisplayDateTime(EndTime));

                          txtHostName.setText(visitor.FirstName + " " + visitor.LastName);
                          txtHostMobile.setText(visitor.ResidentMobile);
                          txtFlatNumber.setText(visitor.FlatNumber);
                          txtFlatIntercom.setText(visitor.ResidentMobile);
                      }
                      else{
                          viewVisitor.setVisibility(View.GONE);
                          txtMessage.setVisibility(View.VISIBLE);
                          txtMessage.setText("Invalid OTP!");
                      }

                    }catch (Exception e){
                        viewVisitor.setVisibility(View.GONE);
                        txtMessage.setVisibility(View.VISIBLE);
                        txtMessage.setText("Error Reading Data!");
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);
                    viewVisitor.setVisibility(View.GONE);
                    txtMessage.setVisibility(View.VISIBLE);

                    txtMessage.setText("Server Error!");
                }
            });
            RetryPolicy rPolicy = new DefaultRetryPolicy(0,-1,0);
            jsArrayRequest.setRetryPolicy(rPolicy);
            queue.add(jsArrayRequest);

        }catch (Exception ex){
            progressBar.setVisibility(View.GONE);
            viewVisitor.setVisibility(View.GONE);
            txtMessage.setVisibility(View.VISIBLE);
            txtMessage.setText("Server Error!");
        }
}




    private void CheckInVisitor(){

       progressBar.setVisibility(View.VISIBLE);


        String url = ApplicationConstants.APP_SERVER_URL+ "/api/Visitor/CheckIn";

        try{
            String reqBody = "{\"RequestId\":\""+ visitor.RequestId +"\", \"VisitorName\":\""+ visitor.VisitorName+"\", \"HostMobile\":\""+ visitor.ResidentMobile
                    +"\", \"VisitorMobile\":\""+ visitor.VisitorMobile +"\"}";

            JSONObject jsRequest = new JSONObject(reqBody);
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest jsArrayRequest = new JsonObjectRequest(Request.Method.POST, url, jsRequest, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jObj) {
                    try {
                        progressBar.setVisibility(View.GONE);
                        String response =  jObj.getString("Response");

                        if(response.equalsIgnoreCase("Ok"))
                        {
                            viewVisitor.setVisibility(View.GONE);
                            txtMessage.setVisibility(View.GONE);
                            txtOTP.setText("");
                            txtMessagechek.setVisibility(View.VISIBLE);


                            btnCheckIn.setVisibility(View.GONE);


                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Not able to Check In, contact admin", Toast.LENGTH_LONG);
                        }

                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Not able to read response, contact admin", Toast.LENGTH_LONG);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_LONG);

                }
            });
            RetryPolicy rPolicy = new DefaultRetryPolicy(0,-1,0);
            jsArrayRequest.setRetryPolicy(rPolicy);
            queue.add(jsArrayRequest);

        }catch (Exception ex){
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_LONG);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_visitor) {

            Intent profileIntent = new Intent(MainActivity.this, VisitorActivity.class);
            startActivity(profileIntent);
        }
        else if(id==R.id.action_employ){
            Intent employIntent=new Intent(MainActivity.this, EmployActivity.class);
            startActivity(employIntent);

        }

        else if (id == R.id.action_logoff) {

            AlertDialog.Builder builder= new AlertDialog.Builder(
                    MainActivity.this);
            builder.setTitle("Log Off");
            builder.setMessage("Are you sure");
            builder.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            dialog.cancel();
                            Log.e("info", "NO");
                        }

                    });

            builder.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {

                            Session.LogOff(getApplicationContext());
                            MainActivity.this.finish();
                        }
                    });


            AlertDialog Alert = builder.create();

            Alert.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //for otp input validation
    private void  setOtpInputs(){

        inputcode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputcode2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputcode3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputcode4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
}
}
