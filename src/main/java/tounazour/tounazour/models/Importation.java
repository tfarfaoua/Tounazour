package tounazour.tounazour.models;

public class Importation {
    String name ,date,produit;

    int id, quantite,prix;

    public Importation(String name, String date, String produit, int id, int quantite, int prix) {
        this.name = name;
        this.date = date;
        this.produit = produit;
        this.id = id;
        this.quantite = quantite;
        this.prix = prix;
    }

    public Importation(String name, String date, String produit, int quantite, int prix) {
        this.name = name;
        this.date = date;
        this.produit = produit;
        this.quantite = quantite;
        this.prix = prix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }
}
