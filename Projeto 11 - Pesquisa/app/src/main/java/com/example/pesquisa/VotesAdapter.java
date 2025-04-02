package com.example.pesquisa;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pesquisa.R;

import java.util.ArrayList;
import java.util.List;

public class VotesAdapter extends RecyclerView.Adapter<VotesAdapter.ViewHolder> {
    private List<VoteItem> votes;
    private final boolean isSpontaneous;

    public VotesAdapter(boolean isSpontaneous) {
        this.isSpontaneous = isSpontaneous;
        this.votes = new ArrayList<>();
    }

    public void setVotes(List<VoteItem> newVotes) {
        this.votes = newVotes;
        notifyDataSetChanged();
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vote_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VoteItem vote = votes.get(position);

        holder.candidateName.setText(vote.getName());
        holder.voteCount.setText(String.valueOf(vote.getCount()));

        if (!isSpontaneous) {
            // Para votos estimulados, podemos mostrar a foto do candidato
            // holder.candidateImage.setImageResource(vote.getImageResId());
        }

        // Calcular porcentagem
        int total = votes.stream().mapToInt(VoteItem::getCount).sum();
        if (total > 0) {
            int percentage = (vote.getCount() * 100) / total;
            holder.percentageText.setText(percentage + "%");
        }
    }

    @Override
    public int getItemCount() {
        return votes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView candidateName;
        TextView voteCount;
        TextView percentageText;
        // ImageView candidateImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            candidateName = itemView.findViewById(R.id.candidate_name);
            voteCount = itemView.findViewById(R.id.vote_count);
            percentageText = itemView.findViewById(R.id.percentage_text);
            // candidateImage = itemView.findViewById(R.id.candidate_image);
        }
    }

    public static class VoteItem {
        private String name;
        private int count;
        private int imageResId;

        public VoteItem(String name, int count) {
            this.name = name;
            this.count = count;
        }

        // Getters
        public String getName() { return name; }
        public int getCount() { return count; }
        public int getImageResId() { return imageResId; }
    }
}