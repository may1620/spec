package edu.iastate.cs.design.spec.post;


import org.junit.Test;

public class PostTest {

    @Test
    public void basicTest() {
        IPendingSpecificationDao pendingSpecificationDao = new MockPendingSpecificationDao();
        Post postProgram = new Post(pendingSpecificationDao);
        postProgram.run();
        // TODO validate things
    }
}
