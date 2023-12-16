package tounazour.tounazour.controllers;




import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tounazour.tounazour.ConnexionMysql;
import tounazour.tounazour.models.Exportation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class ExportationController {
    @FXML
    TextField name ,produit ,quantite,id,prix;
    @FXML
    DatePicker date;
    @FXML
    TableView<Exportation> tableexportation;
    @FXML
    TableColumn<Exportation,String> columnName = new TableColumn<>();
    @FXML
    TableColumn<Exportation,Integer> columnId = new TableColumn<>();
    @FXML
    TableColumn<Exportation,String> columnDate = new TableColumn<>();
    @FXML
    TableColumn<Exportation,String> columnProduit = new TableColumn<>();
    @FXML
    TableColumn<Exportation,Integer> columnQuantite = new TableColumn<>();
    @FXML
    TableColumn<Exportation,Integer> columnPrix = new TableColumn<>();
    Connection con;
    PreparedStatement stmt ;
    ResultSet rs ;







    public void initialize(){

        try {
            con= ConnexionMysql.connexionDB();
            System.out.println("good");

        }catch (Exception e) {
            System.out.print(e.getMessage());
        }

        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnProduit.setCellValueFactory(new PropertyValueFactory<>("produit"));
        columnQuantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        columnPrix.setCellValueFactory(new PropertyValueFactory<>("Prix"));
        getData();
        getItem();
    }

    @FXML
    void saveDate(){
        int exportationID =Integer.parseInt(id.getText());
        int exportationPr =Integer.parseInt(prix.getText());
        int exportationQt =Integer.parseInt(quantite.getText());
        Exportation exportation = new Exportation(name.getText(), date.getValue().toString(), produit.getText(), exportationID ,exportationQt,exportationPr);
        try {
            stmt = con.prepareStatement("INSERT INTO `exportation`(`Id`, `FournisseurName`, `ProduitExporter`, `Quantité`, `Prix`, `DateExportation`) VALUES (?,?,?,?,?,? )  ");
            stmt.setInt(1,exportationID);
            stmt.setString(2,name.getText());
            stmt.setString(3,date.getValue().toString());
            stmt.setString(4,produit.getText());
            stmt.setInt(5,exportationQt);
            stmt.setInt(6,exportationPr);
            stmt.executeUpdate();
            stmt.executeUpdate();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        tableexportation.getItems().add(exportation);



    }

    void getData (){
        try {
            stmt = con.prepareStatement("SELECT * FROM exportation");
            rs = stmt.executeQuery();
            tableexportation.getItems().clear();
            while (rs.next()){
                Exportation exportation = new Exportation(rs.getString("FournisseurName"), rs.getString("DateExportation"), rs.getString("ProduitExporter"), rs.getInt("Id"),rs.getInt("Quantité"),rs.getInt("Prix") );
                tableexportation.getItems().add(exportation);
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }

    }
    void getItem(){
        tableexportation.setOnMouseClicked(event ->{

            name.setText(tableexportation.getSelectionModel().getSelectedItems().getFirst().getName());

            date.setValue(LocalDate.parse((tableexportation.getSelectionModel().getSelectedItems().getFirst().getDate())) );
            produit.setText(tableexportation.getSelectionModel().getSelectedItems().getFirst().getProduit());
            quantite.setText(String.valueOf(tableexportation.getSelectionModel().getSelectedItems().getFirst().getQuantite()));
            id.setText(String.valueOf(tableexportation.getSelectionModel().getSelectedItems().getFirst().getId()));
            prix.setText(String.valueOf(tableexportation.getSelectionModel().getSelectedItems().getFirst().getPrix()));
        } );
    }
    @FXML
    void update() {

        int exportationPr = Integer.parseInt(prix.getText());
        int exportationQt =Integer.parseInt(quantite.getText());

        int selectedId = tableexportation.getSelectionModel().getSelectedItem().getId();

        try {
            stmt = con.prepareStatement("UPDATE `exportation` SET `FournisseurName` = ? ,`ProduitExporter` = ?,`DateExportation` = ?,`Quantité` = ?, `Prix` = ? WHERE id=?");
            stmt.setString(1, name.getText());
            stmt.setString(2, produit.getText());
            stmt.setString(3, date.getValue().toString());
            stmt.setInt(4, exportationQt);
            stmt.setInt(5, exportationPr);
            stmt.setInt(6, selectedId);
            stmt.executeUpdate();

            getData();
            tableexportation.refresh();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    @FXML
    void clear(){
        name.clear();
        date.getEditor().clear();
        produit.clear();
        quantite.clear();
        prix.clear();

    }
    @FXML
    void generateExcel() throws SQLException {

        String table = "exportation";
        String[] columns = {"Id", "DateExportation", "FournisseurName", "ProduitExporter", "Quantité", "Prix"};
        TableView<Exportation> sourceNode = tableexportation;


        Statement statement = con.createStatement();

        ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM %s", table));

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(table);

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        int rowNum = 1;
        while (resultSet.next()) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(resultSet.getString(columns[i]));
            }
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));

        java.io.File selectedFile = fileChooser.showSaveDialog(sourceNode.getScene().getWindow());

        if (selectedFile != null) {
            try {
                FileOutputStream outputStream = new FileOutputStream(selectedFile);
                workbook.write(outputStream);
                workbook.close();
                outputStream.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    @FXML
    void importExcelFileToDataBase() throws IOException, SQLException {

        String table = "exportation";
        String[] columns = {"DateExportation", "FournisseurName", "ProduitExporter", "Quantité", "Prix"};

        TableView<Exportation> sourceNode = tableexportation;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisissez le fichier Excel");
        File file = fileChooser.showOpenDialog(sourceNode.getScene().getWindow());

        if (file != null) {
            FileInputStream fileInputStream = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() > 0) {
                    int i = 1;
                    String DateExportation = row.getCell(i).getStringCellValue();
                    String FournisseurName = row.getCell(++i).getStringCellValue();
                    String ProduitExporter =  row.getCell(++i).getStringCellValue();
                    int Quantite = Integer.parseInt(row.getCell(++i).getStringCellValue());
                    Double Prix = Double.parseDouble(row.getCell(++i).getStringCellValue());
                    String sql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)", table, columns[0], columns[1], columns[2], columns[3], columns[4]);

                    PreparedStatement statement = con.prepareStatement(sql);
                    statement.setString(1, DateExportation);
                    statement.setString(2, FournisseurName);
                    statement.setString(3, ProduitExporter);
                    statement.setInt(4, Quantite);
                    statement.setDouble(5, Prix);
                    statement.executeUpdate();
                }
            }

            getData();
            tableexportation.refresh();

            workbook.close();
            fileInputStream.close();
        }
    }
    public class HelloController implements Initializable {

        @FXML
        private BorderPane body;

        @FXML
        private AnchorPane app;

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

        }

        public void dashboard(MouseEvent mouseEvent) {
            loadPage("dashboard");
        }


        public void supplies(MouseEvent mouseEvent) {
            loadPage("bee_supplies");
        }

        public void home(MouseEvent mouseEvent) {
            loadPage("Home");
        }

        public void command(MouseEvent mouseEvent) {
            loadPage("Commend");
        }
        public void exportation(MouseEvent mouseEvent) {
            loadPage("exportation");
        }
        public void importation(MouseEvent mouseEvent) {
            loadPage("importation");
        }

        private void loadPage(String page) {
            Parent root=null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(page + ".fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            body.setCenter(root);
        }
    }
    @FXML
    void searchExportation() {
        String searchTerm = null; // Obtenir le terme de recherche depuis un champ de texte ou autre source;

        try {
            stmt = con.prepareStatement("SELECT * FROM exportation WHERE FournisseurName LIKE ?");
            stmt.setString(1, "%" + searchTerm + "%");
            rs = stmt.executeQuery();

            tableexportation.getItems().clear();

            while (rs.next()) {
                Exportation exportation = new Exportation(
                        rs.getString("FournisseurName"),
                        rs.getString("DateExportation"),
                        rs.getString("ProduitExporter"),
                        rs.getInt("Id"),
                        rs.getInt("Quantité"),
                        rs.getInt("Prix")
                );
                tableexportation.getItems().add(exportation);
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche : " + e.getMessage());
            e.printStackTrace(); // Afficher la trace complète de l'erreur pour un débogage approfondi
        }
    }



}
