package edu.iastate.cs.design.spec.stackexchange.request;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.iastate.cs.design.spec.stackexchange.objects.AnswerDTO;
import edu.iastate.cs.design.spec.stackexchange.objects.QuestionDTO;

/**
 * This class will make request to StackExchange. Not a fan of the name right now.
 */
public class StackExchangeRequester implements IStackExchangeRequester {

	private static CloseableHttpClient httpclient = HttpClientBuilder.create().build();
	private static HttpGet httpget;
	private static BasicResponseHandler responseHandler = new BasicResponseHandler();
	
	
    public QuestionDTO postQuestion(QuestionAddRequestData requestData) throws ClientProtocolException, IOException, URISyntaxException {
    	 URI postURI = requestData.requestUrl();
    	 HttpGet post = new HttpGet(postURI);
    	 JSONArray rawQuestionInfo = new JSONObject(httpclient.execute(post, responseHandler)).getJSONArray("items");
    	 return null;
    	 //TODO it seems that we may not be able to directly get the QuestionDTO info from the JSON response
    }

    public List<AnswerDTO> getAnswersToQuestion(QuestionAnswersRequestData requestData) throws JSONException, ClientProtocolException, IOException, URISyntaxException {
        ArrayList<AnswerDTO> answers = new ArrayList<AnswerDTO>();
        try {
			httpget = new HttpGet(requestData.requestUrl());
		} catch (Exception e) {
			e.printStackTrace();
		}
        JSONArray rawAnswers = new JSONObject(httpclient.execute(httpget, responseHandler)).getJSONArray("items");
        for(int i = 0; i < rawAnswers.length(); i++) {
        	AnswerDTO answer = new AnswerDTO(rawAnswers.getJSONObject(i).getString("body"), rawAnswers.getJSONObject(i).getInt("answer_id"), requestData.getId());
        	answers.add(answer);
        }
        return answers;
    }

    public AnswerDTO postAnswerToQuestion(AnswerQuestionRequestData requestData) {
        // TODO
        return null;
    }

    public QuestionDTO getQuestionByQuestionId(int questionId) throws JSONException, ClientProtocolException, IOException {
    	String questionURL = "http://api.stackexchange.com/2.2/questions/" + questionId + 
    							"?site=stackoverflow&filter=withbody&sort=votes&key=KW1Do4aYqEdlMNsHpPEHdg((";
		httpget = new HttpGet(questionURL);
		JSONObject rawQuestion = new JSONObject(httpclient.execute(httpget, responseHandler));
		ArrayList<String> tags = new ArrayList<String>();
		JSONArray rawTags = rawQuestion.getJSONArray("tags");
		
		for(int i = 0; i < rawQuestion.getJSONArray("tags").length(); i++) {
			tags.add(rawTags.getString(i));
		}
		
		String title = rawQuestion.getString("title");
		String body = rawQuestion.getString("body");
		QuestionDTO question = new QuestionDTO(title, body, tags, questionId);
		
		return question;
    }
}
