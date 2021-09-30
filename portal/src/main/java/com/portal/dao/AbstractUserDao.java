package com.portal.dao;


import com.portal.model.User;

public interface AbstractUserDao 
{
	boolean addUser(User user) throws Exception;
	User getUser(String username) throws Exception ;
}
