package idv.david.drawcanvasex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
    自訂View
 */
public class RectBar extends View {
    private Handler handler;
    private List<Integer> inputData = new ArrayList<>();
    private Paint paint = new Paint();  // 宣告畫筆 才能使用
    private Rect rect = new Rect();

    public RectBar(Context context) {
        super(context);
    }

    public RectBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        handler = new Handler();
        //取得這個元件的長與寬
        int viewHeight = this.getHeight();
        int viewWidth = this.getWidth();

        //畫水平線
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(1f); // 線條粗細
        //開啟畫圖防鋸齒功能
        paint.setAntiAlias(true);
        canvas.drawLine(0, (int) (viewHeight * 0.8), viewWidth, (int) (viewHeight * 0.8), paint); // drawLine(x,y,筆)畫線的方法

        //亂數產生資料前先清除集合內容一次
        inputData.clear();
//        for(int i = 0; i < 20; i++) {
//            //亂數產生-300～300的值
//            int randomNumber = (int)(Math.random() * 601);
//            randomNumber -= 300;
//            inputData.add(randomNumber);
//        }

        for (int i = 0; i < 20; i++) {
            //亂數產生-300～300的值
            int randomNumber = new Random().nextInt(600);
//            randomNumber -= 300;
            inputData.add(randomNumber);
        }

        //畫線上的點
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(1.5f);

        //每個點之間的間隔
        int space = viewWidth / 20;
        int barHeight = viewHeight / 2;
        for (int i = 0; i < 20; i++) {
            canvas.drawCircle((space + (i * space)), (int) (viewHeight * 0.8), 5f, paint); // drawCircle(x,y,半徑,筆)畫圈方法
        }

        //畫柱狀圖
        paint.setStrokeWidth(10); // 形狀線條粗細
        paint.setStyle(Paint.Style.FILL); // 填滿畫出形狀的面積
//        paint.setStyle(Paint.Style.STROKE); // 空心
        for (int i = 0; i < 20; i++) {
            int data = inputData.get(i);
            //取每個值出來並判斷正或負
            if (data > 0) {
                int left = i * space;
                int right = space + i * space;
//                int left = i * space;
//                int right = space + i * space;
                int top = (int) (viewHeight * 0.8) - data;
                int bottom = (int) (viewHeight * 0.8);
                rect.set(left + 6, top, right - 6, bottom);
                paint.setColor(Color.RED);
            } else {
                int left = i * space;
                int right = space + i * space;
                int top = barHeight;
                int bottom = barHeight - data;
                rect.set(left + 6, top, right - 6, bottom);
                paint.setColor(Color.GREEN);
            }
            canvas.drawRect(rect, paint);
        }
    }
    
    
    


}

