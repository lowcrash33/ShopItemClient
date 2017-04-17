package com.common;

/**
 * Created by Administrator on 2017-04-12.
 */

public class Scan {

    public static String localUrl = "http://192.168.0.5:8080/isthere";
    public static String awsUrl = "http://52.78.243.10:8080/isthere";
    public static String selectedUrl = awsUrl;

    public static String shopAdd = "/shop/add";
    public static String shopScan = "/shop/scan";
    public static String stockUpdate = "/stock/update";

    public static Double lat= 37.491,lng=127.020; //해커톤 위,경도
    public static float scanDist = 1.0f;  //기본 스캔반경

    public static final String BR_ShopList = "bigpicture.hje.scan.SHOP_LIST";
    public static final String KEY_ShopList = "SHOP_LIST";

    public static final String HTTP_RESPONSE_OK = "OK";
    public static final String HTTP_RESPONSE_FAIL = "FAIL";
}
