package edu.iastate.cs.design.spec.stackexchange.request;

import java.util.List;

/**
 * Contains the fields needed to post a question to Stack Exchange.
 */
public class QuestionAddRequestData implements IStackExchangeRequestData {

    private static final String QUESTION_ADD_URL = "https://api.stackexchange.com/2.2/questions/add/";

    private String title;
    private String body;
    private List<String> tags;
    private String key;
    private String accessToken;

    public QuestionAddRequestData(String title, String body, List<String> tags, String key, String accessToken) {
        this.title = title;
        this.body = body;
        this.tags = tags;
        this.key = key;
        this.accessToken = accessToken;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getKey() {
        return key;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String requestUrl() {
        return QUESTION_ADD_URL;
    }
}
