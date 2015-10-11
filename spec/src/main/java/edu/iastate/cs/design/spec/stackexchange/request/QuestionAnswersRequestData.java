package edu.iastate.cs.design.spec.stackexchange.request;

public class QuestionAnswersRequestData implements IStackExchangeRequestData {

    public final String ACTIVITY_SORT = "activity";
    public final String CREATION_DATE_SORT = "creation";
    public final String VOTES_SORT = "votes";

    private String sort;
    // The api actually accepts a list of ids, but we will just use one.
    private String id;

    public QuestionAnswersRequestData(String sort, String id) {
        this.sort = sort;
        this.id = id;
    }

    public String getSort() {
        return sort;
    }

    public String getId() {
        return id;
    }

    public String requestUrl() {
        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append("https://api.stackexchange.com/2.2/questions/");
        requestBuilder.append(id);
        requestBuilder.append("/answers?order=desc&sort=");
        requestBuilder.append(sort);
        requestBuilder.append("&site=stackoverflow");
        requestBuilder.append("&key=KW1Do4aYqEdlMNsHpPEHdg((");
        return requestBuilder.toString();
    }
}
