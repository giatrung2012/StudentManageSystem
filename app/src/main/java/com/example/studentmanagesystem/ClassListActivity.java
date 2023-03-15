package com.example.studentmanagesystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ClassListActivity extends AppCompatActivity {

    ListView lvClass;
    Button btnOpenClass;
    ArrayList<Room> classList = new ArrayList<>();
    MyAdapterClass adapterClass;
    SQLiteDatabase db;
    int posselected = -1;
    public static final int OPEN_CLASS = 113;
    public static final int EDIT_CLASS = 114;
    public static final int SAVE_CLASS = 115;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);

        lvClass = findViewById(R.id.lvClass);
        btnOpenClass = findViewById(R.id.btnOpenClass);

        getClassList();
        registerForContextMenu(lvClass);
        btnOpenClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassListActivity.this,
                        InsertClassActivity.class);
                startActivityForResult(intent, OPEN_CLASS);
            }
        });
        lvClass.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                posselected = position;
                return false;
            }
        });
    }

    public void getClassList() {
        try {
            classList.add(new Room("Mã lớp", "Tên lớp", "Sĩ số"));
            db = openOrCreateDatabase(LoginActivity.DATABASE_NAME, MODE_PRIVATE, null);
            Cursor c = db.query("tblclass", null, null, null,
                    null, null, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                classList.add(new Room(c.getInt(0) + "",
                        c.getString(1).toString(),
                        c.getString(2).toString(),
                        c.getInt(3) + ""));
                c.moveToNext();
            }
            adapterClass = new MyAdapterClass(this,
                    R.layout.my_class,
                    classList);
            lvClass.setAdapter(adapterClass);
        }
        catch (Exception ex) {
            Toast.makeText(getApplicationContext(),
                    "Loi " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
    public void confirmDelete() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Thông báo");
//        alertDialogBuilder.setIcon(R.drawable.question);
        alertDialogBuilder.setMessage("Xác nhận xoá lớp học");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db = openOrCreateDatabase(LoginActivity.DATABASE_NAME,
                                MODE_PRIVATE, null);
                        String id_class = classList.get(posselected).getId_class();
                        if (db.delete("tblclass", "id_class=?",
                                new String[] {id_class}) != -1) {
                            classList.remove(posselected);
                            adapterClass.notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(),
                                    "Xoá lớp học thành công",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
        alertDialogBuilder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mnuclass, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnueditclass:
                Room room = classList.get(posselected);
                Bundle bundle = new Bundle();
                Intent intent = new Intent(ClassListActivity.this,
                        EditClassActivity.class);
                bundle.putSerializable("room", room);
                intent.putExtra("data", bundle);
                startActivityForResult(intent, EDIT_CLASS);
                return true;

                case R.id.mnudeleteclass:
                    confirmDelete();
                    return true;

                    case R.id.mnucloseclass:
                        Notify.exit(this);
                        return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case OPEN_CLASS:
                if (resultCode == SAVE_CLASS) {
                    Bundle bundle = data.getBundleExtra("data");
                    Room room = (Room) bundle.getSerializable("room");
                    classList.add(room);
                    adapterClass.notifyDataSetChanged();
                }
                break;

            case EDIT_CLASS:
                if (resultCode == SAVE_CLASS) {
                    Bundle bundle = data.getBundleExtra("data");
                    Room room = (Room) bundle.getSerializable("room");
                    classList.add(posselected, room);
                    adapterClass.notifyDataSetChanged();
                }
                break;
        }
    }
}