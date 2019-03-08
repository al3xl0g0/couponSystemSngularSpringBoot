package core.facade;

import core.beans.Company;
import core.beans.Customer;
import core.exceptions.CouponSystemException;

// Facade for registration
public class AnonymousFacade implements CouponClientFacade {

    public void registerCompany(Company company) throws CouponSystemException {
        new AdminFacade().createCompany(company);
    }

    public void registerCustomer(Customer customer) throws CouponSystemException {
        new AdminFacade().createCustomer(customer);
    }
}
