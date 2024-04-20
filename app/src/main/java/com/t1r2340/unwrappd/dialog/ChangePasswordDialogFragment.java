package com.t1r2340.unwrappd.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.t1r2340.unwrappd.databinding.DialogChangePasswordBinding;

public class ChangePasswordDialogFragment extends DialogFragment {

  private DialogChangePasswordBinding binding;
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
    binding = DialogChangePasswordBinding.inflate(inflater);

    binding.etNewPassword.setHint("New Password");

    builder
        .setView(binding.getRoot())
        .setPositiveButton(
            "Save",
            (dialog, id) -> {
              String newPassword = binding.etNewPassword.getText().toString();
              user.updatePassword(newPassword);
              dismiss();
            })
        .setNegativeButton("Cancel", (dialog, id) -> dismiss());

    return builder.create();
  }
}
