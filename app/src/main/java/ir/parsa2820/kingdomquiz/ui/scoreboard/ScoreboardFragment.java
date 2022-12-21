package ir.parsa2820.kingdomquiz.ui.scoreboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ir.parsa2820.kingdomquiz.MainActivity;
import ir.parsa2820.kingdomquiz.databinding.FragmentScoreboardBinding;
import ir.parsa2820.kingdomquiz.model.User;
import ir.parsa2820.kingdomquiz.model.UserDao;

public class ScoreboardFragment extends Fragment {

    private FragmentScoreboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ScoreboardViewModel scoreboardViewModel =
                new ViewModelProvider(this).get(ScoreboardViewModel.class);

        binding = FragmentScoreboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final RecyclerView scoreboardRecyclerView = binding.recyclerViewScoreboard;
        scoreboardRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final UserDao userDao = ((MainActivity) requireActivity()).getUserDao();
        scoreboardRecyclerView.setAdapter(new ScoreboardAdapter(userDao.getAllOrderByScore()));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}