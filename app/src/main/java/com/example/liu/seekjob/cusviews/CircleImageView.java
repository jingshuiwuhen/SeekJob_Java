package com.example.liu.seekjob.cusviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

public class CircleImageView extends android.support.v7.widget.AppCompatImageView {
    private Paint mPaint = new Paint();
    private int mRadius;
    private BitmapShader mBitmapShader;
    private Matrix mMatrix;

    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        mRadius = size / 2;

        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = drawableToBitmap(getDrawable());
        if (bitmap == null) {
            return;
        }

        if (mBitmapShader == null)
            mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        float scale = (mRadius * 2.0f) / Math.min(bitmap.getHeight(), bitmap.getWidth());

        if (mMatrix == null)
            mMatrix = new Matrix();

        mMatrix.setScale(scale, scale);
        mBitmapShader.setLocalMatrix(mMatrix);

        mPaint.setShader(mBitmapShader);

        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }
}

