package tounazour.tounazour.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExportationTest {

    @Test
    public void testExportationClass() {
        // Create an instance of Exportation
        Exportation exportation = new Exportation("Name", "2023-12-11", "Product", 1, 5, 100);

        // Test the getters
        assertEquals("Name", exportation.getName());
        assertEquals("2023-12-11", exportation.getDate());
        assertEquals("Product", exportation.getProduit());
        assertEquals(1, exportation.getId());
        assertEquals(5, exportation.getQuantite());
        assertEquals(100, exportation.getPrix());

        // Test the setters
        exportation.setName("NewName");
        exportation.setDate("2023-12-12");
        exportation.setProduit("NewProduct");
        exportation.setId(2);
        exportation.setQuantite(10);
        exportation.setPrix(200);

        assertEquals("NewName", exportation.getName());
        assertEquals("2023-12-1", exportation.getDate());
        assertEquals("NewProduct", exportation.getProduit());
        assertEquals(2, exportation.getId());
        assertEquals(10, exportation.getQuantite());
        assertEquals(200, exportation.getPrix());
    }
}
