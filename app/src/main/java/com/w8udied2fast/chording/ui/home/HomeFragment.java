package com.w8udied2fast.chording.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.w8udied2fast.chording.R;
import com.w8udied2fast.chording.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupClickListeners();
    }

    private void setupClickListeners() {
        // Функция для навигации
        View.OnClickListener chordClickListener = v -> {
            String rootNote = "";
            // Определяем, на какую карточку нажали
            if (v.getId() == R.id.card_c) rootNote = "C";
            else if (v.getId() == R.id.card_d) rootNote = "D";
            else if (v.getId() == R.id.card_e) rootNote = "E";
            else if (v.getId() == R.id.card_f) rootNote = "F";
            else if (v.getId() == R.id.card_g) rootNote = "G";
            else if (v.getId() == R.id.card_a) rootNote = "A";
            else if (v.getId() == R.id.card_b) rootNote = "B";

            // Передаем данные в следующий фрагмент
            Bundle bundle = new Bundle();
            bundle.putString("rootNote", rootNote);
            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_chordsListFragment, bundle);
        };

        // Привязываем слушатель ко всем карточкам
        binding.cardC.setOnClickListener(chordClickListener);
        binding.cardD.setOnClickListener(chordClickListener);
        binding.cardE.setOnClickListener(chordClickListener);
        binding.cardF.setOnClickListener(chordClickListener);
        binding.cardG.setOnClickListener(chordClickListener);
        binding.cardA.setOnClickListener(chordClickListener);
        binding.cardB.setOnClickListener(chordClickListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Важно для избежания утечек памяти
    }
}
