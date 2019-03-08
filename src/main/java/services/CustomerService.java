package services;

import core.beans.Coupon;
import core.exceptions.CouponSystemException;
import core.facade.CustomerFacade;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("/customer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerService {

    @Context
    private HttpServletRequest request;
    private CustomerFacade customerFacade;

    @PostConstruct
    private void session() {
        HttpSession session = request.getSession(false);
        customerFacade = (CustomerFacade) session.getAttribute(LoginService.CUSTOMER_FACADE);
    }

    @GET
    @Path("/purchase/{id}")
    public Response purchaseCoupon(@PathParam("id") long id) {
        try {
            customerFacade.purchaseCoupon(id);
            return Response.ok().build();
        } catch (CouponSystemException e) {
            return new CouponSystemWebException().toResponse("Purchasing coupon is failed. ", e);
        }
    }

    @GET
    @Path("/purchased")
    public Response getAllPurchasedCoupons() {
        try {
            Collection<Coupon> purchasedCoupons = customerFacade.getAllPurchasedCoupons();
            return Response.ok(purchasedCoupons).build();
        } catch (CouponSystemException e) {
            return new CouponSystemWebException().toResponse(e);
        }
    }

    @GET
    @Path("/available")
    public Response getAllAvailableCoupons() {
        try {
            Collection<Coupon> availableCoupons = customerFacade.getAvailableCoupons();
            return Response.ok(availableCoupons).build();
        } catch (CouponSystemException e) {
            return new CouponSystemWebException().toResponse(e);
        }
    }
}
