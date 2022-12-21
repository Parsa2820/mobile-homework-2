package ir.parsa2820.kingdomquiz.ui.scoreboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.parsa2820.kingdomquiz.R;
import ir.parsa2820.kingdomquiz.model.User;

public class ScoreboardAdapter extends RecyclerView.Adapter<ScoreboardAdapter.ViewHolder> {
    private final List<User> localDataSet;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()) .inflate(R.layout.scoreboard_row_item, parent, false);

        return new ScoreboardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getScoreboardPositionTextView().setText(String.valueOf(position+1));
        holder.getScoreboardUserEmailTextView().setText(localDataSet.get(position).getEmail());
        if (position < 3) {
            holder.getScoreboardUserEmailTextView().setTextColor(0xFFD4AF37);
        }
        holder.getScoreBoardUserScoreTextView().setText(String.valueOf(localDataSet.get(position).getScore()));
        holder.getScoreboardUserEmailTextView().setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            LayoutInflater layoutInflater = LayoutInflater.from(v.getContext());
            View view = layoutInflater.inflate(R.layout.layout_score_dialog, null);
            ((TextView) view.findViewById(R.id.textViewScoreboardDialogEmail)).setText(localDataSet.get(position).getEmail());
            ((TextView) view.findViewById(R.id.textViewScoreboardDialogName)).setText(String.valueOf(localDataSet.get(position).getName()));
            ((TextView) view.findViewById(R.id.textViewScoreboardDialogNumber)).setText(String.valueOf(localDataSet.get(position).getNumber()));
            ((TextView) view.findViewById(R.id.textViewScoreboardDialogUsername)).setText(String.valueOf(localDataSet.get(position).getUsername()));
            ((TextView) view.findViewById(R.id.textViewScoreboardDialogScore)).setText(String.valueOf(localDataSet.get(position).getScore()));
            builder.setView(view);
            builder.setTitle("User Info");
            builder.setCancelable(true);
            builder.show();
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView socreboardPositionTextView;
        private TextView scoreboardUserEmailTextView;
        private TextView scoreBoardUserScoreTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            socreboardPositionTextView = itemView.findViewById(R.id.textViewScoreboardPosition);
            scoreboardUserEmailTextView = itemView.findViewById(R.id.textViewScoreboardUserEmail);
            scoreBoardUserScoreTextView = itemView.findViewById(R.id.textViewScoreboardUserScore);
        }

        public TextView getScoreboardPositionTextView() {
            return socreboardPositionTextView;
        }

        public TextView getScoreboardUserEmailTextView() {
            return scoreboardUserEmailTextView;
        }

        public TextView getScoreBoardUserScoreTextView() {
            return scoreBoardUserScoreTextView;
        }
    }

    public ScoreboardAdapter(List<User> dataSet) {
        localDataSet = dataSet;
    }
}
