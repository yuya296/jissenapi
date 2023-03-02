package com.example.jissenapi.service;

import com.example.jissenapi.exception.IllegalDealException;
import com.example.jissenapi.model.Position;
import com.example.jissenapi.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class PositionService {
    @Autowired
    PositionRepository positionRepository;


    /**
     * 全てのポジションを表示
     * @return ポジションのリスト
     */
    public List<Position> findAllPositions() {
        return positionRepository.findAll();
    }

    public Optional<Position> findPositionByCode(String code) {
        return positionRepository.findById(code);
    }

    public Position addPosition(Position newPosition) {
        // 対象の銘柄をDBから探す、なければ空のPortfolioを生成
        if (newPosition == null) throw new IllegalDealException(null);
        Position position = findPositionByCode(newPosition.getCode())
                .orElse(new Position(newPosition.getCode(), 0, 0));

        // 計算
        BigDecimal newQuantity = position.getQuantity().add(newPosition.getQuantity());
        BigDecimal newBookValue = (newPosition.getQuantity().compareTo(BigDecimal.ZERO) >= 0) ?
                position.getQuantity().multiply(position.getBookValue())
                        .add(newPosition.getQuantity().multiply(newPosition.getBookValue()))
                        .divide(position.getQuantity().add(newPosition.getQuantity()),12, RoundingMode.DOWN):
                position.getBookValue();

        // 例外を弾く
        if (newQuantity.compareTo(BigDecimal.ZERO) < 0) throw new IllegalDealException(newPosition);

        // セットして保存
        position.setQuantity(newQuantity);
        position.setBookValue(newBookValue);
        return positionRepository.save(position);
    }

}
