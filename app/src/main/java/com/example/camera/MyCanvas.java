package com.example.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.HashMap;

public class MyCanvas extends View {
    HashMap <Integer, Path> activePaths;
    Paint pathPaint;
    private int currentColor;
    MediaPlayer mediaPlayer;
    Bitmap vt, hokieBird;
    private ArrayList<Icons> iconList = new ArrayList<>();
    private ArrayList<Path> pathList =new ArrayList<>();
    private ArrayList<Paint> paintList =new ArrayList<>();
    private ArrayList<Boolean> removeObject = new ArrayList<>();

    public MyCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        activePaths = new HashMap<>();
        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setColor(Color.RED);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(70);
        currentColor = Color.RED;
        vt = BitmapFactory.decodeResource(context.getResources(), R.drawable.vtlogo);
        hokieBird = BitmapFactory.decodeResource(context.getResources(), R.drawable.hokiebird3);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        mediaPlayer = new MediaPlayer();
        for(Path path: activePaths.values()){
            canvas.drawPath(path, pathPaint);
        }
        for(int i =0; i<iconList.size(); i++){
            Icons temp = iconList.get(i);
            canvas.drawBitmap(temp.getImg(), temp.getX(),temp.getY(), null);
        }
        for(int i=0;i<paintList.size();i++){
            canvas.drawPath(pathList.get(i),paintList.get(i));
        }
        super.onDraw(canvas);
    }

    public void addPath(int id, float x, float y) {
        Path path = new Path();
        path.moveTo(x, y);
        activePaths.put(id, path);
        invalidate();
    }

    public void updatePath(int id, float x, float y) {
        Path path = activePaths.get(id);
        if(path != null){
            path.lineTo(x, y);
        }
        invalidate();
    }

    public void removePath(int id) {
        if(activePaths.containsKey(id)){
            Path temp = activePaths.get(id);
            Paint paint = getNewPaintPen(currentColor);
            paintList.add(paint);
            pathList.add(temp);
            removeObject.add(true);
            activePaths.remove(id);
        }
        invalidate();
    }

    public void setDrawColor(int color) {
        currentColor = color;
        pathPaint.setColor(color);
    }

    public void undo() {
        if(removeObject.size()>0){
            if(removeObject.get(removeObject.size()-1)){
                paintList.remove(paintList.size() - 1);
                pathList.remove(pathList.size() - 1);
            }
            else {
                iconList.remove(iconList.size() -1);

            }
            removeObject.remove(removeObject.size()-1);
        }
        invalidate();
    }

    public void clear() {
        pathList.clear();
        paintList.clear();
        iconList.clear();
        removeObject.clear();
        invalidate();
    }

    public void doubleTap(float x, float y) {
        Icons temp = new Icons(vt, x, y);
        iconList.add(temp);
        removeObject.add(false);
    }
    public void longPress(float x, float y) {
        Icons temp = new Icons(hokieBird, x, y);
        iconList.add(temp);
        removeObject.add(false);
    }

    private Paint getNewPaintPen(int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(70);
        return paint;
    }
}