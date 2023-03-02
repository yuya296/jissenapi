package com.example.jissenapi.service;

import com.example.jissenapi.exception.PositionNotFoundException;
import com.example.jissenapi.model.Issue;
import com.example.jissenapi.model.receiver.MarketValueReceiver;
import com.example.jissenapi.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueService {
    @Autowired
    IssueRepository issueRepository;

    public List<Issue> findAllIssues() {
        return issueRepository.findAll();
    }

    public Optional<Issue> findIssueByCode(String code) {
        return issueRepository.findById(code);
    }




    /**
     * 値洗いを行う
     * @param marketValueReceiver 時価オブジェクト
     * @return 適用済みのMarketValueReceiverのリスト
     */
    public Issue markToMarket(MarketValueReceiver marketValueReceiver) {
        Issue issue = issueRepository.findById(marketValueReceiver.getCode())
                .orElseThrow(() -> new PositionNotFoundException(marketValueReceiver.getCode()));
        issue.setMarketValue(marketValueReceiver.getMarketValue());
        issueRepository.save(issue);
        return issue;
    }
}
