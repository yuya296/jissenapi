package com.example.jissenapi;

import com.example.jissenapi.exception.PositionNotFoundException;
import com.example.jissenapi.model.Deal;
import com.example.jissenapi.model.Issue;
import com.example.jissenapi.model.Position;
import com.example.jissenapi.model.receiver.MarketValueReceiver;
import com.example.jissenapi.model.receiver.AggregatedDataReceiver;
import com.example.jissenapi.service.AggregatedService;
import com.example.jissenapi.service.DealService;
import com.example.jissenapi.service.IssueService;
import com.example.jissenapi.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin // CORS の設定
public class PositionController {

    @Autowired PositionService positionService;
    @Autowired IssueService issueService;
    @Autowired DealService dealService;
    @Autowired AggregatedService aggregatedService;

    public PositionController() {}

    @GetMapping("/positions")
    List<Position> findAllPositions() {
        return positionService.findAllPositions();
    }

    @GetMapping("/positions/{code}")
    Position findPosition(@PathVariable String code) {
        return positionService.findPositionByCode(code).orElseThrow(() -> new PositionNotFoundException(code));
    }

    @PostMapping("/positions/add")
    Position newPosition(@RequestBody Position newPosition) {
        return positionService.addPosition(newPosition);
    }

    @PutMapping("/mtm")
    Issue markToMarket(@RequestBody MarketValueReceiver marketValueReceiver) {
        return issueService.markToMarket(marketValueReceiver);
    }

    @GetMapping("/issues")
    List<Issue> findAllIssues() {
        return issueService.findAllIssues();
    }

    @GetMapping("/issues/{code}")
    Issue findIssue(@PathVariable String code) {
        return issueService.findIssueByCode(code).orElseThrow(() -> new PositionNotFoundException(code));
    }

    @GetMapping("/table")
    List<AggregatedDataReceiver> findTable() {
//        return aggregatedService.getAggregatedData();  // Dealsを含めない場合
        return aggregatedService.getNewAggregatedData(); // Dealsを含める場合
    }

    @GetMapping("/deals")
    List<Deal> findAllDeals() {
        return dealService.findAllDeals();
    }

    @GetMapping("/deals/{code}")
    List<Deal> findDeals(@PathVariable String code) {
        return dealService.findDealsByCode(code);
    }

    @DeleteMapping("/deals/delete/{id}")
    boolean deleteDeal(@PathVariable String id) {
        return dealService.delete(id);
    }

    @PostMapping("/deals/add")
    Deal addDeal(@RequestBody Deal newDeal) {
        return dealService.addDeal(newDeal);
    }
}
