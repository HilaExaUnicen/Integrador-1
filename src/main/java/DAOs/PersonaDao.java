package DAOs;

import database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDao implements IPersonaDao{

    private Connection db;

    public PersonaDao() {

        db = DatabaseConnection.getConnection();
    }

    private void initDb(){
        try {
            if(this.db.isClosed()){
                this.db = DatabaseConnection.getConnection();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeDb(){
        try {
            db.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Persona getPersona(int id) {
        initDb();
        String select = "select * from persona where id = " + id;
        PreparedStatement ps = null;
        Persona persona = null;
        try {
            ps = db.prepareStatement(select);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                persona = new Persona(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        closeDb();
        return persona;
    }

    @Override
    public List<Persona> getAllPersonas() {
        String select = "select * from persona";
        PreparedStatement ps = null;
        List<Persona> personas = new ArrayList<>();
        try {
            ps = db.prepareStatement(select);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                personas.add(new Persona(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return personas;
    }

    @Override
    public boolean addPersona(Persona persona) {
        String insert = "INSERT INTO persona (id, nombre, apellido, edad) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = this.db.prepareStatement(insert);
            ps.setInt(1, persona.id);
            ps.setString(2, persona.nombre);
            ps.setString(3, persona.apellido);
            ps.setInt(4, persona.edad);

            ps.executeUpdate();
            ps.close();

            db.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    @Override
    public boolean updatePersona(Persona persona) {
        return false;
    }

    @Override
    public boolean deletePersona(Persona persona) {
        return false;
    }
}
