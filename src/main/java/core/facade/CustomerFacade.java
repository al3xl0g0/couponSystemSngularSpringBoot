package core.facade;

import core.beans.Coupon;
import core.exceptions.CouponSystemException;
import core.dbdao.CouponDBDAO;
import core.dbdao.CustomerDBDAO;
import core.exceptions.couponExceptions.CouponExpiredException;
import core.exceptions.couponExceptions.CouponNotExistsException;
import core.exceptions.couponExceptions.CouponUnavaliableException;
import core.exceptions.customerExceptions.CustomerAlreadyHasCouponException;
import core.exceptions.customerExceptions.CustomerDoesntOwnCoupon;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public class CustomerFacade implements CouponClientFacade {

    private CustomerDBDAO customerDBDAO = new CustomerDBDAO();
    private CouponDBDAO couponDBDAO = new CouponDBDAO();
    private long loggedCustomer;

    public CustomerFacade(long loggedCustomer) {
        this.loggedCustomer = loggedCustomer;
    }

    public void purchaseCoupon(long couponID) throws CouponSystemException {

        // Checking if coupon exists before purchasing it
        if (isCouponExists(couponID)) {

            // Checking if customer already has a coupon
            if (!isCustomerHasCoupon(couponID)) {

                Coupon coupon = couponDBDAO.getCouponById(couponID);

                // Checking if coupon end date isn't expired
                if (LocalDate.parse(coupon.getEndDate()).isAfter(new Date(System.currentTimeMillis()).toLocalDate())) {

                    // Checking amount of the coupons
                    if (coupon.getAmount() > 0) {

                        coupon.setAmount(coupon.getAmount() - 1);
                        couponDBDAO.updateCoupon(coupon);
                        customerDBDAO.linkCustomerCoupon(loggedCustomer, couponID);
                    } else {
                        throw new CouponUnavaliableException("This coupon is not available");
                    }
                } else {
                    throw new CouponExpiredException("This coupon is expired.");
                }
            } else {
                throw new CustomerAlreadyHasCouponException(
                        "You already have this coupon.");
            }
        } else {
            throw new CouponNotExistsException("This coupon doesn't exist.");
        }
    }

    public Collection<Coupon> getAllPurchasedCoupons() throws CouponSystemException {

        Collection<Coupon> allPurchasedCoupons = customerDBDAO.getAllCustomerCoupons(loggedCustomer);

        // Checking if at least one coupon purchased by customer before getting
        if (!allPurchasedCoupons.isEmpty()) {
            return allPurchasedCoupons;
        } else {
            throw new CustomerDoesntOwnCoupon("You have no purchased coupons.");
        }
    }

    public Collection<Coupon> getAvailableCoupons() throws CouponSystemException {

        Collection<Coupon> availableCoupons = couponDBDAO.getAvailableCoupons();

        if (!availableCoupons.isEmpty()) {
            return availableCoupons;
        } else {
            throw new CouponUnavaliableException("There are no available coupons.");
        }
    }

    // Checking if coupon exists
    private boolean isCouponExists(long couponID) throws CouponSystemException {
        Optional<Coupon> isCouponExists = Optional.ofNullable(couponDBDAO.getCouponById(couponID));
        return isCouponExists.isPresent();
    }

    // Checking if customer already has a coupon
    private boolean isCustomerHasCoupon(long couponID) throws CouponSystemException {
        Optional<Coupon> isCustomerHasCoupon = Optional
                .ofNullable(customerDBDAO.getCustomerCoupon(couponID, loggedCustomer));
        return isCustomerHasCoupon.isPresent();
    }

    @Override
    public String toString() {
        return "CUSTOMER";
    }
}
