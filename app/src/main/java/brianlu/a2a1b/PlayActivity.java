package brianlu.a2a1b;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import static android.widget.ListPopupWindow.WRAP_CONTENT;

public class PlayActivity extends AppCompatActivity {
    private static  int fontSize=15;
    private static String getTextSpace="%-10S";
    private static String getABSpace="%-5S";
    BullsAndCows bullsAndCows;
    Button backButton, howToPlayButton,answerButton,inputButton;
    LinearLayout linearLayout;
    EditText editText;
    String answer;
    String digits;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);


        initializeUIComponent();
        Intent intent=getIntent();
        digits= intent.getStringExtra("digits");



        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    editTextCheckAnswer();
                    handled = true;
                }
                return handled;
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                displayInterstitial();
                Toast.makeText(PlayActivity.this,getString(R.string.back)+"！！",Toast.LENGTH_SHORT).show();
                onBackPressed();
               // finish();
            }
        });

        howToPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(PlayActivity.this);
                alertdialog.setTitle(getString(R.string.play));
                alertdialog.setMessage(getString(R.string.play1) +
                        "\n" +
                        getString(R.string.play2));
                alertdialog.setPositiveButton(getString(R.string.confirm),null);
                AlertDialog dialog=alertdialog.create();
                dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimationSideRightLeft;
                dialog.show();
            }
        });

        answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(PlayActivity.this);
                alertdialog.setTitle(getString(R.string.answer));
                alertdialog.setMessage(answer);
                alertdialog.setCancelable(false);
                alertdialog.setPositiveButton(getString(R.string.confirm)+">>"+getString(R.string.newgame), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startNewGame();

                    }
                });
                AlertDialog dialog=alertdialog.create();
                dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimationSideUpDown;
                dialog.show();

                answer=bullsAndCows.numberCreate();
                linearLayout.removeAllViewsInLayout();

            }
        });


        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextCheckAnswer();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        startNewGame();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(editText, 0);
        }

    }

    private  void initializeUIComponent(){
        backButton= findViewById(R.id.BackButton);
        howToPlayButton = findViewById(R.id.HowToPlayButton);
        answerButton= findViewById(R.id.Answer);
        inputButton= findViewById(R.id.InputButton);
        linearLayout= findViewById(R.id.linearLayoutShowAB);
        editText= findViewById(R.id.EditTxt);
    }

    private void startNewGame(){
        linearLayout.removeAllViewsInLayout();
        bullsAndCows=new BullsAndCows(digits,getResources().getConfiguration().locale);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,WRAP_CONTENT);
        layoutParams.setMargins(5,5,5,5);
        TextView textView=new TextView(this);
        textView.setBackgroundResource(R.color.textviewColor);
        textView.setTextColor(getResources().getColor(R.color.textviewTextColor));
        textView.setTextSize(fontSize);
        String value=String.format("%-15S","---->>\t\t"+getString(R.string.Gaming_establishment)+"\t<<----")+bullsAndCows.getStartDate();
        textView.setText(value);
        textView.setGravity(Gravity.CENTER);
        textView.setAnimation(AnimationUtils.loadAnimation(this,R.anim.side_right_in_play_mode));
        linearLayout.addView(textView,layoutParams);

        Animation animation=AnimationUtils.loadAnimation(this,R.anim.side_right_in);
        LayoutAnimationController layoutAnimationController=new LayoutAnimationController(animation);
        linearLayout.setLayoutAnimation(layoutAnimationController);

        answer=bullsAndCows.numberCreate();
        Toast.makeText(PlayActivity.this,getString(R.string.The_new_game_has_been_established),Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void editTextCheckAnswer(){
        try {
            Boolean boolean1 = true;
            String getTxt=String.valueOf(editText.getText()).trim();
            final char [] cAry = getTxt.toCharArray();

            //判斷輸入的數值是否有重複

            for(int i = 0; i < cAry.length; i++) {
                for (int j = i + 1; j < cAry.length; j++) {
                    if (cAry[i] == cAry[j]) {

                        boolean1 = false;
                    }
                }
            }

            if(!(cAry.length == answer.length())){
                boolean1 = false;
            }

            if(boolean1) {
                String getTextFormat=String.format(PlayActivity.getTextSpace,getTxt);
                String ab=String.format(PlayActivity.getABSpace, bullsAndCows.checkAB(answer,getTxt));
               // textView.setText(getTextFormat+"  "+ab+"  "+bullsAndCows.gap(new Date())+ "\n" + textView.getText());


                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,WRAP_CONTENT);
                layoutParams.setMargins(5,5,5,5);
                TextView textView=new TextView(this);
                textView.setBackgroundResource(R.color.textviewColor);
                textView.setTextColor(getResources().getColor(R.color.textviewTextColor));
                textView.setTextSize(fontSize);
                textView.setClickable(true);
                textView.setGravity(Gravity.CENTER);
                String value=getTextFormat+"  "+ab+"  "+bullsAndCows.gap(new Date());
                textView.setText(value);
                Animation animation= AnimationUtils.loadAnimation(this,R.anim.side_right_in_play_mode);
                textView.setAnimation(animation);
                linearLayout.addView(textView,0,layoutParams);




            }else {
                //數值重複
                if(cAry.length == answer.length()) {
                    //textView.setText(getTxt + "-->>"+getString(R.string.Duplicate_values)+"<<--"+ "\n" + textView.getText());
                    final LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,WRAP_CONTENT);
                    layoutParams.setMargins(5,5,5,5);
                    TextView textView=new TextView(this);
                    textView.setBackgroundResource(R.color.textviewColor);
                    textView.setTextColor(getResources().getColor(R.color.textviewTextColor));
                    textView.setTextSize(fontSize);
                    textView.setClickable(true);
                    textView.setGravity(Gravity.CENTER);
                    String value=getTxt + "-->>"+getString(R.string.Duplicate_values)+"<<--";
                    Animation animation= AnimationUtils.loadAnimation(this,R.anim.side_right_in_play_mode);
                    textView.setAnimation(animation);

                    textView.setOnTouchListener(new View.OnTouchListener() {

                        static final int MIN_DISTANCE = 100;
                        private float downX, upX;


                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN: {
                                    downX = event.getX();
                                    return true;
                                }
                                case MotionEvent.ACTION_UP: {
                                    upX = event.getX();

                                    float deltaX = downX - upX;

                                    if (Math.abs(deltaX) > MIN_DISTANCE) {
                                        if (deltaX < 0) {
                                            linearLayout.removeView(v);
                                            return true;
                                        }
                                        if (deltaX > 0) {
                                            linearLayout.removeView(v);
                                            return true;
                                        }
                                    } else {
                                       // Log.i(logTag, "Swipe was only " + Math.abs(deltaX) + " long horizontally, need at least " + MIN_DISTANCE);
                                        // return false; // We don't consume the event
                                    }

                                    return false; // no swipe horizontally and no swipe vertically
                                }// case MotionEvent.ACTION_UP:
                            }
                            return false;


                        }
                    });
                    textView.setText(value);
                    linearLayout.addView(textView,0,layoutParams);
                }else {
                    //數值不為幾位數
                    //textView.setText(getTxt + "-->>"+getString(R.string.Value_is_not)+answer.length()+getString(R.string.s)+"<<--"+ "\n" + textView.getText());

                    LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,WRAP_CONTENT);
                    layoutParams.setMargins(5,5,5,5);
                    TextView textView=new TextView(this);
                    textView.setBackgroundResource(R.color.textviewColor);
                    textView.setTextColor(getResources().getColor(R.color.textviewTextColor));
                    textView.setTextSize(fontSize);
                    textView.setClickable(true);
                    textView.setGravity(Gravity.CENTER);
                    String value=getTxt + "-->>"+getString(R.string.Value_is_not)+answer.length()+getString(R.string.s)+"<<--";
                    textView.setText(value);
                    textView.setOnTouchListener(new View.OnTouchListener() {

                        static final int MIN_DISTANCE = 100;
                        private float downX, upX;


                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN: {
                                    downX = event.getX();
                                    return true;
                                }
                                case MotionEvent.ACTION_UP: {
                                    upX = event.getX();

                                    float deltaX = downX - upX;

                                    if (Math.abs(deltaX) > MIN_DISTANCE) {
                                        if (deltaX < 0) {
                                            linearLayout.removeView(v);
                                            return true;
                                        }
                                        if (deltaX > 0) {
                                            linearLayout.removeView(v);
                                            return true;
                                        }
                                    } else {
                                        // Log.i(logTag, "Swipe was only " + Math.abs(deltaX) + " long horizontally, need at least " + MIN_DISTANCE);
                                        // return false; // We don't consume the event
                                    }

                                    return false; // no swipe horizontally and no swipe vertically
                                }// case MotionEvent.ACTION_UP:
                            }
                            return false;


                        }
                    });
                    Animation animation= AnimationUtils.loadAnimation(this,R.anim.side_right_in_play_mode);
                    textView.setAnimation(animation);

                    linearLayout.addView(textView,0,layoutParams);
                }
            }

            if(getTxt.equals(answer)){
                //bullsAndCows.checkAB(answer,answer);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(PlayActivity.this);
                alertDialog.setTitle(getString(R.string.Congratulation)+"！！");
                String count=getString(R.string.total)+" "+bullsAndCows.getCount()+" "+getString(R.string.times)+", ";
                String time = getString(R.string.time)+" : "+bullsAndCows.gap(new Date());
                String grade=getString(R.string.grade)+" : "+bullsAndCows.getGrade();

                alertDialog.setCancelable(false);

                RankDocument rankDocument=new RankDocument(this,"RANK",digits);
                rankDocument.readRank();
                final StringBuffer stringBuffer=new StringBuffer();
                for (int i=0;i<linearLayout.getChildCount();i++){
                    stringBuffer.append(((TextView)linearLayout.getChildAt(i)).getText()+"\n");
                }
                int inRank=rankDocument.writeRank(new Date(),bullsAndCows.getGrade(),stringBuffer.toString());

                if (inRank==0){
                    alertDialog.setMessage(getString(R.string.you_win)+","+getString(R.string.answer_is)+" "+answer+"\n"+count+time+"\n"+grade);
                }else{

                    alertDialog.setMessage(getString(R.string.you_win)+","+getString(R.string.answer_is)+" "+answer+"\n"+count+time+"\n"+grade+"\n"+"No."+inRank);
                }


                alertDialog.setPositiveButton(getString(R.string.confirm)+">>"+getString(R.string.newgame), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startNewGame();
                    }
                });
                alertDialog.setNeutralButton(getString(R.string.rank)+"<<"+getString(R.string.confirm),new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(PlayActivity.this,RankActivity.class);
                        intent.putExtra("digits",digits);
                        startActivity(intent);
                        finish();
                    }
                });

                alertDialog.setNegativeButton(getString(R.string.record), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ShowRecordDialog showRecordDialog=new ShowRecordDialog(PlayActivity.this);
                        showRecordDialog.setMessage(stringBuffer.toString().split("\n"));
                        showRecordDialog.setCancelable(false);
                        showRecordDialog.setPositiveButton(getString(R.string.confirm)+">>"+getString(R.string.newgame), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startNewGame();
                            }
                        });
                        showRecordDialog.setNeutralButton(getString(R.string.rank)+"<<"+getString(R.string.confirm),new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent=new Intent(PlayActivity.this,RankActivity.class);
                                intent.putExtra("digits",digits);
                                startActivity(intent);
                                finish();
                            }
                        });
                        linearLayout.removeAllViewsInLayout();
                        showRecordDialog.show();


                    }
                });

                alertDialog.show();


            }
            editText.setText(null);
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
            Toast.makeText(PlayActivity.this,getString(R.string.Please_enter_the_number),Toast.LENGTH_SHORT).show();
        }catch (NullPointerException e){
            e.printStackTrace();
            Toast.makeText(PlayActivity.this,getString(R.string.Press_Newgame_Button),Toast.LENGTH_SHORT).show();
        }



    }


    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(PlayActivity.this,getString(R.string.back)+"！！",Toast.LENGTH_SHORT).show();

        finish();
    }




}
