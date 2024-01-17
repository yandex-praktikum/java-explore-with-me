package com.explore.exploreWithMe.storage;


import com.explore.exploreWithMe.model.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface AppRepository extends JpaRepository<App, String> {


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO app(name,hit_uri) " +
          "VALUES(:name,:uri) " +
            "ON CONFLICT (hit_uri) " +
            "DO NOTHING",
            nativeQuery = true)
    void upsertApp(String name, String uri);

    @Query(value = "Select a.name,a.hit_uri, count( h.ip_user) " +
            "FROM hit AS h " +
            "JOIN app AS a ON h.hit_uri = a.hit_uri " +
            "WHERE h.time_hit BETWEEN :start AND :end " +
            "GROUP BY a.name,a.hit_uri ",
            nativeQuery = true)
    List<App> getStatsAllUri(LocalDateTime start, LocalDateTime end);

    @Query(value = "Select a.name,a.hit_uri, count(DISTINCT h.ip_user) " +
            "FROM hit AS h " +
            "JOIN app AS a ON h.hit_uri = a.hit_uri " +
            "WHERE h.time_hit BETWEEN :start AND :end " +
            "GROUP BY a.name,a.hit_uri ",
            nativeQuery = true)
    List<App> getStatsAllUriForUniqueIp(LocalDateTime start, LocalDateTime end);

    @Query(value = "Select a.name,a.hit_uri, count( h.ip_user)  " +
            "FROM hit AS h " +
            "JOIN app AS a ON h.hit_uri = a.hit_uri " +
            "WHERE h.time_hit BETWEEN :start AND :end AND " +
            "a.name in (:uri) " +
            "GROUP BY a.name,a.hit_uri ",
            nativeQuery = true)
    List<App> getStatsByUriByAllIp(List<String> uri, LocalDateTime start, LocalDateTime end);

    @Query(value = "Select a.name,a.hit_uri, count(DISTINCT h.ip_user) " +
            "FROM hit AS h " +
            "JOIN app AS a ON h.hit_uri = a.hit_uri " +
            "WHERE h.time_hit BETWEEN :start AND :end AND " +
            "a.name in (:uri) " +
            "GROUP BY a.name,a.hit_uri ",
            nativeQuery = true)
    List<App> getStatsByUriByUniqueIp(List<String> uri, LocalDateTime start, LocalDateTime end);
}
