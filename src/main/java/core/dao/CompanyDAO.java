package core.dao;

import core.beans.Company;
import core.beans.Coupon;
import core.exceptions.CouponSystemException;

import java.util.Collection;

public interface CompanyDAO {

    void createCompany(Company company) throws CouponSystemException;

    void removeCompany(long companyID) throws CouponSystemException;

    void updateCompany(Company company) throws CouponSystemException;

    Company getCompanyById(long companyID) throws CouponSystemException;

    Company getCompanyByName(String companyName) throws CouponSystemException;

    Collection<Company> getAllCompanies() throws CouponSystemException;

    Coupon getCompanyCoupon(long couponID, long companyID) throws CouponSystemException;

    Collection<Coupon> getAllCompanyCoupons(long companyID) throws CouponSystemException;

    boolean login(String companyName, String companyPassword) throws CouponSystemException;

}
