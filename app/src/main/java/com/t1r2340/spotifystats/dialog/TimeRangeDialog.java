package com.t1r2340.spotifystats.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.t1r2340.spotifystats.WrappedActivity;

public class TimeRangeDialog extends DialogFragment {

    private String timeRange, accessToken;

    public TimeRangeDialog(String accessToken) {
        timeRange = "short_term";
        this.accessToken = accessToken;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        builder.setTitle("Select Time Range")
                .setItems(new String[]{"Short Term", "Medium Term", "Long Term"}, (dialog, which) -> {
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

                    Intent intent = new Intent(getActivity(), WrappedActivity.class);
                    intent.putExtra("accessToken", accessToken);
                    intent.putExtra("timeRange", timeRange);
                    startActivity(intent);
                });

        return builder.create();
    }
}
