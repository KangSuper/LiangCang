package com.xiekang.king.liangcang.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.xiekang.king.liangcang.R;

public class RegisterActivity extends Activity {

    private ImageView backImg;
    private ImageView closeImg;
    private EditText phoneEdit;
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        backImg = (ImageView) findViewById(R.id.register_back_Img);
        closeImg = (ImageView) findViewById(R.id.register_close_Img);
        phoneEdit = (EditText) findViewById(R.id.register_phone_edit);
        nextBtn = (Button) findViewById(R.id.register_next_btn);

        backImg.setOnClickListener(clickListener);
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNum = phoneEdit.getText().toString();
                if (TextUtils.isEmpty(phoneNum)){
                    Toast.makeText(RegisterActivity.this,"电话号码不能为空",Toast.LENGTH_SHORT).show();
                }else if (phoneNum.length() < 11){
                    Toast.makeText(RegisterActivity.this,"长度小于11位,请检查",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
