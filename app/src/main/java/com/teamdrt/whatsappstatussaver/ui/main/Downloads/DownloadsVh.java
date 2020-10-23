package com.teamdrt.whatsappstatussaver.ui.main.Downloads;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;


import com.teamdrt.whatsappstatussaver.BuildConfig;
import com.teamdrt.whatsappstatussaver.R;

import java.io.File;

public class DownloadsVh extends RecyclerView.ViewHolder {

    public CardView cv;
    public ImageView vthumb;
    public ImageView play,share,delete;
    public TextView date;
    public DownloadsVh(@NonNull View itemView) {
        super ( itemView );
        cv=itemView.findViewById ( R.id.cv );
        vthumb=itemView.findViewById(R.id.imageView2);
        play=itemView.findViewById ( R.id.play );
        share=itemView.findViewById ( R.id.share );
        delete=itemView.findViewById ( R.id.delete );
        date=itemView.findViewById ( R.id.date );
    }

    public void viewContent(String path, Context ctx){
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.addFlags ( Intent.FLAG_GRANT_READ_URI_PERMISSION );
        File file=new File(path);
            Uri uri = FileProvider.getUriForFile ( ctx, BuildConfig.APPLICATION_ID + ".FileProvider", file );
            String mimetype = ctx.getContentResolver ().getType ( uri );
            if (mimetype == null) {
                mimetype = "*/*";
            }
            intent.setDataAndType ( uri, mimetype );
            if (intent.resolveActivity ( ctx.getPackageManager () ) != null) {
                ctx.startActivity ( intent );
            } else {
                Toast.makeText ( ctx, "No app Found to handle this event", Toast.LENGTH_SHORT ).show ();
            }
    }

}
