package app.bkjk.bkjkweb.browse;

/**
 * ================================================
 * 作    者：wudi
 * 版    本：1.0.0
 * 创建日期：2018/6/5
 * 描    述：DefaultHandler.java
 * 修订历史：
 * ================================================
 */
public class DefaultHandler implements BridgeHandler {
    String TAG = "DefaultHandler";
    @Override
    public void handler(String data, CallBackFunction function) {
        if (function != null) {
            function.onCallBack("DefaultHandler response data");
        }
    }

}
