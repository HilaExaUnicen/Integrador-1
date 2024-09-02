package DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductoDao extends Dao {

    public void getProductoMasRecaudo() {
        super.initDb();
        String query = "SELECT p.idProducto, p.nombre, SUM(fp.cantidad * p.valor) AS recaudacion " +
                "FROM Producto p " +
                "JOIN FacturaProducto fp ON p.idProducto = fp.idProducto " +
                "GROUP BY p.idProducto, p.nombre " +
                "ORDER BY recaudacion DESC " +
                "FETCH FIRST ROW ONLY"; //Derby no permite la clausula limit

        try (Statement stmt = super.getDb().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                int idProducto = rs.getInt("idProducto");
                String nombre = rs.getString("nombre");
                float recaudacion = rs.getFloat("recaudacion");

                System.out.println("Producto con más recaudación:");
                System.out.println("ID Producto: " + idProducto + ", Nombre: " + nombre + ", Recaudación: " + recaudacion);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        super.closeDb();
    }
}
