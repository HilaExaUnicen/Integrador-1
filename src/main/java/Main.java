import DAOs.DaoFactory;
import DAOs.DerbyDaoFactory;
import DAOs.Interfaces.IClienteDao;
import DAOs.Interfaces.IProductoDao;
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

        DatabaseSetup dbSetup = new DatabaseSetup();

        dbSetup.cargarBaseDeDatos();
        DaoFactory derbyFactory = new DerbyDaoFactory();

        IClienteDao clienteDao = derbyFactory.getClienteDao();
        IProductoDao productoDao = derbyFactory.getProductoDao();

        productoDao.getProductoMasRecaudo();
        System.out.println(" ");
        System.out.println("-------------------------");
        System.out.println(" ");
        clienteDao.getClientesPorMontoFacturado();
    }
}
