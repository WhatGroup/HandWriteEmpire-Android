package com.gb.hwrlib.pojo;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lht on 16/10/17.
 */

public class DrawPath extends DrawShape {

    private SerializablePath path;

    public DrawPath(SerializablePath path, SerializablePaint paint) {
        this.path = path;
        this.paint = paint;
    }

    private DrawPath(Parcel in) {
        paint = (SerializablePaint)in.readSerializable();
        path = (SerializablePath)in.readSerializable();
    }

    @Override
    public void draw(Canvas canvas, Matrix m) {
        //缩放坐标映射
        path.transform(m);

        canvas.drawPath(path, paint.setStrokeWidth());
    }

    @Override
    public DrawShape clone(float scale) {
        SerializablePaint clonePaint = new SerializablePaint(paint);
        clonePaint.setScale(scale);

        return new DrawPath(new SerializablePath(path), clonePaint);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(paint);
        dest.writeSerializable(path);
    }

    // Parcelable CREATOR class
    public static final Parcelable.Creator<DrawPath> CREATOR = new Parcelable.Creator<DrawPath>() {
        @Override
        public DrawPath createFromParcel(Parcel in) {
            return new DrawPath(in);
        }

        @Override
        public DrawPath[] newArray(int size) {
            return new DrawPath[size];
        }
    };
}
