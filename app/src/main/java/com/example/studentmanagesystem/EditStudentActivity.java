package com.example.studentmanagesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class EditStudentActivity extends AppCompatActivity {

    Button btnSave, btnClear, btnClose;
    EditText edtCode, edtName, edtAddress, edtBirthday;
    RadioGroup rdigroupGender;
    RadioButton rdiMale, rdiFemale;
    Spinner spnClassCode;
    SQLiteDatabase db;
//    ArrayList<Student> studentList = new ArrayList<>();
    ArrayList<Room> classList = new ArrayList<>();
//    ArrayAdapter<Student> adapterStudent;
    ArrayAdapter<Room> adapterClass;
    int posSpinner = -1;
    int idChecked, gender = 0;
    String id_student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);

        initWidget();
        getClassList();
        getData();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (saveStudent()) {
                    Intent intent = getIntent();
                    Bundle bundle = new Bundle();
                    Student student = new Student(classList.get(posSpinner).getId_class(),
                            classList.get(posSpinner).getName_class(),
                            id_student + "",
                            edtCode.getText().toString(),
                            edtName.getText().toString(),
                            gender + "",
                            edtBirthday.getText().toString(),
                            edtAddress.getText().toString());
                    bundle.putSerializable("student", student);
                    intent.putExtra("data", bundle);
                    setResult(StudentListActivity.EDIT_STUDENT, intent);
                    Toast.makeText(getApplicationContext(),
                            "Cập nhật sinh viên thành công",
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
                Notify.exit(EditStudentActivity.this);
            }
        });
        spnClassCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posSpinner = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                posSpinner = -1;
            }
        });
    }

    private void initWidget() {
        btnSave = findViewById(R.id.btnSaveEditStudent);
        btnClear = findViewById(R.id.btnClearEditStudent);
        btnClose = findViewById(R.id.btnCloseEditStudent);
        spnClassCode = findViewById(R.id.spnClassCode);
        edtCode = findViewById(R.id.edtStudentCode);
        edtName = findViewById(R.id.edtStudentName);
        edtAddress = findViewById(R.id.edtStudentAddress);
        edtBirthday = findViewById(R.id.edtStudentBirthday);
        rdigroupGender = findViewById(R.id.rdigroupGender);
        rdiMale = findViewById(R.id.rdiMale);
        rdiFemale = findViewById(R.id.rdiFemale);
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

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        Student student = (Student) bundle.getSerializable("student");
        edtCode.setText(student.getCode_student());
        edtName.setText(student.getName_student());
        edtBirthday.setText(student.getBirthday_student());
        edtAddress.setText(student.getAddress_student());
        id_student = student.getId_student();
        if (student.getGender_student().contains("1")) {
            rdiMale.setChecked(true);
        }
        else {
            rdiFemale.setChecked(true);
        }
        int i = 0;
        while (i < classList.size()) {
            if (student.getId_class().contains(classList.get(i).getId_class())) {
                break;
            }
            i++;
        }
        spnClassCode.setSelection(i);
    }

    private boolean saveStudent() {
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
            if (db.update("tblstudent",
                    values,
                    "id_student=?",
                    new String[] {id_student}) != -1) {
                return true;
            }
        }
        catch (Exception ex) {
            Toast.makeText(this,
                    "Cập nhật lớp học không thành công",
                    Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private void clearStudent() {
        edtName.setText("");
        edtCode.setText("");
        edtBirthday.setText("");
        edtAddress.setText("");
        spnClassCode.setSelection(0);
    }
}