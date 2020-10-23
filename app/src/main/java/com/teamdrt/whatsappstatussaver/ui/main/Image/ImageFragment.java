package com.teamdrt.whatsappstatussaver.ui.main.Image;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.teamdrt.whatsappstatussaver.R;
import com.teamdrt.whatsappstatussaver.ui.main.Databases.AppDatabse;
import com.teamdrt.whatsappstatussaver.ui.main.Databases.Download;
import com.teamdrt.whatsappstatussaver.ui.main.Databases.DownloadsDao;
import com.teamdrt.whatsappstatussaver.ui.main.Databases.DownloadsRepository;
import com.teamdrt.whatsappstatussaver.ui.main.Image.Adapter.ImageAdapter;
import com.teamdrt.whatsappstatussaver.ui.main.Image.ViewModel.ImageVM;
import com.teamdrt.whatsappstatussaver.ui.main.Video.Adapter.VideoAdapter;
import com.teamdrt.whatsappstatussaver.ui.main.Video.ViewModel.VideoFragmnetVM;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ImageFragment extends Fragment implements ImageAdapter.clicklistener {

    private ImageVM mViewModel;

    RecyclerView videoViewlist;
    ImageAdapter adapter;
    ArrayList <String> path;

    public static ImageFragment newInstance() {
        return new ImageFragment ();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        path=new ArrayList <> ();
        View view = inflater.inflate(R.layout.image_fragment, container, false);
        videoViewlist=view.findViewById ( R.id.recyclerview2 );
        videoViewlist.setLayoutManager ( new GridLayoutManager ( getContext (),2 ));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated ( savedInstanceState );
        mViewModel = new ViewModelProvider ( this ).get ( ImageVM.class );
        mViewModel.getPath ();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated ( view, savedInstanceState );
        if (isStoragePermissionGranted ()) {
            mViewModel = new ViewModelProvider ( this ).get ( ImageVM.class );
            mViewModel.path.observe ( getViewLifecycleOwner (), strings -> {
                path=strings;
                adapter=new ImageAdapter ( getContext (),strings,this );
                adapter.setHasStableIds ( true );
                videoViewlist.setAdapter(adapter);
            } );
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getContext ().checkSelfPermission( Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(getActivity (), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            return true;
        }
    }

    private File getDownloadLocation(String name) {
        File downloadsDir = Environment.getExternalStorageDirectory ();
        File youtubeDLDir = new File(downloadsDir,File.separator+ "WhatsApp Status Saver/"+name);
        return youtubeDLDir;
    }

    @Override
    public void OnDownloadClick(int position) {

    }

    @Override
    public void ButtonOnClick(int position) {
        File file = new File(path.get ( position ));
        String filename = file.getName();
        File ofile=getDownloadLocation ( filename );
        if (!ofile.exists ()){
            try
            {
                FileUtils.copyFile ( file,ofile ,true);
                DownloadsDao downloadsDao= AppDatabse.getInstance (getContext ()).downloadsDao ();
                DownloadsRepository repository=new DownloadsRepository(downloadsDao);
                Download download=new Download(new Date ().getTime ());
                download.setDownoadedPath ( ofile.getAbsolutePath () );
                download.setMediaType ( "image" );
                repository.insert ( download );
                Toast.makeText ( getContext (), "Downloaded", Toast.LENGTH_SHORT ).show ();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }else {
            Context context=getContext ();
            CharSequence text="Already Saved";
            Toast toast = Toast.makeText(context, text,Toast.LENGTH_SHORT);
            toast.show ();
        }
    }
}