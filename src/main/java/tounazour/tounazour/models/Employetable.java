package tounazour.tounazour.models;

public class Employetable {
    int id ;
    String nom, cin, tel ,ferme;

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setFerme(String ferme) {
        this.ferme = ferme;
    }



    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getCin() { return cin;
    }

    public String getTel() {
        return tel;
    }
    public String getFerme() {
        return ferme;
    }



    public Employetable(int id, String nom, String cin, String tel,String ferme) {
        this.id = id;
        this.nom = nom;
        this.cin = cin;
        this.tel = tel;
        this.ferme=ferme;
    }
}

