package com.w8udied2fast.chording.ui.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.w8udied2fast.chording.R;
import com.w8udied2fast.chording.data.model.Chord;
import com.w8udied2fast.chording.databinding.FragmentSearchBinding;
import com.w8udied2fast.chording.ui.chords.ChordsAdapter;
import com.w8udied2fast.chording.viewmodel.ChordsViewModel;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;
    private ChordsAdapter adapter;
    private ChordsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // RecyclerView
        adapter = new ChordsAdapter();
        binding.rvSearchResults.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvSearchResults.setAdapter(adapter);

        adapter.setOnItemClickListener(chord -> {
            Bundle bundle = new Bundle();
            bundle.putString("chordName", chord.getName());
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_searchFragment_to_chordDetailFragment, bundle);
        });

        // ViewModel общий с другими фрагментами
        viewModel = new ViewModelProvider(requireActivity()).get(ChordsViewModel.class);

        // результаты поиска
        viewModel.getChordsLiveData().observe(getViewLifecycleOwner(), chords -> {
            adapter.updateList(chords);
            // если искали и ничего не нашли
            boolean isSearching = !binding.etSearch.getText().toString().trim().isEmpty();
            binding.tvEmptySearch.setVisibility(chords.isEmpty() && isSearching ? View.VISIBLE : View.GONE);
        });

        //Живой поиск при вводе текста
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim();
                if (query.isEmpty()) {
                    viewModel.loadAllChords(); // Сброс к полному списку (или можно очистить)
                    adapter.updateList(new ArrayList<>());
                } else {
                    viewModel.search(query);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
