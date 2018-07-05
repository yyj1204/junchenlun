package com.wktx.www.subjects.ui.activity.mine;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.r0adkll.slidr.Slidr;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.utils.ApiURL;
import com.wktx.www.subjects.utils.ConstantUtil;
import com.wktx.www.subjects.widget.PopupLoading;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 相关说明界面---关于我们
 */
public class WebExplainActivity extends AutoLayoutActivity {
    @BindView(R.id.tb_TvBarTitle)
    TextView tvTitle;
    @BindView(R.id.webview)
    WebView webView;

    private String explianUrl;//相关说明url
    private PopupWindow popupWindow;

    //解决popupwindow在oncreate方法中控件未渲染完毕出现的错误
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            if (popupWindow==null){
                popupWindow = PopupLoading.popupLoading(getLayoutInflater(), tvTitle);
            }
        }
    }

    @OnClick(R.id.tb_IvReturn)
    public void MyOnclick(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_explain);
        //沉浸式状态栏
        StatusBarUtil.setColor(WebExplainActivity.this, getResources().getColor(R.color.color_ffb321),0);
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 设置右滑动返回
        Slidr.attach(this);
        ButterKnife.bind(this);

        explianUrl = getIntent().getStringExtra(ConstantUtil.KEY_WEB_EXPLAIN);
        if (explianUrl!=null){
            initUI();
        }
    }

    private void initUI() {
        String titleStr="";
        switch (explianUrl){
            case ConstantUtil.EX_ABOUT:
                titleStr="关于我们";
                break;
            default:
                break;
        }
        tvTitle.setText(titleStr);
        initWeb();
    }

    private void initWeb() {
        //使用JavaScript脚本语言
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //支持缩放
        settings.setSupportZoom(true);
        settings.setDomStorageEnabled(true);

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //网页加载进度
                if (newProgress > 60) {
                    if (popupWindow!=null){
                        popupWindow.dismiss();
                    }
                }
            }
        });
        String url = ApiURL.URL_EXPLAIN+explianUrl;
        webView.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (popupWindow!=null){
            popupWindow.dismiss();
        }
    }
}
