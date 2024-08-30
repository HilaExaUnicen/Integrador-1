import DAOs.IPersonaDao;
import DAOs.Persona;
import DAOs.PersonaDao;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";

        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String uri = "jdbc:derby:MyDerbyDB;create=true";

        IPersonaDao personaDAO = new PersonaDao();

//        var persona1 = new Persona(2, "john", "salchichon", 43);
//        var persona2 = new Persona(3, "Marcus", "Rashford", 42);
//        var persona3 = new Persona(4, "Antony", "Bridgerton", 76);
//
//        personaDAO.addPersona(persona1);
//        personaDAO.addPersona(persona2);personaDAO.addPersona(persona3);

        var personas = personaDAO.getAllPersonas();
        for(Persona persona : personas) {
            System.out.println(persona.getId() + " " + persona.getNombre() + " " + persona.getApellido());
        }
    }

    private static void addPerson(Connection conn, int id, String nombre, String apellido, int edad) {
        String insert = "INSERT INTO persona (id, nombre, apellido, edad) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(insert);
            ps.setInt(1, id);
            ps.setString(2, nombre);
            ps.setString(3, apellido);
            ps.setInt(4, edad);

            ps.executeUpdate();
            ps.close();

            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createTable(Connection conn) {
        String createTableSQL = "CREATE TABLE persona ("
                + "id INT PRIMARY KEY, "
                + "nombre VARCHAR(255), "
                + "apellido VARCHAR(255), "
                + "edad INT)";
        try {
            conn.prepareStatement(createTableSQL).execute();
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
