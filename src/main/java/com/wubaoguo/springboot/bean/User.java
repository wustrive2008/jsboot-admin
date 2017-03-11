package com.wubaoguo.springboot.bean;

import java.io.Serializable;

public class User implements Serializable{
	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;
	
	public User(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	private String id;  
    private String name;  
  
  
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }  
  
    @Override  
    public boolean equals(Object o) {  
        if (this == o) return true;  
        if (o == null || getClass() != o.getClass()) return false;  
  
        User user = (User) o;  
  
        if (id != null ? !id.equals(user.id) : user.id != null) return false;  
  
        return true;  
    }  
  
    @Override  
    public int hashCode() {  
        return id != null ? id.hashCode() : 0;  
    }  
}
