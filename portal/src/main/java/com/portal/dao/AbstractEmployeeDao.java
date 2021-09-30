package com.portal.dao;

import java.sql.SQLException;
import java.util.List;

import com.adventnet.persistence.DataAccessException;
import com.portal.model.Employee;

public interface AbstractEmployeeDao 
{
	boolean save(Employee emp) throws SQLException;
	List<Employee> viewEmployees(int page_no,int total) throws SQLException ;
	int updateEmployee(Employee emp) throws SQLException;
	int deleteEmployee(int id) throws SQLException;
	Employee getEmployeeById(int id) throws SQLException ;
	List<Employee> getEmployeesByName(String name,int total) throws SQLException ;
	List<Employee> getEmployeesByDeparment(String department,int total) throws SQLException ;
	List<Employee> getEmployeesByReportingManager(String manager,int total) throws SQLException ;
	List<Employee> getEmployeesByEmail(String email,int total) throws SQLException ;
	List<Employee> getEmployeesBymobile(String mobile,int total) throws SQLException ;
	List<Employee> getEmployeesByLocation(String location,int total) throws SQLException ;
	List<Employee> getEmployeesById(int id,int total) throws SQLException ;
	List<Employee> viewEmployeesSorted(String sort_by,String mode,int total) throws SQLException ;
	List<Employee> getEmployees() throws SQLException;
	List<String> getEmployeeDetails(int emp_id) throws DataAccessException;
	int getTotalMembers(String reporting_to) throws DataAccessException;
}
