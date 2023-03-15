package com.example.studentmanagesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class InsertStudentActivity extends AppCompatActivity {

    Button btnSave, btnClear, btnClose;
    EditText edtCode, edtName, edtAddress, edtBirthday;
    Calendar myCalendar;
    RadioGroup rdigroupGender;
    Spinner spnClassCode;
    SQLiteDatabase db;
//    ArrayList<Student> studentList = new ArrayList<>();
    ArrayList<Room> classList;
//    ArrayAdapter<Student> adapterStudent;
    ArrayAdapter<Room> adapterClass;
    int posSpinner = -1;
    int idChecked, gender = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_student);

        initWidget();
        getClassList();
        spnClassCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posSpinner = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = saveStudent();
                if (id != -1) {
                    Bundle bundle = new Bundle();
                    Intent intent = getIntent();
                    Student student = new Student(classList.get(posSpinner).getId_class(),
                            classList.get(posSpinner).getName_class(),
                            id + "",
                            edtCode.getText().toString(),
                            edtName.getText().toString(),
                            gender + "",
                            edtBirthday.getText().toString(),
                            edtAddress.getText().toString());
                    bundle.putSerializable("student", student);
                    intent.putExtra("data", bundle);
                    setResult(StudentListActivity.SAVE_STUDENT, intent);
                    Toast.makeText(getApplicationContext(),
                            "Thêm sinh viên thành công",
                            Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearStudent();
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notify.exit(InsertStudentActivity.this);
            }
        });
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabel();
            }
        };
        edtBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(InsertStudentActivity.this,
                        date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void initWidget() {
        myCalendar = Calendar.getInstance();
        btnSave = findViewById(R.id.btnSaveInsertStudent);
        btnClear = findViewById(R.id.btnClearInsertStudent);
        btnClose = findViewById(R.id.btnCloseInsertStudent);
        spnClassCode = findViewById(R.id.spnClassCode);
        edtCode = findViewById(R.id.edtStudentCode);
        edtName = findViewById(R.id.edtStudentName);
        edtAddress = findViewById(R.id.edtStudentAddress);
        edtBirthday = findViewById(R.id.edtStudentBirthday);
        rdigroupGender = findViewById(R.id.rdigroupGender);
    }

    private void updateLabel(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        edtBirthday.setText(dateFormat.format(myCalendar.getTime()));
    }

    public void getClassList() {
        try {
            db = openOrCreateDatabase(LoginActivity.DATABASE_NAME, MODE_PRIVATE, null);
            Cursor c = db.query("tblclass", null, null, null,
                    null, null, null);
            c.moveToFirst();
            classList = new ArrayList<>();
            while (!c.isAfterLast()) {
                classList.add(new Room(c.getInt(0) + "",
                        c.getString(1).toString(),
                        c.getString(2).toString(),
                        c.getInt(3) + ""));
                c.moveToNext();
            }
            adapterClass = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1,
                    classList);
            adapterClass.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
            spnClassCode.setAdapter(adapterClass);
        }
        catch (Exception ex) {
            Toast.makeText(getApplicationContext(),
                    "Loi " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private long saveStudent() {
        db = openOrCreateDatabase(LoginActivity.DATABASE_NAME, MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        idChecked = rdigroupGender.getCheckedRadioButtonId();
        if (idChecked == R.id.rdiMale) {
            gender = 1;
        }
        try {
            values.put("id_class", classList.get(posSpinner).getId_class());
            values.put("code_student", edtCode.getText().toString());
            values.put("name_student", edtName.getText().toString());
            values.put("birthday_student", edtBirthday.getText().toString());
            values.put("address_student", edtAddress.getText().toString());
            values.put("gender_student", gender);
            return (db.insert("tblstudent", null, values));
        }
        catch (Exception ex) {
            Toast.makeText(this,
                    "Thêm sinh viên không thành công",
                    Toast.LENGTH_LONG).show();
        }
        return -1;
    }

    private void clearStudent() {
        edtName.setText("");
        edtCode.setText("");
        edtBirthday.setText("");
        edtAddress.setText("");
        spnClassCode.setSelection(0);
    }
}