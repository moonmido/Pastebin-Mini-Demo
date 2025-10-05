package com.PastebinMiniDemo.PastebinMiniDemo.PastService.Inputs;

import jakarta.persistence.Column;

import java.util.Date;

public record AddPast(
        String past_data ,
        String userName ,
        String custom_url ,
        Date expiration_date) {
}
