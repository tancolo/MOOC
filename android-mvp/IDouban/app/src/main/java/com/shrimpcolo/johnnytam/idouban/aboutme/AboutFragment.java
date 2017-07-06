package com.shrimpcolo.johnnytam.idouban.aboutme;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shrimpcolo.johnnytam.idouban.R;
import com.shrimpcolo.johnnytam.idouban.utils.CircleTransformation;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    private static final String TAG = AboutFragment.class.getSimpleName();

    private ImageView profileImage;

    private TextView profileName;

    public AboutFragment() {
        // Required empty public constructor
    }

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_aboutme, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        profileImage = (ImageView)getActivity().findViewById(R.id.img_profile);
        profileName = (TextView)getActivity().findViewById(R.id.txt_author);

        int width = getResources().getDimensionPixelOffset(R.dimen.profile_avatar_border);
        int color = getResources().getColor(R.color.color_profile_photo_border);

        Picasso.with(getActivity())
                .load(R.mipmap.dayuhaitang)
                .transform(new CircleTransformation(width, color))
                .into(profileImage);

    }
}
