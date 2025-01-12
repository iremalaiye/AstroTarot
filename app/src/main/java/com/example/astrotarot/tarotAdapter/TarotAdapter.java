package com.example.astrotarot.tarotAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.astrotarot.R;

import java.util.List;

public class TarotAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Tarot> tarotList;

    public TarotAdapter(Activity activity, List<Tarot> tarotList) {
        this.mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.tarotList = tarotList;
    }

    @Override
    public int getCount() {
        return tarotList.size();
    }

    @Override
    public Object getItem(int position) {
        return tarotList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tarotList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView =mInflater.inflate(R.layout.tarot_listview, parent, false);
        }

        Tarot tarot = tarotList.get(position);
        return convertView;
    }
}
