package com.portal.rest;

import org.glassfish.jersey.server.ResourceConfig;

import com.portal.filter.AuthenticationFilter;

public class AppConfig extends ResourceConfig
{
	public AppConfig() 
    {
        packages("com.portal.rest");
  
        register(AuthenticationFilter.class);
    }
}
