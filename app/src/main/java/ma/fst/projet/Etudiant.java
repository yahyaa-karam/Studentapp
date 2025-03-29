package ma.fst.projet;

public class Etudiant {
    private int id;
    private String nom;
    private String prenom;
    private String dateNaissance;

    public Etudiant() {}
    private String imagePath;


    public String getImagePath() { return imagePath; }
    public void setImagePath(String path) { this.imagePath = path; }

    public Etudiant(int id,String nom, String prenom, String dateNaissance, String imagePath) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.imagePath = imagePath;
    }
    public Etudiant(String nom, String prenom, String dateNaissance, String imagePath) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.imagePath = imagePath;
    }


    public Etudiant(int id, String nom, String prenom, String dateNaissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }




    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }
    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
}
