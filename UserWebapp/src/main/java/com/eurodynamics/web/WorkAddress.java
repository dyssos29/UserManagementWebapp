package com.eurodynamics.web;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="work_address")
public class WorkAddress
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(nullable = false, unique = true)
	private int id;
	private String address;
	@OneToMany(cascade=CascadeType.ALL)
	private List<User> users;
	
	public WorkAddress()
	{
		users = new ArrayList<User>();
	}
	
	public WorkAddress(String address)
	{
		this.address = address;
		users = new ArrayList<User>();
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}
	
	public void addUser(User user)
	{
		user.setWorkAddress(this);
		users.add(user);
	}
	
	public void deleteUser(User user)
	{
		users.remove(user);
	}
	
	public User getUser(int index)
	{
		return users.get(index);
	}
	
	public int getNumberOfUsers()
	{
		return users.size();
	}
	
	@Override
	public String toString() {
		return "WorkAddress [id=" + id + ", address=" + address + "]";
	}
}