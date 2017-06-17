package com.example.chie.notifitest0429;

import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.chie.notifitest0429.MainActivity.flag;
import static com.example.chie.notifitest0429.MainActivity.uid;

/**
 * Created by chie on 2017/04/29.
 */

public class MyFirebaseInstanceIdservice extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIdservice";
    public  static String token;
    private long createdAt;
    private long endedAt;

    //ユーザIDの指定
    private static String userId = "AB0012-6";

	//Userクラス（未使用）
    //private List<User> hoge1;


    @Override
    public void onTokenRefresh() {
        //アプリ初起動時にFCMトークンを生成
        token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "RefreshedToken = " + token);
        createdAt = new Date().getTime() /1000L;
        endedAt = 0;

        //TODO
        //生成されたトークンをmain_activityのtoken_viewに指定する

        //トークンの値をサーバーへ送信
        submit();
    }

    private void submit() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
		Log.d(TAG, "getInstance");

/*      //Userクラスを用いたDBへの書き込み
        User user = new User(createdAt,endedAt,token);
        DatabaseReference refUser = database.getReference("/usersTokens/" + userId);
        refUser.push().setValue(user);
*/

        
		//ListとMapを用いたDBへの書き込み
        DatabaseReference refUser = database.getReference("/usersTokens/" + userId);
		
		//今までの履歴全部入れて置けるようにするおおもとの箱listHistory
		final List<Map> listHistory = new ArrayList<Map>(1);

		//今できたtokenとcreatedAtとendedAtについてを一つにまとめるmapToken
        final Map<String,Object> mapToken =new HashMap<>();
        {
            hashMap.put("createdAt",createdAt);
            hashMap.put("token",token);
            hashMap.put("endedAt",endedAt);
        }
        //tokenInfoNew.add(hashMap);

		//ユーザID下にmapTokenを登録させる
        //refUser.setValue(mapToken);


		//ID以下のレコードを取り、listHistoryに保存、
		//listHistoryの要素数＋１のところに作成されたmapTokenを追加して、
		//listHistoryごとDBに返す
        refUser.child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {			
            
						 
                        Log.d(TAG, "getChildren: " + dataSnapshot.getChildren());
                        Log.d(TAG, "getKey : " + dataSnapshot.getKey());
                        Log.d(TAG, "getRef : " + dataSnapshot.getRef());

                      
                        for (DataSnapshot child: dataSnapshot.getChildren()) {
                           Log.d(TAG, "in for");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

}
