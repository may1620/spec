package edu.iastate.cs.design.spec.stackexchange.request;

import edu.iastate.cs.design.spec.stackexchange.objects.AnswerDTO;
import edu.iastate.cs.design.spec.stackexchange.objects.QuestionDTO;

import java.util.List;

/**
 * This class will provide methods for interacting with StackExchange.
 */
public interface IStackExchangeRequester {

    QuestionDTO postQuestion(QuestionAddRequestData requestData);

    List<AnswerDTO> getAnswersToQuestion(QuestionAnswersRequestData requestData);

    AnswerDTO postAnswerToQuestion(AnswerQuestionRequestData requestData);

    QuestionDTO getQuestionByQuestionId(int questionId);
}
