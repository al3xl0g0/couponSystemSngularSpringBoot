package services;

import core.beans.Company;
import core.beans.Customer;
import core.exceptions.CouponSystemException;
import core.facade.AnonymousFacade;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/registration")
@Consumes(MediaType.APPLICATION_JSON)
public class RegistrationService {

    private AnonymousFacade anonymousFacade = new AnonymousFacade();

    @POST
    @Path("/company")
    public Response registerCompany(Company company) {
        try {
            anonymousFacade.registerCompany(company);
            return Response.accepted().build();
        } catch (CouponSystemException e) {
            return new CouponSystemWebException().toResponse("Registration is failed. ", e);
        }
    }

    @POST
    @Path("/customer")
    public Response registerCustomer(Customer customer) {
        try {
            anonymousFacade.registerCustomer(customer);
            return Response.accepted().build();
        } catch (CouponSystemException e) {
            return new CouponSystemWebException().toResponse("Registration is failed. ", e);
        }
    }
}
