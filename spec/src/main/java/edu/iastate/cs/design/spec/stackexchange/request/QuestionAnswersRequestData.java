package edu.iastate.cs.design.spec.stackexchange.request;

import java.net.URI;
import java.net.URISyntaxException;

public class QuestionAnswersRequestData implements IStackExchangeRequestData {

    public static final String ACTIVITY_SORT = "activity";
    public static final String CREATION_DATE_SORT = "creation";
    public static final String VOTES_SORT = "votes";

    private String sort;
    // The api actually accepts a list of ids, but we will just use one.
    private int id;

    public QuestionAnswersRequestData(String sort, int id) {
        this.sort = sort;
        this.id = id;
    }

    public String getSort() {
        return sort;
    }

    public int getId() {
        return id;
    }

    public URI requestUrl() throws URISyntaxException {
        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append("https://api.stackexchange.com/2.2/questions/");
        requestBuilder.append(id);
        requestBuilder.append("/answers?order=desc&sort=");
        requestBuilder.append(sort);
        requestBuilder.append("&site=stackoverflow");
        requestBuilder.append("&filter=withbody");
        requestBuilder.append("&key=KW1Do4aYqEdlMNsHpPEHdg((");
        return new URI(requestBuilder.toString());
    }
}
