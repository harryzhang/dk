/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.util
 * @Title: SerializableUtils.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年12月16日 下午5:21:36
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/** 
 *  
 * @author: zhangyunhmf
 * @date 2014年12月16日 下午5:21:36
 */
public final class SerializableUtils {

    private SerializableUtils() {
    }

    public static byte[] serializeToByteArray(Object obj) throws NotSerializableException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(baos);
            out.writeObject(obj);
            return baos.toByteArray();
        } catch (NotSerializableException e) {
            e.fillInStackTrace();
            throw e;
        } catch (IOException e) {

            throw new Error("IOException writing to a byte array!");
        }
    }

    public static byte[] toByteArray(Object obj) throws NotSerializableException {
        return serializeToByteArray(obj);
    }

    /**
     * By default, unwraps IndirectlySerialized objects, returning the original
     */
    public static Object fromByteArray(byte[] bytes) throws IOException, ClassNotFoundException {
        Object out = deserializeFromByteArray(bytes);

        return out;
    }

    public static Object fromByteArray(byte[] bytes, boolean ignore_indirects) throws IOException,
            ClassNotFoundException {
        if (ignore_indirects)
            return deserializeFromByteArray(bytes);
        else
            return fromByteArray(bytes);
    }

    public static Object deserializeFromByteArray(byte[] bytes) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes));
        return in.readObject();
    }

    public static Object testSerializeDeserialize(Object o) throws IOException, ClassNotFoundException {
        return deepCopy(o);
    }

    public static Object deepCopy(Object o) throws IOException, ClassNotFoundException {
        byte[] bytes = serializeToByteArray(o);
        return deserializeFromByteArray(bytes);
    }

}