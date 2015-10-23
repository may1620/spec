package edu.iastate.cs.design.spec.persistenceResource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 * This class creates our EntityManager which is used for persistence throughout the running time of the application.
 * @author Alex
 *
 */

public class FactoryStartup {

	/**
	 * This is our EntityManagerFactory that handles the creation of EntityManagers.
	 */
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("may1620");
	
	/**
	 * This method is used to get an EntityManager.
	 * 
	 * @return An instance of an EntityManager generated with our EntityManagerFactory.
	 */
	public static EntityManager getAnEntityManager() {
		return emf.createEntityManager();
	}
	
	/**
	 * This method closes our EntityManagerFactory.
	 * This method should not be called as EntityManagerFactory
	 * is (typically) automatically dealt with.
	 * 
	 * @param emf	Our EntityManagerFactory to close
	 */
	public void closeEntityManagerFactory(EntityManagerFactory emf) {
		emf.close();
	}
	 
}