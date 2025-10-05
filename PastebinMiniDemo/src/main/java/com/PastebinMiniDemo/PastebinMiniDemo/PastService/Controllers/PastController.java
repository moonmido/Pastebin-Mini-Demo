package com.PastebinMiniDemo.PastebinMiniDemo.PastService.Controllers;

import com.PastebinMiniDemo.PastebinMiniDemo.PastService.Inputs.AddPast;
import com.PastebinMiniDemo.PastebinMiniDemo.PastService.Services.ReadWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@RestController
@RequestMapping("/api/paste")
public class PastController {

    @Autowired
    private ReadWriteService readWriteService;

    // ✅ Create new paste
    @PostMapping("/add")
    public ResponseEntity<String> addPaste(@RequestBody AddPast addPast) {
        String urlHash = readWriteService.addPast(addPast);
        return ResponseEntity.status(HttpStatus.CREATED).body(urlHash);
    }

    // ✅ Get paste by hash (returns the file)
    @GetMapping("/{hash}")
    public ResponseEntity<Resource> getPaste(@PathVariable String hash) throws MalformedURLException {
        Resource resource = readWriteService.getPast(hash);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + hash + ".txt")
                .body(resource);
    }

    // ✅ Delete paste by hash
    @DeleteMapping("/{hash}")
    public ResponseEntity<String> deletePaste(@PathVariable String hash) {
        boolean deleted = readWriteService.deletePast(hash);
        if (deleted)
            return ResponseEntity.ok("Paste deleted successfully");
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paste not found");
    }
}

