package com.app.mohamedgomaa.kids_pj.Anbyaa_Stories;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.mohamedgomaa.kids_pj.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed Gooma on 7/16/2017.
 */

public class RecyleAdapter_videos extends RecyclerView.Adapter<RecyleAdapter_videos.Holder> implements View.OnLongClickListener{
List<item_video> _myList=new ArrayList<>();
    Context _context;

    public RecyleAdapter_videos(List<item_video> _myList, Context _context) {
        this._myList = _myList;
        this._context = _context;
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity__regition__anbyaa_videos_item,parent,false);
        Holder holder=new Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        Picasso.with(_context).load(_myList.get(position).photo_path).error(R.drawable.file_wait).placeholder(R.drawable.file_wait).fit().into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(_context.getApplicationContext(),Activity_Regition_Anbyaa_videos_show.class);
                intent.putExtra("id_video",_myList.get(position).video_path);
                _context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return _myList.size();
    }

    @Override
    public boolean onLongClick(View v) {
        return true;
    }


    public static class Holder extends RecyclerView.ViewHolder {
        ImageView image;
        public Holder(View view) {
            super(view);
            image=(ImageView)view.findViewById(R.id.story_items);
        }
    }

}
