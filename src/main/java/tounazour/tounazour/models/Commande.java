package tounazour.tounazour.models;

public class Commande {
    String name ,date,produit;
    int id  ,quantite;

    public Commande(String name, String date, String produit, int quantite, int id) {
        this.name = name;
        this.date = date;
        this.produit = produit;
        this.quantite = quantite;
        this.id = id;
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

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
