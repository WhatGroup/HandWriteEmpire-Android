package com.gb.hwrlib.pojo;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lht on 16/10/17.
 */

public class DrawPoint extends DrawShape {

    private float x, y;
    private float[] matrixValues = new float[9];

    public DrawPoint(float x, float y, SerializablePaint paint) {
        this.x = x;
        this.y = y;
        this.paint = paint;
    }

    private DrawPoint(Parcel in) {
        paint = (SerializablePaint)in.readSerializable();
        x = in.readFloat();
        y = in.readFloat();
    }

    @Override
    public void draw(Canvas canvas, Matrix matrix) {
        //缩放坐标映射
        matrix.getValues(matrixValues);
        x = x * matrixValues[0] + y * matrixValues[1] + matrixValues[2];
        y = x * matrixValues[3] + y * matrixValues[4] + matrixValues[5];

        canvas.drawPoint(x, y, paint.setStrokeWidth());
    }

    @Override
    public DrawShape clone(float scale) {
        SerializablePaint clonePaint = new SerializablePaint(paint);
        clonePaint.setScale(scale);

        return new DrawPoint(x, y, clonePaint);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(paint);
        dest.writeFloat(x);
        dest.writeFloat(y);
    }

    // Parcelable CREATOR class
    public static final Parcelable.Creator<DrawPoint> CREATOR = new Parcelable.Creator<DrawPoint>() {
        @Override
        public DrawPoint createFromParcel(Parcel in) {
            return new DrawPoint(in);
        }

        @Override
        public DrawPoint[] newArray(int size) {
            return new DrawPoint[size];
        }
    };
}
