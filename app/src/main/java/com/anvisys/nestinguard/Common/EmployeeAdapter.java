package com.anvisys.nestinguard.Common;

import static com.anvisys.nestinguard.EmployActivity.employeeList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anvisys.nestinguard.R;

import java.util.List;

public class EmployeeAdapter extends ArrayAdapter<Employee>
{
    boolean chek_statusofuser =true;
    public EmployeeAdapter(Context context, int resource, List<Employee> employeesList)
    {
        super(context,resource,employeesList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final Employee employee = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_item_empoly, parent, false);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.txtName);
        TextView mobile_no=(TextView) convertView.findViewById(R.id.txtMobile);
        ImageView iv = (ImageView) convertView.findViewById(R.id.employ_img);
        TextView address=(TextView) convertView.findViewById(R.id.txtAddress);
        TextView StartTime=(TextView)convertView.findViewById(R.id.txtActualInTime);
        TextView employee_id=convertView.findViewById(R.id.txtStatus);


        final Button chekInButton=(Button)convertView.findViewById(R.id.btnCheckInn);
//for view in row
        tv.setText(employee.getName());
        mobile_no.setText(employee.getMobile_no());
        iv.setImageResource(employee.getImage());
        address.setText(employee.getAdd());
        StartTime.setText(employee.getStartTime());
        employee_id.setText(employee.getId());
        final Boolean[] status = {employee.getChekStatus()};
        chekInButton.findViewById(R.id.btnCheckInn);

        if(status[0] ==true)
        {
            chekInButton.setText("chek out");


    }else
        {

            chekInButton.setText("chek in");

        }

        chekInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status[0] == true)
                {
//                Toast.makeText(getContext().getApplicationContext(), "Employee  Visited ",
//                        Toast.LENGTH_SHORT).show();
                chekInButton.setText("chek in");


               status[0] =false;
               employee.setChekStatus(false);
                }else
                {
//                    Toast.makeText(getContext().getApplicationContext(), "Employee  Chek out ",
//                            Toast.LENGTH_SHORT).show();
                    chekInButton.setText("chek out");


                    status[0] =true;
                    chek_statusofuser=true;
                    employee.setChekStatus(true);

                }

            }
        });
        return convertView;


    }


}
