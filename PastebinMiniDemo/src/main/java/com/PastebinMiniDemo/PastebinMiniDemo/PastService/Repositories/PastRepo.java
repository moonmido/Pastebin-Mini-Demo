package com.PastebinMiniDemo.PastebinMiniDemo.PastService.Repositories;

import com.PastebinMiniDemo.PastebinMiniDemo.PastService.Models.Past;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PastRepo extends JpaRepository<Past , String> {

List<Past> findByUrlHash(String urlHash);
}
