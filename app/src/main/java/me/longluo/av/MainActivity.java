package me.longluo.av;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import me.longluo.ffmpeg.FFmpegUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.tv_ffmpeg_info);
        tv.setText(FFmpegUtils.getInstance().ffmpegInfo());
    }

}