package edu.iastate.cs.design.spec.post;

import edu.iastate.cs.design.spec.persistenceResource.FactoryStartup;
import edu.iastate.cs.design.spec.stackexchange.request.IStackExchangeRequester;
import edu.iastate.cs.design.spec.stackexchange.request.StackExchangeRequester;

import javax.persistence.EntityManager;

/**
 * This class contains the main method for the part of the system that will
 * find specifications in the database that need to be verified and will post
 * them as questions on StackOverflow.
 */
public class Post {

    private IPendingSpecificationDao pendingSpecificationDao;
    private IStackExchangeRequester stackExchangeRequester;

    public Post(IPendingSpecificationDao pendingSpecificationDao, IStackExchangeRequester stackExchangeRequester) {
        this.pendingSpecificationDao = pendingSpecificationDao;
        this.stackExchangeRequester = stackExchangeRequester;
    }

    public void run() {
        // TODO
    }

    // Entry point
    public static void main(String[] args) {
        EntityManager entityManager = FactoryStartup.getAnEntityManager();
        PendingSpecificationDao pendingSpecificationDao = new PendingSpecificationDao(entityManager);
        StackExchangeRequester stackExchangeRequester = new StackExchangeRequester();
        Post program = new Post(pendingSpecificationDao, stackExchangeRequester);
        program.run();
    }
}
