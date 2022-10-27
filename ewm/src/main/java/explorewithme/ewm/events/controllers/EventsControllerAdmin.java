package explorewithme.ewm.events.controllers;

import explorewithme.ewm.compilation.service.CompilationService;
import explorewithme.ewm.compilation.dto.CompilationDto;
import explorewithme.ewm.compilation.dto.NewCompilationDto;
import explorewithme.ewm.events.admin.AdminUpdateEventRequest;
import explorewithme.ewm.events.dto.CategoryDto;
import explorewithme.ewm.events.dto.EventFullDto;
import explorewithme.ewm.events.dto.NewCategoryDto;
import explorewithme.ewm.events.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;



import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "admin")
@RequiredArgsConstructor
public class EventsControllerAdmin {

    private final EventService eventService;
    private final CompilationService compilationService;



    // Admin methods to work with events

    @GetMapping("/events")
    public List<EventFullDto> getEventsForAdmin(@RequestParam(name = "users", required = false) long[] users,
                                                @RequestParam(name = "states", required = false) String[] states,
                                                @RequestParam(name = "categories", required = false) long[] categories,
                                                @RequestParam(name = "rangeStart", required = false) String start,
                                                @RequestParam(name = "rangeEnd", required = false) String end,
                                                @RequestParam(name = "from", defaultValue = "0") int from,
                                                @RequestParam(name = "size", defaultValue = "10") int size)
            throws RuntimeException {

        log.debug("Get request from admin, filters: users " + users + " states" + "categories " + categories + "start "
                + start + "end " + end + "from " + from +  "size " + size);
        return eventService.getEventsForAdmin(users, states, categories, start, end, from, size);
    }

    @PutMapping("/events/{eventId}")
    public EventFullDto updateEventAdmin(@RequestBody AdminUpdateEventRequest adminRequest,
                                         @PathVariable long eventId) throws RuntimeException {
        log.debug("Put request by admin update event with id "+ eventId);
        return eventService.updateEventAdmin(adminRequest, eventId);
    }

    @PatchMapping("/events/{eventId}/publish")
    public EventFullDto publishEventAdmin(@PathVariable long eventId) throws RuntimeException {
        log.debug("Patch request by admin publish event with id "+ eventId);
        return eventService.publishEvent(eventId);
    }

    @PatchMapping("/events/{eventId}/reject")
    public EventFullDto rejectEventAdmin(@PathVariable long eventId) throws RuntimeException {
        log.debug("Patch request by admin to reject event with id "+ eventId);
        return eventService.rejectEvent(eventId);
    }

    //Admin methods to work with Categories

    @PatchMapping("/categories")
    public CategoryDto editCategoryAdmin(@RequestBody CategoryDto categoryDto) throws RuntimeException {
        log.debug("Patch request by admin to edit category");
        return eventService.updateCategory(categoryDto);
    }

    @PostMapping("/categories")
    public CategoryDto addCategoryAdmin(@RequestBody NewCategoryDto categoryDto) throws RuntimeException {
        log.debug("Post request by admin to create category");
        return eventService.createCategory(categoryDto);
    }

    @DeleteMapping("/categories/{catId}")
    public void deleteCategoryAdmin(@PathVariable long catId) throws RuntimeException {
        log.debug("Delete request by admin category " + catId);
         eventService.deleteCategory(catId);
    }

    //Admin work with events compilation

    @PostMapping("/compilations")
    public CompilationDto createCompilation(@RequestBody NewCompilationDto newCompilationDto)
            throws RuntimeException {
        log.debug("Post request by admin to create compilation");
        return compilationService.create(newCompilationDto);
    }

    @DeleteMapping("/compilations/{compId}")
    public void deleteCompilationById(@PathVariable long compId) throws RuntimeException {
        log.debug("Delete request by admin to delete compilation " + compId);
        compilationService.delete(compId);
    }

    @DeleteMapping("/compilations/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable long compId,
                                           @PathVariable long eventId)
            throws RuntimeException {
        log.debug("Delete event from compilation by admin request");
        compilationService.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/compilations/{compId}/events/{eventId}")
    public void addEventToCompilation(@PathVariable long compId,
                                                @PathVariable long eventId)
            throws RuntimeException {
        log.debug("Patch add event from compilation by admin request");
        compilationService.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping("/compilations/{compId}/pin")
    public void unpinCompilation(@PathVariable long compId)
            throws RuntimeException {
        log.debug("Patch pin compilation by admin request");
        compilationService.unpinCompilation(compId);
    }

    @PatchMapping("/compilations/{compId}/pin")
    public void pinCompilation(@PathVariable long compId)
            throws RuntimeException {
        log.debug("Patch unpin compilation by admin request");
        compilationService.pinCompilation(compId);
    }


}
