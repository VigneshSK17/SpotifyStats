package com.t1r2340.unwrappd.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.t1r2340.unwrappd.R;
import com.t1r2340.unwrappd.WrappedActivity;
import com.t1r2340.unwrappd.helpers.FailureCallback;
import com.t1r2340.unwrappd.helpers.FirestoreHelper;
import com.t1r2340.unwrappd.helpers.SpotifyApiHelper;

import okhttp3.Call;
import okhttp3.OkHttpClient;

public class TimeRangeDialog extends DialogFragment implements FailureCallback{

    private String timeRange;
    private String accessToken;

    private SpotifyApiHelper spotifyApi;
    private FirestoreHelper firestore;
    private final OkHttpClient mOkHttpClient = new OkHttpClient();
    private Call mCall;

    public TimeRangeDialog(String accessToken) {
        timeRange = "short_term";

        this.accessToken = accessToken;
        spotifyApi = new SpotifyApiHelper(this, accessToken, mOkHttpClient, mCall);
        firestore = new FirestoreHelper();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                new ContextThemeWrapper(requireContext(), com.google.android.material.R.style.Base_Theme_Material3_Dark)
        );

        TimeRangeArrayAdapter adapter = new TimeRangeArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                getContext().getResources().getStringArray(R.array.time_range_options)
        );


        builder.setTitle("Select Time Range")
                .setSingleChoiceItems(adapter, -1, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            timeRange = "SHORT_TERM";
                            break;
                        case 1:
                            timeRange = "MEDIUM_TERM";
                            break;
                        default:
                            timeRange = "LONG_TERM";
                            break;
                    }

//                    Intent intent = new Intent(getActivity(), NewWrappedActivity.class);
//                    intent.putExtra("accessToken", accessToken);
//                    intent.putExtra("timeRange", timeRange);
//                    startActivity(intent);
                    genWrapped();
                });

        return builder.create();
    }

    private void genWrapped() {
        SpotifyApiHelper.TimeRange tr = SpotifyApiHelper.TimeRange.valueOf(timeRange);

        spotifyApi.genWrapped(10, 10, 5, tr, pair -> {
            firestore.storeWrapped(pair.first)
                    .addOnSuccessListener(a -> Log.d("FIRESTORE", "Stored wrapped"))
                    .addOnFailureListener(
                            a -> Log.d("FIRESTORE", "Failed to store wrapped" + a));

            dismiss();

            Intent intent = new Intent(getActivity(), WrappedActivity.class);
            intent.putExtra("wrappedTitle", "Today's Wrapped");
            intent.putExtra("wrapped", pair.first);
            intent.putExtra("isPremium", pair.second);
            startActivity(intent);
        });
    }

    @Override
    public void onFailure(Exception e) {
        Log.d("HTTP", "Failed to fetch data: " + e);
        Toast.makeText(
                        getContext(),
                        "Failed to fetch data, watch Logcat for more details",
                        Toast.LENGTH_SHORT)
                .show();
    }

}

class TimeRangeArrayAdapter extends ArrayAdapter<String> {

    public TimeRangeArrayAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        if (position == 0) {
            view.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.short_term));
        } else if (position == 2){
            view.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.long_term));
        } else {
            view.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.primary));
        }
        return view;
    }
}