package core.facade;

import core.beans.Company;
import core.beans.Coupon;
import core.beans.Customer;
import core.dbdao.CompanyDBDAO;
import core.dbdao.CouponDBDAO;
import core.dbdao.CustomerDBDAO;
import core.exceptions.CouponSystemException;
import core.exceptions.companyExceptions.CompanyNameDuplicateException;
import core.exceptions.companyExceptions.CompanyNotExistsException;
import core.exceptions.couponExceptions.CouponExpiredException;
import core.exceptions.couponExceptions.CouponNotExistsException;
import core.exceptions.couponExceptions.CouponTitleDuplicateException;
import core.exceptions.customerExceptions.CustomerNameDuplicateException;
import core.exceptions.customerExceptions.CustomerNotExistsException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public class AdminFacade implements CouponClientFacade {

    private CompanyDBDAO companyDBDAO = new CompanyDBDAO();
    private CustomerDBDAO customerDBDAO = new CustomerDBDAO();
    private CouponDBDAO couponDBDAO = new CouponDBDAO();

    public void createCompany(Company company) throws CouponSystemException {
        isCompanyNameDuplicate(company.getName());
        companyDBDAO.createCompany(company);
    }

    /*
     * Also removing all coupons of the company due to FOREIGN KEY REFERENCES
     * company(id) ON DELETE CASCADE
     */
    public void removeCompany(long companyID) throws CouponSystemException {
        this.isCompanyExists(companyID);
        companyDBDAO.removeCompany(companyID);
    }

    public void updateCompany(long companyID,
                              String companyName,
                              String companyPassword,
                              String companyEmail) throws CouponSystemException {

        this.isCompanyExists(companyID);
        this.isCompanyNameDuplicate(companyName);

        Company company = companyDBDAO.getCompanyById(companyID);
        company.setName(companyName);
        company.setPassword(companyPassword);
        company.setEmail(companyEmail);

        companyDBDAO.updateCompany(company);
    }

    public Collection<Company> getAllCompanies() throws CouponSystemException {

        Collection<Company> companies = companyDBDAO.getAllCompanies();

        // Checking if at least one company exists before getting all of them
        if (!companies.isEmpty()) {
            return companies;
        } else {
            throw new CompanyNotExistsException("There are no companies.");
        }
    }

    public void createCustomer(Customer customer) throws CouponSystemException {
        this.isCustomerNameDuplicate(customer.getName());
        customerDBDAO.createCustomer(customer);
    }

    /*
     * Also removing all coupons of the customer due to FOREIGN KEY REFERENCES
     * customer(id) ON DELETE CASCADE
     */
    public void removeCustomer(long customerID) throws CouponSystemException {
        this.isCustomerExists(customerID);
        customerDBDAO.removeCustomer(customerID);
    }

    public void updateCustomer(long customerID,
                               String customerName,
                               String customerPassword,
                               String customerEmail) throws CouponSystemException {

        this.isCustomerExists(customerID);

        Customer customer = customerDBDAO.getCustomerById(customerID);
        customer.setName(customerName);
        customer.setPassword(customerPassword);
        customer.setEmail(customerEmail);

        customerDBDAO.updateCustomer(customer);
    }

    public Collection<Customer> getAllCustomers() throws CouponSystemException {

        Collection<Customer> customers = customerDBDAO.getAllCustomers();

        // Checking if at least one customer exists before getting all of them
        if (!customers.isEmpty()) {
            return customers;
        } else {
            throw new CustomerNotExistsException("There are no customers.");
        }
    }

    public void createCoupon(Coupon coupon) throws CouponSystemException {

        // Checking if company exists before creating coupon of the company
        this.isCompanyExists(coupon.getCompanyId());

        CompanyFacade.createCoupon(coupon, couponDBDAO, coupon.getCompanyId());
    }

    /*
     * Removing coupon from COUPON table and also from CUSTOMER_COUPON table due to
     * FOREIGN KEY REFERENCES coupon(id) ON DELETE CASCADE
     */
    public void removeCoupon(long couponID) throws CouponSystemException {
        this.isCouponExists(couponID);
        couponDBDAO.removeCoupon(couponID);
    }

    public void updateCoupon(Coupon coupon) throws CouponSystemException {

        this.isCompanyExists(coupon.getCompanyId());
        this.isCouponExists(coupon.getId());
        this.isCouponTitleDuplicate(coupon.getTitle());

        // Checking if updating end date is not expired
        if (LocalDate.parse(coupon.getEndDate()).isAfter(new Date(System.currentTimeMillis()).toLocalDate())) {

            if (coupon.getAmount() > 0) {

                if (coupon.getPrice() > 0) {

                    couponDBDAO.updateCoupon(coupon);
                } else {
                    throw new CouponExpiredException("Not allowed to update price to less than 1.");
                }
            } else {
                throw new CouponExpiredException("Not allowed to update amount to less than 1.");
            }
        } else {
            throw new CouponExpiredException("Not allowed to update the coupon to expired date.");
        }
    }

    public Collection<Coupon> getAllCoupons() throws CouponSystemException {

        Collection<Coupon> allCoupons = couponDBDAO.getAllCoupons();

        // Checking if there is at least one coupon in database
        if (!allCoupons.isEmpty()) {
            return allCoupons;
        } else {
            throw new CouponNotExistsException("There are no coupons.");
        }
    }

    // Checking if company exists in database
    private void isCompanyExists(long companyID) throws CouponSystemException {
        Optional<Company> isCompanyExists = Optional.ofNullable(companyDBDAO.getCompanyById(companyID));
        if (!isCompanyExists.isPresent()) {
            throw new CompanyNotExistsException("This company doesn't exist.");
        }
    }

    // Checking if customer exists in database
    private void isCustomerExists(long customerID) throws CouponSystemException {
        Optional<Customer> isCustomerExists = Optional.ofNullable(customerDBDAO.getCustomerById(customerID));
        if (!isCustomerExists.isPresent()) {
            throw new CustomerNotExistsException("This customer doesn't exist.");
        }
    }

    // Checking if coupon exists in database
    private void isCouponExists(long couponID) throws CouponSystemException {
        Optional<Coupon> isCouponExists = Optional.ofNullable(couponDBDAO.getCouponById(couponID));
        if (!isCouponExists.isPresent()) {
            throw new CustomerNotExistsException("This coupon doesn't exist.");
        }
    }

    // Checking if name of the new company is not duplicate
    private void isCompanyNameDuplicate(String companyName) throws CouponSystemException {
        Optional<Company> isCompanyNameDuplicate = Optional
                .ofNullable(companyDBDAO.getCompanyByName(companyName));
        if (isCompanyNameDuplicate.isPresent()) {
            throw new CompanyNameDuplicateException("Company name: " + companyName
                    + " already exists.");
        }
    }

    // Checking if name of the new customer is not duplicate
    private void isCustomerNameDuplicate(String customerName) throws CouponSystemException {
        Optional<Customer> isCustomerNameDuplicate = Optional
                .ofNullable(customerDBDAO.getCustomerByName(customerName));
        if (isCustomerNameDuplicate.isPresent()) {
            throw new CustomerNameDuplicateException("Customer name: " + customerName
                    + " already exists.");
        }
    }

    // Checking if title of the new coupon is not duplicate
    private void isCouponTitleDuplicate(String couponTitle) throws CouponSystemException {
        Optional<Coupon> isCouponTitleDuplicate = Optional
                .ofNullable(couponDBDAO.getCouponByTitle(couponTitle));
        if (isCouponTitleDuplicate.isPresent()) {
            throw new CouponTitleDuplicateException("Coupon title: " + couponTitle
                    + " already exists.");
        }
    }

    @Override
    public String toString() {
        return "ADMIN";
    }
}
