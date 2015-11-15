package edu.iastate.cs.design.spec.post;


import edu.iastate.cs.design.spec.common.*;
import edu.iastate.cs.design.spec.entities.Question;
import edu.iastate.cs.design.spec.stackexchange.objects.AnswerDTO;
import edu.iastate.cs.design.spec.stackexchange.objects.QuestionDTO;
import edu.iastate.cs.design.spec.stackexchange.request.AnswerQuestionRequestData;
import edu.iastate.cs.design.spec.stackexchange.request.IStackExchangeRequester;
import edu.iastate.cs.design.spec.stackexchange.request.MockStackExchangeRequester;
import edu.iastate.cs.design.spec.stackexchange.request.QuestionAnswersRequestData;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PostTest {

    @Test
    public void basicTest() throws IOException {
        List<String> approxPreconditions = Arrays.asList("items != null", "items.length > 0");
        List<String> approxPostconditions = Arrays.asList("items != null", "items.length == \\old(items.length)");
        List<String> formals = Arrays.asList("E item");
        Specification pendingSpecification = new Specification("java.util", "ArrayList", "add", "void",
                formals, approxPreconditions, approxPostconditions);
        ISpecificationDao specificationDao = new MockSpecificationDao();
        IStackExchangeRequester stackExchangeRequester = new MockStackExchangeRequester();
        IQuestionDao openQuestionsDao = new MockQuestionDao();
        specificationDao.insertPendingSpecification(pendingSpecification);
        Post postProgram = new Post(specificationDao, stackExchangeRequester, openQuestionsDao);
        postProgram.run();
        List<Question> questions = openQuestionsDao.getAllQuestions();
        Assert.assertEquals(1, questions.size());
        int questionId = questions.get(0).getQuestionId();
        QuestionDTO questionDTO = stackExchangeRequester.getQuestionByQuestionId(questionId);
        Assert.assertTrue(questionDTO.getTitle().contains(pendingSpecification.getMethodName()));
        QuestionAnswersRequestData questionRequestData =
                new QuestionAnswersRequestData(QuestionAnswersRequestData.VOTES_SORT, questionId);
        List<AnswerDTO> answers = stackExchangeRequester.getAnswersToQuestion(questionRequestData);
        Assert.assertEquals(0, answers.size());
        // The following code is worthless for now, but would be useful for testing in the future with real connections
        AnswerQuestionRequestData answerRequestData = new AnswerQuestionRequestData(questionId, "answer", null, null);
        stackExchangeRequester.postAnswerToQuestion(answerRequestData);
        answers = stackExchangeRequester.getAnswersToQuestion(questionRequestData);
        Assert.assertEquals(1, answers.size());
        Assert.assertEquals("answer", answers.get(0).getBody());
    }
}
