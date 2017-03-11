package com.wubaoguo.springboot.redis.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class HessianSerializer {

	public static <T> byte[] serialize(T object) throws IOException {
		if(object == null) {
			throw new NullPointerException();
		}
		ByteArrayOutputStream os = null;
		ObjectOutputStream objectInputStream = null;
		try {
			os = new ByteArrayOutputStream();
			objectInputStream = new ObjectOutputStream(os);
			objectInputStream.writeObject(object);
			return os.toByteArray();
		} finally {
			if(os != null) {
				os.close();
			}
			if(objectInputStream != null) {
				objectInputStream.close();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T deserialize(byte[] bytes) throws IOException, ClassNotFoundException{
		if(bytes == null) {
			throw new NullPointerException();
		}
		ByteArrayInputStream is = null;
		ObjectInputStream objectInputStream  = null;
		try {
			is = new ByteArrayInputStream(bytes);
			objectInputStream = new ObjectInputStream(is);
			return (T)objectInputStream.readObject();
		} finally {
			if(is != null) {
				is.close();
			}
			if(objectInputStream != null) {
				objectInputStream.close();
			}
		}
	}
	
}
