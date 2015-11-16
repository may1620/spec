package edu.iastate.cs.design.spec.stackexchange.request;

import java.net.URI;
import java.net.URISyntaxException;





public interface IStackExchangeRequestData {

    URI requestUrl() throws URISyntaxException;
}
