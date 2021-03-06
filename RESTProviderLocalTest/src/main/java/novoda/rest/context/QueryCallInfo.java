
package novoda.rest.context;


import novoda.rest.net.ETag;

import android.os.Parcel;
import android.os.Parcelable;

public class QueryCallInfo extends CallInfo implements Parcelable {

    public String[] projection;

    public String selection;

    public String[] selectionArg;

    public String sortOrder;

    public ETag etag;

    private QueryCallInfo(Parcel parcel) {
        super(parcel);
        parcel.readStringArray(projection);
        selection = parcel.readString();
        parcel.readStringArray(selectionArg);
        sortOrder = parcel.readString();
    };

    public QueryCallInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int parcelableFlags) {
        super.writeToParcel(parcel, parcelableFlags);
        parcel.writeStringArray(projection);
        parcel.writeString(selection);
        parcel.writeStringArray(selectionArg);
        parcel.writeString(sortOrder);
    }

    public static final Parcelable.Creator<QueryCallInfo> CREATOR = new Parcelable.Creator<QueryCallInfo>() {
        @Override
        public QueryCallInfo createFromParcel(Parcel parcel) {
            return new QueryCallInfo(parcel);
        }
        
        @Override
        public QueryCallInfo[] newArray(int size) {
            return new QueryCallInfo[size];
        }
    };

}
