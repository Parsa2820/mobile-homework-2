package ir.parsa2820.kingdomquiz.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Locale;
import java.util.Objects;

import ir.parsa2820.kingdomquiz.MainActivity;
import ir.parsa2820.kingdomquiz.R;
import ir.parsa2820.kingdomquiz.databinding.FragmentHomeBinding;
import ir.parsa2820.kingdomquiz.model.Category;
import ir.parsa2820.kingdomquiz.model.Question;
import ir.parsa2820.kingdomquiz.model.User;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private String baseUrl = "https://opentdb.com/api.php?";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Button startGameButton = binding.buttonStartGame;
        startGameButton.setOnClickListener(v -> {
            User user = ((MainActivity) requireActivity()).getUser();
            StringBuilder url = new StringBuilder(baseUrl);
            url.append("amount=");
            url.append(user.getNumberOfQuestions());
            Category category = Category.valueOf(user.getCategory());
            if (category != Category.ANY) {
                url.append("&category=");
                url.append(category.getValue());
            }
            String difficulty = user.getDifficulty();
            if (!difficulty.toLowerCase().equals("any")) {
                url.append("&difficulty=");
                url.append(difficulty.toLowerCase());
            }
            url.append("&type=multiple");
            RequestQueue queue = Volley.newRequestQueue(requireContext());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url.toString(),
                    response -> {
                        Gson gson = new Gson();
                        JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
                        Question[] questions = gson.fromJson(jsonObject.get("results"), Question[].class);
                    }, error -> {
            });
            queue.add(stringRequest);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}