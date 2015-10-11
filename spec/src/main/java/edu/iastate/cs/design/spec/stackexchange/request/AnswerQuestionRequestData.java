package edu.iastate.cs.design.spec.stackexchange.request;

public class AnswerQuestionRequestData implements IStackExchangeRequestData {

    private String id;
    private String body;
    private String key;
    private String accessToken;

    public AnswerQuestionRequestData(String id, String body, String key, String access_token) {
        this.id = id;
        this.body = body;
        this.key = key;
        this.accessToken = access_token;
    }

    public String getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public String getKey() {
        return key;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String requestUrl() {
        return "https://api.stackexchange.com/2.2/questions/" + id + "/answers/add";
    }
}
