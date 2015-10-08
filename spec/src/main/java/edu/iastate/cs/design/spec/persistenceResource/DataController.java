package edu.iastate.cs.design.spec.persistenceResource;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import edu.iastate.cs.design.spec.entities.TempEntity;

public class DataController {
	/*
	 * TODO this class will contain all the functions to perform actions (retrieval, deletion, editing)
	 * on the database.
	 * 
	 * Our FactoryStartup will have generated an EntityManagerFactory that creates an EntityManager
	 * at this point. This EntityManager (usually variable 'em') will be passed in as a parameter for
	 * all methods in this class so that operations can be performed.
	 */
	
	/**
	 * This method is here to show how a typical function would look in this class
	 * It could be used to retrieve a spec with a given id from our database.
	 * 
	 * @param em	Our instance of EntityManager used for persistence.
	 * @param specId	The id of the specification to be retrieved.
	 *            
	 * @throws Exception
	 * @return The status of the deletion, whether successful or not.
	 */
	public TempEntity getSpecById(EntityManager em, String specId) throws Exception {

		try {
			/*"TempEntity.getSpecById" is a named query named in our entities. This will use that defined
			*query so that we don't have to code it directly in here. 
			*We use a named query so that we can define our Entity class that the query result will map to.
			*This allows the object to be directly turned into a TempEntity rather than us transferring it.
			*/
			Query query = em.createNamedQuery("TempEntity.getSpecById", TempEntity.class).setParameter("specId", specId);
			return (TempEntity) query.getResultList().get(0);
		} catch (Exception e) {
			throw e;
		}

	}
	
	
}
