package com.eurodynamics.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserController
 */
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO dao;
	private List<User> users;
	private List<HomeAddress> homeAddresses;
	private List<WorkAddress> workAddresses;
	
	public void init()
	{
		dao = new UserDAO();
		users = retrieveAllUsers();
		homeAddresses = retrieveAllHomeAddresses();
		workAddresses = retrieveAllWorkAddresses();
		associateUsersHomeAddresses(users, homeAddresses);
        associateUsersWorkAddresses(users, workAddresses);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String uri = request.getRequestURI();
		int index = uri.lastIndexOf("/");
		String action = uri.substring(index);
		
		System.out.println("Get request and served at: " + action);
		
		if (action.equals("/view"))
			showList(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		String uri = request.getRequestURI();
		int index = uri.lastIndexOf("/");
		String action = uri.substring(index);
		
		System.out.println("Post request and served at: " + action);
		
		if (action.equals("/add"))
			persistUser(request,response);
		else if (action.equals("/delete"))
			deleteUser(request,response);
	}
	
	private void persistUser(HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
		String firstName = request.getParameter("fname");
		String lastName = request.getParameter("lname");
		String gender = request.getParameter("gender");
		String birthday = request.getParameter("birthday");
		String homeAddress = request.getParameter("homeAddress");
		String workAddress = request.getParameter("workAddress");
		
		System.out.println("First name inserted: " + firstName);
		System.out.println("Last name inserted: " + lastName);
		System.out.println("Home address inserted: " + homeAddress);
		System.out.println("Work address inserted: " + workAddress);
		System.out.println("Gender inserted: " + gender);
		System.out.println("Birthday inserted: " + birthday);
		
		User newUser = new User(firstName, lastName, gender, birthday);
		users.add(newUser);
		
		dao.openCurrentTransactionSession();
		dao.saveUser(newUser);
		
		if (!homeAddress.equals(""))
			perstistHomeAddress(homeAddress, newUser);
		
		if (!workAddress.equals(""))
			perstistWorkAddress(workAddress, newUser);
		
		dao.closeCurrentTransactionSession();
		
		response.sendRedirect("index.jsp");
	}
	
	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println("Received id: " + id);
		User user = findUser(id);
		System.out.println(user);
		
		users.remove(user);
		
		dao.openCurrentTransactionSession();
		HomeAddress homeAddress = user.getHomeAddress();
		if (homeAddress != null)
		{
			homeAddress.deleteUser(user);
			if (homeAddress.getNumberOfUsers() == 0)
				dao.deleteHomeAddress(homeAddress);
			else
				dao.updateHomeAddress(homeAddress);
		}
		
		WorkAddress workAddress = user.getWorkAddress();
		if (workAddress != null)
		{
			workAddress.deleteUser(user);
			if (workAddress.getNumberOfUsers() == 0)
				dao.deleteWorkAddress(workAddress);
			else
				dao.updateWorkAddress(workAddress);
		}
		dao.closeCurrentTransactionSession();
		
		dao.openCurrentTransactionSession();
		dao.deleteUser(user);
		dao.closeCurrentTransactionSession();
		
		response.sendRedirect("index.jsp");
	}
	
	private User findUser(int id)
	{
		User aUser = null;
		for(User user: users)
		{
			if (user.getId() == id)
				aUser = user;
		}
		
		return aUser;
	}
	
	private void perstistHomeAddress(String homeAddress, User newUser)
	{
		for(HomeAddress address: homeAddresses)
		{
			if (address.getAddress().equals(homeAddress))
			{
				address.addUser(newUser);
				dao.updateHomeAddress(address);
				dao.updateUser(newUser);
				return;
			}
		}
		
		HomeAddress newHomeAddress = new HomeAddress(homeAddress);
		newHomeAddress.addUser(newUser);
		homeAddresses.add(newHomeAddress);
		dao.saveHomeAddress(newHomeAddress);
	}
	
	private void perstistWorkAddress(String workAddress, User newUser)
	{
		for(WorkAddress address: workAddresses)
		{
			if (address.getAddress().equals(workAddress))
			{
				address.addUser(newUser);
				dao.updateWorkAddress(address);
				dao.updateUser(newUser);
				return;
			}
		}
		
		WorkAddress newWorkAddress = new WorkAddress(workAddress);
		newWorkAddress.addUser(newUser);
		workAddresses.add(newWorkAddress);
		dao.saveWorkAddress(newWorkAddress);
	}
	
	private void showList(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
	{
        List<User> users = this.users;
        
        request.setAttribute("userList", users);
        RequestDispatcher dispatcher = request.getRequestDispatcher("UserList.jsp");
        dispatcher.forward(request, response);
    }
	
	private void associateUsersHomeAddresses(List<User> users, List<HomeAddress> homeAddresses)
	{
		for(User user: users)
		{
			for(HomeAddress address: homeAddresses)
			{
				for(int i = 0; i < address.getNumberOfUsers(); i++)
				{
					if (user.getId() == address.getUser(i).getId())
						user.setHomeAddress(address);
				}
			}
		}
	}
	
	private void associateUsersWorkAddresses(List<User> users, List<WorkAddress> workAddresses)
	{
		for(User user: users)
		{
			for(WorkAddress address: workAddresses)
			{
				for(int i = 0; i < address.getNumberOfUsers(); i++)
				{
					if (user.getId() == address.getUser(i).getId())
						user.setWorkAddress(address);
				}
			}
		}
	}
	
	private ArrayList<User> retrieveAllUsers()
	{
		ArrayList<User> users;
		
		dao.openCurrentSession();
		users = dao.getAllUsers();
		
		if (users == null)
		{
			users = new ArrayList();
			return users;
		}
		
        dao.closeCurrentSession();
       
        return users; 
	}
	
	private ArrayList<HomeAddress> retrieveAllHomeAddresses()
	{
		ArrayList<HomeAddress> addresses;
		
		dao.openCurrentSession();
		addresses = dao.getAllHomeAddresses();
		
		if (addresses == null)
		{
			addresses = new ArrayList();
			return addresses;
		}
		
        dao.closeCurrentSession();
       
        return addresses; 
	}
	
	private ArrayList<WorkAddress> retrieveAllWorkAddresses()
	{
		ArrayList<WorkAddress> addresses;
		
		dao.openCurrentSession();
		addresses = dao.getAllWorkAddresses();
		
		if (addresses == null)
		{
			addresses = new ArrayList();
			return addresses;
		}
		
        dao.closeCurrentSession();
       
        return addresses; 
	}
}