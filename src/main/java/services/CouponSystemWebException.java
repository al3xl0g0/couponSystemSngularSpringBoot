package services;

import core.exceptions.CouponSystemException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class CouponSystemWebException extends WebApplicationException implements ExceptionMapper<CouponSystemException> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public Response toResponse(CouponSystemException e) {
        return Response.status(Status.NOT_ACCEPTABLE)
                .entity(e.getMessage())
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    Response toResponse(String msg) {
        return Response.status(Status.NOT_ACCEPTABLE)
                .entity(msg)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    Response toResponse(String msg, CouponSystemException e) {
        return Response.status(Status.NOT_ACCEPTABLE)
                .entity(msg + e.getMessage())
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}
