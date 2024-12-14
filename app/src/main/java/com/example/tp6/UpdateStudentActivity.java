package com.example.tp6;

// UpdateStudentActivity.java
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateStudentActivity extends AppCompatActivity {
    private EditText idEditText, markEditText;
    private Database_Controller dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        dbHelper = new Database_Controller(this);
        idEditText = findViewById(R.id.idEditText);
        markEditText = findViewById(R.id.markEditText);
        Button updateButton = findViewById(R.id.updateButton);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(idEditText.getText().toString());
                double newMark = Double.parseDouble(markEditText.getText().toString());

                if (dbHelper.updateStudent(id, newMark)) {
                    Toast.makeText(UpdateStudentActivity.this, "Student Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateStudentActivity.this, "Error Updating Student", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

