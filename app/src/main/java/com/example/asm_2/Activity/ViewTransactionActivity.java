package com.example.connectdb;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.connectdb.Adapter.TransactionAdapter;
import com.example.connectdb.Model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ViewTransactionActivity extends AppCompatActivity {
        private RecyclerView recyclerView;
        private TransactionAdapter adapter;
        private Button btnAddRecord;
        private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transactions);
        recyclerView = findViewById(R.id.rvTransactions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnAddRecord = findViewById(R.id.btnAddRecord);

        dbHelper = new DatabaseHelper(this);

        List<Transaction> transactionList = fetchTransactions();

        adapter = new TransactionAdapter(transactionList);
        recyclerView.setAdapter(adapter);
        btnAddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewTransactionActivity.this, AddTransactionActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<Transaction> fetchTransactions(){
        List<Transaction> transactionList = new ArrayList<>();

        Cursor cursor = dbHelper.getAllTransactions();
        if(cursor.moveToFirst()){
            do {
                String expenseName = cursor.getString(0);
                double amount = cursor.getDouble(1);
                String detail = cursor.getString(2);
                String date = cursor.getString(3);

                transactionList.add(new Transaction(expenseName, amount, detail, date));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return transactionList;
    }


}
