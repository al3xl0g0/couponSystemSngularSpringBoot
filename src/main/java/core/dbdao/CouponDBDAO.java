package core.dbdao;

import core.beans.Coupon;
import core.dao.CouponDAO;
import core.enums.CouponType;
import core.exceptions.CouponSystemException;
import core.exceptions.couponExceptions.CouponDaoException;
import core.logger.CouponSystemLogger;
import core.pool.ConnectionPool;
import core.sql_commands.SQL_COUPON;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;

public class CouponDBDAO implements CouponDAO {

    private final CouponSystemLogger LOGGER = CouponSystemLogger.getInstance(CouponDBDAO.class.getName());
    ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public void createCoupon(Coupon coupon, long companyID) throws CouponSystemException {

        Connection connection = pool.getConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(SQL_COUPON.CREATE)) {
            pstmt.setString(1, coupon.getTitle());
            pstmt.setDate(2, Date.valueOf(LocalDate.parse(coupon.getStartDate())));
            pstmt.setDate(3, Date.valueOf(LocalDate.parse(coupon.getEndDate())));
            pstmt.setInt(4, coupon.getAmount());
            pstmt.setString(5, coupon.getCouponType().toString());
            pstmt.setString(6, coupon.getMessage());
            pstmt.setDouble(7, coupon.getPrice());
            pstmt.setString(8, coupon.getImage());
            pstmt.setLong(9, companyID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Creating coupon is failed. ", e);
            throw new CouponDaoException("Creating new coupon in database is failed. ", e);
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public void removeCoupon(long couponID) throws CouponSystemException {

        Connection connection = pool.getConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(SQL_COUPON.DELETE)) {
            pstmt.setLong(1, couponID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Removing coupon is failed. ", e);
            throw new CouponDaoException("Removing coupon ID: " + couponID + " from database is failed. ", e);
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public void updateCoupon(Coupon coupon) throws CouponSystemException {

        Connection connection = pool.getConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(SQL_COUPON.UPDATE)) {
            pstmt.setString(1, coupon.getTitle());
            pstmt.setDate(2, Date.valueOf(LocalDate.parse(coupon.getStartDate())));
            pstmt.setDate(3, Date.valueOf(LocalDate.parse(coupon.getEndDate())));
            pstmt.setInt(4, coupon.getAmount());
            pstmt.setString(5, coupon.getCouponType().toString());
            pstmt.setString(6, coupon.getMessage());
            pstmt.setDouble(7, coupon.getPrice());
            pstmt.setString(8, coupon.getImage());
            pstmt.setLong(9, coupon.getCompanyId());
            pstmt.setLong(10, coupon.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Updating coupon is failed. ", e);
            throw new CouponDaoException("Updating coupon ID: " + coupon.getId() + " in database is failed. ", e);
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public Coupon getCouponById(long couponID) throws CouponSystemException {

        Connection connection = pool.getConnection();
        Coupon coupon = null;

        try (PreparedStatement pstmt = connection.prepareStatement(SQL_COUPON.SELECT_BY_ID)) {
            pstmt.setLong(1, couponID);

            try (ResultSet resultSet = pstmt.executeQuery()) {

                while (resultSet.next()) {
                    coupon = new Coupon();
                    coupon.setId(couponID);
                    coupon.setTitle(resultSet.getString("TITLE"));
                    coupon.setStartDate(resultSet.getDate("START_DATE").toString());
                    coupon.setEndDate(resultSet.getDate("END_DATE").toString());
                    coupon.setAmount(resultSet.getInt("AMOUNT"));
                    coupon.setCouponType(CouponType.valueOf(resultSet.getString("TYPE")));
                    coupon.setMessage(resultSet.getString("MESSAGE"));
                    coupon.setPrice(resultSet.getDouble("PRICE"));
                    coupon.setImage(resultSet.getString("IMAGE"));
                    coupon.setCompanyId(resultSet.getLong("COMPANY_ID"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Getting coupon by ID is failed. ", e);
            throw new CouponDaoException("Getting coupon ID: " + couponID + " from database is failed. ", e);
        } finally {
            pool.returnConnection(connection);
        }
        return coupon;
    }

    @Override
    public Coupon getCouponByTitle(String couponTitle) throws CouponSystemException {

        Connection connection = pool.getConnection();
        Coupon coupon = null;

        try (PreparedStatement pstmt = connection.prepareStatement(SQL_COUPON.SELECT_BY_TITLE)) {
            pstmt.setString(1, couponTitle);

            try (ResultSet resultSet = pstmt.executeQuery()) {

                while (resultSet.next()) {
                    coupon = new Coupon();
                    coupon.setId(resultSet.getLong("ID"));
                    coupon.setTitle(couponTitle);
                    coupon.setStartDate(resultSet.getDate("START_DATE").toString());
                    coupon.setEndDate(resultSet.getDate("END_DATE").toString());
                    coupon.setAmount(resultSet.getInt("AMOUNT"));
                    coupon.setCouponType(CouponType.valueOf(resultSet.getString("TYPE")));
                    coupon.setMessage(resultSet.getString("MESSAGE"));
                    coupon.setPrice(resultSet.getDouble("PRICE"));
                    coupon.setImage(resultSet.getString("IMAGE"));
                    coupon.setCompanyId(resultSet.getLong("COMPANY_ID"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Getting coupon by title is failed. ", e);
            throw new CouponDaoException("Getting coupon by the title: " + couponTitle + " from database is failed. ", e);
        } finally {
            pool.returnConnection(connection);
        }
        return coupon;
    }

    @Override
    public Collection<Coupon> getAllCoupons() throws CouponSystemException {

        Connection connection = pool.getConnection();
        Collection<Coupon> allCoupons;

        try (PreparedStatement pstmt = connection.prepareStatement(SQL_COUPON.SELECT_ALL);
             ResultSet resultSet = pstmt.executeQuery()) {

            allCoupons = new ArrayList<>();

            while (resultSet.next()) {
                Coupon coupon = new Coupon();
                coupon.setId(resultSet.getLong("ID"));
                coupon.setTitle(resultSet.getString("TITLE"));
                coupon.setStartDate(resultSet.getDate("START_DATE").toString());
                coupon.setEndDate(resultSet.getDate("END_DATE").toString());
                coupon.setAmount(resultSet.getInt("AMOUNT"));
                coupon.setCouponType(CouponType.valueOf(resultSet.getString("TYPE")));
                coupon.setMessage(resultSet.getString("MESSAGE"));
                coupon.setPrice(resultSet.getDouble("PRICE"));
                coupon.setImage(resultSet.getString("IMAGE"));
                coupon.setCompanyId(resultSet.getLong("COMPANY_ID"));
                allCoupons.add(coupon);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Getting all coupons from database is failed. ", e);
            throw new CouponDaoException("Getting all coupons from database is failed. ", e);
        } finally {
            pool.returnConnection(connection);
        }
        return allCoupons;
    }

    @Override
    public Collection<Coupon> getAvailableCoupons() throws CouponSystemException {

        Connection connection = pool.getConnection();
        Collection<Coupon> availableCoupons;

        try (PreparedStatement pstmt = connection.prepareStatement(SQL_COUPON.SELECT_AVAILABLE);
             ResultSet resultSet = pstmt.executeQuery()) {

            availableCoupons = new ArrayList<>();

            while (resultSet.next()) {
                Coupon coupon = new Coupon();
                coupon.setId(resultSet.getLong("ID"));
                coupon.setTitle(resultSet.getString("TITLE"));
                coupon.setStartDate(resultSet.getDate("START_DATE").toString());
                coupon.setEndDate(resultSet.getDate("END_DATE").toString());
                coupon.setAmount(resultSet.getInt("AMOUNT"));
                coupon.setCouponType(CouponType.valueOf(resultSet.getString("TYPE")));
                coupon.setMessage(resultSet.getString("MESSAGE"));
                coupon.setPrice(resultSet.getDouble("PRICE"));
                coupon.setImage(resultSet.getString("IMAGE"));
                coupon.setCompanyId(resultSet.getLong("COMPANY_ID"));
                availableCoupons.add(coupon);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Getting available coupons from database is failed. ", e);
            throw new CouponDaoException("Getting available coupons from database is failed. ", e);
        } finally {
            pool.returnConnection(connection);
        }
        return availableCoupons;
    }

    /*
     * Removing all expired coupons from COUPON table and also from CUSTOMER_COUPON
     * table due to FOREIGN KEY REFERENCES COUPON(ID) ON DELETE CASCADE
     */
    @Override
    public void removeExpiredCoupons() throws CouponSystemException {

        Connection connection = pool.getConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(SQL_COUPON.DELETE_EXPIRED)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Removing expired coupons is failed. ", e);
            throw new CouponDaoException("Removing expired coupons is failed. ", e);
        } finally {
            pool.returnConnection(connection);
        }
    }
}
