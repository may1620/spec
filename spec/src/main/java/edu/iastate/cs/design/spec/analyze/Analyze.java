package edu.iastate.cs.design.spec.analyze;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.persistence.EntityManager;

import org.json.JSONException;

import edu.iastate.cs.design.spec.common.MethodDao;
import edu.iastate.cs.design.spec.common.QuestionDao;
import edu.iastate.cs.design.spec.entities.Method;
import edu.iastate.cs.design.spec.entities.Question;
import edu.iastate.cs.design.spec.persistenceResource.FactoryStartup;
import edu.iastate.cs.design.spec.stackexchange.objects.AnswerDTO;
import edu.iastate.cs.design.spec.stackexchange.request.IStackExchangeRequester;
import edu.iastate.cs.design.spec.stackexchange.request.QuestionAnswersRequestData;
import edu.iastate.cs.design.spec.stackexchange.request.StackExchangeRequester;

/**
 * This class contains the main method for the Analyze component of the system. This component
 * will look up open question in the database, download their data from StackExchange and then
 * parse the answers given. If it is decided that a question has been correctly answered, the
 * answer will be stored in the database as a finalized specification.
 */
public class Analyze {

	private IStackExchangeRequester stackExchangeRequester;
	private Method method;
	
	public Analyze(IStackExchangeRequester stackExchangeRequester, MethodDao methodDao)
	{
		this.stackExchangeRequester = stackExchangeRequester;
		this.method = methodDao.getInProgressMethod();
	}
	
    public void run() throws JSONException, IOException, URISyntaxException {
    	EntityManager entityManager = FactoryStartup.getAnEntityManager();
    	QuestionDao questions = new QuestionDao(entityManager);
    	for(Question question : questions.getAllQuestions()) {
    		QuestionAnswersRequestData questionData = new QuestionAnswersRequestData(QuestionAnswersRequestData.VOTES_SORT, question.getQuestionId());
        	List<AnswerDTO> answers = stackExchangeRequester.getAnswersToQuestion(questionData);
    	}
    }

    // Entry point
    public static void main(String[] args) throws JSONException, IOException, URISyntaxException {
    	//Analyze Test
        IStackExchangeRequester stackExchangeRequester = new StackExchangeRequester();
		EntityManager entityManager = FactoryStartup.getAnEntityManager();
		MethodDao methodDao = new MethodDao(entityManager);
		
        Analyze program = new Analyze(stackExchangeRequester, methodDao);
        program.run();
    
    }
    
}
