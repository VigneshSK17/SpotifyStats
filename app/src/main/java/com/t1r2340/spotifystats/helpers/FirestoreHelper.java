package com.t1r2340.spotifystats.helpers;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.t1r2340.spotifystats.models.api.Wrapped;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/** Helper class to allow for easier firestore access and storage */
public class FirestoreHelper {

  /** The firestore database */
  FirebaseFirestore db;

  public FirestoreHelper() {
    this.db = FirebaseFirestore.getInstance();
  }

  // TODO: Parse from TextViews to create Wrapped object
  // TODO: create addOnSuccessListener and addOnFailureListener which displays a toast message
  /**
   * Stores a wrapped object in firestore
   *
   * @param wrapped the wrapped object to store
   * @return a task representing the completion of the storage
   */
  public Task<Void> storeWrapped(Wrapped wrapped) {
    return db.collection(wrapped.getUserId())
        .document(wrapped.getGeneratedAt().toString())
        .set(wrapped);
  }

  /**
   * Gets all wrappeds for a user
   *
   * @param userId the user's ID
   * @param consumer the consumer to accept the wrappeds
   */
  public void getWrappeds(String userId, Consumer<List<Wrapped>> consumer) {
    db.collection(userId)
        .get()
        .addOnCompleteListener(
            task -> {
              if (task.isSuccessful()) {
                List<Wrapped> wrappeds =
                    task.getResult().getDocuments().stream()
                        .map(snapshot -> snapshot.toObject(Wrapped.class))
                        .collect(Collectors.toList());
                Log.d("WRAPPED", wrappeds.get(0).getUserId());

                consumer.accept(wrappeds);
              }
            });
  }

  // TODO: create addOnSuccessListener and addOnFailureListener which displays a toast message
  /**
   * Deletes a wrapped object from firestore
   *
   * @param wrapped wrapped to be deleted
   * @return a task representing the completion of the deletion
   */
  public Task<Void> deleteWrapped(Wrapped wrapped) {
    return db.collection(wrapped.getUserId())
        .document(wrapped.getGeneratedAt().toString())
        .delete();
  }
}
