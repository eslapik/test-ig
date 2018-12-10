import static java.lang.System.out;

import com.restfb.*;
import com.restfb.types.Page;

import java.util.*;

public class GraphReaderExample extends Example {
    /**
     * RestFB Graph API client.
     */
    private final FacebookClient facebookClient25;
    private final String hashtag;
    private final String fbBusinessPageId;

    /**
     * Entry point. You must provide a 3 arguments on the command line: a valid Graph API access token,
     * Facebook business page id fbBusinessPageId that has user with role to access Instagram data and hashtag name.
     *
     * @param args Command-line arguments.
     * @throws IllegalArgumentException If no command-line arguments are provided.
     */
    public static void main(String[] args) {
        if (args.length == 0)
            throw new IllegalArgumentException(
                    "You must provide an OAuth access token parameter, business page id and hashtag to search.");

        new GraphReaderExample(args[0], args[1], args[2]).fetchObject();
    }

    GraphReaderExample(String accessToken, String fbBusinessPageId, String hashtag) {
        facebookClient25 = new DefaultFacebookClient(accessToken, Version.VERSION_2_12);
        this.hashtag = hashtag;
        this.fbBusinessPageId = fbBusinessPageId;
    }

    void fetchObject() {
        out.println("* Fetching single objects *");
        Page page = facebookClient25.fetchObject(fbBusinessPageId, Page.class,
                Parameter.with("fields", "instagram_business_account"));

        Connection<HashTag> connection = facebookClient25.fetchConnection("ig_hashtag_search", HashTag.class,
                Parameter.with("fields", "id, name"),
                Parameter.with("user_id", page.getInstagramBusinessAccount().getId()),
                Parameter.with("q", hashtag));

        int count = 0;
        for (List<HashTag> hashTags : connection) {
            count += hashTags.size();
        }

        out.println("Found \"" + hashtag + "\" hashtags count: " + count);
    }
}