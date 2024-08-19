package com.example.connectdb;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.SurfaceControl;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.connectdb.Adapter.TransactionAdapter;
import com.example.connectdb.Model.Expense;
import com.example.connectdb.Model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class AddTransactionActivity extends AppCompatActivity {
    private Spinner spinnerExpenseName;
    private EditText etTransactionMoney, etTransactionDetail, etTransactionDate;
    private Button btnSaveTransaction;
    private DatabaseHelper dbHelper; // Assuming you have a DBHelper class for database interactions
    private List<Expense> expenseList; // List of expenses to populate spinner

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        spinnerExpenseName = findViewById(R.id.spinnerExpenseName);
        etTransactionMoney = findViewById(R.id.etTransactionAmount);
        etTransactionDetail = findViewById(R.id.etTransactionDetail);
        etTransactionDate = findViewById(R.id.etTransactionDate);
        btnSaveTransaction = findViewById(R.id.btnSaveTransaction);
        dbHelper = new DatabaseHelper(this);

        loadExpenseNames();
        btnSaveTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTransaction();
                Intent intent = new Intent(AddTransactionActivity.this, ViewTransactionActivity.class );
                startActivity(intent);
            }
        });
//        displayTransactions();
    }

//    private void displayTransactions() {
//        Cursor cursor = dbHelper.getTransactionsForExpense(expenseId);
//        List<Transaction> transactionList = new ArrayList<>();
//        if (cursor.moveToFirst()) {
//            do {
//                int id = cursor.getInt(0);
//                double amount = cursor.getDouble(2);
//                String date = cursor.getString(3);
//                transactionList.add(new Transaction(id, expenseId, amount, date));
//            } while (cursor.moveToNext());
//        }
//
//
//        TransactionAdapter adapter = new TransactionAdapter(transactionList);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
//    }
    private void loadExpenseNames() {
        List<String> expenseNames = dbHelper.getAllExpenseNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, expenseNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExpenseName.setAdapter(adapter);
    }
    private void addTransaction() {
        String expenseName = spinnerExpenseName.getSelectedItem().toString();
        double amount = Double.parseDouble(etTransactionMoney.getText().toString());
        String detail = etTransactionDetail.getText().toString();
        String date = etTransactionDate.getText().toString();

        // Insert the transaction into the database
        dbHelper.addTransaction(expenseName, amount, detail, date);

        // Update the remaining budget of the corresponding expense
        dbHelper.updateExpenseRemainingBudget(expenseName, amount);

        Toast.makeText(this, "Transaction added successfully!", Toast.LENGTH_SHORT).show();

        // Clear the form
        etTransactionMoney.setText("");
        etTransactionDetail.setText("");
        etTransactionDate.setText("");
    }
}
