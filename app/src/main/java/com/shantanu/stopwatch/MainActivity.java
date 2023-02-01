package com.shantanu.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton btn_start, btn_stop, btn_reset;
    private TextView timer_text;
    int seconds;
    boolean isRunning, wantMillis;
    private Runnable runnable;
    private AppCompatCheckBox milliseconds_cb;
    private GifImageView stop_watch_gif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seconds = 0;
        isRunning = false;
        wantMillis = false;

        btn_start = findViewById(R.id.btn_start);
        btn_stop = findViewById(R.id.btn_stop);
        btn_reset = findViewById(R.id.btn_reset);
        timer_text = findViewById(R.id.timer_text);
        milliseconds_cb = findViewById(R.id.milliseconds_cb);
        stop_watch_gif = findViewById(R.id.stop_watch_gif);

        Handler handler = new Handler();
        handler.post(runnable = new Runnable() {
            @Override
            public void run() {
                int hrs = (seconds/100)/(60*60);
                int mins = ((seconds/100)%3600)/60;
                int secs = (seconds/100)%60;
                int millis = (seconds)%100;

                if(wantMillis)
                    timer_text.setText(String.format(Locale.getDefault(),"0%d : %02d : %02d : %02d",hrs, mins, secs, millis));
                else
                    timer_text.setText(String.format(Locale.getDefault(),"0%d : %02d : %02d",hrs, mins, secs));

                if(isRunning) {
                    seconds++;
                    stop_watch_gif.setVisibility(View.VISIBLE);
                }
                else
                    stop_watch_gif.setVisibility(View.GONE);

                handler.postDelayed(runnable, 10);
            }
        });

        btn_start.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        btn_reset.setOnClickListener(this);

        milliseconds_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                wantMillis = isChecked;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start: {
                isRunning = true;
                btn_start.setEnabled(false);
                btn_start.setText("Resume");
                btn_stop.setEnabled(true);
                btn_start.setBackgroundDrawable(getDrawable(R.drawable.disable_btn_color));
                btn_stop.setBackgroundDrawable(getDrawable(R.drawable.start_stop_button_bg));
                break;
            }
            case R.id.btn_stop:{
                isRunning = false;
                btn_stop.setEnabled(false);
                btn_start.setEnabled(true);
                btn_start.setBackgroundDrawable(getDrawable(R.drawable.start_stop_button_bg));
                btn_stop.setBackgroundDrawable(getDrawable(R.drawable.disable_btn_color));
                break;
            }
            case R.id.btn_reset:{
                isRunning = false;
                btn_stop.setEnabled(false);
                btn_start.setEnabled(true);
                btn_start.setText("Start");
                btn_start.setBackgroundDrawable(getDrawable(R.drawable.start_stop_button_bg));
                btn_stop.setBackgroundDrawable(getDrawable(R.drawable.disable_btn_color));
                seconds = 0;
                break;
            }
        }
    }
}