package ru.service;

import enw.ParamDto;
import enw.ParamHitDto;
import enw.ViewStats;

import java.util.List;

public interface StatService {
    void hit(ParamHitDto paramHitDto);

    List<ViewStats> getStat(ParamDto paramDto);
}
