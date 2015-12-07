package edu.iastate.cs.design.spec.post;

import edu.iastate.cs.design.spec.common.*;
import edu.iastate.cs.design.spec.entities.Method;
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


    private IStackExchangeRequester stackExchangeRequester;
    private IQuestionDao openQuestionsDao;
    private static Method method;
    
    public Post(MethodDao methodDao, IStackExchangeRequester stackExchangeRequester,
                IQuestionDao openQuestionsDao) {
        this.stackExchangeRequester = stackExchangeRequester;
        this.openQuestionsDao = openQuestionsDao;
        method = methodDao.getNewMethod();
    }

    public void run() throws ClientProtocolException, IOException, URISyntaxException {
        QuestionAddRequestData requestData = createQuestionAddRequestData(method);
        QuestionDTO question;
		try {
			question = stackExchangeRequester.postQuestion(requestData);
			openQuestionsDao.insertQuestion(question.getQuestionId());
		} catch (Exception e) {
			e.printStackTrace();
		}
        
    }

    private QuestionAddRequestData createQuestionAddRequestData(Method method) {
        String title = createQuestionTitle(method);
        String body = createQuestionBody(method);
        List<String> tags = createQuestionTags(method);
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
    	String decryptText = encryptor.decrypt("jlzAAN2S5dUPd3ubP/WZ3HZgnAb/SnLbyHR+OadFWa/K/GFL0ZRNSg==");
    	System.out.println(decryptText);
        return decryptText;
    }

    private String getStackExchangeKey() {
        return "zuQeOSftIrtt8lhr0mYUoQ((";
    }

    private List<String> createQuestionTags(Method method) {
        return Arrays.asList("JML", "Java", method.getName());
    }

    private String createQuestionTitle(Method method) {
        return "What should the JML specification be for the " + method.getName() + "() method?";
    }

    //TODO Need a way of getting all of this post info to create the question
    private String createQuestionBody(Method method) {
        StringBuilder postBody = new StringBuilder();
        postBody.append("What would be the proper JML specification for the <code>");
        //postBody.append(method.getClassName());
        postBody.append(".");
        //postBody.append(method.getMethodName());
        postBody.append("()</code> method in the <code>");
        //postBody.append(method.getFullPackageName());
        postBody.append("</code>package?\n");
        postBody.append("What I have for this method so far is:\n<code>");
/*        for (String precondition : method.getPreconditions()) {
            postBody.append(precondition);
            postBody.append('\n');
        }
        for (String postcondition : method.getPostconditions()) {
            postBody.append(postcondition);
            postBody.append('\n');
        }*/
        postBody.append("public ");
        //postBody.append(method.getReturnType());
        postBody.append(' ');
        //postBody.append(method.getMethodName());
        postBody.append("(");
/*        List<String> params = method.getFormalParameters();
        for (int i = 0; i < params.size(); ++i) {
            postBody.append(params.get(i));
            if (i != params.size() - 1) {
                postBody.append(", ");
            }
        }*/
        postBody.append(")</code>");
        // TODO we probably want to use method bodies here
        return postBody.toString();
    }

    // Entry point
    public static void main(String[] args) throws ClientProtocolException, IOException, URISyntaxException {
        EntityManager entityManager = FactoryStartup.getAnEntityManager();
        IQuestionDao questionsDao = new QuestionDao(entityManager);
        IStackExchangeRequester stackExchangeRequester = new StackExchangeRequester();
        MethodDao methodAccess = new MethodDao(entityManager);
        Post program = new Post(methodAccess, stackExchangeRequester, questionsDao);
        program.run();
    }
}
