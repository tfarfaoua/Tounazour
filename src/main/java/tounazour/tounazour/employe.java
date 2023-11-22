package tounazour.tounazour;
public class employe {
    int id;
    String nom;
    String cin;
    String tel;
    String ferme;

    public employe(){
        super();
    }

    public employe(int id, String nom, String cin,String tel,String ferme){
        this.id=id;
        this.nom=nom;
        this.cin=cin;
        this.tel=tel;
        this.ferme=ferme;
    }
    public String getFerme() {
        return ferme;
    }

    public void setFerme(String tel) {
        this.ferme= ferme;
    }
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String prenom) {
        this.cin= cin;
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
}

