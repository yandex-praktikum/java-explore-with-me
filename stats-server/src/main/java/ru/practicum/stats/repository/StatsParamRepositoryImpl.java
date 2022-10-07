package ru.practicum.stats.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.stats.model.Hit;
import ru.practicum.stats.model.ViewStats;

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
public class StatsParamRepositoryImpl implements StatsParamRepository {
    private final EntityManager em;

    @Override
    public List<ViewStats> findStatsWithParam(LocalDateTime start,
                                              LocalDateTime end,
                                              List<String> uris,
                                              Boolean unique) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ViewStats> cq = cb.createQuery(ViewStats.class);
        Root<Hit> root = cq.from(Hit.class);
        List<Predicate> predicates = new ArrayList<>();

        cq.select(cb.construct(ViewStats.class, root.get("app"), root.get("uri"),
                unique ? cb.countDistinct(root.get("ip")) : cb.count(root.get("ip"))));

        if (uris != null && uris.size() > 0) {
            predicates.add(root.get("uri").in(uris));
        }

        predicates.add(cb.between(root.get("timestamp"), start, end));
        cq.where(predicates.toArray(new Predicate[]{}));
        return em.createQuery(cq).getResultList();
    }
}
