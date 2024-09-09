package DAOs;

import DAOs.Interfaces.IClienteDao;
import DAOs.Interfaces.IProductoDao;

public abstract class DaoFactory {
    public abstract IClienteDao getClienteDao();
    public abstract IProductoDao getProductoDao();
}
