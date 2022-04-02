package com.anvisys.nestinguard;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.anvisys.nestinguard.Common.Employee;
import com.anvisys.nestinguard.Common.EmployeeAdapter;
import com.anvisys.nestinguard.Common.Profile;

import java.util.ArrayList;

public class EmployActivity extends AppCompatActivity {

    public static ArrayList<Employee> employeeList = new ArrayList<Employee>();
    ProgressBar prgBar;
    ListView guestListView;
    Profile myProfile;

   int PageNumber = 1;
    int Count =10;
    private ListView listView;

    private String selectedFilter = "all";
    private String currentSearchText = "";
    private SearchView searchView;
//    TextView txtMessage;
//    Society society;
    //for the search bar
    SearchView mySearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employ);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Employee List");
        actionBar.show();

      //  society = Session.GetSociety(getApplicationContext());
      //  txtMessage = findViewById(R.id.txtMessage);

        prgBar=findViewById(R.id.progBar);
        prgBar.setVisibility(View.GONE);

        initSearchWidgets();
        setupData();
        setUpList();
        setUpOnclickListener();
//        guestListView = findViewById(R.id.guestListView);
//        adapter =new EmployActivity.MyAdapter(EmployActivity.this,0,guestList);
//        guestListView.setAdapter(adapter);
//        //Search View
//        guestListView.setTextFilterEnabled(true);
//        GetGuestData();
//
////        //for Search View
//        mySearchView=(SearchView)findViewById(R.id.search_view);
//        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
////                // adapter.getFilter().filter(query);
//                EmployActivity.this.adapter.getFilter().filter(s);
////                 processerch(s);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                adapter.getFilter().filter(s);
//
//                return true;
//            }
//        });
    }



 //   private void GetGuestData()
//    {
//        txtMessage.setVisibility(View.GONE);
//        prgBar.setVisibility(View.VISIBLE);
//        String url = ApplicationConstants.APP_SERVER_URL +  "/api/visitor/Soc/" +society.SocietyId + "/" + PageNumber + "/" + Count;
//        //-------------------------------------------------------------------------------------------------
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//        JsonArrayRequest jsArrayRequest = new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray jResult) {
//
//                try{
//
//                    int GetCount = jResult.length();
//                    if(GetCount>0)
//                    {
//                        guestList.clear();
//
//                        Visitor guest;
//                        for (int i = 0; i < GetCount; i++) {
//                            JSONObject jObj = jResult.getJSONObject(i);
//                            guest = new Visitor();
//                            guest.VisitorName = jObj.getString("VisitorName");
//                            guest.VisitorMobile = jObj.getString("VisitorMobile");
//                            guest.VisitorAddress = jObj.getString("VisitorAddress");
//                            guest.VisitPurpose = jObj.getString("VisitPurpose");
//                            guest.FlatNumber = jObj.getString("Flat");
//                            guest.ActualInTime = jObj.getString("ActualInTime");
//                            guest.ExpectedEndTime = jObj.getString("EndTime");
//                            guestList.add(guest);
//                        }
//
//                        adapter.notifyDataSetChanged();
//                        prgBar.setVisibility(View.GONE);
//                    }
//                    else
//                    {
//                        if(guestList.size()==0)
//                        {
//                            txtMessage.setVisibility(View.VISIBLE);
//                            txtMessage.setText("No Data found!");
//                        }
//                    }
//                }
//                catch (JSONException e)
//                {
//                    if(guestList.size()==0)
//                    {
//                        txtMessage.setVisibility(View.VISIBLE);
//                        txtMessage.setText("Error Reading Data!");
//                    }
//                    prgBar.setVisibility(View.GONE);
//                }
//                catch (Exception ex){
//                    if(guestList.size()==0)
//                    {
//                        txtMessage.setVisibility(View.VISIBLE);
//                        txtMessage.setText("Error Reading Data!");
//                    }
//                    prgBar.setVisibility(View.GONE);
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                if(guestList.size()==0)
//                {
//                    txtMessage.setVisibility(View.VISIBLE);
//                    txtMessage.setText("Server Error!");
//                }
//                prgBar.setVisibility(View.GONE);
//            }
//        });
//
//        RetryPolicy rPolicy = new DefaultRetryPolicy(0,-1,0);
//        jsArrayRequest.setRetryPolicy(rPolicy);
//        queue.add(jsArrayRequest);
//
//        //*******************************************************************************************************
//
//    }

//    private class ViewHolder
//    {
//        TextView txtName,txtMobile,txtAddress,txtFlat,txtActualInTime,txtStatus;
//        View statusBar;
//        ImageView employeeimg;
//
//    }

//    class MyAdapter extends ArrayAdapter<Visitor>  {
//        LayoutInflater inflat;
//       EmployActivity.ViewHolder holder;
//
//        public MyAdapter(@NonNull Context context, int resource, ArrayList<Visitor> objects) {
//            super(context, resource,objects);
//            inflat= LayoutInflater.from(context);
//        }
//
//        @Override
//        public int getCount() {
//            return guestList.size();
//        }
//
//        @Nullable
//        @Override
//        public Visitor getItem(int position) {
//            return guestList.get(position);
//        }
//
//        @NonNull
//        @Override
//        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//            try {
//
//                if (convertView == null) {
//                    convertView = inflat.inflate(R.layout.row_item_empoly, null);
//                    holder = new EmployActivity.ViewHolder();
//                    holder.txtName = convertView.findViewById(R.id.txtName);
//                    holder.txtMobile = convertView.findViewById(R.id.txtMobile);
//                    holder.txtAddress = convertView.findViewById(R.id.txtAddress);
//                    holder.txtFlat = convertView.findViewById(R.id.txtFlat);
//                    holder.txtActualInTime = convertView.findViewById(R.id.txtActualInTime);
//                    holder.txtStatus = convertView.findViewById(R.id.txtStatus);
//                    holder.statusBar = convertView.findViewById(R.id.statusBar);
//                    //for Employee Image
//                    holder.employeeimg=convertView.findViewById(R.id.employ_img);
//                    convertView.setTag(holder);
//                }
//                holder = (EmployActivity.ViewHolder) convertView.getTag();
//                Visitor row = getItem(position);
//
//                holder.txtName.setText(row.VisitorName );
//                holder.txtMobile.setText(row.VisitorMobile);
//                holder.txtAddress.setText(row.VisitorAddress );
//                holder.txtFlat.setText("Flat: "+row.FlatNumber);
//                String status="";
//
//
//                Date InTime = Utility.DBStringToLocalDate(row.ActualInTime);
//                Date ExpectedDateTime = Utility.DBStringToLocalDate(row.ExpectedEndTime);
//                Date ActualInDateTime = Utility.DBStringToLocalDate(row.ActualInTime);
//
//                Date today = new Date();
//                boolean expired = today.after(ExpectedDateTime);
//
//
//                if((Utility.GetYearOnly(ActualInDateTime)<2000) &&  expired)
//                {
//                    status = "Expired";
//                    holder.txtStatus.setText(status);
//                    holder.txtStatus.setBackgroundResource(R.drawable.background_red);
//                    holder.txtActualInTime.setText("Last Time: " +Utility.DateToDisplayDateTime(ExpectedDateTime));
//                    // holder.statusBar.setBackgroundColor(Color.rgb(209,69,69));
//                }
//                else if((Utility.GetYearOnly(ActualInDateTime)<2000) &&  !expired)
//                {
//                    status = "Pending";
//                    holder.txtStatus.setText(status);
//                    holder.txtStatus.setBackgroundResource(R.drawable.background_yellow);
//                    holder.txtActualInTime.setText("Expected Before: " + Utility.DateToDisplayDateTime(ExpectedDateTime));
//                    // holder.statusBar.setBackgroundColor(Color.rgb(209,69,69));
//                }
//                else if (Utility.GetYearOnly(ActualInDateTime)>2018)
//                {
//                    status = "Done";
//                    holder.txtStatus.setText(status);
//                    holder.txtStatus.setBackgroundResource(R.drawable.background_green);
//                    holder.txtActualInTime.setText("In Time: "+ Utility.DateToDisplayDateTime(InTime));
//                    //  holder.statusBar.setBackgroundColor(Color.rgb(0,127,58));
//                }
//                return convertView;
//            }
//            catch (Exception ex)
//            {
//                Toast.makeText(getApplicationContext(),"Could not Load Guest Data", Toast.LENGTH_LONG).show();
//                return null;
//            }
//        }
//
//    }

    private void initSearchWidgets()
    {
        searchView = (SearchView) findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                currentSearchText = s;
                ArrayList<Employee> filteredShapes = new ArrayList<Employee>();

                for(Employee employee: employeeList)
                {
                    if(employee.getName().toLowerCase().contains(s.toLowerCase()))
                    {
                        if(selectedFilter.equals("all"))
                        {
                            filteredShapes.add(employee);
                        }
                        else
                        {
                            if(employee.getName().toLowerCase().contains(selectedFilter))
                            {
                                filteredShapes.add(employee);
                            }
                        }
                    }
                }
               EmployeeAdapter adapter = new EmployeeAdapter(getApplicationContext(), 0, filteredShapes);
                listView.setAdapter(adapter);

                return false;
            }
        });
    }
    private void setUpList()
    {
        listView = (ListView) findViewById(R.id.guestListView);

      EmployeeAdapter adapter = new EmployeeAdapter(getApplicationContext(), 0, employeeList);
        listView.setAdapter(adapter);
    }

    private void setupData()
    {
       Employee employee_name0 = new Employee("0", "Payal", R.drawable.profile);
        employeeList.add(employee_name0);

        Employee employee_name1 = new Employee("1","Hariom", R.drawable.profile);
        employeeList.add(employee_name1);

        Employee employee_name2 = new Employee("2","Raja", R.drawable.profile);
        employeeList.add(employee_name2);

       Employee employee_name3= new Employee("3","Sanu", R.drawable.profile);
        employeeList.add(employee_name3);

        Employee employee_name4 = new Employee("4","Sanskriti", R.drawable.profile);
        employeeList.add(employee_name4);

        Employee employee_name5 = new Employee("5", "Retesh", R.drawable.profile);
        employeeList.add(employee_name5);

        Employee employee_name6 = new Employee("6","Shipra", R.drawable.profile);
        employeeList.add(employee_name6);

        Employee employee_name7 = new Employee("7","Anchal noob  ", R.drawable.profile);
        employeeList.add(employee_name7);

        Employee employee_name8 = new Employee("8","Jakey", R.drawable.profile);
        employeeList.add(employee_name8);

       Employee employee_name9 = new Employee("9","Mohana", R.drawable.profile);
        employeeList.add(employee_name9);
    }

    private void setUpOnclickListener()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Employee selectShape = (Employee) (listView.getItemAtPosition(position));
                Intent showDetail = new Intent(getApplicationContext(), VisitorActivity.class);
                showDetail.putExtra("id",selectShape.getId());
                startActivity(showDetail);
            }
        });

    }

}