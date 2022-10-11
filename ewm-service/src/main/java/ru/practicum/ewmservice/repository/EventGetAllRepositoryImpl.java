package ru.practicum.ewmservice.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewmservice.model.Category;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.model.enums.EventStatus;
import ru.practicum.ewmservice.model.enums.SortEnum;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class EventGetAllRepositoryImpl implements EventGetAllRepository {
    private final EntityManager em;

    @Override
    public List<Event> getAllEventsForAdm(List<Long> users,
                                          List<EventStatus> states,
                                          List<Long> categories,
                                          LocalDateTime rangeStart,
                                          LocalDateTime rangeEnd,
                                          int from, int size) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Event> cq = cb.createQuery(Event.class);
        Root<Event> root = cq.from(Event.class);
        List<Predicate> predicates = new ArrayList<>();

        if (users != null && !users.isEmpty()) {
            predicates.add(root.get("initiator").in(users));
        }

        if (states != null && !states.isEmpty()) {
            predicates.add(root.get("eventStatus").in(states));
        }

        if (categories != null && !categories.isEmpty()) {
            predicates.add(root.get("category").in(categories));
        }

        if (rangeStart != null) {
            predicates.add(cb.greaterThan(root.get("eventDate"), rangeStart));
        }

        if (rangeEnd != null) {
            predicates.add(cb.lessThan(root.get("eventDate"), rangeEnd));
        }

        if (rangeEnd == null && rangeStart == null) {
            predicates.add(cb.greaterThan(root.get("eventDate"), LocalDateTime.now()));
        }

        cq.where(predicates.toArray(new Predicate[]{}));

        return em.createQuery(cq).setFirstResult(from).setMaxResults(size).getResultList();
    }

    @Override
    public List<Event> getAllEvents(String text,
                                    List<Category> categories,
                                    Boolean paid,
                                    LocalDateTime rangeStart,
                                    LocalDateTime rangeEnd,
                                    Boolean onlyAvailable,
                                    SortEnum sort,
                                    int from,
                                    int size) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Event> cq = cb.createQuery(Event.class);
        Root<Event> root = cq.from(Event.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get("eventStatus"), EventStatus.PUBLISHED));

        if (text != null) {
            predicates.add(cb.or(cb.like(cb.upper(root.get("annotation")), "%" + text.toUpperCase() + "%"),
                    cb.like(cb.upper(root.get("description")), "%" + text.toUpperCase() + "%")));
        }

        if (categories != null && !categories.isEmpty()) {
            predicates.add(root.get("category").in(categories));
        }

        if (paid != null) {
            predicates.add(cb.equal(root.get("paid"), paid));
        }

        if (rangeStart != null) {
            predicates.add(cb.greaterThan(root.get("eventDate"), rangeStart));
        }

        if (rangeEnd != null) {
            predicates.add(cb.lessThan(root.get("eventDate"), rangeEnd));
        }

        if (rangeEnd == null && rangeStart == null) {
            predicates.add(cb.greaterThan(root.get("eventDate"), LocalDateTime.now()));
        }

        if (onlyAvailable != null && onlyAvailable) {
            predicates.add(cb.or(cb.le(root.get("confirmedRequest"), root.get("participantLimit")),
                    cb.le(root.get("participantLimit"), 0)));
        }

        if (sort == SortEnum.EVENT_DATE) {
            cq.orderBy(cb.desc(root.get("eventDate")));
        } else if (sort == SortEnum.VIEWS) {
            cq.orderBy(cb.desc(root.get("views")));
        }

        cq.where(predicates.toArray(new Predicate[]{}));

        return em.createQuery(cq).setFirstResult(from).setMaxResults(size).getResultList();
    }
}
