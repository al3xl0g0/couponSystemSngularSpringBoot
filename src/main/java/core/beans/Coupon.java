package core.beans;

import core.enums.CouponType;

import java.io.Serializable;

public class Coupon implements Serializable, Comparable<Coupon> {
    private static final long serialVersionUID = 1L;

    private long id;
    private String title;
    private String startDate;
    private String endDate;
    private int amount;
    private CouponType couponType;
    private String message;
    private double price;
    private String image;
    private long companyId;

    public Coupon() {
    }

    public Coupon(String title,
                  String startDate,
                  String endDate,
                  int amount,
                  CouponType couponType,
                  String message,
                  double price,
                  String image) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.couponType = couponType;
        this.message = message;
        this.price = price;
        this.image = image;
    }

    public Coupon(String title,
                  String startDate,
                  String endDate,
                  int amount, CouponType couponType,
                  String message,
                  double price,
                  String image,
                  long companyId) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.couponType = couponType;
        this.message = message;
        this.price = price;
        this.image = image;
        this.companyId = companyId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public CouponType getCouponType() {
        return couponType;
    }

    public void setCouponType(CouponType couponType) {
        this.couponType = couponType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    @Override
    public int compareTo(Coupon coupon) {
        return Long.compare(this.id, coupon.id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coupon)) {
            return false;
        }
        Coupon other = (Coupon) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return String.format("Coupon ID: %d, " +
                        "Title: %s, " +
                        "Amount: %d, " +
                        "Coupon type: %s, " +
                        "Message: %s, " +
                        "Price: %f, " +
                        "Image: %s, " +
                        "Start date: %tD, " +
                        "End date: %tD",
                id, title, amount, couponType, message, price, image, startDate, endDate);
    }
}
