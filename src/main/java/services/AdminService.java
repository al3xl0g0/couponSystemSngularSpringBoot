package services;

import core.beans.Company;
import core.beans.Coupon;
import core.beans.Customer;
import core.exceptions.CouponSystemException;
import core.facade.AdminFacade;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("/admin")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AdminService {

    @Context
    private HttpServletRequest request;
    private AdminFacade adminFacade;

    @PostConstruct
    private void session() {
        HttpSession session = request.getSession(false);
        adminFacade = (AdminFacade) session.getAttribute(LoginService.ADMIN_FACADE);
    }

    @POST
    @Path("/company")
    public Response createCompany(Company company) {
        try {
            adminFacade.createCompany(company);
            return Response.ok().build();
        } catch (CouponSystemException e) {
            return new CouponSystemWebException().toResponse("Creating company is failed. ", e);
        }
    }

    @DELETE
    @Path("/company/{id}")
    public Response removeCompany(@PathParam("id") long id) {
        try {
            adminFacade.removeCompany(id);
            return Response.ok().build();
        } catch (CouponSystemException e) {
            return new CouponSystemWebException().toResponse("Removing company is failed. ", e);
        }
    }

    @PUT
    @Path("/company")
    public Response updateCompany(Company company) {
        try {
            adminFacade.updateCompany(company.getId(), company.getName(), company.getPassword(), company.getEmail());
            return Response.ok().build();
        } catch (CouponSystemException e) {
            return new CouponSystemWebException().toResponse("Updating company is failed. ", e);
        }
    }

    @GET
    @Path("/company")
    public Response getAllCompanies() {
        try {
            Collection<Company> companies = adminFacade.getAllCompanies();
            return Response.ok(companies).build();
        } catch (CouponSystemException e) {
            return new CouponSystemWebException().toResponse("Showing companies is failed. ", e);
        }
    }

    @POST
    @Path("/coupon")
    public Response createCoupon(Coupon coupon) {
        try {
            adminFacade.createCoupon(coupon);
            return Response.ok().build();
        } catch (CouponSystemException e) {
            return new CouponSystemWebException().toResponse("Creating coupon is failed. ", e);
        }
    }

    @DELETE
    @Path("/coupon/{id}")
    public Response removeCoupon(@PathParam("id") long id) {
        try {
            adminFacade.removeCoupon(id);
            return Response.ok().build();
        } catch (CouponSystemException e) {
            return new CouponSystemWebException().toResponse("Removing coupon is failed. ", e);
        }
    }

    @PUT
    @Path("/coupon")
    public Response updateCoupon(Coupon coupon) {
        try {
            adminFacade.updateCoupon(coupon);
            return Response.ok().build();
        } catch (CouponSystemException e) {
            return new CouponSystemWebException().toResponse("Updating coupon is failed. ", e);
        }
    }

    @GET
    @Path("/coupon")
    public Response getAllCoupons() {
        try {
            Collection<Coupon> coupons = adminFacade.getAllCoupons();
            return Response.ok(coupons).build();
        } catch (CouponSystemException e) {
            return new CouponSystemWebException().toResponse("Showing coupons is failed. ", e);
        }
    }

    @POST
    @Path("/customer")
    public Response createCustomer(Customer customer) {
        try {
            adminFacade.createCustomer(customer);
            return Response.ok().build();
        } catch (CouponSystemException e) {
            return new CouponSystemWebException().toResponse("Creating customer is failed. ", e);
        }
    }

    @DELETE
    @Path("/customer/{id}")
    public Response removeCustomer(@PathParam("id") long id) {
        try {
            adminFacade.removeCustomer(id);
            return Response.ok().build();
        } catch (CouponSystemException e) {
            return new CouponSystemWebException().toResponse("Removing customer is failed. ", e);
        }
    }

    @PUT
    @Path("/customer")
    public Response updateCustomer(Customer customer) {
        try {
            adminFacade.updateCustomer(customer.getId(), customer.getName(), customer.getPassword(), customer.getEmail());
            return Response.ok().build();
        } catch (CouponSystemException e) {
            return new CouponSystemWebException().toResponse("Updating customer is failed. ", e);
        }
    }

    @GET
    @Path("/customer")
    public Response getAllCustomers() {
        try {
            Collection<Customer> customers = adminFacade.getAllCustomers();
            return Response.ok(customers).build();
        } catch (CouponSystemException e) {
            return new CouponSystemWebException().toResponse("Showing customers is failed. ", e);
        }
    }
}
