package com.portal.filter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.HashSet;


import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

import com.portal.dao.impl.DBConnection;
import com.portal.dao.impl.UserDao;
import com.portal.model.User;

@Provider
public class AuthenticationFilter implements javax.ws.rs.container.ContainerRequestFilter
{
     
	
	
    @Context
    private ResourceInfo resourceInfo;
     
    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";
      
   
    public void filter(ContainerRequestContext requestContext) throws IOException
    {
        Method method = resourceInfo.getResourceMethod();
        //Access allowed for all
        if( ! method.isAnnotationPresent(PermitAll.class))
        {
            //Access denied for all
            if(method.isAnnotationPresent(DenyAll.class))
            {
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                         .entity("Access blocked for all users !!").build());
                return;
            }
              
            //Get request headers
            final MultivaluedMap<String, String> headers = requestContext.getHeaders();
              
            //Fetch authorization header
            final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
              
            //If no authorization information present; block access
            if(authorization == null || authorization.isEmpty())
            {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("You cannot access this resource").build());
                return;
            }
              
            //Get encoded username and password
            final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
              
            //Decode username and password
            String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));;
  
            //Split username and password tokens
            final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
            final String username = tokenizer.nextToken();
            final String password = tokenizer.nextToken();
              
            //Verifying Username and password
            System.out.println(username);
            System.out.println(password);
              
            //Verify user access
            if(!isUserAllowed(username, password))
            {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("You cannot access this resource").build());
                return;
            }
        }
    }
    private boolean isUserAllowed(final String username, final String password)
    {
        boolean isAllowed = false;
        User user=null;
        DBConnection con=new DBConnection();
        UserDao connection=UserDao.getConnection(con.getConnection());
		
		try {
			user=connection.getUser(username);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
         
        if(user!=null&&user.getUsername()!=null&&user.getPassword()!=null)
        {
           if(username.equals(user.getUsername()) && password.equals(user.getPassword()))
           {
            isAllowed = true; 
           }
        }
        return isAllowed;
    }
}