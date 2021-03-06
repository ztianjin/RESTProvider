
package novoda.rest.context;

import novoda.rest.services.RESTCallService;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Information you can retrieve for a specific HTTP call
 */
public class CallInfo implements Parcelable {

    public static final int LOW = 0;

    public static final int NORMAL = 1;

    public static final int HIGH = 2;

    public static final int IMMEDIATE = 3;

    /**
     * Priority of the request
     */
    public int priority = NORMAL;

    /**
     * The query has been complete and inserted into database correctly
     */
    public static final int COMPLETE = 0;

    /**
     * The query has finished but is in failure. This could be related to
     * network, parsing, SQL failure.
     */
    public static final int FAILURE = 1;

    /**
     * The query is in progress
     */
    public static final int IN_PROGRESS = 2;

    /**
     * The query is queued to be executed - usually waiting for an another call.
     */
    public static final int QUEUED = 3;

    /**
     * The current status of the query
     * 
     * @see CallInfo#QUEUED
     * @see CallInfo#COMPLETE
     * @see CallInfo#FAILURE
     * @see CallInfo#IN_PROGRESS
     */
    public int status;

    /**
     * The query is being downloaded
     */
    public static final int DOWNLOADING = 4;

    /**
     * The query is being parsed
     */
    public static final int PARSING = 5;

    /**
     * The query is being inserted into DB
     */
    public static final int INSERTING = 6;

    /**
     * The current status of the query
     * 
     * @see CallInfo#DOWNLOADING
     * @see CallInfo#PARSING
     * @see CallInfo#INSERTING
     */
    public int state;

    /**
     * The creation date of the query. The moment it was started by the system,
     * not the actual HTTP call
     */
    public long createdAt;

    /**
     * The timestamp to when the query was finished
     */
    public long finishedAt;

    /**
     * The content length of the request.
     */
    public int contentLenght;

    /**
     * 
     */
    public Uri originatingUri;

    /**
     * Local type of the request received by the content provider. This can be
     * either query, update, delete, insert but will usually be query.
     * 
     * @see RESTCallService#ACTION_DELETE
     * @see RESTCallService#ACTION_INSERT
     * @see RESTCallService#ACTION_QUERY
     * @see RESTCallService#ACTION_UPDATE
     */
    public String action;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int parcelableFlags) {
        parcel.writeInt(status);
        parcel.writeInt(state);
        parcel.writeLong(createdAt);
        parcel.writeLong(finishedAt);
        parcel.writeInt(contentLenght);
        parcel.writeParcelable(originatingUri, parcelableFlags);
        parcel.writeString(action);
    }

    public static final Parcelable.Creator<CallInfo> CREATOR = new Parcelable.Creator<CallInfo>() {
        @Override
        public CallInfo createFromParcel(Parcel parcel) {
            return new CallInfo(parcel);
        }

        @Override
        public CallInfo[] newArray(int size) {
            return new CallInfo[size];
        }
    };

    protected CallInfo(Parcel source) {
        status = source.readInt();
        state = source.readInt();
        createdAt = source.readLong();
        finishedAt = source.readLong();
        contentLenght = source.readInt();
        originatingUri = source.readParcelable(null);
        action = source.readString();
    }

    public CallInfo() {
    }
}
