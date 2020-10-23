package com.teamdrt.whatsappstatussaver.ui.main.Video;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.teamdrt.whatsappstatussaver.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoPopUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoPopUpFragment extends DialogFragment{
    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private VideoView videoView;
    public VideoPopUpFragment() {

    }

    public static VideoPopUpFragment newInstance(String param1) {
        VideoPopUpFragment fragment = new VideoPopUpFragment ();
        Bundle args = new Bundle ();
        args.putString ( ARG_PARAM1, param1 );
        fragment.setArguments ( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        if (getArguments () != null) {
            mParam1 = getArguments ().getString ( ARG_PARAM1 );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_pop_up, container, false);
        videoView=view.findViewById ( R.id.videoView );
        videoView.setVideoPath ( mParam1 );
        videoView.start ();
        videoView.setOnCompletionListener ( mp -> {
            getDialog ().dismiss ();
            //VideoFragmentViewHolder.imageView.setClickable ( true );
        } );
        return view;
    }

}