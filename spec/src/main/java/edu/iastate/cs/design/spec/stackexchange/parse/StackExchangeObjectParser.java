package edu.iastate.cs.design.spec.stackexchange.parse;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.iastate.cs.design.spec.stackexchange.objects.BadgeCountDTO;
import edu.iastate.cs.design.spec.stackexchange.objects.PostDTO;
import edu.iastate.cs.design.spec.stackexchange.objects.ShallowUserDTO;

/**
 This class abstracts away the library that parses our json objects from Stack Exchange. Uses gson for the moment.
 */
public class StackExchangeObjectParser {

    public static PostDTO parsePostObject(String jsonObject) {
        return gsonParse(jsonObject, PostDTO.class);
    }

    public static BadgeCountDTO parseBadgeCountObject(String jsonObject) {
        return gsonParse(jsonObject, BadgeCountDTO.class);
    }

    public static ShallowUserDTO parseShallowUserObject(String jsonObject) {
        return gsonParse(jsonObject, ShallowUserDTO.class);
    }

    private static <T> T gsonParse(String jsonObject, Class<T> objectType) {
        Gson parser = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        return parser.fromJson(jsonObject, objectType);
    }
}
