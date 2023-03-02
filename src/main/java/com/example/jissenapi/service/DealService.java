package com.example.jissenapi.service;

import com.example.jissenapi.model.Deal;
import com.example.jissenapi.repository.DealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DealService {
    @Autowired
    DealRepository dealRepository;

    public List<Deal> findAllDeals() {
        return dealRepository.findAll();
    }

    public List<Deal> findDealsByCode(String code) {
        if (code == null) return new ArrayList<>();
        return dealRepository.findAll().stream().filter(deal -> code.equals(deal.getCode())).toList();
    }

    /**
     * 取引IDが一致するDealを取得
     * @param id 取引のid
     * @return 該当する取引
     */
    public Optional<Deal> findDealById(String id) {
        return dealRepository.findById(id);
    }

    public boolean delete(String id) {
        dealRepository.deleteById(id);
        return true;
    }

    /**
     * 新しい取引を追加する
     * @param newDeal 追加したい取引
     * @return 追加された取引
     */
    public Deal addDeal(Deal newDeal) {
        newDeal.setTimestamp(LocalDate.now());
        return dealRepository.save(newDeal);
    }
}
