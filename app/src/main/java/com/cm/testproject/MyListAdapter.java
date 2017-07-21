package com.cm.testproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by charliema on 7/20/17.
 */

public class MyListAdapter extends ArrayAdapter<MyModel> {
    public MyListAdapter(Context context, List<MyModel> objects) {
        super(context, -1, objects);
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        ViewHolder viewHolder = null;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = convertView.findViewById(R.id.tvTitle);
            viewHolder.body = convertView.findViewById(R.id.tvBody);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MyModel item = getItem(position);
        viewHolder.title.setText(item.title);
        viewHolder.body.setText(item.body);

        return convertView;
    }

    private class ViewHolder{
        public TextView title;
        public TextView body;
    }
}
