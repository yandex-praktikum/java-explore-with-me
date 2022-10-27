package explorewithme.statistics.service;

import explorewithme.statistics.dto.ApiCall;
import explorewithme.statistics.dto.StatsDto;
import explorewithme.statistics.model.Attributes;
import explorewithme.statistics.model.Statistics;
import explorewithme.statistics.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {

    private final StatisticsRepository repository;

    @Override
    @Transactional
    public void create(ApiCall apiCall) {
    Statistics record = new Statistics();
    record.setApp(apiCall.getApp());
    record.setCreated(apiCall.getTimestamp());
    Attributes attributes = new Attributes(apiCall.getUri(), apiCall.getIp());
    record.setAttributes(attributes);

    log.debug("Saving hit");
    repository.save(record);
    }

    @Override //Method to get stats from repo
    public List<StatsDto> getStats(String start, String end, String[] uris, boolean unique) {
        LocalDateTime startDate = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endDate = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<Statistics> toReturn = new ArrayList<>();
        if (uris == null && !unique){
            log.debug("Asking repo for list of all apps from NON unique ips and ALL uris, for time period");
             toReturn = repository.notUniqueNoUrisFilterMethod(startDate,endDate);
        }

        if (uris!=null && !unique){
            log.debug("Asking repo for list of all apps from NON unique ips and SELECTED uris, for time period");
            toReturn = repository.notUniqueFilterMethod(startDate,endDate, Arrays.asList(uris));
        }
        if(uris!=null && unique){
            log.debug("Asking repo for list of all apps from only UNIQUE ips and for SELECTED uris, for time period");
           toReturn= repository.fullFilterMethod(startDate,endDate,Arrays.asList(uris));
        }
        if(uris==null && unique){
            log.debug("Asking repo for list of all apps from only UNIQUE ips and for all uris, for time period");
           toReturn = repository.UniqueNoUrisFilterMethod(startDate,endDate);
        }

        return countHitsByAppsAndUris(toReturn);
    }

    //Method to count stats and transform to Dto
    private List<StatsDto> countHitsByAppsAndUris(List<Statistics> allAppsStats) {
        log.debug("Getting list of stats to sort by apps and count by uris");
        Map<String, List<Statistics>> statsByApps = allAppsStats.stream()
                .collect(Collectors.groupingBy(Statistics::getApp, Collectors.toList()));
        log.debug("Sorted by apps, pass to count by uris");
        List<StatsDto> toReturn = new ArrayList<>();
        for (String app : statsByApps.keySet()){
            StatsDto dto = new StatsDto();
            Map<String, Long> urisCount = statsByApps.get(app).stream()
                    .map(Statistics::getAttributes)
                    .collect(Collectors.groupingBy(Attributes::getUri, Collectors.counting()));
            for(String uri: urisCount.keySet()){
                dto.setApp(app);
                dto.setUri(uri);
                dto.setHits(urisCount.get(uri));
                toReturn.add(dto);
            }
        }
        log.debug("Returning calculations");
        return toReturn;
    }

}
