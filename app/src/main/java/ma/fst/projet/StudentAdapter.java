package ma.fst.projet;
import android.widget.ImageView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.graphics.BitmapFactory;
import java.io.File;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<Etudiant> etudiants;
    private Context context;

    public interface OnStudentClickListener {
        void onStudentClick(Etudiant etudiant);
    }

    private OnStudentClickListener listener;

    public StudentAdapter(Context context, List<Etudiant> etudiants, OnStudentClickListener listener) {
        this.context = context;
        this.etudiants = etudiants;
        this.listener = listener;
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView id, nom, prenom, dateNaissance;

        public StudentViewHolder(View view) {
            super(view);
            photo = view.findViewById(R.id.photo);
            id = view.findViewById(R.id.id);
            nom = view.findViewById(R.id.nom);
            prenom = view.findViewById(R.id.prenom);
            dateNaissance = view.findViewById(R.id.dateNaissance);
        }
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        Etudiant e = etudiants.get(position);
        holder.id.setText(String.valueOf(e.getId()));
        holder.nom.setText(e.getNom());
        holder.prenom.setText(e.getPrenom());
        holder.dateNaissance.setText(e.getDateNaissance().isEmpty() ? "-" : e.getDateNaissance());

        if (e.getImagePath() != null && !e.getImagePath().isEmpty()) {
            Uri imageUri = Uri.parse(e.getImagePath());
            holder.photo.setImageURI(imageUri);
        } else {
            holder.photo.setImageResource(R.drawable.ic_person); // fallback
        }

        holder.itemView.setOnClickListener(v -> listener.onStudentClick(e));
    }

    @Override
    public int getItemCount() {
        return etudiants.size();
    }
}
