package tounazour.tounazour.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javafx.stage.FileChooser;
import tounazour.tounazour.ConnexionMysql;
import tounazour.tounazour.models.Importation;

public class ImportationController {
    @FXML
    TextField name, produit, quantite, id, prix;
    @FXML
    DatePicker date;
    @FXML
    TableView<Importation> tableimportation;
    @FXML
    TableColumn<Importation, String> columnName = new TableColumn<>();
    @FXML
    TableColumn<Importation, Integer> columnId = new TableColumn<>();
    @FXML
    TableColumn<Importation, String> columnDate = new TableColumn<>();
    @FXML
    TableColumn<Importation, String> columnProduit = new TableColumn<>();
    @FXML
    TableColumn<Importation, Integer> columnQuantite = new TableColumn<>();
    @FXML
    TableColumn<Importation, Integer> columnPrix = new TableColumn<>();
    //Database
    Connection con;
    PreparedStatement stmt;
    ResultSet rs;


    public void initialize() {

        try {
            con = ConnexionMysql.connexionDB();
            System.out.println("good");

        } catch (Exception e) {
            System.out.println(e.getMessage());
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
    void saveData() {
        int importationPr = Integer.parseInt(prix.getText());
        int importationQt = Integer.parseInt(quantite.getText());

        try {
            stmt = con.prepareStatement("INSERT INTO `importation`(`FournisseurName`, `ProduitImporter`, `DateImporter`, `Quantité`, `Prix`) VALUES (?,?,?,?,? )  ");
            stmt.setString(1, name.getText());
            stmt.setString(2, produit.getText());
            stmt.setString(3, date.getValue().toString());
            stmt.setInt(4, importationQt);
            stmt.setInt(5, importationPr);
            stmt.executeUpdate();

            getData();
            tableimportation.refresh();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    void getData() {
        try {
            stmt = con.prepareStatement("SELECT * FROM `importation`  ");
            rs = stmt.executeQuery();
            tableimportation.getItems().clear();

            while (rs.next()) {
                Importation importation =
                        new Importation(
                                rs.getString("FournisseurName"),
                                rs.getString("DateImporter"),
                                rs.getString("Produitimporter"),
                                rs.getInt("Id"),
                                rs.getInt("Quantité"),
                                rs.getInt("Prix")
                        );
                tableimportation.getItems().add(importation);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void deleteData() {
        if (id.getText() != null) {
            try {
                stmt = con.prepareStatement("DELETE FROM `importation` WHERE id=?");
                stmt.setInt(1, Integer.parseInt(id.getText()));
                stmt.executeUpdate();

                getData();
                tableimportation.refresh();

                while (rs.next()) {
                    Importation importation =
                            new Importation(
                                    rs.getString("FournisseurName"),
                                    rs.getString("DateImporter"),
                                    rs.getString("Produitimporter"),
                                    rs.getInt("Id"),
                                    rs.getInt("Quantité"),
                                    rs.getInt("Prix")
                            );
                    tableimportation.getItems().add(importation);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    @FXML
    void update() {
        int importationPr = Integer.parseInt(prix.getText());
        int importationQt = Integer.parseInt(quantite.getText());

        int selectedId = tableimportation.getSelectionModel().getSelectedItem().getId();

        try {
            stmt = con.prepareStatement("UPDATE `importation` SET `FournisseurName` = ? ,`ProduitImporter` = ?,`DateImporter` = ?,`Quantité` = ?, `Prix` = ? WHERE id=?");
            stmt.setString(1, name.getText());
            stmt.setString(2, produit.getText());
            stmt.setString(3, date.getValue().toString());
            stmt.setInt(4, importationQt);
            stmt.setInt(5, importationPr);
            stmt.setInt(6, selectedId);
            stmt.executeUpdate();

            getData();
            tableimportation.refresh();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    void getItem() {
        tableimportation.setOnMouseClicked(event -> {

            name.setText(tableimportation.getSelectionModel().getSelectedItem().getName());

            date.setValue(LocalDate.parse((tableimportation.getSelectionModel().getSelectedItem().getDate())));
            produit.setText(tableimportation.getSelectionModel().getSelectedItem().getProduit());
            quantite.setText(String.valueOf(tableimportation.getSelectionModel().getSelectedItem().getQuantite()));
            id.setText(String.valueOf(tableimportation.getSelectionModel().getSelectedItem().getId()));
            prix.setText(String.valueOf(tableimportation.getSelectionModel().getSelectedItem().getPrix()));
        });
    }

    @FXML
    void clear() {
        name.clear();
        date.getEditor().clear();
        produit.clear();
        quantite.clear();
    }

    @FXML
    void generateExcel() throws SQLException {

        String table = "importation";
        String[] columns = {"Id", "DateImporter", "FournisseurName", "ProduitImporter", "Quantité", "Prix"};
        TableView<Importation> sourceNode = tableimportation;


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

        String table = "importation";
        String[] columns = {"DateImporter", "FournisseurName", "ProduitImporter", "Quantité", "Prix"};

        TableView<Importation> sourceNode = tableimportation;
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
                    String DateImporter = row.getCell(i).getStringCellValue();
                    String FournisseurName = row.getCell(++i).getStringCellValue();
                    String ProduitImporter =  row.getCell(++i).getStringCellValue();
                    int Quantite = Integer.parseInt(row.getCell(++i).getStringCellValue());
                    int Prix = Integer.parseInt(row.getCell(++i).getStringCellValue());
                    String sql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)", table, columns[0], columns[1], columns[2], columns[3], columns[4]);

                    PreparedStatement statement = con.prepareStatement(sql);
                    statement.setString(1, DateImporter);
                    statement.setString(2, FournisseurName);
                    statement.setString(3, ProduitImporter);
                    statement.setInt(4, Quantite);
                    statement.setInt(5, Prix);
                    statement.executeUpdate();
                }
            }

            getData();
            tableimportation.refresh();

            workbook.close();
            fileInputStream.close();
        }
    }
}
