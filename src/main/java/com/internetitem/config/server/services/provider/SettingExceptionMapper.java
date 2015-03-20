package com.internetitem.config.server.services.provider;

import com.internetitem.config.server.services.dataModel.response.ErrorResponse;
import com.internetitem.config.server.services.exception.EntityNotFoundException;
import com.internetitem.config.server.services.exception.InsufficientPermissionsException;
import com.internetitem.config.server.services.exception.SettingApplicationException;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Service
public class SettingExceptionMapper implements ExceptionMapper<SettingApplicationException> {
    
    @Override
    public Response toResponse(SettingApplicationException exception) {
        if (exception instanceof EntityNotFoundException) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse(exception.getMessage())).build();
        } else if (exception instanceof InsufficientPermissionsException) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorResponse(exception.getMessage())).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorResponse(exception.getMessage())).build();
        }
    }
    
}
