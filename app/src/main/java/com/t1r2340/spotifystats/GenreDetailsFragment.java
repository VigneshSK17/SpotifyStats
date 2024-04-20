package com.t1r2340.spotifystats;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.t1r2340.spotifystats.models.api.Wrapped;

import java.util.List;

public class GenreDetailsFragment extends Fragment {

    private List<String> genres;
    private TextView genreListTV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_genre_details_page, container, false);

        genreListTV = view.findViewById(R.id.genreListTextView);

        Bundle bundle = getArguments();
        Wrapped wrapped = (Wrapped) bundle.getSerializable("wrapped");

        genres = wrapped.getTopGenres();

        setGenresList();

        return view;
    }

    private void setGenresList() {
        StringBuilder genreList = new StringBuilder();
        for (int i = 0; i < genres.size(); i++) {
            genreList.append(i + 1).append(". ").append(genres.get(i)).append("\n");
        }
        genreListTV.setText(genreList.toString());
    }
}