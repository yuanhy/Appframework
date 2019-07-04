package com.yuanhy.library_tools.util;




import com.alibaba.fastjson.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Json 工具类
 * Created by yuanhy on 2017/3/9.
 */
public class JsonOrEntyTools {

    /**
     * 通过jsonArry 转换成 enty对象数组
     *
     * @param jsonString
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> Object getEntyList (String jsonString, Class<T> obj) {
        List<T> ts = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            if (jsonArray != null && jsonArray.length () > 0) {
                for (int i = 0; i < jsonArray.length (); i++) {
                    JSONObject jsonObject =  jsonArray.optJSONObject (i);
                    T t =JSON.parseObject (jsonObject.toString (), obj);
                    ts.add (t);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace ();
        }
        return ts;
    }

    /**
     * 通过json 转换成 enty对象
     *
     * @param jsonString
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> Object getEnty (String jsonString, Class<T> obj) {
        T enty = JSON.parseObject (jsonString, obj);
        return enty;
    }

    /**
     * 通过实体对象或去jsonString对象
     *
     * @param obj
     * @return
     */
    public static String getJsonString (Object obj) {

        String json =  JSON.toJSONString (obj);

        return json;
    }
    /**
     * 通过实体对象或去JSONArray对象
     *
     * @return
     */
    public static JSONArray getJSONArray (ArrayList<Object> list) {
        JSONArray jsonArray2=new JSONArray();

        com.alibaba.fastjson.JSONArray jsonArray= new com.alibaba.fastjson.JSONArray ();
        for (Object obj:list){
            try {
                JSONObject jsonObject= new JSONObject(getJsonString(obj));
                jsonArray2.put (jsonObject);
            } catch (JSONException e) {
                e.printStackTrace ();
            }

            JSON json= (JSON) JSON.parse (getJsonString(obj));
            jsonArray.add(json)  ;

        }
        return jsonArray2;
    }


}
