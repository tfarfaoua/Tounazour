package tounazour.tounazour.models;

import org.junit.jupiter.api.Test;
import tounazour.tounazour.models.Commande;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandeTest {

    @Test
    public void testCommandeGettersAndSetters() {
        // Create a Commande object
        Commande commande = new Commande("SupplierX", "2023-12-11", "ProductY", 15, 123);

        // Test getters
        assertEquals("SupplierX", commande.getName());
        assertEquals("2023-12-11", commande.getDate());
        assertEquals("ProductY", commande.getProduit());
        assertEquals(15, commande.getQuantite());
        assertEquals(123, commande.getId());

        // Test setters
        commande.setName("UpdatedSupplier");
        commande.setDate("2024-01-01");
        commande.setProduit("UpdatedProduct");
        commande.setQuantite(20);
        commande.setId(456);

        assertEquals("UpdatedSupplier", commande.getName());
        assertEquals("2024-01-01", commande.getDate());
        assertEquals("UpdatedProduct", commande.getProduit());
        assertEquals(20, commande.getQuantite());
        assertEquals(456, commande.getId());
    }
}
