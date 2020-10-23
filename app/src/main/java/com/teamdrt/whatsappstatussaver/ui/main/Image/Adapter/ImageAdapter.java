package com.teamdrt.whatsappstatussaver.ui.main.Image.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.teamdrt.whatsappstatussaver.R;
import com.teamdrt.whatsappstatussaver.ui.main.Image.ViewHolder.ImageFragmentViewHolder;
import com.teamdrt.whatsappstatussaver.ui.main.Video.Adapter.VideoAdapter;
import com.teamdrt.whatsappstatussaver.ui.main.Video.ViewHolder.VideoFragmentViewHolder;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageFragmentViewHolder>{
    Context context;
    ArrayList <String> path;
    ImageAdapter.clicklistener clicklistener;

    public ImageAdapter(Context context, ArrayList <String> path, ImageAdapter.clicklistener clicklistener) {
        this.context = context;
        this.path = path;
        this.clicklistener = clicklistener;
    }

    @NonNull
    @Override
    public ImageFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from ( context ).inflate ( R.layout.video_fragment_single_layout,parent,false );
        return new ImageFragmentViewHolder ( view,clicklistener );
    }

    @Override
    public void onBindViewHolder(@NonNull ImageFragmentViewHolder holder, int position) {
        if (path.get ( position )!=null) {
            holder.imageView.setImageResource ( 0 );
            Glide.with ( context ).load ( path.get ( position ) ).thumbnail ( 0.1f ).centerCrop ().into ( holder.imageView );
        }else {
            Glide.with(context).clear ( holder.imageView );
            holder.imageView.setImageDrawable ( null );
        }
    }

    @Override
    public int getItemCount() {
        return path.size ();
    }

    public interface clicklistener{
        void OnDownloadClick(int position);
        void ButtonOnClick(int position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
