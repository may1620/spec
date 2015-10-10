package edu.iastate.cs.design.spec.post;

import edu.iastate.cs.design.spec.persistenceResource.FactoryStartup;

import javax.persistence.EntityManager;

/**
 * This class contains the main method for the part of the system that will
 * find specifications in the database that need to be verified and will post
 * them as questions on StackOverflow.
 */
public class Post {

    private IPendingSpecificationDao pendingSpecificationDao;

    public Post(IPendingSpecificationDao pendingSpecificationDao) {
        this.pendingSpecificationDao = pendingSpecificationDao;
    }

    public void run() {

    }

    // Entry point
    public static void main(String[] args) {
        EntityManager entityManager = FactoryStartup.getAnEntityManager();
        PendingSpecificationDao pendingSpecificationDao = new PendingSpecificationDao(entityManager);
        Post program = new Post(pendingSpecificationDao);
        program.run();
    }
}
