package ru.practicum.explore_with_me.ewm_stats_service.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.explore_with_me.ewm_stats_service.model.Hit;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class HitCustomRepositoryImpl implements HitCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<Hit> findAllByUri(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Hit> hitCriteriaQuery = criteriaBuilder.createQuery(Hit.class);
        Root<Hit> hitRoot = hitCriteriaQuery.from(Hit.class);

        Predicate criteria = criteriaBuilder.conjunction();

        Predicate predicateDateTime = criteriaBuilder.between(hitRoot.get("timestamp"), start, end);
        criteria = criteriaBuilder.and(criteria, predicateDateTime);

        if (uris != null && uris.length > 0) {
            Collection<String> uriCollection = Arrays.stream(uris)
                    .filter(s -> s != null && !s.isBlank())
                    .collect(Collectors.toList());
            Predicate predicateUri = criteriaBuilder.isTrue(hitRoot.get("uri").in(uriCollection));
            criteria = criteriaBuilder.and(criteria, predicateUri);
        }

        hitCriteriaQuery.where(criteria);

        if (unique) {
            hitCriteriaQuery.select(hitRoot.get("ip")).distinct(true);
        }

        TypedQuery<Hit> hitQuery = entityManager.createQuery(hitCriteriaQuery);
        return hitQuery.getResultList();
    }
}
