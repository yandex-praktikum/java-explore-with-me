package explorewithme.ewm.events.controllers;

import explorewithme.ewm.compilation.service.CompilationService;
import explorewithme.ewm.compilation.dto.CompilationDto;
import explorewithme.ewm.events.dto.CategoryDto;
import explorewithme.ewm.events.dto.EventFullDto;
import explorewithme.ewm.events.dto.EventShortDto;
import explorewithme.ewm.events.dto.StatsDto;
import explorewithme.ewm.events.repository.FilterSort;
import explorewithme.ewm.events.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class EventsControllerPublic {

    private final EventService eventService;
    private final CompilationService compilationService;

    @GetMapping("/events")
    public List<EventShortDto> getEvents(@RequestParam(name = "text", required = false) String text,
                                         @RequestParam(name = "categories", required = false) long[] categories,
                                         @RequestParam(name = "rangeStart", required = false) String start,
                                         @RequestParam(name = "rangeEnd", required = false) String end,
                                         @RequestParam(name = "onlyAvailable", defaultValue = "false") boolean onlyAvailable,
                                         @RequestParam(name = "filterSort", defaultValue = "EVENT_DATE") FilterSort filterSort,
                                         @RequestParam(name = "from", defaultValue = "0") int from,
                                         @RequestParam(name = "size", defaultValue = "10") int size,
                                         HttpServletRequest request)
            throws RuntimeException {

        log.debug("Get request to public API for events with filters: text " + text + " categories " + categories + ", rangeStart " + start
                + ", rangeEnd " + end + ", onlyAvailable " + onlyAvailable + ", filterSort " + filterSort + ", from" + from + ", size " + size);


        log.info("client ip: {}", request.getRemoteAddr());
        log.info("endpoint path: {}", request.getRequestURI());

        sendStats(request);

        return eventService.getEvents(text,categories,start,end,onlyAvailable, filterSort,from,size);
    }


    @GetMapping("/events/{id}")
    public EventFullDto getEventById(@PathVariable long id, HttpServletRequest request) throws RuntimeException {

        log.info("client ip: {}", request.getRemoteAddr());
        log.info("endpoint path: {}", request.getRequestURI());

        sendStats(request);

        return eventService.getEventById(id);
    }

    @GetMapping("/categories")
    public List<CategoryDto> getCategories(@RequestParam(name = "from", defaultValue = "0") int from,
                                           @RequestParam(name = "size", defaultValue = "10") int size)
            throws RuntimeException {

        log.debug("Get request to public Api for categories");
        return eventService.getCategories(from, size);
    }

    @GetMapping("/categories/{catId}")
    public CategoryDto getCategoryById(@PathVariable int catId)
            throws RuntimeException {
        log.debug("Get request to public Api for category by id " + catId);
        return eventService.getCategoryById(catId);
    }

    @GetMapping("/compilations")
    public List<CompilationDto> getCompilations(@RequestParam(name = "from", defaultValue = "0") int from,
                                              @RequestParam(name = "size", defaultValue = "10") int size,
                                              @RequestParam(name = "pinned", required = false) boolean pinned)
            throws RuntimeException {
        log.debug("Get request to public Api for compilations with filter pinned:" + pinned);
        return compilationService.getComilations(from, size, pinned);
    }

    @GetMapping("/compilations/{compId}")
    public CompilationDto getCompilationById(@PathVariable int compId)
            throws RuntimeException {
        log.debug("Get request to public Api for compilation with id: " + compId);
        return compilationService.getComilationById(compId);
    }

    // Method to send hits and uris
    private void sendStats(HttpServletRequest request) {
        Mono<ClientResponse> clientResponse = WebClient.builder().build()
                .post().uri("http://localhost:9090/hit")
                .body(Mono.just(new StatsDto("ewm", request.getRequestURI(),
                        request.getRemoteAddr(), LocalDateTime.now().toString())), StatsDto.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        clientResponse.subscribe((response) -> {

            // here you can access headers and status code
            ClientResponse.Headers headers = response.headers();
            HttpStatus stausCode = response.statusCode();

            Mono<String> bodyToMono = response.bodyToMono(String.class);
            // the second subscribe to access the body
            bodyToMono.subscribe((body) -> {

                // here you can access the body
                log.info("body:" + body);

                // and you can also access headers and status code if you need
                log.info("headers:" + headers.asHttpHeaders());
                log.debug("stausCode:" + stausCode);

            },(ex) -> {
                throw new RuntimeException("stats not working");
            });
        }, (ex) -> {
            throw new RuntimeException("network bad");
        });
    }

}
