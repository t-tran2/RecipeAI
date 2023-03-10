package com.example.recipeai.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public abstract class FirestoreAdapter<VH extends RecyclerView.ViewHolder> extends
        RecyclerView.Adapter<VH> implements EventListener<QuerySnapshot> {
    private ListenerRegistration registration;
    private ArrayList<DocumentSnapshot> docSnapshots = new ArrayList<DocumentSnapshot>();
    private Query query;
    private OnDataChangedListener onDataChangedListener;

    public interface OnDataChangedListener {
        void onDataChanged();
    }

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        this.onDataChangedListener = listener;
    }

    private static final String TAG = "FirestoreAdapter";

    public FirestoreAdapter(Query query) {
        this.query = query;
    }

    // Start listening to the firebase database data from query
    public void startListening() {
        if (registration == null) {
            registration = query.addSnapshotListener(this);
        }
    }

    public void stopListening() {
        if (registration != null) {
            registration.remove();
            registration = null;
        }

        docSnapshots.clear();
        notifyDataSetChanged();
    }

    public void setQuery(Query query) {
        // Stop listening
        stopListening();

        // Clear existing data
        docSnapshots.clear();
        notifyDataSetChanged();

        // Listen to new query
        this.query = query;
        startListening();
    }

    @Override
    public int getItemCount() {
        return docSnapshots.size();
    }

    protected DocumentSnapshot getSnapshot(int index) {
        return docSnapshots.get(index);
    }

    @Override
    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
        // Handle errors
        if (e != null) {
            onError(e);
            return;
        }

        if (documentSnapshots != null) {
            for (DocumentChange change :  documentSnapshots.getDocumentChanges()) {
                // snapshot of the changed document
                if (change.getType() == DocumentChange.Type.ADDED) {
                    onDocumentAdded(change);
                } else if (change.getType() == DocumentChange.Type.MODIFIED) {
                    onDocumentModified(change);
                } else if (change.getType() == DocumentChange.Type.REMOVED){
                    onDocumentRemoved(change);
                }
            }
        }

        onDataChangedListener.onDataChanged();
    }

    private void onDocumentAdded(DocumentChange change) {
        docSnapshots.add(change.getNewIndex(), change.getDocument());
        notifyItemInserted(change.getNewIndex());
    }

    private void onDocumentModified(DocumentChange change) {
        if (change.getOldIndex() == change.getNewIndex()) {
            // Item changed but remained in same position
            docSnapshots.set(change.getOldIndex(), change.getDocument());
            notifyItemChanged(change.getOldIndex());
        } else {
            // Item changed and changed position
            docSnapshots.remove(change.getOldIndex());
            docSnapshots.add(change.getNewIndex(), change.getDocument());
            notifyItemMoved(change.getOldIndex(), change.getNewIndex());
        }
    }

    private void onDocumentRemoved(DocumentChange change) {
        docSnapshots.remove(change.getOldIndex());
        notifyItemRemoved(change.getOldIndex());
    }

    public void onError(FirebaseFirestoreException e) {
        Log.w(TAG, "onError", e);
    }

}
