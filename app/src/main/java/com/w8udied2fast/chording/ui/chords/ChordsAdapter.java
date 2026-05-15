package com.w8udied2fast.chording.ui.chords;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.w8udied2fast.chording.R;
import com.w8udied2fast.chording.data.model.Chord;
import com.w8udied2fast.chording.databinding.ItemChordBinding;

import java.util.ArrayList;
import java.util.List;

public class ChordsAdapter extends RecyclerView.Adapter<ChordsAdapter.ChordViewHolder> {
    private List<Chord> chords = new ArrayList<>();

    @NonNull
    @Override
    public ChordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemChordBinding binding = ItemChordBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ChordViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChordViewHolder holder, int position) {
        Chord chord = chords.get(position);
        holder.binding.tvChordName.setText(chord.getName());
        holder.binding.ivStar.setImageResource(android.R.drawable.btn_star_big_off);
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("chordName", chord.getName());
            Navigation.findNavController(v).navigate(R.id.action_chordsListFragment_to_chordDetailFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return chords.size();
    }

    public void updateList(List<Chord> newChords) {
        chords.clear();
        chords.addAll(newChords);
        notifyDataSetChanged();
    }

    static class ChordViewHolder extends RecyclerView.ViewHolder {
        ItemChordBinding binding;
        ChordViewHolder(ItemChordBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}