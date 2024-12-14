package com.example.tp6;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class FirstActivity extends AppCompatActivity {

    // Déclaration de la ListView
    private ListView listView;
    private Database_Controller dbHelper;

    // Déclaration des éléments et des icônes de la liste
    private String[] items = {"Add", "Delete", "Update", "View"};
    private int[] images = {
            R.drawable.add_icon,
            R.drawable.delete_icon,
            R.drawable.update_icon,    // Icône pour "Update"
            R.drawable.view_icon       // Icône pour "View"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        // Initialisation de la base de données
        dbHelper = new Database_Controller(this);

        // Initialisation de la ListView
        listView = findViewById(R.id.listView);

        // Initialisation de l'adaptateur personnalisé
        List_View_Adapter adapter = new List_View_Adapter(this, items, images);
        listView.setAdapter(adapter);

        // Gestion des clics sur les éléments de la liste
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Démarrer une activité différente en fonction de l'élément sélectionné
                switch (position) {
                    case 0:
                        // Ouvrir l'activité pour ajouter un élément
                        Intent addIntent = new Intent(FirstActivity.this, AddStudentActivity.class);
                        startActivity(addIntent);
                        break;
                    case 1:
                        // Ouvrir l'activité pour supprimer un élément
                        Intent deleteIntent = new Intent(FirstActivity.this, DeleteStudentActivity.class);
                        startActivity(deleteIntent);
                        break;
                    case 2:
                        // Ouvrir l'activité pour mettre à jour un élément
                        Intent updateIntent = new Intent(FirstActivity.this, UpdateStudentActivity.class);
                        startActivity(updateIntent);
                        break;
                    case 3: // View
                        showAllStudents();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void showAllStudents() {
        Cursor cursor = dbHelper.getAllStudents();
        if (cursor == null || cursor.getCount() == 0) {
            // Afficher un message si la base de données est vide
            new AlertDialog.Builder(this)
                    .setTitle("No Students Found")
                    .setMessage("The student list is empty.")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }

        StringBuilder builder = new StringBuilder();
        while (cursor.moveToNext()) {
            builder.append("ID: ").append(cursor.getInt(0))
                    .append(", Name: ").append(cursor.getString(1))
                    .append(", Lastname: ").append(cursor.getString(2))
                    .append(", Mark: ").append(cursor.getDouble(3))
                    .append("\n");
        }
        cursor.close(); // Fermeture du curseur après utilisation pour libérer les ressources

        // Affichage de tous les étudiants dans une boîte de dialogue
        new AlertDialog.Builder(this)
                .setTitle("Students")
                .setMessage(builder.toString())
                .setPositiveButton("OK", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close(); // Fermeture de la base de données lors de la destruction de l'activité
        }
    }
}



