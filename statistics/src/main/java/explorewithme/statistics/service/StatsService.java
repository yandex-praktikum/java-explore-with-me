package explorewithme.statistics.service;

import explorewithme.statistics.dto.StatsDto;
import explorewithme.statistics.dto.ApiCall;

import java.util.List;

public interface StatsService {
    void create(ApiCall apiCall);

    List<StatsDto> getStats(String start, String end, String[] uris, boolean unique);
}
