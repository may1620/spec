package edu.iastate.cs.design.spec.stackCommunication;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.iastate.cs.design.spec.stackexchange.objects.PostDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class DisplayCodeSnippet {
	


    public void run() throws Exception {
        String json = getJson("https://api.stackexchange.com/2.2/posts?page=1&pagesize=100&order=desc&sort=activity&site=stackoverflow&filter=withbody");
        Gson parser = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        Posts posts = parser.fromJson(json, Posts.class);
        System.out.println(posts.items.get(0).getBody());
        Document body = Jsoup.parse(posts.items.get(0).getBody());
        Elements codeBlocks = body.select("code");
        System.out.println("The first code block: " + codeBlocks.get(0).text());
    }

    private static String getJson(String url) throws Exception {
        URL stackExchangeUrl =
                new URL(url);
        HttpURLConnection request = (HttpURLConnection) stackExchangeUrl.openConnection();
        request.setRequestMethod("GET");
        request.setRequestProperty("Accept-Encoding", "gzip");
        request.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(request.getInputStream())));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        reader.close();
        request.disconnect();
        String json = builder.toString();
        return json;
    }


    public static void main(String[] args) throws Exception{
        new DisplayCodeSnippet().run();
    }

    class Posts {
        private List<PostDTO> items;
    }
}
