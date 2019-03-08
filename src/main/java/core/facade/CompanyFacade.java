package core.facade;

import core.beans.Coupon;
import core.dbdao.CompanyDBDAO;
import core.dbdao.CouponDBDAO;
import core.exceptions.CouponSystemException;
import core.exceptions.companyExceptions.CompanyDoesntOwnCoupon;
import core.exceptions.couponExceptions.CouponExpiredException;
import core.exceptions.couponExceptions.CouponTitleDuplicateException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public class CompanyFacade implements CouponClientFacade {

    private CompanyDBDAO companyDBDAO = new CompanyDBDAO();
    private CouponDBDAO couponDBDAO = new CouponDBDAO();
    private long loggedCompany;

    public CompanyFacade(long loggedCompany) {
        this.loggedCompany = loggedCompany;
    }

    public void createCoupon(Coupon coupon) throws CouponSystemException {
        createCoupon(coupon, couponDBDAO, loggedCompany);
    }

    /*
     * Removing coupon from COUPON table and also from CUSTOMER_COUPON table due to
     * FOREIGN KEY REFERENCES coupon(id) ON DELETE CASCADE
     */
    public void removeCoupon(long couponID) throws CouponSystemException {
        this.isCompanyOwnsCoupon(couponID);
        couponDBDAO.removeCoupon(couponID);
    }

    public void updateCoupon(long couponID, String endDate, double price, int amount) throws CouponSystemException {

        this.isCompanyOwnsCoupon(couponID);

        // Checking if updating end date is not expired
        if (LocalDate.parse(endDate).isAfter(new Date(System.currentTimeMillis()).toLocalDate())) {

            if (amount > 0) {

                if (price > 0) {

                    Coupon coupon = couponDBDAO.getCouponById(couponID);
                    coupon.setEndDate(endDate);
                    coupon.setPrice(price);
                    coupon.setAmount(amount);

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

        Collection<Coupon> allCompanyCoupons = companyDBDAO.getAllCompanyCoupons(loggedCompany);

        // Checking if company owns at least one coupon
        if (!allCompanyCoupons.isEmpty()) {
            return allCompanyCoupons;
        } else {
            throw new CompanyDoesntOwnCoupon("You have no coupons.");
        }
    }

    static void createCoupon(Coupon coupon, CouponDBDAO couponDBDAO, long companyID) throws CouponSystemException {

        // Checking if title of the new coupon is not duplicate
        Optional<Coupon> isCouponTitleDuplicate = Optional.ofNullable(couponDBDAO.getCouponByTitle(coupon.getTitle()));

        if (!isCouponTitleDuplicate.isPresent()) {
            couponDBDAO.createCoupon(coupon, companyID);
        } else {
            throw new CouponTitleDuplicateException("Coupon title: " + coupon.getTitle()
                    + " already exists.");
        }
    }

    // Checking if company is owner of the coupon
    private void isCompanyOwnsCoupon(long couponID) throws CouponSystemException {
        Optional<Coupon> isCompanyOwnsCoupon = Optional
                .ofNullable(companyDBDAO.getCompanyCoupon(couponID, loggedCompany));
        if (!isCompanyOwnsCoupon.isPresent()) {
            throw new CompanyDoesntOwnCoupon("You don't own this coupon.");
        }
    }

    @Override
    public String toString() {
        return "COMPANY";
    }
}
