package in.ac.iitb.cse.iitbplusplus;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

WebView mWebView;
    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;
    Button button1;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.btn_check);
        mWebView = (WebView) findViewById(R.id.webView1);
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            // Internet Connection is Present
            // make HTTP requests
            //showAlertDialog(AndroidDetectInternetConnectionActivity.this, "Internet Connection","You have internet connection", true);
            button1.setVisibility(View.INVISIBLE);


            if(getSupportActionBar()!=null){
                getSupportActionBar().hide();
            }
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
            );
            //WebView browser = (WebView) findViewById(R.id.webview);
            WebSettings webSettings = mWebView.getSettings();
            button1.setVisibility(View.INVISIBLE);
            mWebView.getSettings().setJavaScriptEnabled(true);
            //improve WebView performance priority improvement
            mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            mWebView.getSettings().setAppCacheEnabled(true);
            mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

            webSettings.setDomStorageEnabled(true);
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            webSettings.setUseWideViewPort(true);
            webSettings.setSavePassword(true);
            webSettings.setSaveFormData(true);
            //webSettings.setEnableSmoothTransition(true); For older versions than HoneyComb deprecated, used Hardware Acceleration in Manifest XML instead

            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return false;
                }
                //Progress Dialouge
                ProgressDialog pd = null;
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    pd=new ProgressDialog(MainActivity.this);
                    pd.setTitle("Please wait");
                    pd.setMessage("Loading Page...");
                    pd.show();
                    super.onPageStarted(view, url, favicon);
                }
                @Override
                public void onPageFinished(WebView view, String url) {
                    pd.dismiss();
                    super.onPageFinished(view, url);;
                }
            });
            mWebView.loadUrl("http://www.cfilt.iitb.ac.in/iitbplus/");
        } else {
            // Internet connection is not present
            // Ask user to connect to Internet

            button1.setVisibility(View.VISIBLE);

            showAlertDialog(MainActivity.this, "No Internet Connection","You don't have internet connection.", false);


        }

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent) {
                    // Internet Connection is Present
                    // make HTTP requests
                    //showAlertDialog(AndroidDetectInternetConnectionActivity.this, "Internet Connection","You have internet connection", true);
                    //super.onCreate(savedInstanceState);
                    if(getSupportActionBar()!=null){
                        getSupportActionBar().hide();
                    }
                    getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                    );
                    //WebView browser = (WebView) findViewById(R.id.webview);
                    WebSettings webSettings = mWebView.getSettings();
                    button1.setVisibility(View.INVISIBLE);
                    mWebView.getSettings().setJavaScriptEnabled(true);
                    //improve WebView performance priority improvement
                    mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
                    mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                    mWebView.getSettings().setAppCacheEnabled(true);
                    mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

                    webSettings.setDomStorageEnabled(true);
                    webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
                    webSettings.setUseWideViewPort(true);
                    webSettings.setSavePassword(true);
                    webSettings.setSaveFormData(true);
                    //webSettings.setEnableSmoothTransition(true); For older versions than HoneyComb deprecated, used Hardware Acceleration in Manifest XML instead

                    mWebView.setWebViewClient(new WebViewClient() {
                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            view.loadUrl(url);
                            return false;
                        }
                        //Progress Dialouge
                        ProgressDialog pd = null;
                        @Override
                        public void onPageStarted(WebView view, String url, Bitmap favicon) {
                            pd=new ProgressDialog(MainActivity.this);
                            pd.setTitle("Please wait");
                            pd.setMessage("IITBPlus+ is starting...");
                            pd.show();
                            super.onPageStarted(view, url, favicon);
                        }
                        @Override
                        public void onPageFinished(WebView view, String url) {
                            pd.dismiss();
                            super.onPageFinished(view, url);;
                        }
                    });
                    mWebView.loadUrl("http://www.cfilt.iitb.ac.in/iitbplus/");
                } else {
                    // Internet connection is not present
                    // Ask user to connect to Internet
                    showAlertDialog(MainActivity.this, "No Internet Connection","You don't have internet connection.", false);
                }

            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction()==KeyEvent.ACTION_DOWN){
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    }
                    else{
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    /**
     * Function to display simple Alert Dialog
     * */
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

        // Setting OK Button

        // Showing Alert Message
        alertDialog.show();
    }
}

