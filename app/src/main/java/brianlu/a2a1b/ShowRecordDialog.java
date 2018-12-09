package brianlu.a2a1b;

import android.animation.LayoutTransition;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Date;

import static android.widget.ListPopupWindow.WRAP_CONTENT;

/**
 * Created by eebrian123tw93 on 2017/5/25.
 */

public class ShowRecordDialog extends AlertDialog.Builder {

    Context context;
    LinearLayout linearLayout;
    ShowRecordDialog(Context context) {
        super(context);
        this.context=context;
        //ActionBar.LayoutParams layoutParams=new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout=new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        //通过加载XML动画设置文件来创建一个Animation对象,子View进入的动画
        Animation animation=AnimationUtils.loadAnimation(this.context, R.anim.side_right_in);
        //得到一个LayoutAnimationController对象；
        LayoutAnimationController lac=new LayoutAnimationController(animation);
        //设置控件显示的顺序；
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        //设置控件显示间隔时间；
        lac.setDelay(0.2f);
        //为ListView设置LayoutAnimationController属性；
        linearLayout.setLayoutAnimation(lac);

        ScrollView scrollView=new ScrollView(context);
        scrollView.addView(linearLayout);
        scrollView.setSmoothScrollingEnabled(true);
        this.setView(scrollView);
    }
    public void setMessage(String[]strings){
        for (int i=strings.length-1;i>=0;i--){
            if(!(strings[i].indexOf(context.getString(R.string.Gaming_establishment))>0)&&(strings[i].indexOf("B")>0)) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, WRAP_CONTENT);
                layoutParams.setMargins(5, 5, 5, 5);

                TextView textView = new TextView(this.context);
                textView.setBackgroundResource(i==0?R.color.textviewWinColor:R.color.textviewColor);
                textView.setTextColor(context.getResources().getColor(i==0?R.color.textviewTextWinColor:R.color.textviewTextColor));
                int fontSize = 15;
                textView.setTextSize(fontSize);
                textView.setGravity(Gravity.CENTER);
                textView.setText(strings[i]);

                //add aimate
                Animation am = AnimationUtils.loadAnimation(context,R.anim.side_right_in_play_mode);
                textView.startAnimation(am);
                linearLayout.addView(textView, layoutParams);
            }

        }

    }
}
