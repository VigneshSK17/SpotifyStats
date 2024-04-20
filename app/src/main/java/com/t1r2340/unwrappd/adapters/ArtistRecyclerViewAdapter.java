package com.t1r2340.unwrappd.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.t1r2340.unwrappd.R;
import com.t1r2340.unwrappd.models.api.ArtistObject;
import com.t1r2340.unwrappd.models.api.ImageObject;

import java.util.List;

public class ArtistRecyclerViewAdapter extends RecyclerView.Adapter<ArtistRecyclerViewAdapter.ArtistViewHolder> {

    private Context context;
    private List<ArtistObject> artists;
    private int color;

    public ArtistRecyclerViewAdapter(Context context, List<ArtistObject> artists, int color) {
        this.context = context;
        this.artists = artists;
        this.color = color;
    }

    @NonNull
    @Override
    public ArtistRecyclerViewAdapter.ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.your_song_list_item_layout, parent, false);

        return new ArtistRecyclerViewAdapter.ArtistViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public void onBindViewHolder(@NonNull ArtistRecyclerViewAdapter.ArtistViewHolder holder, int position) {
        ArtistObject artist = artists.get(position);

        setColor(holder);

        String title = (position + 2) + ". " + artist.getName();
        holder.artistName.setText(title);

        ImageObject artistImage = artist.getImages().get(0);
        if (artistImage != null) {
            Picasso.get()
                    .load(artistImage.getUrl())
                    .resize(artistImage.getWidth(), artistImage.getHeight())
                    .into(holder.artistImage);
        }

        holder.extraText.setHeight(0);
        holder.extraText.setWidth(0);
    }

    private void setColor(@NonNull ArtistRecyclerViewAdapter.ArtistViewHolder holder) {
        holder.card.setBackgroundColor(context.getColor(color));
    }

    protected static class ArtistViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout card;
        private ImageView artistImage;
        private TextView artistName;
        private TextView extraText;

        public ArtistViewHolder(@NonNull View view) {
            super(view);

            this.card = view.findViewById(R.id.card_layout);
            this.artistImage = view.findViewById(R.id.card_image);
            this.artistName = view.findViewById(R.id.card_title);
            this.extraText = view.findViewById(R.id.card_artist_name);
        }
    }
}
