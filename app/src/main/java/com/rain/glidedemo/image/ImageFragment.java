/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rain.glidedemo.image;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.rain.glidedemo.R;
import com.rain.glidedemo.data.DataUtils;
import com.rain.glidedemo.glide.GlideApp;
import com.rain.glidedemo.glide.ProgressListener;

/**
 * A fragment for displaying an image.
 */
public class ImageFragment extends Fragment {

    private static final String KEY_IMAGE_RES = "data";
    private DataUtils.ImageDataDetail imageDataDetail;

    public static ImageFragment newInstance(DataUtils.ImageDataDetail data) {
        ImageFragment fragment = new ImageFragment();
        Bundle argument = new Bundle();
        argument.putSerializable(KEY_IMAGE_RES, data);
        fragment.setArguments(argument);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        view.findViewById(R.id.photoView).setTransitionName(String.valueOf(imageRes));

//        Glide.with(this)
//                .load(imageRes)
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable>
//                            target, boolean isFirstResource) {
//                        getParentFragment().startPostponedEnterTransition();
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable>
//                            target, DataSource dataSource, boolean isFirstResource) {
//                        getParentFragment().startPostponedEnterTransition();
//                        return false;
//                    }
//                })
//                .into((ImageView) view.findViewById(R.id.image));

        Bundle arguments = getArguments();
        imageDataDetail = (DataUtils.ImageDataDetail) arguments.getSerializable(KEY_IMAGE_RES);
        View view = inflater.inflate(R.layout.layout_image_photo, null);
        final ViewHolder viewHolder = new ViewHolder(view);
        GlideApp.with(this)
                .load(imageDataDetail.source)
                .thumbnail(Glide.with(this).load(imageDataDetail.thumb))
                .loadImage(imageDataDetail.source, new ProgressListener() {
                    @Override
                    public void onProgress(int progress) {
                        viewHolder.progressBar.setProgress(progress);
                    }
                    @Override
                    public void onPrepare() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                viewHolder.progressBar.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                    @Override
                    public void onComplete() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                viewHolder.progressBar.setVisibility(View.GONE);
                            }
                        });
                    }
                })
                .into(viewHolder.photoView);

        if (savedInstanceState == null) {
            postponeEnterTransition();
        }

        return view;
    }

    class ViewHolder implements View.OnClickListener {
        View rootView;
        PhotoView photoView;
        ProgressBar progressBar;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            photoView = rootView.findViewById(R.id.photoView);
            progressBar = rootView.findViewById(R.id.progressView);
            photoView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            getActivity().onBackPressed();
        }
    }

}
