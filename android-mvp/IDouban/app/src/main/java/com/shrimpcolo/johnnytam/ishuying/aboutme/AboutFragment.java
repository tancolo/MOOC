package com.shrimpcolo.johnnytam.ishuying.aboutme;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shrimpcolo.johnnytam.ishuying.IShuYingApplication;
import com.shrimpcolo.johnnytam.ishuying.R;
import com.shrimpcolo.johnnytam.ishuying.beans.UserInfo;
import com.shrimpcolo.johnnytam.ishuying.login.LoginListener;
import com.shrimpcolo.johnnytam.ishuying.utils.CircleTransformation;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment implements LoginListener{

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
        View view = inflater.inflate(R.layout.fragment_aboutme, container, false);

        profileImage = (ImageView)view.findViewById(R.id.img_profile);
        profileName = (TextView)view.findViewById(R.id.txt_author);

        int width = getResources().getDimensionPixelOffset(R.dimen.profile_avatar_border);
        int color = getResources().getColor(R.color.color_profile_photo_border);

        Picasso.with(getActivity())
                .load(R.mipmap.dayuhaitang)
                .transform(new CircleTransformation(width, color))
                .placeholder(R.mipmap.ic_ishuying)
                .into(profileImage);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onLoginSuccess() {
        updateProfile();
    }

    @Override
    public void onLogoutSuccess() {

    }

    private void updateProfile() {

        UserInfo userInfo = IShuYingApplication.getInstance().getUser();
        Log.d(TAG, "url: " + userInfo.getUserIcon() + ",  name: " + userInfo.getUserName());

        int size = getResources().getDimensionPixelOffset(R.dimen.profile_aboutme_size);
        int width = getResources().getDimensionPixelOffset(R.dimen.profile_aboutme_border);
        int color = getResources().getColor(R.color.color_profile_photo_border);


        Picasso.with(getActivity())
                .load(userInfo.getUserIcon())
                .resize(size, size)
                .transform(new CircleTransformation(width, color))
                .placeholder(R.mipmap.ic_ishuying)
                .into(profileImage);

        profileName.setText(userInfo.getUserName());
    }
}
