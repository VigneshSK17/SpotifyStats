package com.t1r2340.spotifystats.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.t1r2340.spotifystats.PrevWrappedActivity;
import com.t1r2340.spotifystats.R;
import com.t1r2340.spotifystats.helpers.SpotifyApiHelper;
import com.t1r2340.spotifystats.models.api.Wrapped;

import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

public class WrappedRecyclerViewAdapter extends RecyclerView.Adapter<WrappedRecyclerViewAdapter.WrappedViewHolder> {

    private Context context;
    public List<Wrapped> wrappeds;

    /**
     * Constructor for WrappedRecyclerViewAdapter
     * @param context the context of the adapter
     * @param wrappeds the list of wrappeds to display
     */
    public WrappedRecyclerViewAdapter(Context context, List<Wrapped> wrappeds) {
        this.context = context;
        this.wrappeds = wrappeds;
        Log.d("WRAPPED_RECYCLER_VIEW", "" + this.wrappeds.size());
    }

    @NonNull
    @Override
    public WrappedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.all_wrappeds_button_layout, parent, false);

        Log.d("WRAPPED_RECYCLER_VIEW_2", "" + wrappeds.size());

        return new WrappedViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return wrappeds.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull WrappedViewHolder holder, int position) {
        Wrapped wrapped = wrappeds.get(position);
        Log.d("WRAPPED_RECYCLER_VIEW", wrapped.toString());

        StringBuilder title = new StringBuilder();
        SpotifyApiHelper.TimeRange timeRange = wrapped.getTimeRange();
        Log.d("WRAPPED_RECYCLER_VIEW", "Time Range: " + timeRange);
        if (timeRange == SpotifyApiHelper.TimeRange.SHORT_TERM) {
            title.append("Short Term");
        } else if (timeRange == SpotifyApiHelper.TimeRange.MEDIUM_TERM) {
            title.append("Medium Term");
        } else if (timeRange == SpotifyApiHelper.TimeRange.LONG_TERM) {
            title.append("Long Term");
        }
        title.append(" on ");
        title.append(DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US).format(wrapped.getGeneratedAt()));

        holder.wrappedTextView.setText(title.toString());

        holder.card.setOnClickListener(v -> {
            Intent intent = new Intent(context, PrevWrappedActivity.class);
            intent.putExtra("wrappedTitle", title.toString());
            intent.putExtra("wrapped", wrapped);
            context.startActivity(intent);
        });
    }

    public void setWrappeds(List<Wrapped> wrappeds) {
        this.wrappeds = wrappeds;
        Log.d("WRAPPED_RECYCLER_VIEW_SET", "" + this.wrappeds.size());
        notifyDataSetChanged();
    }

    public static class WrappedViewHolder extends RecyclerView.ViewHolder {
        private CardView card;
        private TextView wrappedTextView;

        public WrappedViewHolder(@NonNull View itemView) {
            super(itemView);

            this.card = itemView.findViewById(R.id.wrappedCardView);
            this.wrappedTextView = itemView.findViewById(R.id.wrappedTextView);
        }
    }



}
