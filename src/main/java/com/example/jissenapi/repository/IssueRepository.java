package com.example.jissenapi.repository;

import com.example.jissenapi.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, String> {}
