package ma.fst.projet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class StudentListActivity extends AppCompatActivity {

    private EtudiantService es;
    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private List<Etudiant> etudiants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        es = new EtudiantService(this);

        loadStudents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadStudents();
    }

    private void loadStudents() {
        etudiants = es.findAll();
        adapter = new StudentAdapter(this, etudiants, this::showOptionsDialog);
        recyclerView.setAdapter(adapter);
    }

    private void showOptionsDialog(Etudiant etudiant) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Options pour " + etudiant.getNom() + " " + etudiant.getPrenom());
        builder.setItems(new CharSequence[]{"Modifier", "Supprimer"}, (dialog, which) -> {
            if (which == 0) {
                Intent intent = new Intent(this, ModifyStudentActivity.class);
                intent.putExtra("studentId", etudiant.getId());
                startActivity(intent);
            } else {
                es.delete(etudiant);
                onResume();
            }
        });
        builder.setNegativeButton("Annuler", null);
        builder.show();
    }
}
