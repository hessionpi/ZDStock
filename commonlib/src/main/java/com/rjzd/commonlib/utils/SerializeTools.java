package com.rjzd.commonlib.utils;

import android.content.Context;
import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Serialize Tools,Created  on 2017/5/9.
 *
 * @author Hition
 */
public class SerializeTools {

    private SerializeTools() {
        throw new AssertionError();
    }

    public static boolean cacheObj(Context context, String fileNameKey, Object object) {
        if (null == object || TextUtils.isEmpty(fileNameKey)) {
            return false;
        }
        try {
            File file = new File(context.getFilesDir(), fileNameKey);
            serialization(file.getAbsolutePath(), object);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deserialization object from file.
     *
     * @param filePath file path
     * @return de-serialized object
     * @throws RuntimeException if an error occurs
     */
    public static Object deserialization(String filePath) {
        ObjectInputStream in = null;
        try {
            File f = new File(filePath);
            if (!f.exists()){
               return  null;
            }
            in = new ObjectInputStream(new FileInputStream(filePath));
            Object o = in.readObject();
            in.close();
            return o;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred. ", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("ClassNotFoundException occurred. ", e);
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            IOUtils.close(in);
        }
    }

    /**
     * Serialize object to file.
     *
     * @param filePath file path
     * @param obj      object
     * @throws RuntimeException if an error occurs
     */
    public static void serialization(String filePath, Object obj) {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(filePath));
            out.writeObject(obj);
            out.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred. ", e);
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            IOUtils.close(out);
        }
    }

    /**
     * 删除文件
     *
     * @param filePath
     */
    public static void deletePath(String filePath) {
        File file = new File(filePath);
        if (file != null) {
            if (file.isFile()){
                file.delete();
            }
        }
    }

}

