package com.example.studentmanagesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InsertClassActivity extends AppCompatActivity {

    Button btnSaveClass, btnClearClass, btnCloseClass;
    EditText edtClassName, edtClassCode, edtClassNumber;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_class);

        initWidget();
        btnSaveClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = saveClass();
                Bundle bundle = new Bundle();
                Intent intent = getIntent();
                if (id != -1) {
                    Room r = new Room(id + "",
                            edtClassCode.getText().toString(),
                            edtClassName.getText().toString(),
                            edtClassNumber.getText().toString());
                    bundle.putSerializable("room", r);
                    intent.putExtra("data", bundle);
                    setResult(ClassListActivity.SAVE_CLASS, intent);
                    Toast.makeText(getApplicationContext(),
                            "Thêm lớp học thành công",
                            Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
        btnClearClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearClass();
            }
        });
        btnCloseClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notify.exit(InsertClassActivity.this);
            }
        });
    }

    private void initWidget() {
        btnSaveClass = findViewById(R.id.btnSaveInsertClass);
        btnClearClass = findViewById(R.id.btnClearInsertClass);
        btnCloseClass = findViewById(R.id.btnCloseInsertClass);
        edtClassName = findViewById(R.id.edtClassName);
        edtClassCode = findViewById(R.id.edtClassCode);
        edtClassNumber = findViewById(R.id.edtClassNumber);
    }

    private long saveClass() {
        try {
            db = openOrCreateDatabase(LoginActivity.DATABASE_NAME, MODE_PRIVATE, null);
            ContentValues values = new ContentValues();
            values.put("code_class", edtClassCode.getText().toString());
            values.put("name_class", edtClassName.getText().toString());
            values.put("number_student", Integer.parseInt(edtClassNumber.getText().toString()));
            long id = db.insert("tblclass", null, values);
            if (id != -1) {
                return id;
            }
        }
        catch (Exception ex) {
            Toast.makeText(this,
                    "Thêm lớp học không thành công",
                    Toast.LENGTH_LONG).show();
        }
        return -1;
    }

    private void clearClass() {
        edtClassCode.setText("");
        edtClassName.setText("");
        edtClassNumber.setText("");
    }
}