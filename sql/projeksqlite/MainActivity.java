package com.indah.projeksqlite;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;
public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editNama, editKelas, editNohp, editTextnim, editemail;
    Button btnAddData;
    Button btnViewAll;
    Button btnUpdate;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        editNama = (EditText)findViewById(R.id.editText_nama);
        editKelas = (EditText)findViewById(R.id.editText_kelas);
        editNohp = (EditText)findViewById(R.id.editText_nohp);
        editTextnim = (EditText)findViewById(R.id.editTextnim);
        editemail = (EditText)findViewById(R.id.editText_email);
        btnAddData = (Button)findViewById(R.id.button_add);
        btnViewAll = (Button)findViewById(R.id.button_view);
        btnUpdate = (Button)findViewById(R.id.button_update);
        btnDelete = (Button)findViewById(R.id.button_delete);
        AddData();
        viewAll();
        UpdateData();
        deleteData();
    }

    //fungsi hapus
    public void deleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Integer deletedRows = myDb.deleteData(editTextnim.getText().toString());

                        if (deletedRows > 0)

                            Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();

                        else
                            Toast.makeText(MainActivity.this,"Data Failed to Deleted!",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    //fungsi update
    public void UpdateData() {
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        boolean isUpdate = myDb.updateData(editTextnim.getText().toString(),

                                editNama.getText().toString(),
                                editKelas.getText().toString(),
                                editNohp.getText().toString(),
                                editemail.getText().toString());
                        if(isUpdate == true)

                            Toast.makeText(MainActivity.this,"Data Updated",Toast.LENGTH_LONG).show();

                        else
                            Toast.makeText(MainActivity.this,"Data Failed toUpdate",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    //fungsi tambah
    public void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @SuppressLint("SuspiciousIndentation")
                    @Override
                    public void onClick(View v) {

                        boolean isInserted = myDb.insertData(editTextnim.getText().toString(),

                                editNama.getText().toString(),
                                editKelas.getText().toString(),
                                editNohp.getText().toString(),
                                editemail.getText().toString());

                        if(isInserted == true)
                            Toast.makeText(MainActivity.this,"DataIserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data Not Iserted",Toast.LENGTH_LONG).show();
                                    editNama.setText("");
                        editKelas.setText("");
                        editTextnim.setText("");
                        editNohp.setText("");
                        editemail.setText("");
                    }
                }
        );
    }

    //fungsi menampilkan data
    public void viewAll() {
        btnViewAll.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
// show message

                            showMessage("Error","Noting Found");

                            return;
                        }

                        StringBuffer buffer = new StringBuffer();

                        while (res.moveToNext() ) {
                            buffer.append("NIM :"+ res.getString(0)+"\n");
                            buffer.append("NAMA :"+ res.getString(1)+"\n");
                            buffer.append("KELAS :"+ res.getString(2)+"\n");
                            buffer.append("NOHP:"+ res.getString(3)+"\n");
                            buffer.append("EMAIL :"+

                                    res.getString(4)+"\n\n");
                        }

// show all data

                        showMessage("Data Mahasiswa",buffer.toString());
                    }
                }
        );
    }

    //membuat alert dialog
    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
