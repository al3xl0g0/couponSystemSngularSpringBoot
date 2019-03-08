package core.dao;

import core.beans.Coupon;
import core.exceptions.CouponSystemException;

import java.util.Collection;

public interface CouponDAO {

    void createCoupon(Coupon coupon, long companyID) throws CouponSystemException;

    void removeCoupon(long couponID) throws CouponSystemException;

    void updateCoupon(Coupon coupon) throws CouponSystemException;

    Coupon getCouponById(long couponID) throws CouponSystemException;

    Coupon getCouponByTitle(String couponTitle) throws CouponSystemException;

    Collection<Coupon> getAllCoupons() throws CouponSystemException;

    Collection<Coupon> getAvailableCoupons() throws CouponSystemException;

    void removeExpiredCoupons() throws CouponSystemException;

}
