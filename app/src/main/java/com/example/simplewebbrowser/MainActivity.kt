package com.example.simplewebbrowser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {

    private val goHomeButton : ImageButton by lazy {
        findViewById(R.id.goHomeButton)
    }

    private val addressBar : EditText by lazy {
        findViewById(R.id.addressBar)
    }

    private val goBackButton : ImageButton by lazy {
        findViewById(R.id.goBackButton)
    }

    private val goForwardButton : ImageButton by lazy {
        findViewById(R.id.goForwardButton)
    }


    private val webView : WebView by lazy{
        findViewById(R.id.webView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        bindViews()
    }


    //명시된 url불러오기 //암호화되지않은 웹주소 에러 (보안관련) https사용 요망
    //기본적으로 http프로토콜을 막는 것이 보안상 적절
    //안드로이드9부터 http지원 X -> 옵션부여해서 해결 usesCleartextTraffic
    //그러나 외부 웹브라우저가 실행 (디폴트 웹브라우저.크롬) -> override를 통해 해결 (WebViewClient)
    //메뉴버튼 동작 안 함(자바스크립트 허용 안 함.) -> 뷰를 초기화할 때 자바스크립트 사용 명시 필요
    //자판 Action버튼 눌렀을 때 동작하도록 (imeOptions추가) -> actionDone(XML)

    override fun onBackPressed() {//뒤 버튼 눌렀을 때

        if(webView.canGoBack()){
            webView.goBack()
        }else{
            super.onBackPressed() //원래 백버튼 뒤로가기 종료
        }



    }

    private fun initViews(){
        webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true //메뉴버튼 동작 안 함(자바스크립트 허용 안 함.) -> 뷰를 초기화할 때 자바스크립트 사용 명시 필요(보안상 취약)
            loadUrl(DEFAULT_URL)

        }



    }

    private fun bindViews(){

        goHomeButton.setOnClickListener{
            webView.loadUrl(DEFAULT_URL)
        }
        //액션이 수행됐을 때 이벤트 발생( , 발생한 id, 눌렀는지뗏는지)
        addressBar.setOnEditorActionListener{v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                webView.loadUrl(v.text.toString())

            }

            return@setOnEditorActionListener false
        }


        goBackButton.setOnClickListener{
            webView.goBack()//이전 히스토리
        }
        goForwardButton.setOnClickListener{
            webView.goForward()//다음 히스토리
        }


    }

    companion object {
        private const val DEFAULT_URL = "http://www.google.com"
    }
}