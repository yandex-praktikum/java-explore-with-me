package ru.explorewithme;

import org.springframework.stereotype.Service;
import ru.explorewithme.admin.categories.CategoryRepository;
import ru.explorewithme.admin.compilations.CompilationRepository;
import ru.explorewithme.admin.model.Category;
import ru.explorewithme.admin.model.Compilation;
import ru.explorewithme.admin.model.User;
import ru.explorewithme.admin.users.UserRepository;
import ru.explorewithme.exception.IdException;
import ru.explorewithme.users.events.EventRepository;
import ru.explorewithme.users.model.Event;
import ru.explorewithme.users.model.Request;
import ru.explorewithme.users.requests.RequestRepository;

@Service
public class IdService {
    private UserRepository userRepository;
    private EventRepository eventRepository;

    private CategoryRepository categoryRepository;

    private RequestRepository requestRepository;

    private CompilationRepository compilationRepository;

    public IdService(UserRepository userRepository,
                     EventRepository eventRepository,
                     CategoryRepository categoryRepository,
                     CompilationRepository compilationRepository,
                     RequestRepository requestRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.compilationRepository = compilationRepository;
        this.requestRepository = requestRepository;
    }

    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new IdException("no event with such id=" + eventId));
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IdException("no user with such id=" + userId));
    }

    public Category getCategoryById(Long catId) {
        return categoryRepository.findById(catId).orElseThrow(() -> new IdException("no category with such id=" + catId));
    }

    public Compilation getCompilationById(Long compId) {
        return compilationRepository.findById(compId).orElseThrow(() -> new IdException("no compilation with such id=" + compId));
    }

    public Request getRequestById(Long reqId) {
        return requestRepository.findById(reqId).orElseThrow(() -> new IdException("no request with such id=" + reqId));
    }
}
