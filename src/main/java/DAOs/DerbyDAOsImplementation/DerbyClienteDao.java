package DAOs.DerbyDAOsImplementation;

import DAOs.DerbyDaoFactory;
import DAOs.Interfaces.IClienteDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DerbyClienteDao extends DerbyDaoFactory implements IClienteDao {

    @Override
    public void getClientesPorMontoFacturado() {
        super.initDb();
        String query = "SELECT c.idCliente, c.nombre, c.email, SUM(fp.cantidad * p.valor) AS totalFacturado " +
                "FROM Cliente c " +
                "JOIN Factura f ON c.idCliente = f.idCliente " +
                "JOIN FacturaProducto fp ON f.idFactura = fp.idFactura " +
                "JOIN Producto p ON fp.idProducto = p.idProducto " +
                "GROUP BY c.idCliente, c.nombre, c.email " +
                "ORDER BY totalFacturado DESC";

        try (Statement stmt = super.getDb().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("Lista de clientes ordenada por monto facturado:");
            while (rs.next()) {
                int idCliente = rs.getInt("idCliente");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                float totalFacturado = rs.getFloat("totalFacturado");

                System.out.println("ID Cliente: " + idCliente + ", Nombre: " + nombre + ", Email: " + email + ", Total Facturado: " + totalFacturado);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        super.closeDb();
    }
}
