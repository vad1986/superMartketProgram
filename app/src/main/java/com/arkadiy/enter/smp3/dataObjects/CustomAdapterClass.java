package com.arkadiy.enter.smp3.dataObjects;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arkadiy.enter.smp3.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomAdapterClass extends ArrayAdapter<Alert> {

    private List<String>userName;
    private List<Integer>id;
    private List<Alert>alerts;
    private Intent img;
    private Activity context;


    public CustomAdapterClass(Activity context, List<Alert>alert) {
        super(context, R.layout.customlayout,alert);
        this.context = context;
        this.userName = userName;
        this.id = id;
        this.alerts = alert;
        this.img = null;

    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder = null;
        if (r == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.customlayout,null,true);
            viewHolder =new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) r.getTag();
        }
        viewHolder.imageView.setImageResource(R.mipmap.ic_launcher_round);
        viewHolder.userNameTextView.setText(alerts.get(position).getName());
        viewHolder.idTextView.setText(alerts.get(position).getDescription());

        return r;
    }

    class ViewHolder{

        TextView userNameTextView;
        TextView idTextView;
        ImageView imageView;
        ViewHolder(View v){
            userNameTextView = (TextView) v.findViewById(R.id.onlineUserName_textView);
            idTextView = (TextView) v.findViewById(R.id.onlineStatusUserName_textView);
            imageView =  (ImageView) v.findViewById(R.id.imageView);


        }

    }

}
