package core.coupon_system;

import core.dbdao.CompanyDBDAO;
import core.dbdao.CustomerDBDAO;
import core.enums.ClientType;
import core.exceptions.CouponSystemException;
import core.exceptions.LoginFailedException;
import core.facade.AdminFacade;
import core.facade.CompanyFacade;
import core.facade.CouponClientFacade;
import core.facade.CustomerFacade;
import core.pool.ConnectionPool;
import core.task.DailyCouponExpirationTask;

public class CouponSystem {

    private CompanyDBDAO companyDBDAO = new CompanyDBDAO();
    private CustomerDBDAO customerDBDAO = new CustomerDBDAO();

    private DailyCouponExpirationTask task = new DailyCouponExpirationTask();
    private Thread taskThread = new Thread(task);

    private static CouponSystem instance;

    private CouponSystem() {
        task.startTask();
        taskThread.start();
    }

    public synchronized static CouponSystem getInstance() {
        if (instance == null) {
            instance = new CouponSystem();
        }
        return instance;
    }

    public CouponClientFacade login(String username, String password, ClientType clientType)
            throws LoginFailedException {
        try {
            switch (clientType) {

                case ADMIN:
                    if (username.equalsIgnoreCase("admin") && password.equals("admin")) {
                        return new AdminFacade();
                    } else {
                        throw new LoginFailedException("Authorization is failed, please try again.");
                    }
                case COMPANY:
                    if (companyDBDAO.login(username, password)) {
                        return new CompanyFacade(companyDBDAO.getCompanyByName(username).getId());
                    } else {
                        throw new LoginFailedException("Authorization is failed, please try again.");
                    }
                case CUSTOMER:
                    if (customerDBDAO.login(username, password)) {
                        return new CustomerFacade(customerDBDAO.getCustomerByName(username).getId());
                    } else {
                        throw new LoginFailedException("Authorization is failed, please try again.");
                    }
                default:
                    throw new LoginFailedException("Authorization is failed, please try again.");
            }
        } catch (CouponSystemException e) {
            throw new LoginFailedException("Authorization is failed, please try again.");
        }
    }

    public synchronized void shutdown() {
        if (task.isActive()) {
            task.stopTask();
            taskThread.interrupt();
        }
        ConnectionPool.getInstance().closeAllConnections();
    }
}
