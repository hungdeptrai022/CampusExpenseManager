package com.example.connectdb.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.connectdb.Model.Expense;
import com.example.connectdb.R;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {
    private List<Expense> expenseList;

    // Constructor to pass the expense list
    public ExpenseAdapter(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }
    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each expense item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_item, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        // Bind the data to each item view
        Expense expense = expenseList.get(position);
        holder.tvName.setText(expense.getName());
        holder.tvBudget.setText(String.format("Budget: %.2f", expense.getBudget()));
        holder.tvRemaining.setText(String.format("Remaining: %.2f", expense.getRemaining()));
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    // ViewHolder class to hold the views for each item
    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvBudget, tvRemaining;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvBudget = itemView.findViewById(R.id.tvBudget);
            tvRemaining = itemView.findViewById(R.id.tvRemaining);
        }
    }
}
