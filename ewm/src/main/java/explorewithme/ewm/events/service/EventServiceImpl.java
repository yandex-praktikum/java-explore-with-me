package explorewithme.ewm.events.service;

import explorewithme.ewm.events.State;
import explorewithme.ewm.events.admin.AdminUpdateEventRequest;
import explorewithme.ewm.events.admin.UpdateEventRequest;
import explorewithme.ewm.events.dto.*;
import explorewithme.ewm.events.mappers.CategoryMapper;
import explorewithme.ewm.events.mappers.EventMapper;
import explorewithme.ewm.events.model.Category;
import explorewithme.ewm.events.model.Event;
import explorewithme.ewm.events.repository.*;
import explorewithme.ewm.exception.ArgumentException;
import explorewithme.ewm.exception.ConflictException;
import explorewithme.ewm.exception.NotFoundException;
import explorewithme.ewm.requests.services.UtilRequestService;
import explorewithme.ewm.users.UserMapper;
import explorewithme.ewm.users.UserService;
import explorewithme.ewm.util.OffsetBasedPageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static explorewithme.ewm.events.State.*;
import static explorewithme.ewm.events.repository.FilterSort.VIEWS;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Lazy
    private final UtilRequestService utilRequestService;

    private final UserService userService;
    private final EventSpecifications eventSpecifications;



    @Override
    public List<EventShortDto> getEvents(String text, long[] categories, String startStr, String endStr, boolean onlyAvailable,
                                         FilterSort sort, int from, int size) {

        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = null;
        if(startStr != null) {
            start = LocalDateTime.parse(startStr);
        }
        if(endStr != null){
            end = LocalDateTime.parse(endStr);
            if (end.isBefore(start)){
                log.debug("Time range end should be after start, specify earlier date of the start." +
                        " Default start is " + LocalDateTime.now());
                throw new ConflictException("Time range end should be after start, specify earlier date of the start." +
                        " Default start is " + LocalDateTime.now());
            }
        }

        String sortColumn = "eventDate";
        if(sort == VIEWS){
            sortColumn = "views";
        }
        log.debug("Parsed default filters: start" + start + ", end " + end + ", sort " + sortColumn);
        Pageable pageable = new OffsetBasedPageRequest(size, from, Sort.by(Sort.Direction.ASC, sortColumn));

        List<SearchCriteria> filters = new ArrayList<>();
        if (text != null){
            log.debug("Building search criteria for text");
            SearchCriteria filterByText = SearchCriteria.builder()
                    .key("") // keys are preset in the Specification^ annotation, description, title
                    .operator(SearchOperation.LIKE)
                    .value(text)
                    .build();
            filters.add(filterByText);
        }
         log.debug("Building search criteria for event state preset as Published");
            SearchCriteria filterByStates = SearchCriteria.builder()
                    .key("state")
                    .operator(SearchOperation.IN)
                    .values(List.of("PUBLISHED"))
                    .build();
            filters.add(filterByStates);

        if (categories != null) {
            log.debug("Building search criteria for list of categories");
            SearchCriteria filterByCategory = SearchCriteria.builder()
                    .key("category")
                    .operator(SearchOperation.IN)
                    .values(getListFromArray(categories))
                    .build();
            filters.add(filterByCategory);
        }
        log.debug("Building search criteria for start");
        SearchCriteria filterByStart = SearchCriteria.builder()
                .key("eventDate")
                .operator(SearchOperation.GREATER_THAN)
                .value(start.toString())
                .build();
        filters.add(filterByStart);

        if (end != null) {
            log.debug("Building search criteria for end");
            SearchCriteria filterByEnd = SearchCriteria.builder()
                    .key("eventDate")
                    .operator(SearchOperation.LESS_THAN)
                    .value(end.toString())
                    .build();
            filters.add(filterByEnd);
        }

        log.debug("Getting specification from list of search criteria");
        Specification toApply = eventSpecifications.getSpecificationFromFilters(filters);
        log.debug("Asking repo for Page of events according to search");
        return ((List<Event>) eventRepository.findAll(toApply, pageable).getContent()).stream()
                .filter(this::isAvailable)
                .map(this::getEventByEventShort)
                .collect(Collectors.toList());
    }

    private boolean isAvailable(Event event){
        int requests = utilRequestService.getCountOfApproveRequest(event.getId());
        if (event.getParticipantLimit()==0 || requests < event.getParticipantLimit()){
            log.debug("Event is available");
            return true;
        }
        log.debug("Event is unavailable");
        return false;
    }


    // Method returns FullEventDto by eventId from controller
    @Override
    @Transactional
    public EventFullDto getEventById(long id) {
        checkEventId(id);
        log.debug("Returning event by id " + id);
        Event event = eventRepository.getReferenceById(id);
        log.debug("asking repo to ad +1 view for the event");
        eventRepository.updateEventViews(id, event.getViews()+1L);
        return getEventByEventFull(event);
    }

    // Method to add short event description to compilations doesn't add views to statistics

    @Override
    public EventShortDto getEventByIdShort(long id) {
        log.debug("asking repo for event by id " + id);
      Event event = eventRepository.getReferenceById(id);
      EventShortDto dtoToReturn = EventMapper.fromEventShort(event);
        log.debug("asking category repo for category by category id");
      Category category = categoryRepository.getReferenceById(event.getCategory());
      dtoToReturn.setCategory(CategoryMapper.fromCategory(category));
        log.debug("asking util service for requests to get count of approved requests");
      dtoToReturn.setConfirmedRequests(utilRequestService.getCountOfApproveRequest(id));
        log.debug("asking userservice for userShort DTO");
      dtoToReturn.setInitiator(UserMapper.fromUserDtoToShort(userService.getUserById(event.getInitiator())));
        return dtoToReturn;
    }

    // Method to transform event to Short Dto by event
    private EventShortDto getEventByEventShort(Event event) {
        EventShortDto dtoToReturn = EventMapper.fromEventShort(event);
        log.debug("asking category repo for category by category id");
        Category category = categoryRepository.getReferenceById(event.getCategory());
        dtoToReturn.setCategory(CategoryMapper.fromCategory(category));
        log.debug("asking util service for requests to get count of approved requests");
        dtoToReturn.setConfirmedRequests(utilRequestService.getCountOfApproveRequest(event.getId()));
        log.debug("asking userservice for userShort DTO");
        dtoToReturn.setInitiator(UserMapper.fromUserDtoToShort(userService.getUserById(event.getInitiator())));
        return dtoToReturn;
    }

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        log.debug("asking Category repo for all categories by page");
        return categoryRepository.findAll().stream()
                .map(CategoryMapper::fromCategory)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(int catId) {
        checkCategoryId(catId);
        log.debug("asking Category repo for category by id " + catId);
        Category category = categoryRepository.getReferenceById(Long.valueOf(catId));
        return CategoryMapper.fromCategory(category);
    }

    @Override
    public List<EventShortDto> getEventsByUser(long userId, int from, int size) {
        Pageable pageable = new OffsetBasedPageRequest(size, from, Sort.by(Sort.DEFAULT_DIRECTION, "eventDate"));
        log.debug("asking repo for events by user");
        return eventRepository.findEventsByInitiator(userId,pageable).stream()
                .map(this :: getEventByEventShort)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventFullDto> getEventsForAdmin(long[] users, String[] states, long[] categories, String startStr,
                                                String endStr, int from, int size) {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = null;
        if (startStr != null) {
            start = LocalDateTime.parse(startStr);
        }
        if (endStr != null) {
            end = LocalDateTime.parse(endStr);
            if (end.isBefore(start)){
                log.debug("Time range end should be after start, specify earlier date of the start." +
                        " Default start is " + LocalDateTime.now());
                throw new ConflictException("Time range end should be after start, specify earlier date of the start." +
                        " Default start is " + LocalDateTime.now());
            }
        }
        log.debug("Parsed default filters: start" + start + ", end " + end);
        Pageable pageable = new OffsetBasedPageRequest(size, from, Sort.by(Sort.Direction.DESC, "eventDate"));

        List<SearchCriteria> filters = new ArrayList<>();

        if (users != null) {
            log.debug("Building search criteria for users");
            SearchCriteria filterByUsers = SearchCriteria.builder()
                    .key("initiator")
                    .operator(SearchOperation.IN)
                    .values(getListFromArray(users))
                    .build();
            filters.add(filterByUsers);
        }
        if (states != null) {
            log.debug("Building search criteria for event states");
            SearchCriteria filterByStates = SearchCriteria.builder()
                    .key("state")
                    .operator(SearchOperation.IN)
                    .values(List.of(states))
                    .build();
            filters.add(filterByStates);
        }
        if (categories != null) {
            log.debug("Building search criteria for event categories");
            SearchCriteria filterByCategory = SearchCriteria.builder()
                    .key("category")
                    .operator(SearchOperation.IN)
                    .values(getListFromArray(categories))
                    .build();
            filters.add(filterByCategory);
        }
        log.debug("Building search criteria for startRange");
        SearchCriteria filterByStart = SearchCriteria.builder()
                .key("eventDate")
                .operator(SearchOperation.GREATER_THAN)
                .value(start.toString())
                .build();
        filters.add(filterByStart);

        if (end != null) {
            log.debug("Building search criteria for endRange");
            SearchCriteria filterByEnd = SearchCriteria.builder()
                    .key("eventDate")
                    .operator(SearchOperation.LESS_THAN)
                    .value(end.toString())
                    .build();
            filters.add(filterByEnd);
        }

        log.debug("Getting specification from list of search criteria");
        Specification toApply = eventSpecifications.getSpecificationFromFilters(filters);

        log.debug("Asking repo for Page of events according to search");
        return ((List<Event>) eventRepository.findAll(toApply, pageable).getContent()).stream()
                .map(this::getEventByEventFull)
                .collect(Collectors.toList());
    }


    private EventFullDto getEventByEventFull(Event event){
        EventFullDto dtoToReturn = EventMapper.fromEvent(event);
        log.debug("Getting category from Category repo");
        Category category = categoryRepository.getReferenceById(event.getCategory());
        dtoToReturn.setCategory(CategoryMapper.fromCategory(category));
        log.debug("Getting number of requests from util service for requests");
        dtoToReturn.setConfirmedRequests(utilRequestService.getCountOfApproveRequest(event.getId()));
        log.debug("Getting initiator user dto from user service");
        dtoToReturn.setInitiator(UserMapper.fromUserDtoToShort(userService.getUserById(event.getInitiator())));
        return dtoToReturn;
    }

    @Override
    @Transactional
    public EventFullDto updateEventAdmin(AdminUpdateEventRequest adminRequest, long eventId) {
        checkEventId(eventId);
        log.debug("Get event from repo by id");
        Event existingEvent = eventRepository.getReferenceById(eventId);
        Event event = EventMapper.fromAdminUpdateEventRequest(adminRequest, existingEvent);
        log.debug("Save mapped event and convert to Full Dto");
        return getEventByEventFull(eventRepository.save(event));
    }

    @Override
    @Transactional
    public EventFullDto publishEvent(long eventId) {
        checkEventId(eventId);
        log.debug("Get event from repo by id");
        Event event = eventRepository.getReferenceById(eventId);
        if (event.getState()!= PENDING && event.getState()!= PUBLISHED){
            log.debug("State is either Canceled or Published, cannot change state");
            throw new ConflictException("State is either Canceled or Published, cannot change state");
        }
        log.debug("Asking repo to update record for the event to Published and moderation to false");
        eventRepository.updateEventStatus(PUBLISHED, false, eventId);
        return getEventByEventFull(eventRepository.getReferenceById(eventId));
    }

    @Override
    @Transactional
    public EventFullDto rejectEvent(long eventId) {
        checkEventId(eventId);
        if (eventRepository.getReferenceById(eventId).getState()!= PENDING
                && eventRepository.getReferenceById(eventId).getState()!= CANCELED){
            log.debug("State is either Canceled or Published or Canceled, cannot change state");
            throw new ConflictException("State is either Canceled or Published or Canceled, cannot change state");
        }
        log.debug("Asking repo to update record for the event to Canceled and moderation to false");
        eventRepository.updateEventStatus(CANCELED, false, eventId);
        return getEventByEventFull(eventRepository.getReferenceById(eventId));
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        checkCategoryId(categoryDto.getId());
        log.debug("Asking repo to update record for the category");
        int changed = categoryRepository.updateCategory(categoryDto.getName(), categoryDto.getId());
        if (changed != 1){
            log.debug("Category was not changed, maybe name is not unique");
            throw new ConflictException("Category was not changed, maybe name is not unique");
        }
        log.debug("Returning updated category by id");
        return CategoryMapper.fromCategory(categoryRepository.getReferenceById(categoryDto.getId()));
    }

    @Override
    @Transactional
    public CategoryDto createCategory(NewCategoryDto categoryDto) {
        Category category;
        try {
            log.debug("Asking repo to save the category");
            category = categoryRepository.save(CategoryMapper.fromNewCategoryDto(categoryDto));
        } catch (ConstraintViolationException e) {
            log.debug("Category was not changed, maybe name is not unique");
            throw new ConflictException("Category was not created, maybe name is not unique");
        }
        return CategoryMapper.fromCategory(category);
    }

    @Override
    @Transactional
    public void deleteCategory(long catId) {
        checkCategoryId(catId);
        log.debug("Asking repo to delete the category " + catId);
        categoryRepository.deleteById(catId);
    }

    @Override
    @Transactional
    public EventFullDto updateEvent(long userId, UpdateEventRequest updateEventRequest) {
        userService.checkId(userId);
        checkEventId(updateEventRequest.getEventId());
        Event event = eventRepository.getReferenceById(updateEventRequest.getEventId());
        if(event.getInitiator()!=userId){
            log.debug("Event does not belong to user");
            throw new ArgumentException("Event does not belong to user");
        }
        if(event.getState()!= PENDING){
            log.debug("Event can no longer be updated because of its state is not Pending");
            throw new ArgumentException("Event can no longer be updated because of its state");
        }
        Event updated = EventMapper.fromUpdateEventRequest(updateEventRequest,event);
        log.debug("Save updated event");
        Event saved = eventRepository.save(updated);
        return getEventByEventFull(saved);
    }

    @Override
    public EventFullDto getEventsById(long userId, long eventId) {
        checkEventId(eventId);
        userService.checkId(userId);
        if (!checkOwnership(userId,eventId)){
            log.debug("Event does not belong to user");
            throw new ArgumentException("Event does not belong to user");
        }
        log.debug("Getting event from repo");
        return getEventByEventFull(eventRepository.getReferenceById(eventId));
    }

    @Override
    @Transactional
    public EventFullDto createEvent(long userId, NewEventDto newEventDto) {
        userService.checkId(userId);
        newEventDto.setInitiator(userId);
        Event event = EventMapper.fromNewEventDto(newEventDto);
        log.debug("Saving event to repo");
        Event saved = eventRepository.save(event);
        return  getEventByEventFull(saved);
    }

    @Override
    @Transactional
    public EventFullDto cancelEvent(long userId, long eventId) {
        userService.checkId(userId);
        if (!checkOwnership(userId,eventId)){
            log.debug("Event does not belong to user");
            throw new ArgumentException("Event does not belong to user");
        }
        log.debug("Update event in repo to set Canceled and modaration false");
        eventRepository.updateEventStatus(State.CANCELED, false, eventId);
        Event canceled = eventRepository.getReferenceById(eventId);
        return getEventByEventFull(canceled);
    }

    //Validation methods

    public void checkCategoryId(long id) {
        if (categoryRepository.findById(id).isEmpty()) {
            log.debug("category with id " + id + " not found");
            throw new NotFoundException("category with id " + id + " not found");
        }
    }
    @Override
    public boolean checkOwnership(long userId, long eventId){
        if (eventRepository.getReferenceById(eventId).getInitiator()!=userId){
            log.debug("user is not owner");
          return false;
        }
        return true;
    }

    @Override
    public int checkRequestIsAllowed(long userId, long eventId){
        userService.checkId(userId);
        checkEventId(eventId);
        Event event = eventRepository.getReferenceById(eventId);
        if (event.getInitiator()==userId) {
            log.debug("Owner cannot make requests");
            throw new ArgumentException("Owner cannot make requests");
        } else if (event.getState() != PUBLISHED) {
            log.debug("Cannot submit request unpublished event");
            throw new ArgumentException("Cannot submit request unpublished event");
        }
        return event.getParticipantLimit();
    }

        private static ArrayList<String> getListFromArray(long[] ids) {
        ArrayList<String> list = new ArrayList<>();
        for (long i : ids){
            list.add(String.valueOf(i));
        }
        return list;
    }

    @Override
    public void checkEventId(long id) {
        if (eventRepository.findById(id).isEmpty()) {
            log.debug("event with id " + id + " not found");
            throw new NotFoundException("event with id " + id + " not found");
        }
    }


}

