package com.example.dell.sphinx_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Dell on 15-01-2018.
 */

public class StudListAdapter extends BaseAdapter
{
    private Context context;
    private List<StudentManager> list;
    private int layout;
    private List<AttendanceManager> attendList;

    public StudListAdapter(Context context,int layout,List<StudentManager> list,List<AttendanceManager> attendList)
    {
        this.context = context;
        this.layout = layout;
        this.list = list;
        this.attendList = attendList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View arg1, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View root= inflater.inflate(this.layout, parent,false);
        TextView tv1 = (TextView) root.findViewById(R.id.roll);
        TextView tv2 = (TextView) root.findViewById(R.id.studname);


        RadioGroup rg = (RadioGroup) root.findViewById(R.id.rg);
        tv1.setText(list.get(position).getRollno());
        tv2.setText(list.get(position).getSname());



        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group,int checkedId) {

                switch(checkedId)
                {
                    case R.id.p : attendList.get(position).setAtt_status("P");break;
                    case R.id.a : attendList.get(position).setAtt_status("A");break;
                    case R.id.l : attendList.get(position).setAtt_status("L");break;
                }
            }
        });

        RadioButton p= (RadioButton) root.findViewById(R.id.p);
        RadioButton a= (RadioButton) root.findViewById(R.id.a);
        RadioButton l= (RadioButton) root.findViewById(R.id.l);

        if(attendList.get(position).getAtt_status().equals("P"))
        {
            p.setChecked(true);
        }
        else
        {
            if(attendList.get(position).getAtt_status().equals("A"))
            {
                a.setChecked(true);
            }
            else
            {
                if(attendList.get(position).getAtt_status().equals("L"))
                {
                    l.setChecked(true);
                }
            }

        }

        return root;
    }
}
