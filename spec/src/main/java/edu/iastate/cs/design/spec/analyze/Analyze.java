package edu.iastate.cs.design.spec.analyze;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import edu.iastate.cs.design.spec.common.ISpecificationDao;
import edu.iastate.cs.design.spec.common.QuestionDao;
import edu.iastate.cs.design.spec.common.Specification;
import edu.iastate.cs.design.spec.common.SpecificationDao;
import edu.iastate.cs.design.spec.entities.Question;
import edu.iastate.cs.design.spec.entities.TestEntity;
import edu.iastate.cs.design.spec.persistenceResource.FactoryStartup;
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
	private ISpecificationDao specificationDao;
	
	public Analyze(IStackExchangeRequester stackExchangeRequester, ISpecificationDao specificationDao)
	{
		this.stackExchangeRequester = stackExchangeRequester;
		this.specificationDao = specificationDao;
	}
	
    public void run() throws JSONException, IOException, URISyntaxException {
    	EntityManager entityManager = FactoryStartup.getAnEntityManager();
    	QuestionDao questions = new QuestionDao(entityManager);
    	for(Question question : questions.getAllQuestions()) {
    		QuestionAnswersRequestData questionData = new QuestionAnswersRequestData(QuestionAnswersRequestData.VOTES_SORT, question.getQuestionId());
        	Specification specification = AnswerAnalysis.analyze(stackExchangeRequester.getAnswersToQuestion(questionData));
        	specificationDao.insertFinalizedSpecification(specification);
    	}
    }

    // Entry point
    public static void main(String[] args) throws JSONException, IOException, URISyntaxException {
    	//Analyze Test
        IStackExchangeRequester stackExchangeRequester = new StackExchangeRequester();
		EntityManager entityManager = FactoryStartup.getAnEntityManager();
		ISpecificationDao specificationDao = new SpecificationDao(entityManager);
        Analyze program = new Analyze(stackExchangeRequester, specificationDao);
        program.run();
    	
    	//Database Test
    	TypedQuery<TestEntity> query = entityManager.createNamedQuery("TestEntity.getAll", TestEntity.class);
    	for(TestEntity test : query.getResultList()) {
    		System.out.println(test.getTestId() + " " + test.getTestName());
    	}
    
    }
    
}
