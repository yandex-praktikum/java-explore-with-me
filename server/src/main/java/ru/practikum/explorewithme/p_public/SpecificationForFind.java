package ru.practikum.explorewithme.p_public;

import org.springframework.data.jpa.domain.Specification;
import ru.practikum.explorewithme.model.Compilation;
import ru.practikum.explorewithme.model.Event;
import ru.practikum.explorewithme.model.EventStatus;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpecificationForFind {

    public static Specification<Event> statusFilter(EventStatus status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Event> statusFilter(List<EventStatus> status) {
        return (root, query, criteriaBuilder) -> {
            if (status.isEmpty()) return null;
            List<Predicate> predicates = new ArrayList<>();
            for (EventStatus st : status) {
                predicates.add(criteriaBuilder.equal(root.get("status"), st));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Event> usersFilter(Long[] ids) {
        return (root, query, criteriaBuilder) -> {
            if (ids.length == 0) return null;
            List<Predicate> predicates = new ArrayList<>();
            for (Long id : ids) {
                predicates.add(criteriaBuilder.equal(root.get("initiator"), id));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Event> categoriesFilter(Long[] ids) {
        return (root, query, criteriaBuilder) -> {
            if (ids.length == 0) return null;
            List<Predicate> predicates = new ArrayList<>();
            for (Long id : ids) {
                predicates.add(criteriaBuilder.equal(root.get("category"), id));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Event> rangeTimeFilter(String start, String end) {
        return (root, query, criteriaBuilder) -> {
            LocalDateTime rangeStart = null;
            LocalDateTime rangeEnd = null;
            if (!start.isEmpty())
                rangeStart = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if (!end.isEmpty())
                rangeEnd = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            if (rangeStart == null || rangeEnd == null) {
                rangeStart = LocalDateTime.now();
                return criteriaBuilder.greaterThan(root.get("eventDate"), rangeStart);
            }
            return criteriaBuilder.between(root.get("eventDate"), rangeStart, rangeEnd);
        };
    }

    public static Specification<Event> textFilter(String text) {
        String finalText = text.toLowerCase();
        return (root, query, criteriaBuilder) -> {
            if (finalText.isEmpty()) return null;
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("annotation")),
                    "%" + finalText + "%"));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                    "%" + finalText + "%"));
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Event> paidFilter(Optional<Boolean> paid) {
        if (paid.isEmpty()) return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("paid"), paid.get());
    }

    public static Specification<Compilation> pinnedFilter(Optional<Boolean> pinned) {
        if (pinned.isEmpty()) return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("pinned"), pinned.get());
    }

    public static Specification<Event> availableFilter(Boolean onlyAvailable) {
        if (onlyAvailable == null) return null;
        return (root, query, criteriaBuilder) -> {
            if (onlyAvailable)
                return criteriaBuilder.lessThan(root.get("confirmedRequests"), root.get("participantLimit"));
            return null;
        };
    }
}
