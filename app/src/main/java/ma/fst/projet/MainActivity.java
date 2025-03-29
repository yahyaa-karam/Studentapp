package ma.fst.projet;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText nom, prenom, idEtudiant, dateNaissance;
    private TextView result;
    private ImageView imageView;
    private Button btnChooseImage;

    private Uri imageUri;
    private static final int PICK_IMAGE_REQUEST = 1;

    private EtudiantService es;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        idEtudiant = findViewById(R.id.idEtudiant);
        dateNaissance = findViewById(R.id.dateNaissance);
        result = findViewById(R.id.result);
        imageView = findViewById(R.id.imageView);
        btnChooseImage = findViewById(R.id.btnChooseImage);

        Button btnAjouter = findViewById(R.id.btnAjouter);
        Button btnChercher = findViewById(R.id.btnChercher);
        Button btnSupprimer = findViewById(R.id.btnSupprimer);
        Button btnList = findViewById(R.id.btnList);

        es = new EtudiantService(this);
        calendar = Calendar.getInstance();

        btnChooseImage.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Sélectionner une image"), PICK_IMAGE_REQUEST);
        });

        dateNaissance.setOnClickListener(v -> {
            new DatePickerDialog(MainActivity.this, (view, year, month, day) -> {
                calendar.set(year, month, day);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                dateNaissance.setText(sdf.format(calendar.getTime()));
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        btnAjouter.setOnClickListener(v -> {
            String nomText = nom.getText().toString();
            String prenomText = prenom.getText().toString();
            String date = dateNaissance.getText().toString();
            String imagePath = imageUri != null ? imageUri.toString() : "";

            if (!nomText.isEmpty() && !prenomText.isEmpty()) {
                // Create a new Etudiant object without the ID since it's auto-generated
                es.create(new Etudiant(nomText, prenomText, date, imagePath));
                nom.setText("");
                prenom.setText("");
                dateNaissance.setText("");
                imageView.setImageResource(R.drawable.default_avatar); // Reset image
                result.setText("Etudiant ajouté !");
            } else {
                result.setText("Veuillez remplir tous les champs");
            }
        });

        btnChercher.setOnClickListener(v -> {
            String idText = idEtudiant.getText().toString();
            if (!idText.isEmpty()) {
                int id = Integer.parseInt(idText);
                Etudiant e = es.findById(id);
                if (e != null) {
                    result.setText(e.getNom() + " " + e.getPrenom() + " (" + e.getDateNaissance() + ")");
                } else {
                    result.setText("Etudiant non trouvé");
                }
            } else {
                result.setText("Veuillez entrer l'ID");
            }
        });

        btnSupprimer.setOnClickListener(v -> {
            String idText = idEtudiant.getText().toString();
            if (!idText.isEmpty()) {
                int id = Integer.parseInt(idText);
                Etudiant e = es.findById(id);
                if (e != null) {
                    es.delete(e);
                    result.setText("Etudiant supprimé");
                } else {
                    result.setText("Etudiant introuvable");
                }
            } else {
                result.setText("Veuillez entrer l'ID");
            }
        });

        btnList.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StudentListActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
