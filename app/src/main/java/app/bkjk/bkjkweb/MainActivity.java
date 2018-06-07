package app.bkjk.bkjkweb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import app.bkjk.bkjkweb.activity.BKJKWebViewActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.demo_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BKJKWebViewActivity.class);
                intent.putExtra(BKJKWebViewActivity.KEY_WEB_LEFT_BUTTON,BKJKWebViewActivity.LeftButton.BACK);
                intent.putExtra(BKJKWebViewActivity.KEY_WEB_RIGHT_BUTTON,BKJKWebViewActivity.RightButton.SHARE);
                intent.putExtra(BKJKWebViewActivity.KEY_WEB_PAGE_MODE,BKJKWebViewActivity.PageMode.HAS_TITLE);
                intent.putExtra(BKJKWebViewActivity.KEY_WEB_TITLE,"测试");
                intent.putExtra(BKJKWebViewActivity.KEY_WEB_URL,"http://www.github.com/demomath");
                startActivity(intent);
            }
        });
    }

}
