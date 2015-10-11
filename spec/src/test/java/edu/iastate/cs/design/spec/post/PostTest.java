package edu.iastate.cs.design.spec.post;


import edu.iastate.cs.design.spec.common.IOpenQuestionsDao;
import edu.iastate.cs.design.spec.common.MockOpenQuestionsDao;
import edu.iastate.cs.design.spec.common.Specification;
import edu.iastate.cs.design.spec.stackexchange.request.IStackExchangeRequester;
import edu.iastate.cs.design.spec.stackexchange.request.MockStackExchangeRequester;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class PostTest {

    @Test
    public void basicTest() {
        List<String> approxPreconditions = Arrays.asList("items != null", "items.length > 0");
        List<String> approxPostconditions = Arrays.asList("items != null", "items.length == \\old(items.length)");
        List<String> formals = Arrays.asList("E item");
        Specification pendingSpecification = new Specification("java.util", "ArrayList", "add", "void",
                formals, approxPreconditions, approxPostconditions);
        IPendingSpecificationDao pendingSpecificationDao = new MockPendingSpecificationDao();
        IStackExchangeRequester stackExchangeRequester = new MockStackExchangeRequester();
        IOpenQuestionsDao openQuestionsDao = new MockOpenQuestionsDao();
        pendingSpecificationDao.insertPendingSpecification(pendingSpecification);
        Post postProgram = new Post(pendingSpecificationDao, stackExchangeRequester, openQuestionsDao);
        postProgram.run();
        // TODO validate things
    }
}
