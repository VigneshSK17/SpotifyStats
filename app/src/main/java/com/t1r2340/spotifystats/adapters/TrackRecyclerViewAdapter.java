package com.t1r2340.spotifystats.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.t1r2340.spotifystats.R;
import com.t1r2340.spotifystats.helpers.SpotifyAppRemoteHelper;
import com.t1r2340.spotifystats.models.api.ImageObject;
import com.t1r2340.spotifystats.models.api.TrackObject;

import java.util.List;

public class TrackRecyclerViewAdapter extends RecyclerView.Adapter<TrackRecyclerViewAdapter.TrackViewHolder> {

    private Context context;
    private List<TrackObject> tracks;
    private SpotifyAppRemoteHelper appRemoteHelper;
    private boolean isPlaying;
    private int color;
    private boolean isPremium;
    private boolean isWrappedDetails;

    public TrackRecyclerViewAdapter(Context context, List<TrackObject> tracks, SpotifyAppRemoteHelper appRemoteHelper, int color, boolean isPremium, boolean isWrappedDetails) {
        this.context = context;
        this.tracks = tracks;
        this.appRemoteHelper = appRemoteHelper;
        this.color = color;
        isPlaying = false;
        this.isPremium = isPremium;
        this.isWrappedDetails = isWrappedDetails;
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.your_song_list_item_layout, parent, false);

        return new TrackViewHolder(view);
    }


    @Override
    public int getItemCount() {
        return tracks.size();
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        TrackObject track = tracks.get(position);

        setColor(holder);

        ImageObject trackImage = track.getAlbum().getImages().get(0);
        if (trackImage != null) {
            Picasso.get()
                    .load(trackImage.getUrl())
                    .resize(trackImage.getWidth(), trackImage.getHeight())
                    .into(holder.trackImage);
        }

        if (isWrappedDetails) {
            holder.trackTitle.setText(track.getName());
        } else {
            String title = (position + 2) + ". " + track.getName();
            holder.trackTitle.setText(title);
        }
        holder.artistName.setText(track.getArtists().get(0).getName());

        holder.card.setOnClickListener(l -> {
            if (!isPremium) {
                Toast.makeText(context, "You need Spotify Premium to play songs", Toast.LENGTH_SHORT).show();
            } else {
                if (!isPlaying) {
                    playTrack(track);
                    isPlaying = true;
                } else {
                    pauseTrack();
                    isPlaying = false;
                }
            }
        });

    }

    private void setColor(@NonNull TrackRecyclerViewAdapter.TrackViewHolder holder) {
        holder.card.setBackgroundColor(context.getColor(color));
    }

    private void playTrack(TrackObject track) {
        appRemoteHelper.play(track.getUri());
    }

    private void pauseTrack() {
        appRemoteHelper.pause();
    }

    protected static class TrackViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout card;
        private ImageView trackImage;
        private TextView trackTitle, artistName;

        public TrackViewHolder(@NonNull View itemView) {
            super(itemView);

            this.card = itemView.findViewById(R.id.card_layout);
            this.trackImage = card.findViewById(R.id.card_image);
            this.trackTitle = card.findViewById(R.id.card_title);
            this.artistName = card.findViewById(R.id.card_artist_name);
        }
    }

}

