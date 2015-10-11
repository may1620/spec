package edu.iastate.cs.design.spec.stackexchange.request;

import edu.iastate.cs.design.spec.stackexchange.objects.AnswerDTO;
import edu.iastate.cs.design.spec.stackexchange.objects.QuestionDTO;

import java.util.List;

/**
 * This class will make request to StackExchange. Not a fan of the name right now.
 */
public class StackExchangeRequester implements IStackExchangeRequester {

    public QuestionDTO postQuestion(QuestionAddRequestData requestData) {
        // TODO
        return null;
    }

    public List<AnswerDTO> getAnswersToQuestion(QuestionAnswersRequestData requestData) {
        // TODO
        return null;
    }

    public AnswerDTO postAnswerToQuestion(AnswerQuestionRequestData requestData) {
        // TODO
        return null;
    }
}
