package com.example.astrotarot;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.astrotarot.tarotAdapter.Tarot;
import com.example.astrotarot.tarotAdapter.TarotAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TarotSection extends AppCompatActivity {
    private GridView tarotListView;
    private TarotAdapter tarotAdapter;
    private List<Tarot> tarotList;
    private FirebaseFirestore firestore;
    private ExecutorService executorService; // Background thread executor
    private Handler mainHandler; // Main thread handler

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tarot_section);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tarotListView = findViewById(R.id.tarot_listview);
        tarotList = new ArrayList<>();
        tarotAdapter = new TarotAdapter(this, tarotList);
        tarotListView.setAdapter(tarotAdapter);


        firestore = FirebaseFirestore.getInstance();


        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());


        fetchTarotsFromFirestore();
    }

    private void fetchTarotsFromFirestore() {
        executorService.execute(() -> {

            firestore.collection("tarotDescription")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<Tarot> tempList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                int id = Integer.valueOf(document.getId());
                                String description = document.getString("description");
                                tempList.add(new Tarot(id, description));
                            }


                            mainHandler.post(() -> {
                                tarotList.clear();
                                tarotList.addAll(tempList);
                                tarotAdapter.notifyDataSetChanged();
                                // Set an item click listener
                                tarotListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        // Get the clicked Tarot object
                                        Tarot clickedTarot = (Tarot) tarotAdapter.getItem(position);

                                        // Display the description in a Toast
                                        if (clickedTarot != null) {
                                            Toast.makeText(getApplicationContext(), clickedTarot.getDescription(), Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "No description available", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            });

                        } else {
                            Log.e("FirestoreError", "Error getting documents", task.getException());
                        }
                    });
        });
    }
}
