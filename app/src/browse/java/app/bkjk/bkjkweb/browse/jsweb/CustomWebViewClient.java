package app.bkjk.bkjkweb.browse.jsweb;


import android.support.annotation.NonNull;
import android.webkit.WebView;

import app.bkjk.bkjkweb.browse.BridgeWebView;
import app.bkjk.bkjkweb.browse.BridgeWebViewClient;

import java.util.Map;

/**
 * ================================================
 * 作    者：wudi
 * 版    本：1.0.0
 * 创建日期：2018/6/5
 * 描    述：CustomWebViewClient.java
 * 修订历史：
 * ================================================
 */
public abstract class CustomWebViewClient extends BridgeWebViewClient {

    public CustomWebViewClient(BridgeWebView webView) {
        super(webView);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        if (onPageHeaders(url) != null) {
            view.loadUrl(url, onPageHeaders(url));
        }
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode,
                                String description, String failingUrl) {
        view.loadUrl(onPageError(failingUrl));
    }

    /**
     * return errorUrl
     * @param url
     * @return
     */
    public abstract String onPageError(String url);

    /**
     * HttpHeaders
     * return
     * @return
     */
    @NonNull
    public abstract Map<String, String> onPageHeaders(String url);
    
}
