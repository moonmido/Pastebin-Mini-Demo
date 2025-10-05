package com.PastebinMiniDemo.PastebinMiniDemo.PastService.Services;

import com.PastebinMiniDemo.PastebinMiniDemo.PastService.Inputs.AddPast;
import com.PastebinMiniDemo.PastebinMiniDemo.PastService.Models.Key;
import com.PastebinMiniDemo.PastebinMiniDemo.PastService.Models.Past;
import com.PastebinMiniDemo.PastebinMiniDemo.PastService.MyExceptions.NoContentException;
import com.PastebinMiniDemo.PastebinMiniDemo.PastService.MyExceptions.UrlNotFoundException;
import com.PastebinMiniDemo.PastebinMiniDemo.PastService.Repositories.KeyRepo;
import com.PastebinMiniDemo.PastebinMiniDemo.PastService.Repositories.PastRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReadWriteService {

private final PastRepo pastRepo;
private final KGS kgs;

    @Autowired
    private KeyRepo keyRepo;


    public ReadWriteService(PastRepo pastRepo, KGS kgs) {
        this.pastRepo = pastRepo;
        this.kgs = kgs;
    }

    private static final String STORAGE_DIR = "C:\\Users\\Hp\\Desktop\\Store_object";

    public void StorePastData(String pastData , String ContentKey){
        if(pastData.isEmpty()) throw new IllegalArgumentException();

        File dir = new File(STORAGE_DIR);
        if(!dir.exists()){
             dir.mkdirs();
        }

        File file = new File(dir,ContentKey+".txt");
        try (FileWriter fileWriter= new FileWriter(file)){
            fileWriter.write(pastData);

        } catch (IOException e) {
            throw new RuntimeException("Saving file Failed "+e.getMessage());
        }
    }

    public Resource getFileByContentKey(String contentKey) throws MalformedURLException {
        if (contentKey == null || contentKey.isBlank()) {
            throw new IllegalArgumentException("❌ contentKey cannot be null or blank");
        }

        Path filePath = Path.of(STORAGE_DIR, contentKey + ".txt");
        File file = filePath.toFile();

        if (!file.exists()) {
            throw new IllegalArgumentException("❌ File not found for contentKey: " + contentKey);
        }

        return new UrlResource(file.toURI());
    }



    public String addPast(AddPast addPast){
        if(addPast==null) throw new IllegalArgumentException();

        try {
            String gen_key = kgs.generateHashKey(addPast.userName(), addPast.custom_url());
            String gen_content_key = kgs.GenerateContentKey();
            Past past = new Past();
            past.setCreatedAt(new Date());
            past.setCustomUrl(addPast.custom_url());
            past.setExpiredAt(addPast.expiration_date());
            past.setUrlHash(gen_key);
            past.setUserName(addPast.userName());
            past.setContentKey(gen_content_key);
            StorePastData(addPast.past_data() , gen_content_key);
            Past save = pastRepo.save(past);
            return save.getUrlHash();
        } catch (Exception e){
            return e.getMessage();
        }
    }

    public Resource getPast(String past_key) throws MalformedURLException {
        if(past_key==null) throw new IllegalArgumentException("must implement past_key");
        if(!kgs.isKeyAlreadyUsed(past_key)) throw new UrlNotFoundException("past key not found !");
        Optional<Past> byId = pastRepo.findById(past_key);
        if(byId.isEmpty()) throw new NoContentException("no data was found !");
        String contentKey = byId.get().getContentKey();
        return getFileByContentKey(contentKey);
    }




    public boolean deletePast(String past_key){
        if(past_key==null) throw new IllegalArgumentException();
        if(!kgs.isKeyAlreadyUsed(past_key)) throw new UrlNotFoundException("that url not exist");
        pastRepo.deleteById(past_key);
        Key keyByKeyGenerated = keyRepo.findByKeyGenerated(past_key);
        if(keyByKeyGenerated==null) return false;
        keyByKeyGenerated.setUsed(false);
        keyRepo.save(keyByKeyGenerated);
        return true;
    }





}
