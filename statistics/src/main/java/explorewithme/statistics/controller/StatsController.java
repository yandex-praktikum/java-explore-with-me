package explorewithme.statistics.controller;

import explorewithme.statistics.dto.ApiCall;
import explorewithme.statistics.dto.StatsDto;
import explorewithme.statistics.service.StatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsController {

    private final StatsService statsService;


    @PostMapping("/hit")
    public void create(@RequestBody ApiCall apiCall) throws RuntimeException {
        log.debug("POST request to statistics");
        statsService.create(apiCall);
    }

    @GetMapping("/stats")
    public List<StatsDto> getStats(@RequestParam(name = "start") String start,
                                   @RequestParam(name = "end") String end,
                                   @RequestParam (name = "uris",required = false) String[] uris,
                                   @RequestParam (name = "unique", defaultValue = "false") boolean unique)
            throws RuntimeException {
        log.debug("GET request to controller: start " + start + ", end " + end +  ", unique " + unique);
        return statsService.getStats(start, end, uris, unique);
    }

}
