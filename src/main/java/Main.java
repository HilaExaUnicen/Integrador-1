import DAOs.ClienteDao;
import DAOs.ProductoDao;
import database.DatabaseSetup;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) {
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";

        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        var dbSetup = new DatabaseSetup();
        String clientesCsvPath = "src/main/resources/csv/clientes.csv";
        String productosCsvPath = "src/main/resources/csv/productos.csv";
        String facturasCsvPath = "src/main/resources/csv/facturas.csv";
        String facturaProductosCsvPath = "src/main/resources/csv/facturas-productos.csv";

        dbSetup.createClienteTable();
        dbSetup.createProductoTable();
        dbSetup.createFacturaTable();
        dbSetup.createFacturaProductoTable();

        dbSetup.loadClientesFromCSV(clientesCsvPath);
        dbSetup.loadProductosFromCSV(productosCsvPath);
        dbSetup.loadFacturasFromCSV(facturasCsvPath);
        dbSetup.loadFacturaProductosFromCSV(facturaProductosCsvPath);

        var productoDao = new ProductoDao();
        var clienteDao = new ClienteDao();

        productoDao.getProductoMasRecaudo();
        System.out.println(" ");
        System.out.println("-------------------------");
        System.out.println(" ");
        clienteDao.getClientesPorMontoFacturado();
    }
}
