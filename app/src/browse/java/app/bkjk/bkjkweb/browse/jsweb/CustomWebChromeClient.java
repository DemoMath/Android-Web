package app.bkjk.bkjkweb.browse.jsweb;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import app.bkjk.bkjkweb.view.NumberProgressBar;


/**
 * ================================================
 * 作    者：wudi
 * 版    本：1.0.0
 * 创建日期：2018/6/5
 * 描    述：CustomWebChromeClient.java
 * 修订历史：
 * ================================================
 */
public class CustomWebChromeClient extends WebChromeClient {

    private NumberProgressBar mProgressBar;
    private final static int DEF = 95;

    public CustomWebChromeClient(NumberProgressBar progressBar) {
        this.mProgressBar = progressBar;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (newProgress >= DEF) {
            mProgressBar.setVisibility(View.GONE);
        } else {
            if (mProgressBar.getVisibility() == View.GONE) {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            mProgressBar.setProgress(newProgress);
        }
        super.onProgressChanged(view, newProgress);
    }
}
