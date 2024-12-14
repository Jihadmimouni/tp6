package com.example.tp6;

// AddStudentActivity.java
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddStudentActivity extends AppCompatActivity {
    private EditText nameEditText, lastnameEditText, markEditText;
    private Database_Controller dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        dbHelper = new Database_Controller(this);
        nameEditText = findViewById(R.id.nameEditText);
        lastnameEditText = findViewById(R.id.lastnameEditText);
        markEditText = findViewById(R.id.markEditText);
        Button addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String lastname = lastnameEditText.getText().toString();
                double mark = Double.parseDouble(markEditText.getText().toString());

                if (dbHelper.addStudent(name, lastname, mark)) {
                    Toast.makeText(AddStudentActivity.this, "Student Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddStudentActivity.this, "Error Adding Student", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

