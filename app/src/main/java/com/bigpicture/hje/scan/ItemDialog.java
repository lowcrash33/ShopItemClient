package com.bigpicture.hje.scan;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2017-04-11.
 */

public class ItemDialog extends Dialog {

    private EditText edittext_code,edittext_name,edittext_price,edittext_brand,edittext_info;
    private Spinner spinner_type;
    private Button button_image, button_send;
    private TextView textview_image;

    private Dialog dialog;
    private Context context;
    private String code,name,price,type,brand,info;

    public ItemDialog(Context context, String scanCode) {
        super(context);
        this.context = context;
        this.code = scanCode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_item_info);
        dialog = this;

        edittext_code = (EditText) findViewById(R.id.edittext_code);
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
        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (code == null || name == null || price == null || type == null || brand ==null)
                    Toast.makeText(getContext(), "전송 불가 : 필수 상품 입력 정보 확인", Toast.LENGTH_LONG).show();
                else {

                    /*NetworkTask networkTask = new NetworkTask(context);

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("shop_name", name);
                    params.put("shop_lat", String.valueOf(shop.getShop_lat()));
                    params.put("shop_lng", String.valueOf(shop.getShop_lng()));
                    params.put("shop_type", shop.getShop_type());
                    params.put("shop_info", shop.getShop_info());
                    params.put("shop_vendor", shop.getShop_vendor());

                    networkTask.execute(params);*/

                    dialog.dismiss();
                }
            }
        });
    }



}
