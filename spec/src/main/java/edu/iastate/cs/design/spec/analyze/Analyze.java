package edu.iastate.cs.design.spec.analyze;

import javax.persistence.EntityManager;

import edu.iastate.cs.design.spec.common.OpenQuestionsDao;
import edu.iastate.cs.design.spec.common.Specification;
import edu.iastate.cs.design.spec.persistenceResource.FactoryStartup;
import edu.iastate.cs.design.spec.post.IPendingSpecificationDao;
import edu.iastate.cs.design.spec.post.PendingSpecificationDao;
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
	
	public Analyze(IStackExchangeRequester stackExchangeRequester) 
	{
		this.stackExchangeRequester = stackExchangeRequester;
	}
	
    public void run() {
    	EntityManager entityManager = FactoryStartup.getAnEntityManager();
    	OpenQuestionsDao questions = new OpenQuestionsDao(entityManager);
    	for(Integer questionId : questions.getOpenQuestions()) {
    		QuestionAnswersRequestData questionData = new QuestionAnswersRequestData(QuestionAnswersRequestData.VOTES_SORT, questionId);
        	IPendingSpecificationDao pendingSpecificationDao = new PendingSpecificationDao(entityManager);
        	Specification specification = AnswerAnalysis.analyze(stackExchangeRequester.getAnswersToQuestion(questionData));
        	pendingSpecificationDao.insertPendingSpecification(specification);
    	}
    }

    // Entry point
    public static void main(String[] args) {
        IStackExchangeRequester stackExchangeRequester = new StackExchangeRequester();
        Analyze program = new Analyze(stackExchangeRequester);
        program.run();
    }
    
}
