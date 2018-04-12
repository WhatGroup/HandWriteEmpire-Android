package com.gb.hwrlib.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by lht on 17/1/12.
 */

public class BitmapUtil {

    public static Bitmap zoomBitmap(Bitmap bm, int newWidth , int newHeight){
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        float scale = scaleWidth > scaleHeight ? scaleHeight : scaleWidth;

        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        // 得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }

    /**
     * 处理图片绘制位置，在width和height中显示居中
     * @param width
     * @param height
     * @param matrix
     */
    public static void setBitmapPosition(Bitmap bitmap, int width, int height, Matrix matrix) {
        float left = (width - bitmap.getWidth()) / 2;
        float top = (height - bitmap.getHeight()) / 2;
        //缩放后
        if (bitmap.getWidth() < width && bitmap.getHeight() < height) {
            matrix.setTranslate(left, top);
        }
        else if (bitmap.getWidth() < width) {
            matrix.setTranslate(left, 0);
        }
        else if (bitmap.getHeight() < height) {
            matrix.setTranslate(0, top);
        }
    }
}
