package com.gb.hwrlib.pojo;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Parcelable;

/**
 * Created by lht on 16/10/17.
 */

public abstract class DrawShape implements Parcelable {
    SerializablePaint paint;

    public abstract void draw(Canvas canvas, Matrix matrix);

    public abstract DrawShape clone(float scale);
}
