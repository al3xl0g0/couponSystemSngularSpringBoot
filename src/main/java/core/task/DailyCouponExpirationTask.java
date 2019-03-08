package core.task;

import core.dbdao.CouponDBDAO;
import core.exceptions.CouponSystemException;

import java.util.concurrent.TimeUnit;

public class DailyCouponExpirationTask implements Runnable {

    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void startTask() {
        this.active = true;
    }

    public void stopTask() {
        this.active = false;
    }

    @Override
    public void run() {
        while (active) {
            try {
                new CouponDBDAO().removeExpiredCoupons();
                TimeUnit.DAYS.sleep(1);
            } catch (InterruptedException | CouponSystemException e) {
                e.printStackTrace();
            }
        }
    }
}
