package com.teamdrt.whatsappstatussaver.ui.main.Downloads;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.teamdrt.whatsappstatussaver.BuildConfig;
import com.teamdrt.whatsappstatussaver.R;
import com.teamdrt.whatsappstatussaver.ui.main.Databases.AppDatabse;
import com.teamdrt.whatsappstatussaver.ui.main.Databases.Download;
import com.teamdrt.whatsappstatussaver.ui.main.Databases.DownloadsDao;
import com.teamdrt.whatsappstatussaver.ui.main.Databases.DownloadsRepository;
import com.teamdrt.whatsappstatussaver.ui.main.Image.DiffUtils.ImageDiffUtil;
import com.teamdrt.whatsappstatussaver.ui.main.Util.GetTimeAgo;

import java.io.File;
import java.util.List;

public class DownloadsAdapter extends RecyclerView.Adapter<DownloadsVh> {

    private List <Download> alldownloads;
    ViewGroup parent;
    Context context;


    public DownloadsAdapter(List <Download> alldownloads) {
        this.alldownloads = alldownloads;
    }

    public void addItems(List <Download> alldownloads){
        this.alldownloads = alldownloads;
        notifyDataSetChanged ();
    }

    @NonNull
    @Override
    public DownloadsVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent=parent;
        context = parent.getContext ();
        View view =LayoutInflater.from(parent.getContext()).inflate( R.layout.download_single_layout, parent, false);
        return new DownloadsVh (view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DownloadsVh holder, int position) {
        File file=new File(alldownloads.get ( position ).getDownoadedPath ());
        if (file.exists ()) {
            Glide.with(context).load ( alldownloads.get ( position ).getDownoadedPath () ).centerCrop ().into ( holder.vthumb );
            String date1= GetTimeAgo.getTimeAgo ( alldownloads.get ( position ).getTimestamp (),context);
            holder.date.setText ( date1 );
            if (alldownloads.get ( position ).getMediaType ().equals ( "image" ))
            {
                holder.play.setImageResource ( R.drawable.ic_baseline_image_24 );
            }else {
                holder.play.setImageResource ( R.drawable.ic_baseline_play_circle_filled_24 );
            }
            holder.play.setOnClickListener ( v -> holder.viewContent ( alldownloads.get ( position ).getDownoadedPath (), v.getContext () ) );
            holder.share.setOnClickListener ( v -> {
                        if (alldownloads.get ( position ).getMediaType ().equals ( "video" )) {
                            context.startActivity (
                                    Intent.createChooser (
                                            new Intent ().setAction ( Intent.ACTION_SEND )
                                                    .setType ( "video/*" )
                                                    .setFlags ( Intent.FLAG_GRANT_READ_URI_PERMISSION )
                                                    .putExtra (
                                                            Intent.EXTRA_STREAM,
                                                            FileProvider.getUriForFile ( context, BuildConfig.APPLICATION_ID + ".FileProvider", new File ( alldownloads.get ( position ).getDownoadedPath () ) )
                                                    ), "Share Video..."
                                    )

                            );
                        } else {
                            context.startActivity (
                                    Intent.createChooser (
                                            new Intent ().setAction ( Intent.ACTION_SEND )
                                                    .setType ( "image/*" )
                                                    .setFlags ( Intent.FLAG_GRANT_READ_URI_PERMISSION )
                                                    .putExtra (
                                                            Intent.EXTRA_STREAM,
                                                            FileProvider.getUriForFile ( context, BuildConfig.APPLICATION_ID + ".FileProvider", new File ( alldownloads.get ( position ).getDownoadedPath () ) )
                                                    ), "Share Video..."
                                    )

                            );
                        }
                    });
            holder.delete.setOnClickListener ( v -> {
                File file1 = new File(alldownloads.get ( position ).getDownoadedPath ());
                boolean done=file1.delete ();
                if (done){
                    Toast.makeText ( context, "Deleted", Toast.LENGTH_SHORT ).show ();
                    DownloadsDao downloadsDao= AppDatabse.getInstance (context).downloadsDao ();
                    DownloadsRepository repository=new DownloadsRepository(downloadsDao);
                    repository.delete ( alldownloads.get ( position ) );
                }
            } );
        }else {
            DownloadsDao downloadsDao= AppDatabse.getInstance (context).downloadsDao ();
            DownloadsRepository repository=new DownloadsRepository(downloadsDao);
            repository.delete ( alldownloads.get ( position ) );
        }


    }


    @Override
    public void onBindViewHolder(@NonNull DownloadsVh holder, int position, @NonNull List <Object> payloads) {
        if (payloads.isEmpty ()) {
            super.onBindViewHolder ( holder, position, payloads );
        }else {
            Bundle o=(Bundle)payloads.get (0);
            for (String key:o.keySet ()){
                switch (key){
                    case "path":
                        File file=new File(o.getString("path"));
                        if (file.exists ()) {
                            Glide.with(context).load ( o.getString("path") ).centerCrop ().into ( holder.vthumb );
                        }else {
                            DownloadsDao downloadsDao= AppDatabse.getInstance (context).downloadsDao ();
                            DownloadsRepository repository=new DownloadsRepository(downloadsDao);
                            repository.delete ( alldownloads.get ( position ) );
                        }

                    case "time":
                        String date1= GetTimeAgo.getTimeAgo ( o.getLong(key),context);
                        holder.date.setText ( date1 );

                    case  "media":
                        if (o.getString(key).equals ( "image" ))
                        {
                            holder.play.setImageResource ( R.drawable.ic_baseline_image_24 );
                        }else {
                            holder.play.setImageResource ( R.drawable.ic_baseline_play_circle_filled_24 );
                        }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return alldownloads.size ();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void update(List <Download> alldownloads){
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DownloadsDiffUtil (this.alldownloads,alldownloads));
        this.alldownloads.clear ();
        this.alldownloads.addAll(alldownloads);
        notifyDataSetChanged ();
        diffResult.dispatchUpdatesTo ( this );
    }

}
