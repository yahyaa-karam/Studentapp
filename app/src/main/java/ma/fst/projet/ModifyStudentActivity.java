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

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ModifyStudentActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText nom, prenom, dateNaissance;
    private Button btnSave, btnChooseImage;
    private ImageView imageView;
    private EtudiantService es;
    private int studentId;
    private Calendar calendar;
    private Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_student);

        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        dateNaissance = findViewById(R.id.dateNaissance);
        btnSave = findViewById(R.id.btnSave);
        imageView = findViewById(R.id.imageView);
        btnChooseImage = findViewById(R.id.btnChooseImage);

        es = new EtudiantService(this);
        calendar = Calendar.getInstance();

        // Handle image picking
        btnChooseImage.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Choisir une image"), PICK_IMAGE_REQUEST);
        });

        // Open calendar on date field click
        dateNaissance.setOnClickListener(v -> {
            new DatePickerDialog(ModifyStudentActivity.this, (view, year, month, dayOfMonth) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        // Load student data
        studentId = getIntent().getIntExtra("studentId", -1);
        Etudiant e = es.findById(studentId);
        if (e != null) {
            nom.setText(e.getNom());
            prenom.setText(e.getPrenom());
            dateNaissance.setText(e.getDateNaissance());

            if (e.getImagePath() != null && !e.getImagePath().isEmpty()) {
                imageUri = Uri.parse(e.getImagePath());
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        btnSave.setOnClickListener(v -> {
            String newNom = nom.getText().toString().trim();
            String newPrenom = prenom.getText().toString().trim();
            String newDateNaissance = dateNaissance.getText().toString().trim();
            String imagePath = imageUri != null ? imageUri.toString() : null;

            Etudiant updated = new Etudiant(studentId, newNom, newPrenom, newDateNaissance, imagePath);

            es.update(updated);

            finish();
        });
    }

    private void updateLabel() {
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        dateNaissance.setText(sdf.format(calendar.getTime()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
