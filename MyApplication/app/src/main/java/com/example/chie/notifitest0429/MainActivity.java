package com.example.chie.notifitest0429;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.chie.notifitest0429.MyFirebaseInstanceIdservice.token;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    //Firebaseからの通知を受け取ったかどうかを判定する変数flag
    //初期値：０、通知付け取り後：１
    public static int flag = 1;

    //ログイン成功時に取得したUIDを保存
    public static String uid;
    //public static Object updateView;

    //Authentication機能を使うのに必要
    private FirebaseAuth mAuth;

    //ログイン状態を追うためのリスナー
    private FirebaseAuth.AuthStateListener mAuthListener;

    //サインインに必要なメールアドレスとパスワード
    //private String mEmail = "star318.ss@yawnchat.webapp";
    //private String mPassword = "999999";
    private String mEmail = "hoge@yawnchat.webapp";
    private String mPassword = "fugafuga";

    private TextView textFlag;
    private TextView textToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        //view関連
        textFlag =(TextView)findViewById(R.id.flag_view);
        textFlag.setText("flag:" + flag);
        textToken = (TextView)findViewById(R.id.token_view);
        textToken.setText("token:" + token);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            //すでにサインインしているのかを確認
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //サインイン済みであれば、
                    // サインインしたアカウントに対応するUIDをString型変数uidに保存
                    uid = user.getUid().toString();
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    //サインアウト時
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };

        Button button = (Button)findViewById(R.id.button_chat_open);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG,"flag = "+ flag);
                //Uri uri = Uri.parse("https://yawnchat-919a4.firebaseapp.com/");
                Uri uri = Uri.parse("https://yawnchat-919a4.firebaseapp.com/?emaillocal=star318.ss&password=999999");
                Intent intent = new Intent(Intent.ACTION_VIEW , uri);

                //flagが１であればチャットアプリを開く
                if(flag == 1)
                startActivity(intent);
            }
        });

        //Firebaseにサインイン
        signIn(mEmail, mPassword);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        Log.d(TAG,"onStart()");
    }

    // Firebaseへのサインインを担う部分
    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // サインイン成功時
                            Log.d(TAG, "signInWithEmail:success");


                        } else {
                            // サインイン失敗時
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // activity_mainの"token: "と"flag: "の更新を担う部分
    void updateView(String flag, String token){
        textFlag.setText("flag:" + flag);
        textToken.setText("token:" + token);
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
