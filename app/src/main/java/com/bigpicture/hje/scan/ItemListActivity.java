package com.bigpicture.hje.scan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.Network.NetworkTask;
import com.VO.Item;
import com.adapter.ListViewAdapter;
import com.common.RoundedAvatarDrawable;
import com.common.Scan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.common.Scan.BR_ItemList;
import static com.common.Scan.HTTP_ACTION_ITEMLIST;
import static com.common.Scan.KEY_ItemList;
import static com.common.Scan.itemImage;
import static com.common.Scan.selectedUrl;

public class ItemListActivity extends AppCompatActivity {
    private Context context;
    private String shop_id;
    private ArrayList<Item> itemList;

    private ListView listview ;
    private ListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        context = this;
        shop_id = getIntent().getStringExtra("shop_id");
        itemList = new ArrayList<Item>();

        adapter = new ListViewAdapter() ;
        listview = (ListView) findViewById(R.id.listview_item);
        listview.setAdapter(adapter);
        listview.setEmptyView((TextView)findViewById(R.id.textview_empty));

        NetworkTask networkTask = new NetworkTask(context, HTTP_ACTION_ITEMLIST, Scan.itemList);
        Map<String, String> params = new HashMap<String, String>();
        params.put("shop_id", shop_id);
        networkTask.execute(params);
    }

    //서버에서 Shop 리스트 목록을 리시버로 받음 (NetworkTask)
    public void onResume(){
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BR_ItemList);
        registerReceiver(mItemListBR, filter);
    }
    public void onPause(){
        super.onPause();
        unregisterReceiver(mItemListBR);
    }

    BroadcastReceiver mItemListBR = new BroadcastReceiver(){
        public void onReceive(Context context, Intent intent){
            try {

                JSONArray jArray = new JSONArray(intent.getStringExtra(KEY_ItemList));
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject oneObject = jArray.getJSONObject(i); // Pulling items from the array
                    String item_code = oneObject.getString("item_code");
                    String item_name = oneObject.getString("item_name");
                    String item_price = oneObject.getString("item_price");
                    String item_type = oneObject.getString("item_type");
                    String item_info = oneObject.getString("item_info");
                    String stock_stock = oneObject.getString("stock_stock");
                    Item item = new Item(item_code, item_name, item_price, item_type, item_info, stock_stock);
                    new LoadImage(item_code).execute();
                    itemList.add(item);
                    adapter.addItem(item);
                }
            }catch(JSONException e){
                Log.e("JSON Parsing error", e.toString());
            }
        }
    };


    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        private Bitmap item_image;
        private String item_code, url;
        public LoadImage(String item_code){
            this.item_code = item_code;
            url = selectedUrl+itemImage+item_code;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... args) {
            Log.i("Image download", "Request URL : "+url);
            try {
                item_image = BitmapFactory
                        .decodeStream((InputStream) new URL(url)
                                .getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return item_image;
        }

        protected void onPostExecute(Bitmap image) {
            if (image != null) {
                for (Item i : itemList){
                    if(i.getItem_code().equals(item_code)) {
                        RoundedAvatarDrawable tmpRoundedAvatarDrawable = new RoundedAvatarDrawable(image);
                        i.setItem_image(tmpRoundedAvatarDrawable);
                        adapter.notifyDataSetChanged();
                    }
                }

                Log.i("Image download", "Download complete!!");
            } else {
                Toast.makeText(context, "이미지가 존재하지 않습니다.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
