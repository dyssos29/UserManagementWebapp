package com.eurodynamics.web;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="user_information")
public class User
{
	@Column(name="first_name")
	private String fName;
	@Column(name="last_name")
	private String lName;
	private String gender;
	private String birthdate;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(nullable = false, unique = true)
	private int id;
	@ManyToOne
	@Transient
	private HomeAddress homeAddress;
	@ManyToOne
	@Transient
	private WorkAddress workAddress;
	
	public User()
	{
		
	}
	
	public User(String fName, String lName, String gender, String birthday)
	{
		this.fName = fName;
		this.lName = lName;
		this.gender = gender;
		this.birthdate = birthday;
	}
	
	public String getfName()
	{
		return fName;
	}
	
	public void setfName(String fName)
	{
		this.fName = fName;
	}
	
	public String getlName()
	{
		return lName;
	}
	
	public void setlName(String lName)
	{
		this.lName = lName;
	}
	
	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public String getBirthdate()
	{
		return birthdate;
	}

	public void setBirthdate(String birthdate)
	{
		this.birthdate = birthdate;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public HomeAddress getHomeAddress()
	{
		return homeAddress;
	}
	
	public void setHomeAddress(HomeAddress homeAddress)
	{
		this.homeAddress = homeAddress;
	}

	public WorkAddress getWorkAddress()
	{
		return workAddress;
	}
	
	public void setWorkAddress(WorkAddress workAddress)
	{
		this.workAddress = workAddress;
	}

	@Override
	public String toString()
	{
		return "User [fName=" + fName + ", lName=" + lName + ", gender=" + gender + ", birthdate=" + birthdate + ", id="
				+ id + ", homeAddress=" + homeAddress + ", workAddress=" + workAddress + "]";
	}
}