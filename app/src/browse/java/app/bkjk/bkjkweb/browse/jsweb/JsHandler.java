package app.bkjk.bkjkweb.browse.jsweb;


import app.bkjk.bkjkweb.browse.CallBackFunction;

/**
 * ================================================
 * 作    者：wudi
 * 版    本：1.0.0
 * 创建日期：2018/6/5
 * 描    述：JsHandler.java
 * 修订历史：
 * ================================================
 */
public interface JsHandler {

    public void OnHandler(String handlerName, String responseData, CallBackFunction function);

}
