package edu.iastate.cs.design.spec.post;

import edu.iastate.cs.design.spec.common.IOpenQuestionsDao;
import edu.iastate.cs.design.spec.common.OpenQuestionsDao;
import edu.iastate.cs.design.spec.common.Specification;
import edu.iastate.cs.design.spec.persistenceResource.FactoryStartup;
import edu.iastate.cs.design.spec.stackexchange.objects.QuestionDTO;
import edu.iastate.cs.design.spec.stackexchange.request.IStackExchangeRequester;
import edu.iastate.cs.design.spec.stackexchange.request.QuestionAddRequestData;
import edu.iastate.cs.design.spec.stackexchange.request.StackExchangeRequester;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

/**
 * This class contains the main method for the part of the system that will
 * find specifications in the database that need to be verified and will post
 * them as questions on StackOverflow.
 */
public class Post {

    private IPendingSpecificationDao pendingSpecificationDao;
    private IStackExchangeRequester stackExchangeRequester;
    private IOpenQuestionsDao openQuestionsDao;

    public Post(IPendingSpecificationDao pendingSpecificationDao,
                IStackExchangeRequester stackExchangeRequester,
                IOpenQuestionsDao openQuestionsDao) {
        this.pendingSpecificationDao = pendingSpecificationDao;
        this.stackExchangeRequester = stackExchangeRequester;
        this.openQuestionsDao = openQuestionsDao;
    }

    public void run() {
        Specification pendingSpecification = pendingSpecificationDao.removeNextPendingSpecification();
        QuestionAddRequestData requestData = createQuestionAddRequestData(pendingSpecification);
        QuestionDTO question = stackExchangeRequester.postQuestion(requestData);
        openQuestionsDao.insertOpenQuestion(question.getQuestionId());
    }

    private QuestionAddRequestData createQuestionAddRequestData(Specification pendingSpecification) {
        String title = createQuestionTitle(pendingSpecification);
        String body = createQuestionBody(pendingSpecification);
        List<String> tags = createQuestionTags(pendingSpecification);
        String key = getStackExchangeKey();
        String accessToken = getStackExchangeAccessToken();
        return new QuestionAddRequestData(title, body, tags, key, accessToken);
    }

    private String getStackExchangeAccessToken() {
        // TODO implement this correctly
        return "access_token";
    }

    private String getStackExchangeKey() {
        // TODO implement this correctly
        return "key";
    }

    private List<String> createQuestionTags(Specification pendingSpecification) {
        return Arrays.asList("JML", "Java", pendingSpecification.getClassName());
    }

    private String createQuestionTitle(Specification pendingSpecification) {
        return "What should the JML specification be for the " + pendingSpecification.getMethodName() + "() method?";
    }

    private String createQuestionBody(Specification pendingSpecification) {
        StringBuilder postBody = new StringBuilder();
        postBody.append("What would be the proper JML specification for the <code>");
        postBody.append(pendingSpecification.getClassName());
        postBody.append(".");
        postBody.append(pendingSpecification.getMethodName());
        postBody.append("()</code> method in the <code>");
        postBody.append(pendingSpecification.getFullPackageName());
        postBody.append("</code>package?\n");
        postBody.append("What I have for this method so far is:\n<code>");
        for (String precondition : pendingSpecification.getPreconditions()) {
            postBody.append(precondition);
            postBody.append('\n');
        }
        for (String postcondition : pendingSpecification.getPostconditions()) {
            postBody.append(postcondition);
            postBody.append('\n');
        }
        postBody.append("public ");
        postBody.append(pendingSpecification.getReturnType());
        postBody.append(' ');
        postBody.append(pendingSpecification.getMethodName());
        postBody.append("(");
        List<String> params = pendingSpecification.getFormalParameters();
        for (int i = 0; i < params.size(); ++i) {
            postBody.append(params.get(i));
            if (i != params.size() - 1) {
                postBody.append(", ");
            }
        }
        postBody.append(")</code>");
        // TODO we probably want to use method bodies here
        return postBody.toString();
    }

    // Entry point
    public static void main(String[] args) {
        EntityManager entityManager = FactoryStartup.getAnEntityManager();
        IPendingSpecificationDao pendingSpecificationDao = new PendingSpecificationDao(entityManager);
        IOpenQuestionsDao openQuestionsDao = new OpenQuestionsDao(entityManager);
        IStackExchangeRequester stackExchangeRequester = new StackExchangeRequester();
        Post program = new Post(pendingSpecificationDao, stackExchangeRequester, openQuestionsDao);
        program.run();
    }
}
