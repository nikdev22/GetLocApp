package com.example.nik.getlocapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class ListRow extends BaseAdapter {

private Activity activity;
    private ArrayList<HashMap<String,String>> data;

    public ListRow(Activity a, ArrayList<HashMap<String,String>> d){
        activity = a;
        data = d;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ListNewsViewHolder holder = null;
        if(view == null){
            holder = new ListNewsViewHolder();
            view = LayoutInflater.from(activity).inflate(R.layout.list_row,viewGroup,false);
            holder.galleryImage = (ImageView) view.findViewById(R.id.galleryImage);
            holder.author = (TextView) view.findViewById(R.id.author);
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.sdetails = (TextView) view.findViewById(R.id.sdetails);
            holder.time = (TextView) view.findViewById(R.id.time);
            view.setTag(holder);
        }else{
            holder = (ListNewsViewHolder) view.getTag();
        }
        holder.galleryImage.setId(i);
        holder.author.setId(i);
        holder.title.setId(i);
        holder.sdetails.setId(i);
        holder.time.setId(i);

        HashMap<String,String> song = new HashMap<String, String>();
        song = data.get(i);
        try {
            holder.author.setText(song.get(NewsApp.KEY_AUTHOR));
            holder.title.setText(song.get(NewsApp.KEY_TITLE));
            holder.sdetails.setText(song.get(NewsApp.KEY_DESCRIPTION));
            holder.time.setText(song.get(NewsApp.KEY_PUBLISHEDAT));

            if (song.get(NewsApp.KEY_URL2IMAGE).toString().length() < 5) {
                holder.galleryImage.setVisibility(View.GONE);
            } else {
                Picasso.get()
                        .load(song.get(NewsApp.KEY_URL2IMAGE).toString())
                        .resize(300,200)
                        .into(holder.galleryImage);
            }
        }catch (Exception e){

        }
        return view;
    }
}

class ListNewsViewHolder{
    ImageView galleryImage;
    TextView author,title,sdetails,time;
}
