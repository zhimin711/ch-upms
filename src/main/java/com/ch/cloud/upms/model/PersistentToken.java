package com.ch.cloud.upms.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "persistent_token")
public class PersistentToken implements Serializable {
    @Id
    @Column(name = "SERIAL")
    private String serial;

    @Column(name = "EXPIRES")
    private Date expires;

    @Column(name = "TOKEN")
    private String token;

    @Column(name = "USERNAME")
    private String username;

    private static final long serialVersionUID = 1L;

    /**
     * @return SERIAL
     */
    public String getSerial() {
        return serial;
    }

    /**
     * @param serial
     */
    public void setSerial(String serial) {
        this.serial = serial;
    }

    /**
     * @return EXPIRES
     */
    public Date getExpires() {
        return expires;
    }

    /**
     * @param expires
     */
    public void setExpires(Date expires) {
        this.expires = expires;
    }

    /**
     * @return TOKEN
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return USERNAME
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serial=").append(serial);
        sb.append(", expires=").append(expires);
        sb.append(", token=").append(token);
        sb.append(", username=").append(username);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}