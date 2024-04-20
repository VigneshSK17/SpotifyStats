package com.t1r2340.unwrappd;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.t1r2340.unwrappd.adapters.ArtistRecyclerViewAdapter;
import com.t1r2340.unwrappd.databinding.FragmentArtistsDetailsPageBinding;
import com.t1r2340.unwrappd.models.api.ArtistObject;
import com.t1r2340.unwrappd.models.api.ImageObject;
import com.t1r2340.unwrappd.models.api.Wrapped;

import java.util.List;

public class ArtistsDetailsFragment extends Fragment {

    private List<ArtistObject> artists;

    private FragmentArtistsDetailsPageBinding binding;
    private ArtistRecyclerViewAdapter adapter;

    private int color;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentArtistsDetailsPageBinding.inflate(inflater, container, false);

        Bundle bundle = getArguments();
        Wrapped wrapped = (Wrapped) bundle.getSerializable("wrapped");

        color = wrapped.getColor();
        artists = wrapped.getTopArtists().getItems();
        loadTopArtist(artists.get(0));
        artists = artists.subList(1, artists.size());


        setArtistsRecyclerView();

        return binding.getRoot();
    }

    private void loadTopArtist(ArtistObject topArtist) {
        String title = "1. " + topArtist.getName();
        binding.topArtistTitle.setText(title);

        ImageObject artistImage = topArtist.getImages().get(0);
        if (artistImage != null) {
            Picasso.get()
                    .load(artistImage.getUrl())
                    .resize(artistImage.getWidth(), artistImage.getHeight())
                    .into(binding.topArtistImage);
        }
    }

    private void setArtistsRecyclerView() {
        RecyclerView recyclerView = binding.artistRecyclerView;

        adapter = new ArtistRecyclerViewAdapter(getContext(), artists, color);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

    }
}