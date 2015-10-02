package info.duhovniy.maxim.note;

/**
 * Created by maxduhovniy on 9/5/15.
 */
public class Note {

    private String mHeader;
    private String mBody;
    private String mEmail;

    public Note() {

    }

    public Note(String header, String body, String email) {
        this.mHeader = header;
        this.mBody = body;
        this.mEmail = email;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String email) {
        this.mEmail = email;
    }

    public String getHeader() {
        return mHeader;
    }

    public void setHeader(String header) {
        this.mHeader = header;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String body) {
        this.mBody = body;
    }

}
