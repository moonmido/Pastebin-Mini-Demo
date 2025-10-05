package com.PastebinMiniDemo.PastebinMiniDemo.PastService.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Key {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keyGenerated;  // camelCase

    private boolean isUsed;       // camelCase

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getKeyGenerated() { return keyGenerated; }
    public void setKeyGenerated(String keyGenerated) { this.keyGenerated = keyGenerated; }

    public boolean isUsed() { return isUsed; }
    public void setUsed(boolean used) { this.isUsed = used; }
}
