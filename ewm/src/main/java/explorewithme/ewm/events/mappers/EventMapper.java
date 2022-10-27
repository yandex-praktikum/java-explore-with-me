package explorewithme.ewm.events.mappers;

import explorewithme.ewm.events.admin.AdminUpdateEventRequest;
import explorewithme.ewm.events.admin.UpdateEventRequest;
import explorewithme.ewm.events.dto.EventFullDto;
import explorewithme.ewm.events.dto.EventShortDto;
import explorewithme.ewm.events.dto.NewEventDto;
import explorewithme.ewm.events.model.Event;
import explorewithme.ewm.events.model.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventMapper {


        public static Event fromNewEventDto (NewEventDto eventDto){
            log.debug("Mapping from newDto to event");
            Event event = new Event(
                    eventDto.getTitle(),
                    eventDto.getAnnotation(),
                    eventDto.getDescription(),
                    eventDto.getCategory(),
                    eventDto.getInitiator(),
                    eventDto.getEventDate()
            );
            event.setLat(eventDto.getLocation().getLat());
            event.setLon(eventDto.getLocation().getLon());
            if (eventDto.getParticipantLimit() != 0) event.setParticipantLimit(eventDto.getParticipantLimit());
            if (!eventDto.isRequestModeration()) { event.setRequestModeration(true);}
            if (eventDto.isPaid()) {event.setPaid(true);}

            return event;
        }

        public static Event fromUpdateEventRequest (UpdateEventRequest updateEventRequest, Event existingEvent){
            log.debug("Mapping from updateDto to event");
            if (updateEventRequest.getAnnotation() !=null) {existingEvent.setAnnotation(updateEventRequest.getAnnotation());}
            if (updateEventRequest.getCategory() != 0) {existingEvent.setAnnotation(updateEventRequest.getAnnotation());}
            if (updateEventRequest.getDescription() !=null) {existingEvent.setDescription(updateEventRequest.getDescription());}
            if (updateEventRequest.getEventDate() !=null) {existingEvent.setEventDate(updateEventRequest.getEventDate());}
            if (updateEventRequest.isPaid()) { existingEvent.setPaid(true);}
            if (updateEventRequest.getParticipantLimit()!=existingEvent.getParticipantLimit())
            {existingEvent.setParticipantLimit(updateEventRequest.getParticipantLimit());}
            if (updateEventRequest.getTitle()!=null){existingEvent.setTitle(updateEventRequest.getTitle());}
            existingEvent.setRequestModeration(true);
            return existingEvent;
        }

    public static Event fromAdminUpdateEventRequest (AdminUpdateEventRequest updateEventRequest, Event existingEvent){
        log.debug("Mapping from admin updateDto to event");
        if (updateEventRequest.getAnnotation() !=null) {existingEvent.setAnnotation(updateEventRequest.getAnnotation());}
        if (updateEventRequest.getCategory() != 0) {existingEvent.setAnnotation(updateEventRequest.getAnnotation());}
        if (updateEventRequest.getDescription() !=null) {existingEvent.setDescription(updateEventRequest.getDescription());}
        if (updateEventRequest.getEventDate() !=null) {existingEvent.setEventDate(updateEventRequest.getEventDate());}
        if (updateEventRequest.isPaid()) { existingEvent.setPaid(true);}
        if (updateEventRequest.getParticipantLimit()!=existingEvent.getParticipantLimit())
        {existingEvent.setParticipantLimit(updateEventRequest.getParticipantLimit());}
        if (updateEventRequest.getTitle()!=null){existingEvent.setTitle(updateEventRequest.getTitle());}
        if (updateEventRequest.isRequestModeration() != existingEvent.isRequestModeration())
        {existingEvent.setRequestModeration(updateEventRequest.isRequestModeration());}
        return existingEvent;
    }

    public static EventFullDto fromEvent (Event event){
        log.debug("Mapping from event to dto");
        EventFullDto eventFullDto = new EventFullDto();
        eventFullDto.setAnnotation(event.getAnnotation());
        eventFullDto.setDescription(event.getDescription());
        eventFullDto.setCreatedOn(event.getCreated());
        eventFullDto.setEventDate(event.getEventDate());
        eventFullDto.setId(event.getId());
        eventFullDto.setPaid(event.isPaid());
        eventFullDto.setParticipantLimit( event.getParticipantLimit());
        eventFullDto.setRequestModeration(eventFullDto.isRequestModeration());
        eventFullDto.setTitle( event.getTitle());
        eventFullDto.setState( event.getState());
        eventFullDto.setLocation(new Location(event.getLat(), event.getLon()));
        eventFullDto.setViews(eventFullDto.getViews());
        return eventFullDto;
    }

    public static EventShortDto fromEventShort (Event event){
        log.debug("Mapping from event to short dto");
        EventShortDto eventShortDto = new EventShortDto();
        eventShortDto.setEventDate(event.getEventDate());
        eventShortDto.setViews(event.getViews());
        eventShortDto.setAnnotation(event.getAnnotation());
        eventShortDto.setId(event.getId());
        eventShortDto.setPaid(event.isPaid());
        eventShortDto.setTitle(event.getTitle());
        return eventShortDto;
    }


    }
