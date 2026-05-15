package com.w8udied2fast.chording.ui.chords;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.w8udied2fast.chording.databinding.FragmentChordsListBinding;
import com.w8udied2fast.chording.viewmodel.ChordsViewModel;

public class ChordsListFragment extends Fragment {
    private FragmentChordsListBinding binding;
    private ChordsAdapter adapter;
    private ChordsViewModel viewModel;
    private String currentRoot = "C";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChordsListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Читаем переданную букву
        if (getArguments() != null) {
            currentRoot = getArguments().getString("rootNote", "C");
        }
        binding.tvTitle.setText(currentRoot + " Chords");

        // 2. Настраиваем RecyclerView
        adapter = new ChordsAdapter();
        binding.rvChords.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvChords.setAdapter(adapter);

        // 3. Подключаем ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(ChordsViewModel.class);

        // Фильтруем аккорды по выбранной ноте
        viewModel.filterByRoot(currentRoot);

        // Наблюдаем за изменениями
        viewModel.getChordsLiveData().observe(getViewLifecycleOwner(), chords -> {
            adapter.updateList(chords);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}