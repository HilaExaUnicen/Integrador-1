package database;

import java.io.FileReader;
import java.sql.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;

import static database.DatabaseConnection.getConnection;


public class DatabaseSetup {

    private Connection db;

    public DatabaseSetup() {
        db = getConnection();
    }

    private void initDb() {
        db = getConnection();
    }

    private void closeDb() {
        DatabaseConnection.closeConnection();
    }

    public void createClienteTable() {
        String clienteTableCreationQuery = "CREATE TABLE Cliente ("
                + "idCliente INT PRIMARY KEY, "
                + "nombre VARCHAR(500), "
                + "email VARCHAR(500))";

        executeQuery(clienteTableCreationQuery);
    }

    public void createProductoTable() {
        String productoTableCreationQuery = "CREATE TABLE Producto ("
                + "idProducto INT PRIMARY KEY, "
                + "nombre VARCHAR(45), "
                + "valor FLOAT)";

        executeQuery(productoTableCreationQuery);
    }

    public void createFacturaTable() {
        String facturaTableCreationQuery = "CREATE TABLE Factura ("
                + "idFactura INT PRIMARY KEY, "
                + "idCliente INT, "
                + "fecha DATE, " // Asumiendo que necesitas una fecha para la factura
                + "FOREIGN KEY (idCliente) REFERENCES Cliente(idCliente))";

        executeQuery(facturaTableCreationQuery);
    }

    public void createFacturaProductoTable() {
        String facturaProductoTableCreationQuery = "CREATE TABLE FacturaProducto ("
                + "idFactura INT, "
                + "idProducto INT, "
                + "cantidad INT, "
                + "PRIMARY KEY (idFactura, idProducto), "
                + "FOREIGN KEY (idFactura) REFERENCES Factura(idFactura), "
                + "FOREIGN KEY (idProducto) REFERENCES Producto(idProducto))";

        executeQuery(facturaProductoTableCreationQuery);
    }

    private void executeQuery(String query) {
        initDb();

        try {
            db.prepareStatement(query).execute();
            db.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeDb();
    }

    public void loadClientesFromCSV(String csvFilePath) {
        initDb();
        String insertQuery = "INSERT INTO Cliente (idCliente, nombre, email) VALUES (?, ?, ?)";

        try (CSVParser csvParser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(csvFilePath));
             PreparedStatement stmt = db.prepareStatement(insertQuery)) {

            db.setAutoCommit(false);

            for (CSVRecord record : csvParser) {
                int idCliente = Integer.parseInt(record.get("idCliente"));
                String nombre = record.get("nombre");
                String email = record.get("email");

                stmt.setInt(1, idCliente);
                stmt.setString(2, nombre);
                stmt.setString(3, email);
                stmt.addBatch();
            }

            stmt.executeBatch();
            db.commit();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }

        closeDb();
    }

    public void listClientes() {
        initDb();
        String selectQuery = "SELECT * FROM Cliente";

        try (Statement stmt = db.createStatement();
             ResultSet rs = stmt.executeQuery(selectQuery)) {

            while (rs.next()) {
                int idCliente = rs.getInt("idCliente");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");

                System.out.println("ID Cliente: " + idCliente + ", Nombre: " + nombre + ", Email: " + email);
            }
        } catch (SQLException e) {
           throw new RuntimeException(e);
        }

        closeDb();
    }

    public void loadProductosFromCSV(String csvFilePath) {
        initDb();
        String insertQuery = "INSERT INTO Producto (idProducto, nombre, valor) VALUES (?, ?, ?)";

        try (CSVParser csvParser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(csvFilePath));
             PreparedStatement stmt = db.prepareStatement(insertQuery)) {

            db.setAutoCommit(false);

            for (CSVRecord record : csvParser) {
                int idProducto = Integer.parseInt(record.get("idProducto"));
                String nombre = record.get("nombre");
                float valor = Float.parseFloat(record.get("valor"));

                stmt.setInt(1, idProducto);
                stmt.setString(2, nombre);
                stmt.setFloat(3, valor);
                stmt.addBatch();
            }

            stmt.executeBatch();
            db.commit();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }

        closeDb();
    }

    public void loadFacturasFromCSV(String csvFilePath) {
        initDb();
        String insertQuery = "INSERT INTO Factura (idFactura, idCliente) VALUES (?, ?)";

        try (CSVParser csvParser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(csvFilePath));
             PreparedStatement stmt = db.prepareStatement(insertQuery)) {

            db.setAutoCommit(false);

            for (CSVRecord record : csvParser) {
                int idFactura = Integer.parseInt(record.get("idFactura"));
                int idCliente = Integer.parseInt(record.get("idCliente"));

                stmt.setInt(1, idFactura);
                stmt.setInt(2, idCliente);
                stmt.addBatch();
            }

            stmt.executeBatch();
            db.commit();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }

        closeDb();
    }

    public void loadFacturaProductosFromCSV(String csvFilePath) {
        initDb();
        String insertQuery = "INSERT INTO FacturaProducto (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";

        try (CSVParser csvParser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(csvFilePath));
             PreparedStatement stmt = db.prepareStatement(insertQuery)) {

            db.setAutoCommit(false);

            for (CSVRecord record : csvParser) {
                int idFactura = Integer.parseInt(record.get("idFactura"));
                int idProducto = Integer.parseInt(record.get("idProducto"));
                int cantidad = Integer.parseInt(record.get("cantidad"));

                stmt.setInt(1, idFactura);
                stmt.setInt(2, idProducto);
                stmt.setInt(3, cantidad);
                stmt.addBatch();
            }

            stmt.executeBatch();
            db.commit();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }

        closeDb();
    }
}
