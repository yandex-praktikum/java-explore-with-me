package explorewithme.ewm.compilation;

import explorewithme.ewm.compilation.dto.CompilationDto;
import explorewithme.ewm.compilation.dto.NewCompilationDto;
import explorewithme.ewm.compilation.mappers.CompilationMapper;
import explorewithme.ewm.events.dto.EventShortDto;
import explorewithme.ewm.events.service.EventService;
import explorewithme.ewm.exception.ArgumentException;
import explorewithme.ewm.exception.NotFoundException;
import explorewithme.ewm.util.OffsetBasedPageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompilationServiceImp implements CompilationService {

    private final CompilationRepository repository;
    private final CompEventRepository compEventRepository;

    private final EventService eventService;


    @Override
    @Transactional
    public List<CompilationDto> getComilations(int from, int size, boolean pinned) {
        Pageable pageable = new OffsetBasedPageRequest(size, from, Sort.by(Sort.DEFAULT_DIRECTION, "id"));
        List<Compilation> compilations = repository.getCompilationsByPinnedEquals(pinned, pageable);
        log.debug("Get compilations from repository: pinned " + pinned + ", from " + from + " size " + size);
        return compilations.stream()
                .map(this::addEventsShort)
                .collect(toList());
    }

    @Override
    public CompilationDto getComilationById(long compId) {
        checkId(compId);
        Compilation compilation = repository.getReferenceById(compId);
        return addEventsShort(compilation);
    }

    @Override
    @Transactional
    public CompilationDto create(NewCompilationDto newCompilationDto) {
        Compilation compilation = CompilationMapper.fromNewCompilationDto(newCompilationDto);
        log.debug("Asking Compilation repo to save compilation");
        Compilation compSaved = repository.save(compilation);
        log.debug("Asking EventCompilation repo to save comilation and events in for-cycle");
            for (int i = 0; i < newCompilationDto.getEvents().size(); i++) {
                CompilationEvent compEvent = new CompilationEvent(compSaved.getId(),newCompilationDto.getEvents().get(i));
                compEventRepository.save(compEvent);
            }
        log.debug("Calling method to add events");
            CompilationDto toReturn = addEventsShort(compSaved);
        return toReturn;
    }

    private CompilationDto addEventsShort(Compilation compilation){

        log.debug("Getting id from comilation, asking EventService to add short dto for events");
        List<EventShortDto> events = compEventRepository.getEventsByIdEquals(compilation.getId()).stream()
                .map(id -> eventService.getEventByIdShort(id))
                .collect(toList());
        CompilationDto toReturn = CompilationMapper.fromCompilation(compilation);
        log.debug("Adding events to compiltaion dto");
        toReturn.setEvents(events);
        return toReturn;
    }


    public void checkId(long id) {
        if (repository.findById(id).isEmpty()){
            log.debug("compilation with id "+ id + " not found");
            throw new NotFoundException("compilation with id "+ id + " not found");
        }
    }

    @Override
    @Transactional
    public void delete(long compId) {
        checkId(compId);
        if(compEventRepository.countCompilationEventByIdEquals(compId)==0) {
            log.debug("Asking Compilation repo to delete empty compilation " + compId);
            repository.delete(repository.getReferenceById(compId));
        } else {
            log.debug("Cannot delete not empty compilation");
            throw new ArgumentException("Compilation is not empty. Cannot delete");
        }
    }

    @Override
    @Transactional
    public void deleteEventFromCompilation(long compId, long eventId) {
        if (compEventRepository.countCompilationEventByIdEqualsAndAndEventEquals(compId, eventId) == 0){
            log.debug("Event " + eventId + " or compilation " + compId + " not found");
            throw new NotFoundException("No such compilation, or no such event in it");
        }
        log.debug("Asking CompilationEvent repository to delete event " + eventId + " from compilation " +  compId);
            compEventRepository.delete(compEventRepository.getCompilationEventByIdEqualsAndAndEventEquals(compId, eventId));
    }

    @Override
    @Transactional
    public void addEventToCompilation(long compId, long eventId) {
        checkId(compId);
        eventService.checkEventId(eventId);
        if (compEventRepository.countCompilationEventByIdEqualsAndAndEventEquals(compId, eventId) != 0){
            log.debug("Event " + eventId + " in compilation " + compId + " already exists");
            throw new ArgumentException("Event" + eventId + " is already in" + compId + " compilation");
        }
        log.debug("Asking CompilationEvent repository to save event " + eventId + " to compilation " +  compId);
        compEventRepository.save(new CompilationEvent(compId,eventId));
    }

    @Override
    @Transactional
    public void unpinCompilation(long compId) {
        checkId(compId);
        if (!repository.getReferenceById(compId).isPinned()){
            log.debug("Compilation " + compId + "already unpinned");
            throw new NotFoundException("Already unpinned, no action to be performed");
        } else {
            log.debug("Asking Compilation repo to set compilation" + compId + "as pinned");
            repository.setCompilationPinned(false, compId);
        }
    }

    @Override
    @Transactional
    public void pinCompilation(long compId) {
        checkId(compId);
        if (repository.getReferenceById(compId).isPinned()){
            log.debug("Compilation " + compId + "already pinned");
            throw new NotFoundException("Already pinned");
        } else {
            log.debug("Asking Compilation repo to set compilation" + compId + "as unpinned");
            repository.setCompilationPinned(true, compId);
        }
    }
}
