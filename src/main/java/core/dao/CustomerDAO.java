package core.dao;

import core.beans.Coupon;
import core.beans.Customer;
import core.exceptions.CouponSystemException;

import java.util.Collection;

public interface CustomerDAO {

    void createCustomer(Customer customer) throws CouponSystemException;

    void removeCustomer(long customerID) throws CouponSystemException;

    void updateCustomer(Customer customer) throws CouponSystemException;

    Customer getCustomerById(long customerID) throws CouponSystemException;

    Customer getCustomerByName(String customerName) throws CouponSystemException;

    Collection<Customer> getAllCustomers() throws CouponSystemException;

    Coupon getCustomerCoupon(long couponID, long customerID) throws CouponSystemException;

    Collection<Coupon> getAllCustomerCoupons(long customerID) throws CouponSystemException;

    void linkCustomerCoupon(long customerID, long couponID) throws CouponSystemException;

    boolean login(String customerName, String customerPassword) throws CouponSystemException;

}
