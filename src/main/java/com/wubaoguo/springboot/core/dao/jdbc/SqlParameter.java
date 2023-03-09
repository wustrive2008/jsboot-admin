package com.wubaoguo.springboot.core.dao.jdbc;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("all")
public class SqlParameter implements Map {

	private Map source;
	
	
	public SqlParameter(){
		this.source = new HashMap();
	}
	
	public SqlParameter(Object key,Object value){
		this.source = new HashMap();
		this.source.put(key, value);
	}
	
	public Map getSource() {
		return source;
	}

	public SqlParameter setSource(Map source) {
		this.source = source;
		return this;
	}

	public void clear() {
		this.source.clear();
	}

	public boolean containsKey(Object key) {
		return this.source.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return this.source.containsValue(value);
	}

	public Set entrySet() {
		return this.source.entrySet();
	}

	public Object get(Object key) {
		return this.source.get(key);
	}
	

	public boolean isEmpty() {
		return this.source.isEmpty();
	}

	public Set keySet() {
		return this.source.keySet();
	}

	public SqlParameter addValue(Object key, Object value) {
		this.source.put(key, value);
		return this;
	}

	public void putAll(Map m) {
		this.source.putAll(m);
	}
	
	public SqlParameter remove(Object key) {
		this.source.remove(key);
		return this;
	}

	public int size() {
		return this.source.size();
	}

	public Collection values() {
		return this.source.values();
	}

	public SqlParameter put(Object key, Object value) {
		this.source.put(key, value);
		return this;
	}
}
