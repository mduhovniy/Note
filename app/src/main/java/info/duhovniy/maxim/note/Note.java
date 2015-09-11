package info.duhovniy.maxim.note;

import java.io.Serializable;

/**
 * Created by maxduhovniy on 9/5/15.
 */
public class Note implements Serializable {

    private String mHeader;
    private String mBody;
    private String mEmail;

    public Note() {

    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getHeader() {
        return mHeader;
    }

    public void setHeader(String mHeader) {
        this.mHeader = mHeader;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String mBody) {
        this.mBody = mBody;
    }

    @Override
    public String toString() {
        if(mBody.length() > 30) {
            return mHeader + "\n" + mBody.substring(0, 30) + " ...";
        } else {
            return mHeader + "\n" + mBody;
        }
    }
}
