package com.PastebinMiniDemo.PastebinMiniDemo.PastService.Models;

import jakarta.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Past {

    @Id
    @Column(length = 6)
    private String urlHash;       // renamed to camelCase

    @Column(length = 6, nullable = true)
    private String userName;

    private String customUrl;     // renamed

    @Column(nullable = false)
    private String contentKey;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = true)
    private Date expiredAt;

    // PrePersist to set default expiration
    @PrePersist
    public void setDefaultExpiration() {
        if (expiredAt == null) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, 2);
            this.expiredAt = cal.getTime();
        }
    }

    // Getters & Setters
    public String getUrlHash() { return urlHash; }
    public void setUrlHash(String urlHash) { this.urlHash = urlHash; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getCustomUrl() { return customUrl; }
    public void setCustomUrl(String customUrl) { this.customUrl = customUrl; }

    public String getContentKey() { return contentKey; }
    public void setContentKey(String contentKey) { this.contentKey = contentKey; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getExpiredAt() { return expiredAt; }
    public void setExpiredAt(Date expiredAt) { this.expiredAt = expiredAt; }
}

