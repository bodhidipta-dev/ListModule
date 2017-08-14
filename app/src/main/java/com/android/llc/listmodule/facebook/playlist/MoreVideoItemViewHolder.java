/*
 * Copyright (c) 2017 Nam Nguyen, nam@ene.im
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.llc.listmodule.facebook.playlist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.llc.listmodule.ApplCont;
import com.android.llc.listmodule.R;
import com.android.llc.listmodule.common.MediaUrl;
import com.android.llc.listmodule.facebook.data.FbVideo;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import im.ene.toro.ToroPlayer;
import im.ene.toro.ToroUtil;
import im.ene.toro.exoplayer.ExoPlayerHelper;
import im.ene.toro.exoplayer.SimpleExoPlayerViewHelper;
import im.ene.toro.media.PlaybackInfo;
import im.ene.toro.widget.Container;

import java.util.List;

import static java.lang.String.format;
import static java.util.Locale.getDefault;

/**
 * @author eneim | 6/18/17.
 */

@SuppressWarnings("WeakerAccess") //
public class MoreVideoItemViewHolder extends RecyclerView.ViewHolder implements ToroPlayer {

    static final int LAYOUT_RES = R.layout.vh_fbcard_base_dark;

    @Nullable
    SimpleExoPlayerViewHelper helper;
    @Nullable
    private Uri mediaUri;

    ImageView userIcon;
    TextView userName;
    TextView userProfile;
    FrameLayout container;
    SimpleExoPlayerView playerView;
    TextView state;
    View overLay;

    private ExoPlayerHelper.EventListener listener = new ExoPlayerHelper.EventListener() {
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            super.onPlayerStateChanged(playWhenReady, playbackState);
            state.setText(format(getDefault(), "STATE: %d・PWR: %s", playbackState, playWhenReady));
        }
    };

    private EventListener eventListener;

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    MoreVideoItemViewHolder(View itemView) {
        super(itemView);
        userIcon = (ImageView) itemView.findViewById(R.id.fb_user_icon);
        userName = (TextView) itemView.findViewById(R.id.fb_user_name);
        userProfile = (TextView) itemView.findViewById(R.id.fb_user_profile);
        container = (FrameLayout) itemView.findViewById(R.id.fb_item_middle);
        playerView = (SimpleExoPlayerView) itemView.findViewById(R.id.fb_video_player);
        state = (TextView) itemView.findViewById(R.id.player_state);
        overLay = (View) itemView.findViewById(R.id.over_lay);

        playerView.setVisibility(View.VISIBLE);
        playerView.setUseController(false);
    }

    @SuppressWarnings("SameParameterValue")
        //
    void bind(MoreVideosAdapter adapter, FbVideo item, List<Object> payloads) {
        if (item != null) {
            userName.setText(item.author.userName);
            //Glide.with(ApplCont.getInstance()).load(item.author.userIcon).into(userIcon);
            MediaUrl url = item.getMediaUrl();
            mediaUri = url.getUri();
            userProfile.setText("%s・%s" + item.timeStamp + "--" + url.name());
        }
    }


    @NonNull
    @Override
    public View getPlayerView() {
        return this.playerView;
    }

    @NonNull
    @Override
    public PlaybackInfo getCurrentPlaybackInfo() {
        return helper != null ? helper.getLatestPlaybackInfo() : new PlaybackInfo();
    }

    @Override
    public void initialize(@NonNull Container container, @Nullable PlaybackInfo playbackInfo) {
        if (helper == null) {
            helper = new SimpleExoPlayerViewHelper(container, this, mediaUri);
            helper.setEventListener(listener);
            helper.addPlayerEventListener(eventListener);
        }
        helper.initialize(playbackInfo);
    }

    ViewPropertyAnimator onPlayAnimator;
    ViewPropertyAnimator onPauseAnimator;
    int animatorDuration = 300;

    @Override
    public void play() {
        playerView.setUseController(true);
        if (onPlayAnimator != null) onPlayAnimator.cancel();
        onPlayAnimator = overLay.animate().alpha(0.0f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                animation.end();
            }
        }).setDuration(animatorDuration);
        onPlayAnimator.start();
        if (helper != null) helper.play();
    }

    @Override
    public void pause() {
        playerView.setUseController(false);
        if (onPauseAnimator != null) onPauseAnimator.cancel();
        onPauseAnimator = overLay.animate().alpha(1.0f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                animation.end();
            }
        }).setDuration(animatorDuration);
        onPauseAnimator.start();
        if (helper != null) helper.pause();
    }

    @Override
    public boolean isPlaying() {
        return helper != null && helper.isPlaying();
    }

    @Override
    public void release() {
        if (onPlayAnimator != null) onPlayAnimator.cancel();
        if (onPauseAnimator != null) onPauseAnimator.cancel();
        onPlayAnimator = null;
        onPauseAnimator = null;

        if (helper != null) {
            helper.setEventListener(null);
            helper.removePlayerEventListener(eventListener);
            helper.release();
            helper = null;
        }
    }

    @Override
    public boolean wantsToPlay() {
        return ToroUtil.visibleAreaOffset(this, itemView.getParent()) >= 0.85;
    }

    @Override
    public void onContainerScrollStateChange(Container container, int newState) {
        // Do nothing
    }

    @Override
    public int getPlayerOrder() {
        return getAdapterPosition();
    }
}
