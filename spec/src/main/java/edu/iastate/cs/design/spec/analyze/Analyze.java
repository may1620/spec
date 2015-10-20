package edu.iastate.cs.design.spec.analyze;

import javax.persistence.EntityManager;

import edu.iastate.cs.design.spec.common.OpenQuestionsDao;
import edu.iastate.cs.design.spec.common.Specification;
import edu.iastate.cs.design.spec.persistenceResource.FactoryStartup;
import edu.iastate.cs.design.spec.common.ISpecificationDao;
import edu.iastate.cs.design.spec.common.SpecificationDao;
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
	
    public void run() {
    	EntityManager entityManager = FactoryStartup.getAnEntityManager();
    	OpenQuestionsDao questions = new OpenQuestionsDao(entityManager);
    	for(Integer questionId : questions.getOpenQuestions()) {
    		QuestionAnswersRequestData questionData = new QuestionAnswersRequestData(QuestionAnswersRequestData.VOTES_SORT, questionId);
        	Specification specification = AnswerAnalysis.analyze(stackExchangeRequester.getAnswersToQuestion(questionData));
        	specificationDao.insertFinalizedSpecification(specification);
    	}
    }

    // Entry point
    public static void main(String[] args) {
        IStackExchangeRequester stackExchangeRequester = new StackExchangeRequester();
		EntityManager entityManager = FactoryStartup.getAnEntityManager();
		ISpecificationDao specificationDao = new SpecificationDao(entityManager);
        Analyze program = new Analyze(stackExchangeRequester, specificationDao);
        program.run();
    }
    
}
