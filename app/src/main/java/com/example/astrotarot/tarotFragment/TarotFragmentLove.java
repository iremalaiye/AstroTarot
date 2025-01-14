package com.example.astrotarot.tarotFragment;
//iremalaiye
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.astrotarot.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A fragment representing a list of Items.
 */
public class TarotFragmentLove extends Fragment {

    private GridView tarotListView;
    private TarotAdapter tarotAdapter;
    private List<Tarot> tarotList;
    private FirebaseFirestore firestore;
    private ExecutorService executorService; // Background thread executor
    private Handler mainHandler; // Main thread handler

    // Mandatory empty constructor
    public TarotFragmentLove() {}

    public static TarotFragmentLove newInstance() {
        return new TarotFragmentLove();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firestore, ExecutorService, and Handler
        firestore = FirebaseFirestore.getInstance();
        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the fragment layout
        View rootView = inflater.inflate(R.layout.fragment_tarot_love, container, false);

        // Initialize views
        tarotListView = rootView.findViewById(R.id.tarot_listview);

        // Initialize tarot list and adapter
        tarotList = new ArrayList<>();
        tarotAdapter = new TarotAdapter(requireActivity(), tarotList);
        tarotListView.setAdapter(tarotAdapter);

        // Fetch data from Firestore
        fetchTarotsFromFirestore();


        return rootView;
    }

    private void fetchTarotsFromFirestore() {
        executorService.execute(() -> {
            firestore.collection("tarotLoveDescription")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<Tarot> tempList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                int id = Integer.parseInt(document.getId());
                                String description = document.getString("description");
                                String title = document.getString("title");
                                tempList.add(new Tarot(id, description,title));
                            }

                            // Update UI on the main thread
                            mainHandler.post(() -> {
                                tarotList.clear();
                                tarotList.addAll(tempList);
                                tarotAdapter.notifyDataSetChanged();

                                // Set an item click listener
                                tarotListView.setOnItemClickListener((parent, view, position, id) -> {
                                    Tarot clickedTarot = tarotList.get(position);
                                    if (clickedTarot != null) {
                                        openTarotDetailFragment(clickedTarot.getDescription(),clickedTarot.getTitle());
                                    }
                                });
                            });
                        } else {
                            Log.e("FirestoreError", "Error getting documents", task.getException());
                        }
                    });
        });
    }

    private void openTarotDetailFragment(String description,String title) {
        TarotDetailFragment detailFragment = TarotDetailFragment.newInstance(description,title);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment1, detailFragment) // Replace with your container ID
                .addToBackStack(null) // Allows the user to navigate back
                .commit();
    }

}
