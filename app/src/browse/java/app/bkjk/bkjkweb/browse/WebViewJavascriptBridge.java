package app.bkjk.bkjkweb.browse;

/**
 * ================================================
 * 作    者：wudi
 * 版    本：1.0.0
 * 创建日期：2018/6/5
 * 描    述：WebViewJavascriptBridge.java
 * 修订历史：
 * ================================================
 */
public interface WebViewJavascriptBridge {
    public void send(String data);
    public void send(String data, CallBackFunction responseCallback);
}
