package edu.iastate.cs.design.spec.stackexchange.request;

import edu.iastate.cs.design.spec.stackexchange.objects.AnswerDTO;
import edu.iastate.cs.design.spec.stackexchange.objects.QuestionDTO;

import java.util.*;

public class MockStackExchangeRequester implements IStackExchangeRequester {

    Map<QuestionDTO, List<AnswerDTO>> postMap;
    private int nextQuestionId;
    private int nextAnswerId;

    public MockStackExchangeRequester() {
        postMap = new HashMap<QuestionDTO, List<AnswerDTO>>();
        nextQuestionId = 1;
        nextAnswerId = 1;
    }

    public QuestionDTO postQuestion(QuestionAddRequestData requestData) {
        QuestionDTO question = new QuestionDTO(requestData.getTitle(), requestData.getBody(), requestData.getTags(), nextQuestionId);
        postMap.put(question, new ArrayList<AnswerDTO>());
        ++nextQuestionId;
        return question;
    }

    public List<AnswerDTO> getAnswersToQuestion(QuestionAnswersRequestData requestData) {
        QuestionDTO question = getQuestionByQuestionId(requestData.getId());
        return postMap.get(question);
    }

    public AnswerDTO postAnswerToQuestion(AnswerQuestionRequestData requestData) {
        QuestionDTO question = getQuestionByQuestionId(requestData.getId());
        // Will throw if question id didn't exist. Fine for the tests.
        AnswerDTO answer = new AnswerDTO(requestData.getBody(), nextAnswerId, question.getQuestionId());
        List<AnswerDTO> answers = postMap.get(question);
        answers.add(answer);
        ++nextAnswerId;
        return answer;
    }

    public QuestionDTO getQuestionByQuestionId(int questionId) {
        for (QuestionDTO question : postMap.keySet()) {
            if (question.getQuestionId() == questionId) {
                return question;
            }
        }
        return null;
    }
}
