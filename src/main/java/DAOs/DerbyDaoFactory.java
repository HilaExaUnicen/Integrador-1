package DAOs;

import DAOs.DerbyDAOsImplementation.DerbyClienteDao;
import DAOs.DerbyDAOsImplementation.DerbyProductoDao;
import DAOs.Interfaces.IClienteDao;
import DAOs.Interfaces.IProductoDao;
import database.DerbyDatabaseConnection;

import java.sql.Connection;

public class DerbyDaoFactory extends DaoFactory {
    private Connection db;

    protected void initDb() {
        db = DerbyDatabaseConnection.getConnection();
    }

    protected void closeDb() {
        DerbyDatabaseConnection.closeConnection();
    }

    protected Connection getDb() {
        return db;
    }

    @Override
    public IClienteDao getClienteDao() {
        return new DerbyClienteDao();
    }

    @Override
    public IProductoDao getProductoDao() {
        return new DerbyProductoDao();
    }
}
