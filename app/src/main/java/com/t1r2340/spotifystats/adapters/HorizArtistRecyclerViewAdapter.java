package com.t1r2340.spotifystats.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.t1r2340.spotifystats.R;
import com.t1r2340.spotifystats.models.api.ArtistObject;
import com.t1r2340.spotifystats.models.api.ImageObject;

import java.util.List;

public class HorizArtistRecyclerViewAdapter extends RecyclerView.Adapter<HorizArtistRecyclerViewAdapter.HorizArtistViewHolder> {

     private List<ArtistObject> artists;

     /**
      * Constructor for HorizArtistRecyclerViewAdapter
      * @param artists the list of artists to display
      */
     public HorizArtistRecyclerViewAdapter(List<ArtistObject> artists) {
         this.artists = artists;
     }

     @NonNull
     @Override
     public HorizArtistRecyclerViewAdapter.HorizArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         LayoutInflater inflater = LayoutInflater.from(parent.getContext());
         View view = inflater.inflate(R.layout.your_artist_horiz_list_item_layout, parent, false);

         return new HorizArtistRecyclerViewAdapter.HorizArtistViewHolder(view);
     }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    @Override
    public void onBindViewHolder(@NonNull HorizArtistViewHolder holder, int position) {
         ArtistObject artist = artists.get(position);

         String title = (position + 1) + ": " + artist.getName();
         holder.artistTitle.setText(title);

         ImageObject artistImage = artist.getImages().get(0);
         if (artistImage != null) {
             Picasso.get()
                     .load(artistImage.getUrl())
                     .resize(artistImage.getWidth(), artistImage.getHeight())
                     .into(holder.artistImage);
         }
    }

    protected static class HorizArtistViewHolder extends RecyclerView.ViewHolder {
        private CardView card;
        private ImageView artistImage;
        private TextView artistTitle;

        public HorizArtistViewHolder(@NonNull View itemView) {
            super(itemView);

            this.card = itemView.findViewById(R.id.horizArtistCardView);
            this.artistImage = card.findViewById(R.id.horizArtistImageView);
            this.artistTitle = card.findViewById(R.id.horizArtistTitleTextView);
        }
    }

}
