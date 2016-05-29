package idv.david.viewaniex;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private TextView tvTitle;
    private ImageView ivSoccer;
    private RadioButton rbTranslate, rbRotate, rbScale, rbAlpha, rbAnimSet;
    private Spinner spInterpolator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        ivSoccer = (ImageView) findViewById(R.id.ivSoccer);
        rbTranslate = (RadioButton) findViewById(R.id.rbTranslate);
        rbRotate = (RadioButton) findViewById(R.id.rbRotate);
        rbScale = (RadioButton) findViewById(R.id.rbScale);
        rbAlpha = (RadioButton) findViewById(R.id.rbAlpha);
        rbAnimSet = (RadioButton) findViewById(R.id.rbAnimSet);

        spInterpolator = (Spinner) findViewById(R.id.spInterpolator);
        spInterpolator.setSelection(0, true);

        String[] interpolators = {
                "linear_interpolator",
                "accelerate",
                "decelerate",
                "accelerate_decelerate",
                "anticipate",
                "overshoot",
                "anticipate_overshoot",
                "bounce"
        };

        ArrayAdapter arrayAdapter =
                new ArrayAdapter(this, android.R.layout.simple_spinner_item, interpolators);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spInterpolator.setAdapter(arrayAdapter);
        spInterpolator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String interpolator = parent.getItemAtPosition(position).toString();
                int interpolatorId = 0;
                switch (interpolator) {
                    case "linear_interpolator": // 可判斷字串
                        // 線性效果
                        interpolatorId = android.R.anim.linear_interpolator;
                        break;
                    case "accelerate":
                        // 加速
                        interpolatorId = android.R.anim.accelerate_interpolator;
                        break;
                    case "decelerate":
                        // 減速
                        interpolatorId = android.R.anim.decelerate_interpolator;
                        break;
                    case "accelerate_decelerate":
                        // 先加速後減速
                        interpolatorId = android.R.anim.accelerate_decelerate_interpolator;
                        break;
                    case "anticipate":
                        // 先退後進
                        interpolatorId = android.R.anim.anticipate_interpolator;
                        break;
                    case "overshoot":
                        // 衝過頭
                        interpolatorId = android.R.anim.overshoot_interpolator;
                        break;
                    case "anticipate_overshoot":
                        // 先退後進過頭
                        interpolatorId = android.R.anim.anticipate_overshoot_interpolator;
                        break;
                    case "bounce":
                        // 反彈
                        interpolatorId = android.R.anim.bounce_interpolator;
                        break;
                    default:
                        interpolatorId = android.R.anim.accelerate_interpolator;
                        break;
                }

                if (rbTranslate.isChecked()) {
                    TranslateAnimation translateAnimation = getTranslateAnimation();
                    translateAnimation.setInterpolator(
                            AnimationUtils.loadInterpolator(MainActivity.this, interpolatorId));
                    ivSoccer.startAnimation(translateAnimation);
                } else if (rbRotate.isChecked()) {
                    RotateAnimation rotateAnimation = getRotateAnimation();
                    rotateAnimation.setInterpolator(
                            AnimationUtils.loadInterpolator(MainActivity.this, interpolatorId));
                    ivSoccer.startAnimation(rotateAnimation);
                } else if (rbScale.isChecked()) {
                    ScaleAnimation scaleAnimation = getScaleAnimation();
                    scaleAnimation.setInterpolator(
                            AnimationUtils.loadInterpolator(MainActivity.this, interpolatorId));
                    ivSoccer.startAnimation(scaleAnimation);
                } else if (rbAlpha.isChecked()) {
                    AlphaAnimation alphaAnimation = getAlphaAnimation();
                    alphaAnimation.setInterpolator(
                            AnimationUtils.loadInterpolator(MainActivity.this, interpolatorId));
                    ivSoccer.startAnimation(alphaAnimation);
                } else if (rbAnimSet.isChecked()) {
                    AnimationSet animationSet = getAnimationSet();
                    animationSet.setInterpolator(
                            AnimationUtils.loadInterpolator(MainActivity.this, interpolatorId));
                    ivSoccer.startAnimation(animationSet);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onTranslateClick(View view) {
        ivSoccer.startAnimation(getTranslateAnimation());
        spInterpolator.setSelection(0, true);
        tvTitle.setText("Translate animation");
        tvTitle.startAnimation(getShakeAnimation());
    }

    public void onRotateClick(View view) {
        ivSoccer.startAnimation(getRotateAnimation());
        spInterpolator.setSelection(0, true);
        tvTitle.setText("Rotate animation");
        tvTitle.startAnimation(getShakeAnimation());
    }

    public void onScaleClick(View view) {
        ivSoccer.startAnimation(getScaleAnimation());
        spInterpolator.setSelection(0, true);
        tvTitle.setText("Scale animation");
        tvTitle.startAnimation(getShakeAnimation());
    }

    public void onAlphaClick(View view) {
        ivSoccer.startAnimation(getAlphaAnimation());
        spInterpolator.setSelection(0, true);
        tvTitle.setText("Alpha animation");
        tvTitle.startAnimation(getShakeAnimation());
    }

    public void onAnimSetClick(View view) {
        ivSoccer.startAnimation(getAnimationSet());
        spInterpolator.setSelection(0, true);
        tvTitle.setText("Animation set");
        tvTitle.startAnimation(getShakeAnimation());
    }

    private TranslateAnimation getTranslateAnimation() {
        View parentView = (View) ivSoccer.getParent(); // 取得父類物件
        // 球移動的距離
        float distance = parentView.getWidth() - parentView.getPaddingLeft() -
                parentView.getPaddingRight() - ivSoccer.getWidth(); // 取得可移動長度
        TranslateAnimation translateAnimation = new TranslateAnimation(0, distance, 0, 0);
        translateAnimation.setDuration(2000); // 播放兩秒
        translateAnimation.setRepeatMode(Animation.RESTART); // 從頭播放
        translateAnimation.setRepeatCount(Animation.INFINITE); // 無限循環
        return translateAnimation;
    }

    private RotateAnimation getRotateAnimation() {
        RotateAnimation rotateAnimation = new RotateAnimation(
                0, 360, // 從0度～360度
                Animation.RELATIVE_TO_SELF, 0.5f, // 從中心點動作
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotateAnimation.setDuration(300); // 時間
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        return rotateAnimation;
    }

    private ScaleAnimation getScaleAnimation() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0.1f, 2,
                0.1f, 2,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleAnimation.setDuration(2000);
        scaleAnimation.setRepeatMode(Animation.RESTART);
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        return scaleAnimation;
    }

    private AlphaAnimation getAlphaAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1); // 透明度
        alphaAnimation.setDuration(2000);
        alphaAnimation.setRepeatMode(Animation.RESTART);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        return alphaAnimation;
    }

    private AnimationSet getAnimationSet() {
        // true if all of the animations in this set should use the interpolator associated with this AnimationSet
        AnimationSet animationSet = new AnimationSet(true);
        RotateAnimation rotateAnimation = getRotateAnimation();  // 旋轉
        animationSet.addAnimation(rotateAnimation);
        // rotate一定要先於translate，否則rotate角度會錯誤
        TranslateAnimation translateAnimation = getTranslateAnimation();
        animationSet.addAnimation(translateAnimation);
        ScaleAnimation scaleAnimation = getScaleAnimation(); // 縮放
        animationSet.addAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = getAlphaAnimation();
        animationSet.addAnimation(alphaAnimation);
        return animationSet;
    }

    private TranslateAnimation getShakeAnimation() {
        TranslateAnimation shakeAnimation = new TranslateAnimation(0, 10, 0, 0);
        shakeAnimation.setDuration(1000);
        CycleInterpolator cycleInterpolator = new CycleInterpolator(7);
        shakeAnimation.setInterpolator(cycleInterpolator);
        return shakeAnimation;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
