package com.bigpicture.hje.scan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.Network.NetworkTask;
import com.common.Scan;

import java.util.HashMap;
import java.util.Map;

import static com.common.Scan.HTTP_ACTION_CMD;
import static com.common.Scan.REQ_CODE_SELECT_IMAGE;

/**
 * Created by Administrator on 2017-04-11.
 */

public class ItemInfoActivity extends AppCompatActivity {

    private EditText edittext_code,edittext_name,edittext_price,edittext_brand,edittext_info;
    private Spinner spinner_type;
    private Button button_image, button_send;
    private TextView textview_image;

    private String mImgPath,mImgTitle,mImgOrient;
    private Context context;
    private String code,name,price,type,brand,info="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);
        context = this;

        edittext_code = (EditText) findViewById(R.id.edittext_code);
        edittext_code.setText("85944041151115");
        edittext_name = (EditText) findViewById(R.id.edittext_name);
        edittext_price = (EditText) findViewById(R.id.edittext_price);
        spinner_type = (Spinner) findViewById(R.id.spinner_type);
        edittext_brand = (EditText) findViewById(R.id.edittext_brand);
        edittext_info = (EditText) findViewById(R.id.edittext_info);
        textview_image = (TextView) findViewById(R.id.textview_image);
        button_image = (Button) findViewById(R.id.button_image);
        button_send = (Button) findViewById(R.id.button_send);

        spinner_type.setSelection(0);
        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = (String)parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //마지막 전송 버튼 눌렀을때
        button_image.setOnClickListener(new Button.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(Intent.ACTION_PICK);
                                                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                                                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
                                            }
                                        });

        //마지막 전송 버튼 눌렀을때
        button_send.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                code = edittext_code.getText().toString();
                name = edittext_name.getText().toString();
                price = edittext_price.getText().toString();
                brand = edittext_brand.getText().toString();
                info = edittext_info.getText().toString();
                if (code == null || name == null || price == null || brand ==null)
                    Toast.makeText(context, "전송 불가 : 필수 상품 입력 정보 확인", Toast.LENGTH_LONG).show();
                else {
                    NetworkTask networkTask = new NetworkTask(context, HTTP_ACTION_CMD, Scan.itemAdd);
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("item_code", code);
                    params.put("item_name", name);
                    params.put("item_price", price);
                    params.put("item_type", type);
                    params.put("item_info", info);
                    params.put("item_brand", brand);
                    networkTask.execute(params);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 선택된 사진을 받아 서버에 업로드한다
        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                getImageNameToUri(uri);
                textview_image.setText(mImgPath+mImgTitle+mImgOrient);
            }
        }
    }

    // URI 정보를 이용하여 사진 정보 가져온다
    private void getImageNameToUri(Uri data) {
        String[] proj = { MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.TITLE,
                MediaStore.Images.Media.ORIENTATION};


        Cursor cursor = managedQuery(data, proj, null, null, null);
        cursor.moveToFirst();

        int column_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        int column_title = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE);
        int column_orientation = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.ORIENTATION);

        mImgPath = cursor.getString(column_data);
        mImgTitle = cursor.getString(column_title);
        mImgOrient = cursor.getString(column_orientation);
    }
}
