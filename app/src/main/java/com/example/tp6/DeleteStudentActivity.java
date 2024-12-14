package com.example.tp6;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DeleteStudentActivity extends AppCompatActivity {
    private EditText idEditText;
    private Database_Controller dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_student);

        dbHelper = new Database_Controller(this);
        idEditText = findViewById(R.id.idEditText);
        Button deleteButton = findViewById(R.id.deleteButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idText = idEditText.getText().toString();

                if (idText.isEmpty()) {
                    Toast.makeText(DeleteStudentActivity.this, "Please enter a student ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                int id = Integer.parseInt(idText);

                // Show confirmation dialog
                new AlertDialog.Builder(DeleteStudentActivity.this)
                        .setTitle("Delete Confirmation")
                        .setMessage("Are you sure you want to delete this student?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            // Proceed with deletion if user confirms
                            if (dbHelper.deleteStudent(id)) {
                                Toast.makeText(DeleteStudentActivity.this, "Student Deleted", Toast.LENGTH_SHORT).show();
                                idEditText.setText(""); // Clear input field
                            } else {
                                Toast.makeText(DeleteStudentActivity.this, "Error Deleting Student", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            // Dismiss dialog if user cancels
                            dialog.dismiss();
                        })
                        .show();
            }
        });
    }
}
