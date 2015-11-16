package edu.iastate.cs.design.spec.post;

import edu.iastate.cs.design.spec.common.*;
import edu.iastate.cs.design.spec.persistenceResource.FactoryStartup;
import edu.iastate.cs.design.spec.stackexchange.objects.QuestionDTO;
import edu.iastate.cs.design.spec.stackexchange.request.IStackExchangeRequester;
import edu.iastate.cs.design.spec.stackexchange.request.QuestionAddRequestData;
import edu.iastate.cs.design.spec.stackexchange.request.StackExchangeRequester;

import javax.persistence.EntityManager;

import org.apache.http.client.ClientProtocolException;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * This class contains the main method for the part of the system that will
 * find specifications in the database that need to be verified and will post
 * them as questions on StackOverflow.
 */
public class Post {

    private ISpecificationDao specificationDao;
    private IStackExchangeRequester stackExchangeRequester;
    private IQuestionDao openQuestionsDao;

    public Post(ISpecificationDao specificationDao,
                IStackExchangeRequester stackExchangeRequester,
                IQuestionDao openQuestionsDao) {
        this.specificationDao = specificationDao;
        this.stackExchangeRequester = stackExchangeRequester;
        this.openQuestionsDao = openQuestionsDao;
    }

    public void run() throws ClientProtocolException, IOException, URISyntaxException {
        Specification pendingSpecification = specificationDao.removeNextPendingSpecification();
        QuestionAddRequestData requestData = createQuestionAddRequestData(pendingSpecification);
        QuestionDTO question;
		try {
			question = stackExchangeRequester.postQuestion(requestData);
			openQuestionsDao.insertQuestion(question.getQuestionId());
		} catch (Exception e) {
			e.printStackTrace();
		}
        
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
    	System.out.println("Enter decyrption password: ");
    	Scanner scan = new Scanner(System.in);
    	String password = scan.next();
    	scan.close();
    	System.out.println("Processing...");
    	StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
    	encryptor.setPassword(password);
    	String decryptText = encryptor.decrypt("LrJhEUS82WeTvRzJsbGzQfihL3EKW0sdMEqtnyRUiS8g6IdYLBybQA==");
    	System.out.println(decryptText);
        return decryptText;
    }

    private String getStackExchangeKey() {
        return "zuQeOSftIrtt8lhr0mYUoQ((";
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
    public static void main(String[] args) throws ClientProtocolException, IOException, URISyntaxException {
        EntityManager entityManager = FactoryStartup.getAnEntityManager();
        ISpecificationDao specificationDao = new SpecificationDao(entityManager);
        IQuestionDao questionsDao = new QuestionDao(entityManager);
        IStackExchangeRequester stackExchangeRequester = new StackExchangeRequester();
        Post program = new Post(specificationDao, stackExchangeRequester, questionsDao);
        program.run();
    }
}
