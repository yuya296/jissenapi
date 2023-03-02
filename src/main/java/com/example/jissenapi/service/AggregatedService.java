package com.example.jissenapi.service;

import com.example.jissenapi.model.Deal;
import com.example.jissenapi.model.Issue;
import com.example.jissenapi.model.Position;
import com.example.jissenapi.model.receiver.AggregatedDataReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AggregatedService {

    @Autowired
    IssueService issueService;

    @Autowired
    PositionService positionService;

    @Autowired
    DealService dealService;

    public List<AggregatedDataReceiver> getAggregatedData() {
        return positionService.findAllPositions().stream().map(this::apply).toList();
    }

    /**
     * PositionとDealsをすべて複合したデータを AggregatedDataReceiver で返す
     * @return 集約後のデータ
     */
    public List<AggregatedDataReceiver> getNewAggregatedData() {
        return issueService.findAllIssues().stream()
                .map(this::applyWithDeals)
                .filter(adr -> adr.getQuantity().compareTo(BigDecimal.ZERO) != 0)
                .toList();
    }

    /**
     * 指定した Issue と同様の銘柄コードを持つ Position, Deals の数量・簿価を合算して表示する
     * @param issue 計算したいissue
     * @return 集約後の AggregatedDataReceiver インスタンス
     */
    private AggregatedDataReceiver applyWithDeals(Issue issue) {
        String      code        = issue.getCode();
        String      name        = issue.getName();
        LocalDate   maturity    = issue.getMaturity();
        BigDecimal  rate        = issue.getRate();
        Integer     couponTimes = issue.getCouponTimes();
        BigDecimal  quantity    = BigDecimal.ZERO;
        BigDecimal  bookValue   = BigDecimal.ZERO;

        for (Deal deal : dealService.findDealsByCode(code)) {
            quantity = quantity.add(deal.getQuantity());
            bookValue = bookValue.add(deal.getBookValue().multiply(deal.getQuantity()));
        }

        if (positionService.findPositionByCode(code).isPresent()) {
            Position position = positionService.findPositionByCode(code).get();
            quantity = quantity.add(position.getQuantity());
            bookValue = bookValue.add(position.getBookValue().multiply(position.getQuantity()));
        }

        if (quantity.compareTo(BigDecimal.ZERO) == 0) bookValue = BigDecimal.ZERO;
        else bookValue = bookValue.divide(quantity,12, RoundingMode.DOWN);

        Optional<BigDecimal> marketValue    = Optional.ofNullable(issue.getMarketValue());
        BigDecimal finalBookValue = bookValue;
        BigDecimal finalQuantity = quantity;
        Optional<BigDecimal> profitAndLoss  = marketValue.map(m -> m.subtract(finalBookValue).multiply(finalQuantity));

        return new AggregatedDataReceiver(code, name, maturity, rate, couponTimes, quantity, bookValue, marketValue, profitAndLoss);
    }

    private AggregatedDataReceiver apply(Position position) {

        String      code        = position.getCode();
        Issue       issue       = issueService.findIssueByCode(code).orElseThrow(() -> new RuntimeException("Issueに存在しない銘柄があります")); //TODO: Optionに適用させる
        String      name        = issue.getName();
        LocalDate   maturity    = issue.getMaturity();
        BigDecimal  rate        = issue.getRate();
        Integer     couponTimes = issue.getCouponTimes();
        BigDecimal  quantity    = position.getQuantity();
        BigDecimal  bookValue   = position.getBookValue();

        Optional<BigDecimal> marketValue    = Optional.ofNullable(issue.getMarketValue());
        Optional<BigDecimal> profitAndLoss  = marketValue.map(m -> m.subtract(bookValue).multiply(quantity));

        return new AggregatedDataReceiver(code, name, maturity, rate, couponTimes, quantity, bookValue, marketValue, profitAndLoss);
    }
}
