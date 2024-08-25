package com.example.campusexpensemanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.SurfaceControl;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusexpensemanager.Adapter.TransactionAdapter;
import com.example.campusexpensemanager.DatabaseSQLite.DatabaseHelper;
import com.example.campusexpensemanager.Models.Categories;
import com.example.campusexpensemanager.Models.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddTransactionActivity extends AppCompatActivity {
    private Spinner spinnerExpenseName;
    private EditText etTransactionMoney, etTransactionDetail, etTransactionDate;
    private Button btnSaveTransaction, btnReturn;
    private Calendar calendar;
    private DatabaseHelper dbHelper; // Assuming you have a DBHelper class for database interactions
    private List<Categories> expenseList; // List of expenses to populate spinner

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        btnReturn = findViewById(R.id.btnReturnFromAddSpend);

        spinnerExpenseName = findViewById(R.id.spinnerCategories);
        etTransactionMoney = findViewById(R.id.et_transaction_amount);
        etTransactionDetail = findViewById(R.id.edtDescription1);
        etTransactionDate = findViewById(R.id.et_date);
        btnSaveTransaction = findViewById(R.id.btnAddTransaction);
        calendar = Calendar.getInstance();

        etTransactionDate.setInputType(InputType.TYPE_NULL);
        etTransactionDate.setFocusable(false);
        etTransactionDate.setClickable(true);

        etTransactionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTransactionActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
                        calendar.set(year, monthOfYear, dayOfMonth);
                        String dateString = dateFormat.format(calendar.getTime());
                        etTransactionDate.setText(dateString);
                    }
                },year,month,day);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        dbHelper = new DatabaseHelper(this);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");
        String phone = intent.getStringExtra("phone");
        String pass = intent.getStringExtra("pass");
        loadExpenseNames();

        btnSaveTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTransaction();
            }
        });
//        displayTransactions();
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTransactionActivity.this, ViewTransactionActivity.class);
                intent.putExtra("username",username);
                intent.putExtra("email",email);
                intent.putExtra("phone", phone);
                intent.putExtra("pass",pass);
                startActivity(intent);
            }
        });
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
        String amountText = etTransactionMoney.getText().toString().trim();
        String detail = etTransactionDetail.getText().toString();
        String date = etTransactionDate.getText().toString();
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");
        String phone = intent.getStringExtra("phone");
        String pass = intent.getStringExtra("pass");



        if (expenseName.isEmpty()) {
            Toast.makeText(this, "Please select an expense category.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (amountText.isEmpty()) {
            etTransactionMoney.setError("Please enter the amount.");
            etTransactionMoney.requestFocus();
            return;
        }

        double amount;

        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            etTransactionMoney.setError("Invalid amount.");
            etTransactionMoney.requestFocus();
            return;
        }

        if (amount <= 0) {
            etTransactionMoney.setError("Amount must be greater than zero.");
            etTransactionMoney.requestFocus();
            return;
        }
        double remainingBudget = dbHelper.getRemainingBudget(expenseName);
        if (amount > remainingBudget){
            Toast.makeText(this,"Transaction amount exceeds the remaining budget!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (date.isEmpty()) {
            etTransactionDate.setError("Please enter a date.");
            etTransactionDate.requestFocus();
            return;
        }

        if (detail.isEmpty()) {
            detail = "No details provided"; // Set a default value if no detail is provided
        }

        // Insert the transaction into the database
        dbHelper.addTransaction(expenseName, amount, detail, date);

        // Update the remaining budget of the corresponding expense
        dbHelper.updateExpenseRemainingBudget(expenseName, amount);

        Toast.makeText(this, "Transaction added successfully!", Toast.LENGTH_SHORT).show();

        // Clear the form
        etTransactionMoney.setText("");
        etTransactionDetail.setText("");
        etTransactionDate.setText("");

        // Return to ViewTransactionActivity and notify that data was updated
        Intent resultIntent = new Intent();
        intent.putExtra("username",username);
        intent.putExtra("email",email);
        intent.putExtra("phone", phone);
        intent.putExtra("pass",pass);

        setResult(RESULT_OK, resultIntent);
        finish(); // Close AddTransactionActivity
    }
}