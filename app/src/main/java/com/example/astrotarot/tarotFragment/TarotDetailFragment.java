package com.example.astrotarot.tarotFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.astrotarot.R;

public class TarotDetailFragment extends Fragment {

    private static final String ARG_DESCRIPTION = "description";
    private static final String ARG_TITLE = "title";

    public static TarotDetailFragment newInstance(String description,String title) {
        TarotDetailFragment fragment = new TarotDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DESCRIPTION, description);
        args.putString(ARG_TITLE,title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tarot_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            String description = getArguments().getString(ARG_DESCRIPTION);
            String title = getArguments().getString(ARG_TITLE);
            TextView descriptionTextView = view.findViewById(R.id.tarot_detail_description);
            TextView titleTextView = view.findViewById(R.id.title);
            descriptionTextView.setText(description);
            titleTextView.setText(title);
        }
    }
}
