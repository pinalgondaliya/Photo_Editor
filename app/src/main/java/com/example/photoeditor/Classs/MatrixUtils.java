package com.example.photoeditor.Classs;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import com.example.photoeditor.ModelClass.PolishArea;

import java.util.Arrays;

/* loaded from: classes3.dex */
public class MatrixUtils {
    private static final float[] sMatrixValues = new float[9];
    private static final Matrix sTempMatrix = new Matrix();

    private MatrixUtils() {
    }

    public static float getMatrixScale(Matrix matrix) {
        return (float) Math.sqrt(Math.pow(getMatrixValue(matrix, 0), 2.0d) + Math.pow(getMatrixValue(matrix, 3), 2.0d));
    }

    public static float getMatrixAngle(Matrix matrix) {
        return (float) (-(Math.atan2(getMatrixValue(matrix, 1), getMatrixValue(matrix, 0)) * 57.29577951308232d));
    }

    public static float getMatrixValue(Matrix matrix, int i) {
        float[] fArr = sMatrixValues;
        matrix.getValues(fArr);
        return fArr[i];
    }

    public static float getMinMatrixScale(PolishGrid polishGrid) {
        if (polishGrid == null) {
            return 1.0f;
        }
        Matrix matrix = sTempMatrix;
        matrix.reset();
        matrix.setRotate(-polishGrid.getMatrixAngle());
        float[] cornersFromRect = getCornersFromRect(polishGrid.getArea().getAreaRect());
        matrix.mapPoints(cornersFromRect);
        RectF trapToRect = trapToRect(cornersFromRect);
        return Math.max(trapToRect.width() / polishGrid.getWidth(), trapToRect.height() / polishGrid.getHeight());
    }

    public static boolean judgeIsImageContainsBorder(PolishGrid polishGrid, float f) {
        Matrix matrix = sTempMatrix;
        matrix.reset();
        matrix.setRotate(-f);
        float[] fArr = new float[8];
        float[] fArr2 = new float[8];
        matrix.mapPoints(fArr, polishGrid.getCurrentDrawablePoints());
        matrix.mapPoints(fArr2, getCornersFromRect(polishGrid.getArea().getAreaRect()));
        return trapToRect(fArr).contains(trapToRect(fArr2));
    }

    public static float[] calculateImageIndents(PolishGrid polishGrid) {
        if (polishGrid == null) {
            return new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
        }
        Matrix matrix = sTempMatrix;
        matrix.reset();
        matrix.setRotate(-polishGrid.getMatrixAngle());
        float[] currentDrawablePoints = polishGrid.getCurrentDrawablePoints();
        float[] copyOf = Arrays.copyOf(currentDrawablePoints, currentDrawablePoints.length);
        float[] cornersFromRect = getCornersFromRect(polishGrid.getArea().getAreaRect());
        matrix.mapPoints(copyOf);
        matrix.mapPoints(cornersFromRect);
        RectF trapToRect = trapToRect(copyOf);
        RectF trapToRect2 = trapToRect(cornersFromRect);
        float f = trapToRect.left - trapToRect2.left;
        float f2 = trapToRect.top - trapToRect2.top;
        float f3 = trapToRect.right - trapToRect2.right;
        float f4 = trapToRect.bottom - trapToRect2.bottom;
        float[] fArr = new float[4];
        if (f <= 0.0f) {
            f = 0.0f;
        }
        fArr[0] = f;
        if (f2 <= 0.0f) {
            f2 = 0.0f;
        }
        fArr[1] = f2;
        if (f3 >= 0.0f) {
            f3 = 0.0f;
        }
        fArr[2] = f3;
        if (f4 >= 0.0f) {
            f4 = 0.0f;
        }
        fArr[3] = f4;
        matrix.reset();
        matrix.setRotate(polishGrid.getMatrixAngle());
        matrix.mapPoints(fArr);
        return fArr;
    }

    public static RectF trapToRect(float[] fArr) {
        RectF rectF = new RectF(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
        for (int i = 1; i < fArr.length; i += 2) {
            float round = Math.round(fArr[i - 1] * 10.0f) / 10.0f;
            float round2 = Math.round(fArr[i] * 10.0f) / 10.0f;
            rectF.left = round < rectF.left ? round : rectF.left;
            rectF.top = round2 < rectF.top ? round2 : rectF.top;
            if (round <= rectF.right) {
                round = rectF.right;
            }
            rectF.right = round;
            if (round2 <= rectF.bottom) {
                round2 = rectF.bottom;
            }
            rectF.bottom = round2;
        }
        rectF.sort();
        return rectF;
    }

    public static float[] getCornersFromRect(RectF rectF) {
        return new float[]{rectF.left, rectF.top, rectF.right, rectF.top, rectF.right, rectF.bottom, rectF.left, rectF.bottom};
    }

    public static Matrix generateMatrix(PolishGrid polishGrid, float f) {
        return generateMatrix(polishGrid.getArea(), polishGrid.getDrawable(), f);
    }

    public static Matrix generateMatrix(PolishArea polishArea, Drawable drawable, float f) {
        return generateCenterCropMatrix(polishArea, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), f);
    }

    private static Matrix generateCenterCropMatrix(PolishArea polishArea, int i, int i2, float f) {
        float f2;
        RectF areaRect = polishArea.getAreaRect();
        Matrix matrix = new Matrix();
        matrix.postTranslate(areaRect.centerX() - (i / 2), areaRect.centerY() - (i2 / 2));
        float f3 = i;
        float f4 = i2;
        if (areaRect.height() * f3 > areaRect.width() * f4) {
            f2 = (areaRect.height() + f) / f4;
        } else {
            float f22 = areaRect.width();
            f2 = (f22 + f) / f3;
        }
        matrix.postScale(f2, f2, areaRect.centerX(), areaRect.centerY());
        return matrix;
    }
}
