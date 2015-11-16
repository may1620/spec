package edu.iastate.cs.design.spec.stackexchange.request;

import edu.iastate.cs.design.spec.stackexchange.objects.AnswerDTO;
import edu.iastate.cs.design.spec.stackexchange.objects.QuestionDTO;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

/**
 * This class will provide methods for interacting with StackExchange.
 */
public interface IStackExchangeRequester {

    QuestionDTO postQuestion(QuestionAddRequestData requestData) throws ClientProtocolException, IOException, URISyntaxException;

    List<AnswerDTO> getAnswersToQuestion(QuestionAnswersRequestData requestData) throws JSONException, ClientProtocolException, IOException, URISyntaxException;

    AnswerDTO postAnswerToQuestion(AnswerQuestionRequestData requestData);

    QuestionDTO getQuestionByQuestionId(int questionId) throws JSONException, ClientProtocolException, IOException;
}
