package edu.iastate.cs.design.spec.stackexchange.objects;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.iastate.cs.design.spec.stackexchange.parse.StackExchangeObjectParser;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class PostTest {

    @Test
    public void deserialize() {
        String json = "{\n" +
                "  \"owner\": {\n" +
                "    \"reputation\": 9001,\n" +
                "    \"user_id\": 1,\n" +
                "    \"user_type\": \"registered\",\n" +
                "    \"accept_rate\": 55,\n" +
                "    \"profile_image\": \"https://www.gravatar.com/avatar/a007be5a61f6aa8f3e85ae2fc18dd66e?d=identicon&r=PG\",\n" +
                "    \"display_name\": \"Example User\",\n" +
                "    \"link\": \"http://example.stackexchange.com/users/1/example-user\"\n" +
                "  },\n" +
                "  \"down_vote_count\": 2,\n" +
                "  \"up_vote_count\": 7,\n" +
                "  \"score\": 5,\n" +
                "  \"last_edit_date\": 1443416142,\n" +
                "  \"last_activity_date\": 1443437742,\n" +
                "  \"creation_date\": 1443372942,\n" +
                "  \"post_type\": \"question\",\n" +
                "  \"post_id\": 1,\n" +
                "  \"body\": \"question body\",\n" +
                "  \"comments\": [\"hello\", \"there\", \"needs more jquery\"]\n" +
                "}";
        PostDTO post = StackExchangeObjectParser.parsePostObject(json);
        ShallowUserDTO expectedOwner = new ShallowUserDTO(55, null, "Example User",
                "http://example.stackexchange.com/users/1/example-user",
                "https://www.gravatar.com/avatar/a007be5a61f6aa8f3e85ae2fc18dd66e?d=identicon&r=PG", 9001, 1, UserDTO.REGISTERED_USER_TYPE);
        assertEquals(expectedOwner, post.getOwner());
        assertEquals(2, post.getDownVoteCount());
        assertEquals(7, post.getUpVoteCount());
        assertEquals(5, post.getScore());
        assertEquals(1443416142L, post.getLastEditDate());
        assertEquals(1443437742L, post.getLastActivityDate());
        assertEquals(1443372942L, post.getCreationDate());
        assertEquals(PostDTO.QUESTION_POST_TYPE, post.getPostType());
        assertEquals(1, post.getPostId());
        assertEquals("question body", post.getBody());
        assertEquals(Arrays.asList("hello", "there", "needs more jquery"), post.getComments());
    }
}
