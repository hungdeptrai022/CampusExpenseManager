package com.example.connectdb;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.connectdb.Adapter.ExpenseAdapter;
import com.example.connectdb.Model.Expense;

import java.util.ArrayList;
import java.util.List;

public class AddExpenseActivity extends AppCompatActivity {
    private EditText etName, etBudget;
    private Button btnAddExpense;
    private RecyclerView recyclerView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        etName = findViewById(R.id.etName);
        etBudget = findViewById(R.id.etBudget);
        btnAddExpense = findViewById(R.id.btnAddExpense);
        recyclerView = findViewById(R.id.recyclerView);
        dbHelper = new DatabaseHelper(this);

        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                double budget = Double.parseDouble(etBudget.getText().toString());
                dbHelper.addExpense(name, budget);
                displayExpenses();
            }
        });
        displayExpenses();
    }
    private void displayExpenses() {
        Cursor cursor = dbHelper.getAllExpenses();
        List<Expense> expenseList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                double budget = cursor.getDouble(2);
                double remaining = cursor.getDouble(3);
                expenseList.add(new Expense(id, name, budget, remaining));
            } while (cursor.moveToNext());
        }
        ExpenseAdapter adapter = new ExpenseAdapter(expenseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
}
