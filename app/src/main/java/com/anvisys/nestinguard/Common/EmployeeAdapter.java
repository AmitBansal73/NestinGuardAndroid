package com.anvisys.nestinguard.Common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anvisys.nestinguard.R;

import java.util.List;

public class EmployeeAdapter extends ArrayAdapter<Employee>
{
    public EmployeeAdapter(Context context, int resource, List<Employee> employeesList)
    {
        super(context,resource,employeesList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Employee employee = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_item_empoly, parent, false);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.txtName);
        TextView mobile_no=(TextView) convertView.findViewById(R.id.txtMobile);
        ImageView iv = (ImageView) convertView.findViewById(R.id.employ_img);

        tv.setText(employee.getName());
        iv.setImageResource(employee.getImage());


        return convertView;
    }

}
