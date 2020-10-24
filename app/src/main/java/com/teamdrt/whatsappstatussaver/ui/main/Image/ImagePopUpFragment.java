package com.teamdrt.whatsappstatussaver.ui.main.Image;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.teamdrt.whatsappstatussaver.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImagePopUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImagePopUpFragment extends DialogFragment {
    private static final String ARG_PARAM1 = "param1";
    private String mParam1;

    private ImageView imageView;

    public ImagePopUpFragment() {

    }


    public static ImagePopUpFragment newInstance(String param1) {
        ImagePopUpFragment fragment = new ImagePopUpFragment ();
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
        View view = inflater.inflate(R.layout.fragment_image_pop_up, container, false);
        imageView=view.findViewById ( R.id.imageView4 );
        Glide.with ( getContext () ).load ( mParam1 ).into(imageView);
        return view;
    }
}