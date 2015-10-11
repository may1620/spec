package edu.iastate.cs.design.spec.stackexchange.request;

import edu.iastate.cs.design.spec.stackexchange.objects.AnswerDTO;
import edu.iastate.cs.design.spec.stackexchange.objects.QuestionDTO;

import java.util.List;

/**
 * This class will provide methods for interacting with StackExchange.
 */
public interface IStackExchangeRequests {

    QuestionDTO postQuestion(QuestionAddRequestData requestData);

    List<AnswerDTO> getAnswersToQuestion(QuestionAnswersRequestData requestData);
}
