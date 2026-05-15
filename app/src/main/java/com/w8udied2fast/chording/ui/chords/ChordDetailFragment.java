package com.w8udied2fast.chording.ui.chords;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.w8udied2fast.chording.data.model.Chord;
import com.w8udied2fast.chording.databinding.FragmentChordDetailBinding;
import com.w8udied2fast.chording.viewmodel.ChordsViewModel;

import java.util.List;

public class ChordDetailFragment extends Fragment {
    private FragmentChordDetailBinding binding;
    private ChordsViewModel viewModel;
    private String currentChordName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChordDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Получаем имя аккорда из аргументов
        currentChordName = getArguments() != null ? getArguments().getString("chordName") : "C";
        binding.tvChordTitle.setText(currentChordName);

        // Подключаем ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(ChordsViewModel.class);

        // Находим аккорд по названию
        viewModel.getChordsLiveData().observe(getViewLifecycleOwner(), chords -> {
            Chord selectedChord = findChordByName(chords, currentChordName);
            if (selectedChord != null) {
                binding.chordDiagram.setChord(selectedChord);
            }
        });

        // Загружаем все аккорды
        viewModel.loadAllChords();
    }

    private Chord findChordByName(List<Chord> chords, String name) {
        for (Chord chord : chords) {
            if (chord.getName().equalsIgnoreCase(name)) {
                return chord;
            }
        }
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
