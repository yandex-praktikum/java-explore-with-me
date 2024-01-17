package com.explore.exploreWithMe.storage;


import com.explore.exploreWithMe.model.Hit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface HitRepository extends JpaRepository<Hit, Long> {
}
