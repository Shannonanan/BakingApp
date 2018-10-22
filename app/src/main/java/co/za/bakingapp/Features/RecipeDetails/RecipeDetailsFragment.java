package co.za.bakingapp.Features.RecipeDetails;

import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.za.bakingapp.Data.models.Step;
import co.za.bakingapp.R;

public class RecipeDetailsFragment extends Fragment implements ExoPlayer.EventListener {


    private final String STATE_RESUME_POSITION = "resumePosition";
    private static final String TAG_PLAYING = "playing";
    private static final String TAG_PAUSED = "paused";
    private static final String TAG_SESSION_INSTANCE = "session";
    private static final String STEP_DATA = "step";
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private MediaSession mMediaSessionCompat;
    private PlaybackState.Builder playbackStateCompat;
    private Step step;
    TextView tv_title;
    TextView tv_description;
    View view;
    boolean landscapeOrientation;
    private long mResumePosition;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            step = (Step) savedInstanceState.getSerializable(STEP_DATA);
            landscapeOrientation = savedInstanceState.getBoolean("landscape");
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);


        } else {
            try {
                step = (Step) getArguments().getSerializable(STEP_DATA);
            } catch (NullPointerException ex) {
                String message = ex.getMessage();
            }
        }

        if (landscapeOrientation) {
            view = inflater.inflate(R.layout.fragment_recipe_details_landscape, container, false);
        } else {
            view = inflater.inflate(R.layout.fragment_recipe_details, container, false);
            tv_title = view.findViewById(R.id.tv_title);
            tv_description = view.findViewById(R.id.tv_description);
            tv_title.setText(step.getShortDescription());
            tv_description.setText(step.getDescription());
        }

        ButterKnife.bind(this, view);

        mPlayerView = view.findViewById(R.id.playerView);
        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                (getResources(), R.drawable.question_mark));
        initializeMediaSession(view);
        // Initialize the player.
        if (step.getVideoURL().equals("")) {
            Toast.makeText(getContext(), "No video available", Toast.LENGTH_SHORT).show();
        } else {
            initializePlayer(Uri.parse(step.getVideoURL()));
        }
        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            landscapeOrientation = true;
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            landscapeOrientation = false;
        }
    }

    private void initializeMediaSession(View v) {
        mMediaSessionCompat = new MediaSession(getContext(), TAG_SESSION_INSTANCE);

        mMediaSessionCompat.setFlags(MediaSession.FLAG_HANDLES_MEDIA_BUTTONS | MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mMediaSessionCompat.setMediaButtonReceiver(null);
        playbackStateCompat = new PlaybackState.Builder()
                .setActions(PlaybackState.ACTION_PLAY | PlaybackState.ACTION_PAUSE |
                        PlaybackState.ACTION_PLAY_PAUSE | PlaybackState.ACTION_SKIP_TO_PREVIOUS);

        mMediaSessionCompat.setPlaybackState(playbackStateCompat.build());
        mMediaSessionCompat.setCallback(new SessionCallBack());
        mMediaSessionCompat.setActive(true);

    }

    /**
     * Initialize ExoPlayer.
     *
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            mExoPlayer.addListener(this);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(STEP_DATA, step);
        outState.putBoolean("landscape", landscapeOrientation);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            Log.d(TAG_PLAYING, "onPlayerStateChanged: PLAYING");
            playbackStateCompat.setState(PlaybackState.STATE_PLAYING, mExoPlayer.getCurrentPosition(), 1f);
        } else if (playbackState == ExoPlayer.STATE_READY) {
            Log.d(TAG_PAUSED, "onPlayerStateChanged: PAUSED");
            playbackStateCompat.setState(PlaybackState.STATE_PAUSED, mExoPlayer.getCurrentPosition(), 1f);
        }

        mMediaSessionCompat.setPlaybackState((playbackStateCompat.build()));
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    //to handle the completion handlers
    private class SessionCallBack extends MediaSession.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }

    /**
     * Release the player when the activity is destroyed.
     */

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
        mMediaSessionCompat.setActive(false);
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }
}
