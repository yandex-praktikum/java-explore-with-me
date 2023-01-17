package ru.practicum.explore_with_me.ewm_main_service.event.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.explore_with_me.ewm_main_service.event.model.Event;
import ru.practicum.explore_with_me.ewm_main_service.event.model.EventStateEnum;
import ru.practicum.explore_with_me.ewm_main_service.event.model.FilterCollection;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventCustomRepositoryImpl implements EventCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<Event> searchEvents(FilterCollection filter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> criteriaQuery = criteriaBuilder.createQuery(Event.class);
        Root<Event> eventRoot = criteriaQuery.from(Event.class);

        List<Predicate> textPredicate = new ArrayList<>();

        if (filter.getText() != null && !filter.getText().isEmpty()
                && !filter.getText().isBlank()) {
            textPredicate.add(criteriaBuilder.like(criteriaBuilder.lower(eventRoot.get("annotation")), "%" +
                    filter.getText().toLowerCase() + "%"));
            textPredicate.add(criteriaBuilder.like(criteriaBuilder.lower(eventRoot.get("description")), "%" +
                    filter.getText().toLowerCase() + "%"));
        }

        List<Predicate> filterPredicates = new ArrayList<>();
        if (filter.getCategories() != null && filter.getCategories().length != 0) {
            filterPredicates.add(criteriaBuilder.isTrue(eventRoot.get("category").in(filter.getCategories())));
        }

        if (filter.getRangeStart() != null && filter.getRangeEnd() != null) {
            filterPredicates.add(criteriaBuilder.between(eventRoot.get("eventDate"), filter.getRangeStart(),
                    filter.getRangeEnd()));
        } else {
            filterPredicates.add(criteriaBuilder.greaterThan(eventRoot.get("eventDate"),
                    criteriaBuilder.currentTimestamp()));
        }
        filterPredicates.add(criteriaBuilder.equal(eventRoot.get("paid"), filter.getPaid()));

        if (filter.isOnlyAvailable()) {
            filterPredicates.add(criteriaBuilder.equal(eventRoot.get("isAvailable"), true));
        }
        criteriaQuery.select(eventRoot).where(criteriaBuilder.or(textPredicate.toArray((new Predicate[]{}))),
                criteriaBuilder.and(filterPredicates.toArray(new Predicate[]{})));

        TypedQuery<Event> typedQuery = entityManager.createQuery(criteriaQuery);

        typedQuery.setFirstResult(filter.getFrom());
        typedQuery.setMaxResults(filter.getFrom() + filter.getSize());

        return typedQuery.getResultList();
    }

    @Override
    public List<Event> findEvents(Long[] users, EventStateEnum[] states, Long[] categories, LocalDateTime rangeStart,
                                  LocalDateTime rangeEnd, int from, int size) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = cb.createQuery(Event.class);
        Root<Event> eventRoot = query.from(Event.class);
        List<Predicate> filterPredicates = new ArrayList<>();
        if (users != null && users.length != 0) {
            filterPredicates.add(cb.isTrue(eventRoot.get("initiator").in(users)));
        }
        if (states != null && states.length != 0) {
            filterPredicates.add(cb.isTrue(eventRoot.get("state").in(states)));
        }
        if (categories != null && categories.length != 0) {
            filterPredicates.add(cb.isTrue(eventRoot.get("category").in(categories)));
        }
        if (rangeStart != null && rangeEnd != null) {
            filterPredicates.add(cb.between(eventRoot.get("eventDate"), rangeStart, rangeEnd));
        } else {
            filterPredicates.add(cb.greaterThan(eventRoot.get("eventDate"), cb.currentTimestamp()));
        }
        query.select(eventRoot).where(cb.and(filterPredicates.toArray(new Predicate[]{})));

        TypedQuery<Event> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(from);
        typedQuery.setMaxResults(size);
        return typedQuery.getResultList();
    }
}
