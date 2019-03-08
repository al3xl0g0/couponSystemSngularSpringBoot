package services;

import core.coupon_system.CouponSystem;
import core.enums.ClientType;
import core.exceptions.LoginFailedException;
import core.facade.AdminFacade;
import core.facade.CompanyFacade;
import core.facade.CustomerFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
//@SuppressWarnings("Duplicates")
public class LoginService {

    @Context
    private HttpServletRequest request;

    static final String ADMIN_FACADE = "adminFacade";
    static final String COMPANY_FACADE = "companyFacade";
    static final String CUSTOMER_FACADE = "customerFacade";

    @POST
    public Response login(AnonymousUser user) {

        request.getSession().invalidate();
        CouponSystem couponSystem = CouponSystem.getInstance();

        switch (user.getClientType()) {
            case ADMIN:
                try {
                    AdminFacade adminFacade = (AdminFacade) couponSystem.login(user.getName(), user.getPassword(), ClientType.ADMIN);
                    HttpSession adminSession = request.getSession();
                    adminSession.setAttribute(ADMIN_FACADE, adminFacade);
                    adminSession.setMaxInactiveInterval(/* 2 days */60 * 60 * 48);
                    return Response.accepted().build();
                } catch (LoginFailedException e) {
                    return new CouponSystemWebException().toResponse(e);
                }
            case COMPANY:
                try {
                    CompanyFacade companyFacade = (CompanyFacade) couponSystem.login(user.getName(), user.getPassword(), ClientType.COMPANY);
                    HttpSession companySession = request.getSession();
                    companySession.setAttribute(COMPANY_FACADE, companyFacade);
                    companySession.setMaxInactiveInterval(/* 2 days */60 * 60 * 48);
                    return Response.accepted().build();
                } catch (LoginFailedException e) {
                    return new CouponSystemWebException().toResponse(e);
                }
            case CUSTOMER:
                try {
                    CustomerFacade customerFacade = (CustomerFacade) couponSystem.login(user.getName(), user.getPassword(), ClientType.CUSTOMER);
                    HttpSession customerSession = request.getSession();
                    customerSession.setAttribute(CUSTOMER_FACADE, customerFacade);
                    customerSession.setMaxInactiveInterval(/* 2 days */60 * 60 * 48);
                    return Response.accepted().build();
                } catch (LoginFailedException e) {
                    return new CouponSystemWebException().toResponse(e);
                }
            default:
                return new CouponSystemWebException().toResponse("Authorization is failed, please try again.");
        }
    }
}
