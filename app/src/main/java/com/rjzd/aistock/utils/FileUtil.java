package com.rjzd.aistock.utils;

import android.content.Context;
import com.rjzd.aistock.ZdStockApp;
import com.rjzd.commonlib.utils.LogUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * 文件操作工具类
 *
 * Created by Hition on 2017/1/10.
 */

public class FileUtil {

    public static void saveFile(String fileName,String content){
        OutputStream os = null;
        try {
            os = ZdStockApp.getAppContext().openFileOutput(fileName,Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(null == os){
            return ;
        }

        try {
            os.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取文件从 data/data 下
     *
     * @param fileName  文件名
     * @return
     */
    public static String readFile(String fileName) {
        InputStream is = null;
        String json = "";
        try {
            is = ZdStockApp.getAppContext().openFileInput(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            json = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(is !=null ){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return json;
    }

    /**
     * Assets 资源文件转换成 string
     * Assets里可放josn数据
     *
     * @param fileName  文件名
     * @param context   上下文参数
     * @return
     */
    public static String getFromAssets(String fileName, Context context) {
        InputStream is = null;
        try {
            is = context.getAssets().open("json/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (is == null) {
            LogUtil.e("fileName err", "fileName 路径有问题");
            return null;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line ;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }




}
