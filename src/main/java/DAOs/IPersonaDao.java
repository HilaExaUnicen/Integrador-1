package DAOs;

import java.util.List;

public interface IPersonaDao {
    public Persona getPersona(int id);
    public List<Persona> getAllPersonas();
    public boolean addPersona(Persona persona);
    public boolean updatePersona(Persona persona);
    public boolean deletePersona(Persona persona);
}
