package ir.parsa2820.kingdomquiz.ui.scoreboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ir.parsa2820.kingdomquiz.databinding.FragmentScoreboardBinding;

public class ScoreboardFragment extends Fragment {

    private FragmentScoreboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ScoreboardViewModel galleryViewModel =
                new ViewModelProvider(this).get(ScoreboardViewModel.class);

        binding = FragmentScoreboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}