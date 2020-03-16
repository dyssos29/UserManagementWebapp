package com.eurodynamics.web;

import java.util.ArrayList;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class UserDAO
{
	private Session currentSession;
    private Transaction currentTransaction;
    
	public UserDAO()
	{
		
	}
	
	private static SessionFactory getSessionFactory()
	{
		Configuration config = new Configuration().configure()
				.addAnnotatedClass(User.class)
				.addAnnotatedClass(HomeAddress.class)
				.addAnnotatedClass(WorkAddress.class);
		StandardServiceRegistryBuilder bRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties());
		SessionFactory sessionFactory = config.buildSessionFactory(bRegistry.build());
		
		return sessionFactory;
	}
	
	public Session openCurrentSession()
	{
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }
 
    public Session openCurrentTransactionSession()
    {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }
     
    public void closeCurrentSession()
    {
        currentSession.close();
    }
     
    public void closeCurrentTransactionSession()
    {
        currentTransaction.commit();
        currentSession.close();
    }
    
    public Session getCurrentSession()
    {
        return currentSession;
    }
    
    public void saveUser(User model) 
    {
        getCurrentSession().save(model);
    }
    
    public void updateUser(User model) 
    {
        getCurrentSession().merge(model);
    }
    
    public void deleteUser(User model) 
    {
        getCurrentSession().delete(model);
    }
    
    public void saveHomeAddress(HomeAddress model) 
    {
        getCurrentSession().save(model);
    }
    
    public void updateHomeAddress(HomeAddress model) 
    {
        getCurrentSession().merge(model);
    }
    
    public void deleteHomeAddress(HomeAddress model) 
    {
        getCurrentSession().delete(model);
    }
    
    public void saveWorkAddress(WorkAddress model) 
    {
        getCurrentSession().save(model);
    }
    
    public void updateWorkAddress(WorkAddress model) 
    {
        getCurrentSession().merge(model);
    }
    
    public void deleteWorkAddress(WorkAddress model) 
    {
    	if (model != null)
    		getCurrentSession().delete(model);
    }
    
    @SuppressWarnings("unchecked")
	public ArrayList<HomeAddress> getAllHomeAddresses() 
    {
    	ArrayList<HomeAddress> homeAddresses = (ArrayList<HomeAddress>) getCurrentSession().createQuery("from HomeAddress").getResultList();
        return homeAddresses;
    }
    
    @SuppressWarnings("unchecked")
	public ArrayList<WorkAddress> getAllWorkAddresses() 
    {
    	ArrayList<WorkAddress> workAddresses = (ArrayList<WorkAddress>) getCurrentSession().createQuery("from WorkAddress").getResultList();

    	for(WorkAddress address: workAddresses)
    		Hibernate.initialize(address.getNumberOfUsers());
    	
    	return workAddresses;
    }
 
    @SuppressWarnings("unchecked")
	public ArrayList<User> getAllUsers() 
    {
    	ArrayList<User> users = (ArrayList<User>) getCurrentSession().createQuery("from User").getResultList();
        return users;
    }
}