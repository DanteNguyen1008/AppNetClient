package production.bcf.com.appnetclient.models;

/**
 * Created by annguyenquocduy on 1/18/15.
 */
public class Feed {
    public String avatarUrl;
    public String username;
    public String content;
    public String contentHTML;

    public Feed(String avatarUrl, String username, String content, String contentHTML) {
        this.avatarUrl = avatarUrl;
        this.username = username;
        this.content = content;
        this.contentHTML = contentHTML;
    }
}
