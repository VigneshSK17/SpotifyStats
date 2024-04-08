package com.t1r2340.spotifystats.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.t1r2340.spotifystats.databinding.DialogChangeUsernameBinding;

public class ChangeUsernameDialogFragment extends DialogFragment {

  private DialogChangeUsernameBinding binding;
  private FirebaseUser user;

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    user = FirebaseAuth.getInstance().getCurrentUser();
    if (user == null) {
      dismiss();
    }

    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
    LayoutInflater inflater = requireActivity().getLayoutInflater();
    binding = DialogChangeUsernameBinding.inflate(inflater);

    getActivity().runOnUiThread(() -> binding.etNewUsername.setText(user.getDisplayName()));

    builder
        .setView(binding.getRoot())
        .setPositiveButton(
            "Save",
            (dialog, id) -> {
              String newUsername = binding.etNewUsername.getText().toString();
              user.updateProfile(
                  new UserProfileChangeRequest.Builder().setDisplayName(newUsername).build());
              dismiss();
            })
        .setNegativeButton("Cancel", (dialog, id) -> dismiss());

    return builder.create();
  }
}
