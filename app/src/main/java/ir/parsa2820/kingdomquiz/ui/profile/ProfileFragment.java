package ir.parsa2820.kingdomquiz.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;

import ir.parsa2820.kingdomquiz.LoginActivity;
import ir.parsa2820.kingdomquiz.MainActivity;
import ir.parsa2820.kingdomquiz.R;
import ir.parsa2820.kingdomquiz.databinding.FragmentProfileBinding;
import ir.parsa2820.kingdomquiz.model.User;
import ir.parsa2820.kingdomquiz.model.UserDao;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        User user = ((MainActivity) requireActivity()).getUser();
        UserDao userDao = ((MainActivity) requireActivity()).getUserDao();
        final TextView profileEmail = binding.textViewProfileEmail;
        profileEmail.setText(user.getEmail());

        final TextView profileName = binding.textViewProfileName;
        profileName.setText(user.getName());
        final TextView changeName = binding.textViewProfileChangeName;
        changeName.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.layout_dialog, null);
            builder.setView(view);
            builder.setTitle("Enter your new name");
            builder.setPositiveButton("Change", (dialog, which) -> {
                EditText newValueEditText = view.findViewById(R.id.editTextDialog);
                String newValue = newValueEditText.getText().toString();
                user.setName(newValue);
                userDao.update(user);
                profileName.setText(newValue);
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> {
            });
            builder.show();
        });

        final TextView profileUsername = binding.textViewProfileUsername;
        profileUsername.setText(user.getUsername());
        final TextView changeUsername = binding.textViewProfileChangeUsername;
        changeUsername.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.layout_dialog, null);
            builder.setView(view);
            builder.setTitle("Enter your new username");
            builder.setPositiveButton("Change", (dialog, which) -> {
                EditText newValueEditText = view.findViewById(R.id.editTextDialog);
                String newValue = newValueEditText.getText().toString();
                user.setUsername(newValue);
                userDao.update(user);
                profileUsername.setText(newValue);
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> {
            });
            builder.show();
        });

        Button logoutButton = binding.buttonLogout;
        logoutButton.setOnClickListener(view -> {
            SharedPreferences settings = getActivity().getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.remove("currentUser");
            editor.apply();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}