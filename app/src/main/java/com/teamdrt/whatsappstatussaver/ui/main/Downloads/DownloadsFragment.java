package com.teamdrt.whatsappstatussaver.ui.main.Downloads;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamdrt.whatsappstatussaver.R;
import com.teamdrt.whatsappstatussaver.ui.main.Databases.Download;

import java.util.ArrayList;
import java.util.List;

public class DownloadsFragment extends Fragment {

    private DownloadsViewModel mViewModel;
    private TextView nod;
    private List <Download> downloads;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView download_list;
    private DownloadsAdapter adapter;

    public static DownloadsFragment newInstance() {
        return new DownloadsFragment ();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.downloads_fragment, container, false);
        download_list=view.findViewById ( R.id.download_list );
        download_list.setLayoutManager ( new LinearLayoutManager ( getContext () ) );
        downloads=new ArrayList <> ();
        adapter=new DownloadsAdapter(downloads);
        adapter.setHasStableIds ( true );
        download_list.setAdapter(adapter);
        nod=view.findViewById(R.id.textView5);

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        init ();
        super.onActivityCreated ( savedInstanceState );

    }

    public void init(){
        DownloadsViewModel vm= new ViewModelProvider (DownloadsFragment.this).get ( DownloadsViewModel.class );
        vm.getAllDownloads ( getContext () );
        vm.allDownloads.observe ( getViewLifecycleOwner (), downloads -> {
            if (downloads.size ()!=0){
                nod.setVisibility ( View.GONE );
                download_list.setVisibility ( View.VISIBLE );
            }else {
                nod.setVisibility ( View.VISIBLE );
                download_list.setVisibility ( View.GONE);
            }
            adapter.addItems ( downloads );
        } );
    }

}