package database;

import java.io.FileReader;
import java.sql.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;

public class DatabaseSetup {

    private Connection db;

    public DatabaseSetup() {
        db = DerbyDatabaseConnection.getConnection();
    }

    private void initDb() {
        db = DerbyDatabaseConnection.getConnection();
    }

    private void closeDb() {
        DerbyDatabaseConnection.closeConnection();
    }

    public void cargarBaseDeDatos() {
        String clientesCsvPath = "src/main/resources/csv/clientes.csv";
        String productosCsvPath = "src/main/resources/csv/productos.csv";
        String facturasCsvPath = "src/main/resources/csv/facturas.csv";
        String facturaProductosCsvPath = "src/main/resources/csv/facturas-productos.csv";

        this.createClienteTable();
        this.createProductoTable();
        this.createFacturaTable();
        this.createFacturaProductoTable();

        this.loadClientesFromCSV(clientesCsvPath);
        this.loadProductosFromCSV(productosCsvPath);
        this.loadFacturasFromCSV(facturasCsvPath);
        this.loadFacturaProductosFromCSV(facturaProductosCsvPath);
    }

    private void createClienteTable() {
        String clienteTableCreationQuery = "CREATE TABLE Cliente ("
                + "idCliente INT PRIMARY KEY, "
                + "nombre VARCHAR(500), "
                + "email VARCHAR(500))";

        executeQuery(clienteTableCreationQuery);
    }

    private void createProductoTable() {
        String productoTableCreationQuery = "CREATE TABLE Producto ("
                + "idProducto INT PRIMARY KEY, "
                + "nombre VARCHAR(45), "
                + "valor FLOAT)";

        executeQuery(productoTableCreationQuery);
    }

    private void createFacturaTable() {
        String facturaTableCreationQuery = "CREATE TABLE Factura ("
                + "idFactura INT PRIMARY KEY, "
                + "idCliente INT, "
                + "fecha DATE, " // Asumiendo que necesitas una fecha para la factura
                + "FOREIGN KEY (idCliente) REFERENCES Cliente(idCliente))";

        executeQuery(facturaTableCreationQuery);
    }

    private void createFacturaProductoTable() {
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

    private void loadClientesFromCSV(String csvFilePath) {
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

    private void loadProductosFromCSV(String csvFilePath) {
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

    private void loadFacturasFromCSV(String csvFilePath) {
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

    private void loadFacturaProductosFromCSV(String csvFilePath) {
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
