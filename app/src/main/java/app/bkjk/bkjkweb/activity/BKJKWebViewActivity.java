package app.bkjk.bkjkweb.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.ValueCallback;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

import app.bkjk.bkjkweb.R;
import app.bkjk.bkjkweb.browse.CallBackFunction;
import app.bkjk.bkjkweb.browse.jsweb.CustomWebViewClient;
import app.bkjk.bkjkweb.browse.jsweb.JavaCallHandler;
import app.bkjk.bkjkweb.browse.jsweb.JsHandler;
import app.bkjk.bkjkweb.view.ProgressBarWebView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * ================================================
 * 作    者：wudi
 * 版    本：1.0.0
 * 创建日期：2018/6/6
 * 描    述：BKJKWebViewActivity.java
 * 修订历史：
 * ================================================
 */
public class BKJKWebViewActivity extends AppCompatActivity {

    @InjectView(R.id.web_header_iv_back)
    ImageView mWebHeaderIvBack;
    @InjectView(R.id.web_header_tv_close)
    TextView mWebHeaderTvClose;
    @InjectView(R.id.web_header_tv_title)
    TextView mWebHeaderTvTitle;
    @InjectView(R.id.web_header_iv_share)
    ImageView mWebHeaderIvShare;
    @InjectView(R.id.web_inlcude_header)
    FrameLayout mWebHeaderFlAll;
    @InjectView(R.id.web_webview)
    ProgressBarWebView mWebview;

    @OnClick({R.id.web_header_iv_back, R.id.web_header_tv_close, R.id.web_header_iv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.web_header_iv_back:
                Toast.makeText(this,"回退页面",Toast.LENGTH_SHORT).show();
                if (mWebview.getWebView().canGoBack()) {
                    mWebview.getWebView().goBack();
                } else {
                    finish();
                }
                break;
            case R.id.web_header_tv_close:
                finish();
                break;
            case R.id.web_header_iv_share:
                Toast.makeText(this,"调用分享方法",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //页面模式
    public enum PageMode {
        FULL_SCREEN, HAS_TITLE
    }

    //头部左侧模式
    public enum LeftButton {
        BACK, CLOSE, NONE
    }

    //头部右侧模式
    public enum RightButton {
        SHARE, NONE
    }

    public static final String KEY_WEB_LEFT_BUTTON = "KEY_WEB_LEFT_BUTTON";
    public static final String KEY_WEB_RIGHT_BUTTON = "KEY_WEB_RIGHT_BUTTON";
    public static final String KEY_WEB_PAGE_MODE = "KEY_WEB_PAGE_MODE";
    public static final String KEY_WEB_URL = "KEY_WEB_URL";
    public static final String KEY_WEB_TITLE = "KEY_WEB_TITLE";

    private String mUrl;
    private ArrayList<String> mHandlers = new ArrayList<>();
    ValueCallback<Uri> mUploadMessage;
    private static CallBackFunction mfunction;
    int RESULT_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity_webview);
        ButterKnife.inject(this);
        mUrl = getIntent().getStringExtra(KEY_WEB_URL);
        initPageSet();
        initWebView();
    }

    private void initPageSet() {
        LeftButton leftButtonMode = (LeftButton) getIntent().getSerializableExtra(KEY_WEB_LEFT_BUTTON);
        RightButton rightButtonMode = (RightButton) getIntent().getSerializableExtra(KEY_WEB_RIGHT_BUTTON);
        PageMode pageMode = (PageMode) getIntent().getSerializableExtra(KEY_WEB_PAGE_MODE);
        String title = getIntent().getStringExtra(KEY_WEB_TITLE);
        setLeftButtonMode(leftButtonMode == null ? LeftButton.NONE : leftButtonMode);
        setRightButtonMode(rightButtonMode == null ? RightButton.NONE : rightButtonMode);
        setPageMode(pageMode == null ? PageMode.FULL_SCREEN : pageMode);
        setTitle(title);
    }

    private void setLeftButtonMode(LeftButton leftButtonMode) {
        switch (leftButtonMode) {
            case BACK:
                mWebHeaderIvBack.setVisibility(View.VISIBLE);
                break;
            case CLOSE:
                mWebHeaderTvClose.setVisibility(View.VISIBLE);
                break;
            case NONE:
                break;
            default:
                break;
        }
    }

    private void setRightButtonMode(RightButton rightButtonMode) {
        switch (rightButtonMode) {
            case SHARE:
                mWebHeaderIvShare.setVisibility(View.VISIBLE);
                break;
            case NONE:
                break;
            default:
                break;
        }
    }

    private void setPageMode(PageMode pageMode) {
        switch (pageMode) {
            case FULL_SCREEN:
                mWebHeaderFlAll.setVisibility(View.GONE);
                break;
            case HAS_TITLE:
                mWebHeaderFlAll.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private void setTitle(String title) {
        mWebHeaderTvTitle.setText(title == null ? "" : title);
    }


    private void initWebView() {
        mWebview.setWebViewClient(new CustomWebViewClient(mWebview.getWebView()) {
            @Override
            public String onPageError(String url) {
                //指定网络加载失败时的错误页面
                return "file:///android_asset/error.html";
            }

            @Override
            public Map<String, String> onPageHeaders(String url) {
                // 可以加入header
                return null;
            }

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType, String capture) {
                this.openFileChooser(uploadMsg);
            }

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType) {
                this.openFileChooser(uploadMsg);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                pickFile();
            }
        });

        // 打开页面，也可以支持网络url
        mWebview.loadUrl("file:///android_asset/demo.html");

        mHandlers.add("login");
        mHandlers.add("callNative");
        mHandlers.add("callJs");
        mHandlers.add("open");
        //回调js的方法
        mWebview.registerHandlers(mHandlers, new JsHandler() {
            @Override
            public void OnHandler(String handlerName, String responseData, CallBackFunction function) {
                if (handlerName.equals("login")) {
                    Toast.makeText(BKJKWebViewActivity.this, responseData, Toast.LENGTH_SHORT).show();
                } else if (handlerName.equals("callNative")) {
                    Toast.makeText(BKJKWebViewActivity.this, responseData, Toast.LENGTH_SHORT).show();
                    function.onCallBack("我在上海");
                } else if (handlerName.equals("callJs")) {
                    Toast.makeText(BKJKWebViewActivity.this, responseData, Toast.LENGTH_SHORT).show();
                    // 想调用你的方法：
                    function.onCallBack("好的 这是图片地址 ：xxxxxxx");
                }
                if (handlerName.equals("open")) {
                    mfunction = function;
                    pickFile();
                }

            }
        });

        // 调用js
        mWebview.callHandler("callNative", "hello H5, 我是java", new JavaCallHandler() {
            @Override
            public void OnHandler(String handlerName, String jsResponseData) {
                Toast.makeText(BKJKWebViewActivity.this, "h5返回的数据：" + jsResponseData, Toast.LENGTH_SHORT).show();
            }
        });

        //发送消息给js
        mWebview.send("哈喽", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

                Toast.makeText(BKJKWebViewActivity.this, data, Toast.LENGTH_SHORT).show();

            }
        });
        mWebview.loadUrl(mUrl);
    }

    public void pickFile() {
        Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooserIntent.setType("image/*");
        startActivityForResult(chooserIntent, RESULT_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == RESULT_CODE) {
            if (null == mUploadMessage) {
                return;
            }
            Uri result = intent == null || resultCode != Activity.RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;

            mfunction.onCallBack(intent.getData().toString());

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebview.getWebView() != null) {
            mWebview.getWebView().destroy();
        }
    }
}
