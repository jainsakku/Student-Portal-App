package com.example.dell.sphinx_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Dell on 15-01-2018.
 */

public class SubAdapter extends BaseAdapter
{
    private Context context;
    private List<SubjectManager> list;
    private int layout;

    public SubAdapter(Context context,int layout,List<SubjectManager> list)
    {
        this.context = context;
        this.layout = layout;
        this.list = list;
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
    public View getView(int position, View arg1, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root= inflater.inflate(this.layout, parent,false);
        TextView tv1 = (TextView) root.findViewById(R.id.cid);
        TextView tv2 = (TextView) root.findViewById(R.id.bid);
        TextView tv3 = (TextView) root.findViewById(R.id.sname);
        tv1.setText(list.get(position).getCourseid());
        tv2.setText(list.get(position).getBatchid());
        tv3.setText(list.get(position).getSubjectname());
        return root;
    }
}
