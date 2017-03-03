package jun.netvideoview;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;

import jun.netvideoview.Jtools.CustomToast;

public class MainActivity extends AppCompatActivity {

    private MyVideoView videoview;
    private String url1="http://ht-mobile.cdn.turner.com/nba/big/teams/kings/2014/12/12/HollinsGlassmov-3462827_8382664.mp4";
    private String url2="http://ht-mobile.cdn.turner.com/nba/big/teams/kings/2014/12/12/VivekSilverIndiamov-3462702_8380205.mp4";
    private String url3="https://www.zhkjtfb.com/edu/upload/123123126/device/201702280929552315.mp4";

    //注意： 视频网址必须带http://

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        videoview = (MyVideoView) findViewById(R.id.videoview);
        MediaController mediaController = new MediaController(this);
        videoview.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoview);
//      videoview.setVideoPath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/yigexiaolaoshu.mp4");
        Uri uri = Uri.parse(url3);

        videoview.setVideoURI(uri);
        videoview.start();
        videoview.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                CustomToast.showToast(MainActivity.this,"视频有错误");
                return true;
            }
        });
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

            }
        });
    }
}
