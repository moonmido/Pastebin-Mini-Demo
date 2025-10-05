package com.PastebinMiniDemo.PastebinMiniDemo.PastService.Repositories;

import com.PastebinMiniDemo.PastebinMiniDemo.PastService.Models.Key;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeyRepo extends JpaRepository<Key , Long> {
    Key findByKeyGenerated(String keyGenerated);

}

