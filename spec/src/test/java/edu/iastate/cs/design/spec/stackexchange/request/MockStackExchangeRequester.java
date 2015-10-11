package edu.iastate.cs.design.spec.stackexchange.request;

import edu.iastate.cs.design.spec.stackexchange.objects.AnswerDTO;
import edu.iastate.cs.design.spec.stackexchange.objects.QuestionDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockStackExchangeRequester implements IStackExchangeRequester {

    Map<QuestionDTO, List<AnswerDTO>> postMap;

    public MockStackExchangeRequester() {
        postMap = new HashMap<QuestionDTO, List<AnswerDTO>>();
    }

    public QuestionDTO postQuestion(QuestionAddRequestData requestData) {
        QuestionDTO question = new QuestionDTO();
        // TODO populate question
        postMap.put(question, new ArrayList<AnswerDTO>());
        return question;
    }

    public List<AnswerDTO> getAnswersToQuestion(QuestionAnswersRequestData requestData) {
        QuestionDTO question = new QuestionDTO();
        // TODO populate question
        return postMap.get(question);
    }

    public AnswerDTO postAnswerToQuestion(AnswerQuestionRequestData requestData) {
        QuestionDTO question = new QuestionDTO();
        AnswerDTO answer = new AnswerDTO();
        // TODO populate question and answer
        List<AnswerDTO> answers = postMap.get(question);
        answers.add(answer);
        return answer;
    }
}
