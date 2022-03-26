package com.anvisys.nestinguard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.anvisys.nestinguard.Common.Profile;
import com.anvisys.nestinguard.Common.Session;
import com.anvisys.nestinguard.Common.ApplicationConstants;
import com.anvisys.nestinguard.Common.Society;
import com.anvisys.nestinguard.Common.Utility;
import com.anvisys.nestinguard.Common.Visitor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class VisitorActivity extends AppCompatActivity {

    ArrayList<Visitor> guestList = new ArrayList<Visitor>();
    ProgressBar prgBar;
    MyAdapter adapter;
    ListView guestListView;
    Profile myProfile;

    int PageNumber = 1;
    int Count =10;

    TextView txtMessage;
    Society society;
    //for the search bar
    SearchView mySearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Visitor");
        actionBar.show();

        society = Session.GetSociety(getApplicationContext());

        txtMessage = findViewById(R.id.txtMessage);

        prgBar=findViewById(R.id.progBar);
        prgBar.setVisibility(View.GONE);


        guestListView = findViewById(R.id.guestListView);
        adapter =new MyAdapter(VisitorActivity.this,0,guestList);
        guestListView.setAdapter(adapter);
        GetGuestData();

        //for Search View
         mySearchView=(SearchView)findViewById(R.id.search_view);
         mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
             @Override
             public boolean onQueryTextSubmit(String s) {
//                // adapter.getFilter().filter(query);
//                 processerch(s);
                 return false;
             }

             @Override
             public boolean onQueryTextChange(String s) {
                 adapter.getFilter().filter(s.toString());

                 return false;
             }
         });
    }



    private void GetGuestData()
    {
        txtMessage.setVisibility(View.GONE);
        prgBar.setVisibility(View.VISIBLE);
        String url = ApplicationConstants.APP_SERVER_URL +  "/api/visitor/Soc/" +society.SocietyId + "/" + PageNumber + "/" + Count;
        //-------------------------------------------------------------------------------------------------
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsArrayRequest = new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jResult) {

                try{

                    int GetCount = jResult.length();
                    if(GetCount>0)
                    {
                        guestList.clear();

                        Visitor guest;
                        for (int i = 0; i < GetCount; i++) {
                            JSONObject jObj = jResult.getJSONObject(i);
                            guest = new Visitor();
                            guest.VisitorName = jObj.getString("VisitorName");
                            guest.VisitorMobile = jObj.getString("VisitorMobile");
                            guest.VisitorAddress = jObj.getString("VisitorAddress");
                            guest.VisitPurpose = jObj.getString("VisitPurpose");
                            guest.FlatNumber = jObj.getString("Flat");
                            guest.ActualInTime = jObj.getString("ActualInTime");
                            guest.ExpectedEndTime = jObj.getString("EndTime");
                            guestList.add(guest);
                        }

                        adapter.notifyDataSetChanged();
                        prgBar.setVisibility(View.GONE);
                    }
                    else
                    {
                        if(guestList.size()==0)
                        {
                            txtMessage.setVisibility(View.VISIBLE);
                            txtMessage.setText("No Data found!");
                        }
                    }
                }
                catch (JSONException e)
                {
                    if(guestList.size()==0)
                    {
                        txtMessage.setVisibility(View.VISIBLE);
                        txtMessage.setText("Error Reading Data!");
                    }
                    prgBar.setVisibility(View.GONE);
                }
                catch (Exception ex){
                    if(guestList.size()==0)
                    {
                        txtMessage.setVisibility(View.VISIBLE);
                        txtMessage.setText("Error Reading Data!");
                    }
                    prgBar.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(guestList.size()==0)
                {
                    txtMessage.setVisibility(View.VISIBLE);
                    txtMessage.setText("Server Error!");
                }
                prgBar.setVisibility(View.GONE);
            }
        });

        RetryPolicy rPolicy = new DefaultRetryPolicy(0,-1,0);
        jsArrayRequest.setRetryPolicy(rPolicy);
        queue.add(jsArrayRequest);

        //*******************************************************************************************************

    }

    private class ViewHolder
    {
        TextView txtName,txtMobile,txtAddress,txtFlat,txtActualInTime,txtStatus;
        View statusBar;

    }

    class MyAdapter extends ArrayAdapter<Visitor>  {
        LayoutInflater inflat;
        ViewHolder holder;

        public MyAdapter(@NonNull Context context, int resource, ArrayList<Visitor> objects) {
            super(context, resource,objects);
            inflat= LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return guestList.size();
        }

        @Nullable
        @Override
        public Visitor getItem(int position) {
            return guestList.get(position);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            try {

                if (convertView == null) {
                    convertView = inflat.inflate(R.layout.row_item_visitor, null);
                    holder = new ViewHolder();
                    holder.txtName = convertView.findViewById(R.id.txtName);
                    holder.txtMobile = convertView.findViewById(R.id.txtMobile);
                    holder.txtAddress = convertView.findViewById(R.id.txtAddress);
                    holder.txtFlat = convertView.findViewById(R.id.txtFlat);
                    holder.txtActualInTime = convertView.findViewById(R.id.txtActualInTime);
                    holder.txtStatus = convertView.findViewById(R.id.txtStatus);
                    holder.statusBar = convertView.findViewById(R.id.statusBar);
                    convertView.setTag(holder);
                }
                holder = (ViewHolder) convertView.getTag();
                Visitor row = getItem(position);

                holder.txtName.setText(row.VisitorName );
                holder.txtMobile.setText(row.VisitorMobile);
                holder.txtAddress.setText(row.VisitorAddress );
                holder.txtFlat.setText("Flat: "+row.FlatNumber);
                String status="";


                Date InTime = Utility.DBStringToLocalDate(row.ActualInTime);
                Date ExpectedDateTime = Utility.DBStringToLocalDate(row.ExpectedEndTime);
                Date ActualInDateTime = Utility.DBStringToLocalDate(row.ActualInTime);

                Date today = new Date();
                boolean expired = today.after(ExpectedDateTime);


                if((Utility.GetYearOnly(ActualInDateTime)<2000) &&  expired)
                {
                    status = "Expired";
                    holder.txtStatus.setText(status);
                    holder.txtStatus.setBackgroundResource(R.drawable.background_red);
                    holder.txtActualInTime.setText("Last Time: " +Utility.DateToDisplayDateTime(ExpectedDateTime));
                    // holder.statusBar.setBackgroundColor(Color.rgb(209,69,69));
                }
                else if((Utility.GetYearOnly(ActualInDateTime)<2000) &&  !expired)
                {
                    status = "Pending";
                    holder.txtStatus.setText(status);
                    holder.txtStatus.setBackgroundResource(R.drawable.background_yellow);
                    holder.txtActualInTime.setText("Expected Before: " + Utility.DateToDisplayDateTime(ExpectedDateTime));
                    // holder.statusBar.setBackgroundColor(Color.rgb(209,69,69));
                }
                else if (Utility.GetYearOnly(ActualInDateTime)>2018)
                {
                    status = "Done";
                    holder.txtStatus.setText(status);
                    holder.txtStatus.setBackgroundResource(R.drawable.background_green);
                    holder.txtActualInTime.setText("In Time: "+ Utility.DateToDisplayDateTime(InTime));
                    //  holder.statusBar.setBackgroundColor(Color.rgb(0,127,58));
                }
                return convertView;
            }
            catch (Exception ex)
            {
                Toast.makeText(getApplicationContext(),"Could not Load Guest Data", Toast.LENGTH_LONG).show();
                return null;
            }
        }

    }

}
