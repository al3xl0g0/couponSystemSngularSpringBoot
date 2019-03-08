package services;

import core.beans.Coupon;
import core.exceptions.CouponSystemException;
import core.facade.CompanyFacade;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("/company")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CompanyService {

    @Context
    private HttpServletRequest request;
    private CompanyFacade companyFacade;

    @PostConstruct
    private void session() {
        HttpSession session = request.getSession(false);
        companyFacade = (CompanyFacade) session.getAttribute(LoginService.COMPANY_FACADE);
    }

    @POST
    @Path("/coupon")
    public Response createCoupon(Coupon coupon) {
        try {
            companyFacade.createCoupon(coupon);
            return Response.ok().build();
        } catch (CouponSystemException e) {
            return new CouponSystemWebException().toResponse("Creating coupon is failed. ", e);
        }
    }

    @DELETE
    @Path("/coupon/{id}")
    public Response removeCoupon(@PathParam("id") long id) {
        try {
            companyFacade.removeCoupon(id);
            return Response.ok().build();
        } catch (CouponSystemException e) {
            return new CouponSystemWebException().toResponse("Removing coupon is failed. ", e);
        }
    }

    @PUT
    @Path("/coupon")
    public Response updateCoupon(Coupon coupon) {
        try {
            companyFacade.updateCoupon(coupon.getId(), coupon.getEndDate(), coupon.getPrice(), coupon.getAmount());
            return Response.ok().build();
        } catch (CouponSystemException e) {
            return new CouponSystemWebException().toResponse("Updating coupon is failed. ", e);
        }
    }

    @GET
    @Path("/coupon")
    public Response getAllCoupons() {
        try {
            Collection<Coupon> coupons = companyFacade.getAllCoupons();
            return Response.accepted(coupons).build();
        } catch (CouponSystemException e) {
            return new CouponSystemWebException().toResponse("Showing coupons is failed. ", e);
        }
    }
}
