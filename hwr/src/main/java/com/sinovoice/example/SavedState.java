package com.sinovoice.example;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import com.sinovoice.example.pojo.DrawShape;
import java.util.ArrayList;

/**
 * Created by lht on 17/1/5.
 */

public class SavedState extends View.BaseSavedState {

    private ArrayList<DrawShape> mDrawShapes;
    private int mLastDimensionW;
    private int mLastDimensionH;

    public SavedState(Parcelable superState, ArrayList<DrawShape> drawShapes,
                      int lastDimensionW, int lastDimensionH) {
        super(superState);

        mDrawShapes = drawShapes;

        mLastDimensionW = lastDimensionW;
        mLastDimensionH = lastDimensionH;
    }

    private SavedState(Parcel in) {
        super(in);

        try {
            mDrawShapes = in.readArrayList(DrawShape.class.getClassLoader());

            mLastDimensionW = in.readInt();
            mLastDimensionH = in.readInt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);

        out.writeTypedList(mDrawShapes);

        out.writeInt(mLastDimensionW);
        out.writeInt(mLastDimensionH);
    }

    // Parcelable CREATOR class, needed for parcelable to work
    public static final Creator<SavedState> CREATOR =
            new Creator<SavedState>() {
                public SavedState createFromParcel(Parcel in) {
                    return new SavedState(in);
                }

                public SavedState[] newArray(int size) {
                    return new SavedState[size];
                }
            };

    public ArrayList<DrawShape> getDrawShapes() {
        return mDrawShapes;
    }

    int getLastDimensionW() {
        return mLastDimensionW;
    }

    int getLastDimensionH() {
        return mLastDimensionH;
    }
}
