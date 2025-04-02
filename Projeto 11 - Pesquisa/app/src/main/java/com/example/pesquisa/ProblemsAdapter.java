package com.example.pesquisa;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProblemsAdapter extends RecyclerView.Adapter<ProblemsAdapter.ViewHolder> {
    private List<ProblemItem> problems;
    private Context context;

    public ProblemsAdapter(Context context) {
        this.context = context;
        this.problems = new ArrayList<>();
    }

    public void setProblems(List<ProblemItem> newProblems) {
        this.problems = newProblems;
        notifyDataSetChanged();
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_problem_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProblemItem problem = problems.get(position);

        holder.problemName.setText(problem.getName());
        holder.reportCount.setText(String.valueOf(problem.getCount()));

        // Calcular porcentagem
        int total = problems.stream().mapToInt(ProblemItem::getCount).sum();
        if (total > 0) {
            int percentage = (problem.getCount() * 100) / total;
            holder.percentageText.setText(percentage + "%");

            // Atualizar a largura da barra de progresso
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.progressBar.getLayoutParams();
            params.weight = percentage;
            holder.progressBar.setLayoutParams(params);
        }

        // Definir cor baseada na posição (opcional)
        int colorRes = getColorForPosition(position);
        holder.progressBar.setBackgroundColor(ContextCompat.getColor(context, colorRes));
    }

    @Override
    public int getItemCount() {
        return problems.size();
    }

    private int getColorForPosition(int position) {
        // Cores alternadas para melhor visualização
        int[] colors = {
                R.color.problem_1,
                R.color.problem_2,
                R.color.problem_3,
                R.color.problem_4,
                R.color.problem_5,
                R.color.problem_6
        };
        return colors[position % colors.length];
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView problemName;
        TextView reportCount;
        TextView percentageText;
        View progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            problemName = itemView.findViewById(R.id.problem_name);
            reportCount = itemView.findViewById(R.id.report_count);
            percentageText = itemView.findViewById(R.id.percentage_text);
            progressBar = itemView.findViewById(R.id.progress_bar);
        }
    }

    public static class ProblemItem {
        private String name;
        private int count;

        public ProblemItem(String name, int count) {
            this.name = name;
            this.count = count;
        }

        public String getName() { return name; }
        public int getCount() { return count; }
    }
}
