package com.example.studentmanagesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    public static final String DATABASE_NAME = "student.db";
    SQLiteDatabase db;
    EditText edtUsername, edtPassword;
    Button btnCloseLogin, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnCloseLogin = findViewById(R.id.btnCloseLogin);
        btnLogin = findViewById(R.id.btnLogin);

        initDB();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                if (username.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Xin vui lòng nhập tài khoản",
                            Toast.LENGTH_LONG).show();
                    edtUsername.requestFocus();
                }
                else if (password.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Xin vui lòng nhập mật khẩu",
                            Toast.LENGTH_LONG).show();
                    edtUsername.requestFocus();
                }
                else if (isUser(username, password)) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),
                            "Tài khoản hoặc mật khẩu không tồn tại",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnCloseLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initDB() {
        db = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        String sql = "";
        try {
            if (!isTableExists(db, "tbluser")) {
                sql += "CREATE TABLE tbluser (id_user INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,";
                sql += "username TEXT NOT NULL,";
                sql += "password TEXT NOT NULL)";
                db.execSQL(sql);
                sql = "insert into tbluser(username, password) values('admin', 'admin')";
                db.execSQL(sql);
            }
            if (!isTableExists(db, "tblclass")) {
                sql += "CREATE TABLE tblclass (";
                sql += "id_class INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,";
                sql += "code_class TEXT,";
                sql += "name_class TEXT,";
                sql += "number_student INTEGER);";
                db.execSQL(sql);
            }
            if (!isTableExists(db, "tblstudent")) {
                sql += "CREATE TABLE tblstudent (";
                sql += "id_student INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,";
                sql += "id_class INTEGER NOT NULL,";
                sql += "code_student TEXT NOT NULL,";
                sql += "name_student TEXT,";
                sql += "gender_student NUMBERIC,";
                sql += "birthday_student TEXT,";
                sql += "address_student TEXT);";
                db.execSQL(sql);
            }
        }
        catch (Exception ex) {
            Toast.makeText(this,
                    "Khởi tạo CSDL không thành công",
                    Toast.LENGTH_LONG).show();
        }
    }

    private boolean isTableExists(SQLiteDatabase database, String tableName) {
        Cursor cursor = database.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name " +
                "= '" + tableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    private boolean isUser(String username, String password) {
        try {
            db = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            Cursor c = db.rawQuery("select * from tbluser where username = ? and password = ?",
                    new String[] {username, password});
            c.moveToFirst();
            if (c.getCount() > 0) {
                return true;
            }
        }
        catch (Exception ex) {
            Toast.makeText(this,
                    "Lỗi hệ thống",
                    Toast.LENGTH_LONG).show();
        }
        return false;
    }
}