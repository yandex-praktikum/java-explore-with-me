package ru.explorewithme.hit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/hit")
@Slf4j
public class EndPointController {
    private EndPointService endPointService;

    public EndPointController(EndPointService endPointService) {
        this.endPointService = endPointService;
    }

    @PostMapping()
    public void addEndPoint(@RequestBody EndPointHit endPointHit) {
        log.info("Adding new endpoint={}", endPointHit);
        endPointService.addEndPoint(endPointHit);
    }
}
