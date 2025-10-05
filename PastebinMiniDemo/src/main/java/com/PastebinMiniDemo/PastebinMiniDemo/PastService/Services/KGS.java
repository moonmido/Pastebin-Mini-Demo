package com.PastebinMiniDemo.PastebinMiniDemo.PastService.Services;

import com.PastebinMiniDemo.PastebinMiniDemo.PastService.Models.Key;
import com.PastebinMiniDemo.PastebinMiniDemo.PastService.MyExceptions.UrlAlreadyExistException;
import com.PastebinMiniDemo.PastebinMiniDemo.PastService.MyExceptions.UsernameNotValidException;
import com.PastebinMiniDemo.PastebinMiniDemo.PastService.Repositories.KeyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
public class KGS {

    @Autowired
    private KeyRepo keyRepo;

    // Check if key already used
    public boolean isKeyAlreadyUsed(String key) {
        Key byKeyGenerated = keyRepo.findByKeyGenerated(key);
        if(byKeyGenerated==null) return false;
       return byKeyGenerated.isUsed();
    }

    // Generate 6-char short key
    public String generateHashKey(String username, String customUrl) {
        if (username != null && username.length() > 6) {
            throw new UsernameNotValidException("username length must be < 6 letters");
        }

        // 1️⃣ If custom URL already used, reject
        if (customUrl != null && isKeyAlreadyUsed(customUrl)) {
            throw new UrlAlreadyExistException("That URL is already in use");
        }

        String baseString;

        // 2️⃣ Combine logic based on what user provides
        if (customUrl != null && username != null) {
            baseString = username + "_" + customUrl;
        } else if (customUrl != null) {
            baseString = customUrl;
        } else if (username != null) {
            baseString = username;
        } else {
            // 3️⃣ Both null → generate random base string
            baseString = generateRandomBase();
        }

        // 4️⃣ Hash and shorten to 6 chars
        String key = hashAndShorten(baseString);

        // 5️⃣ Ensure uniqueness in DB
        while (isKeyAlreadyUsed(key)) {
            baseString = baseString + generateRandomBase();
            key = hashAndShorten(baseString);
        }

        Key key1= new Key();
        key1.setKeyGenerated(key);
        key1.setUsed(true);
        keyRepo.save(key1);
        return key;
    }

    // Helper: random base
    private String generateRandomBase() {
        byte[] bytes = new byte[4];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    // Helper: hash + shorten
    private String hashAndShorten(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash).substring(0, 6);
        } catch (Exception e) {
            throw new RuntimeException("Error generating hash key", e);
        }
    }


    public String GenerateContentKey(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-","").substring(0,12);
    }


}

