package brianlu.a2a1b;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import static android.widget.ListPopupWindow.WRAP_CONTENT;

public class RankActivity extends AppCompatActivity {
    private static final String space="%-20S";
    LinearLayout linearLayout;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        initializeLinearLayout();

        Intent intent=getIntent();
        String digits=intent.getStringExtra("digits");

        final RankDocument rankDocument=new RankDocument(this,"RANK",digits);
        rankDocument.readRank();
        TreeMap<String,Long> rankSorted=rankDocument.getRankSorted();
        int i=1;
        int fontSize=29;
        for (final Map.Entry<String, Long> entry : rankSorted.entrySet()){
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            layoutParams.setMargins(10,10,10,10);
            TextViewExtends textView=new  TextViewExtends(this,new DialogOnClickListener(rankDocument,entry.getKey(),entry.getValue().toString()));
            textView.setOnClickListener(textView.getDialogOnClickListener()) ;
            textView.setBackgroundResource(R.color.textviewColor);
            textView.setTextColor(getResources().getColor(R.color.textviewTextColor));
            textView.setTextSize(fontSize);
            textView.setClickable(true);
            String value=String.format(space,"No."+i+" "+getString(R.string.grade)+" : "+entry.getValue().toString());
            textView.setText(value+" "+entry.getKey());

            //add aimate
            Animation am = AnimationUtils.loadAnimation(RankActivity.this,R.anim.side_down_in);
            textView.startAnimation(am);

            linearLayout.addView(textView,layoutParams);
            i++;
            fontSize--;
        }
    }

    class DialogOnClickListener implements View.OnClickListener{
        RankDocument rankDocument;
        String key;
        String value;

        DialogOnClickListener(RankDocument rankDocument, String key, String value){
            this.rankDocument=rankDocument;
            this.key=key;
            this.value=value;
        }

        @Override
        public void onClick(View v) {
            //dialog
            ShowRecordDialog builder = new ShowRecordDialog(RankActivity.this);
            //key wrong

            String recordKey=key+value;
            String record=rankDocument.sharedPreferences.getString(recordKey,"");
            // 2. Chain together various setter methods to set the dialog characteristics

            builder.setTitle(((TextView)v).getText().toString());
            builder.setMessage(record.trim().split("\n"));

            android.app.AlertDialog dialog=builder.create();
            Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations=R.style.DialogAnimationGrow;
            dialog.show();


        }
    }
    public void initializeLinearLayout(){
        linearLayout= findViewById(R.id.Rank_LinearLayout);
        //通过加载XML动画设置文件来创建一个Animation对象,子View进入的动画
        Animation animation=AnimationUtils.loadAnimation(this, R.anim.side_down_in);
        //得到一个LayoutAnimationController对象；
        LayoutAnimationController lac=new LayoutAnimationController(animation);
        //设置控件显示的顺序；
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        //设置控件显示间隔时间；
        lac.setDelay(0.5f);
        //为ListView设置LayoutAnimationController属性；
        linearLayout.setLayoutAnimation(lac);

    }
    class TextViewExtends extends androidx.appcompat.widget.AppCompatTextView{
        private DialogOnClickListener dialogOnClickListener;

        public TextViewExtends(Context context) {
            super(context);
        }
        public TextViewExtends(Context context,DialogOnClickListener dialogOnClickListener) {
            super(context);
            this.dialogOnClickListener=dialogOnClickListener;
        }

        public DialogOnClickListener getDialogOnClickListener() {
            return dialogOnClickListener;
        }
    }

}
