package com.wubaoguo.springboot.redis.common.conf;

import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class RedisConfig {
	
    private GenericObjectPoolConfig poolConfig;
    
    private String host;
    
    private int port;
    
    private int timeout;
    
    private String password;
    
    private Set<String> sentinels;
    
    /**
     * Getter method for property <tt>poolConfig</tt>.
     * 
     * @return property value of poolConfig
     */
    public GenericObjectPoolConfig getPoolConfig() {
        return poolConfig;
    }

    /**
     * Setter method for property <tt>poolConfig</tt>.
     * 
     * @param poolConfig value to be assigned to property poolConfig
     */
    public void setPoolConfig(GenericObjectPoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }

    /**
     * Getter method for property <tt>sentinels</tt>.
     * 
     * @return property value of sentinels
     */
    public Set<String> getSentinels() {
        return sentinels;
    }

    /**
     * Setter method for property <tt>sentinels</tt>.
     * 
     * @param sentinels value to be assigned to property sentinels
     */
    public void setSentinels(Set<String> sentinels) {
        this.sentinels = sentinels;
    }

    /**
     * Getter method for property <tt>host</tt>.
     * 
     * @return property value of host
     */
    public String getHost() {
        return host;
    }

    /**
     * Setter method for property <tt>host</tt>.
     * 
     * @param host value to be assigned to property host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Getter method for property <tt>port</tt>.
     * 
     * @return property value of port
     */
    public int getPort() {
        return port;
    }

    /**
     * Setter method for property <tt>port</tt>.
     * 
     * @param port value to be assigned to property port
     */
    public void setPort(int port) {
        this.port = port;
    }

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    
}
