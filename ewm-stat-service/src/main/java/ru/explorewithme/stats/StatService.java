package ru.explorewithme.stats;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import ru.explorewithme.hit.EndPointHit;
import ru.explorewithme.hit.EndPointRepository;

import java.util.ArrayList;
import java.util.List;

import ru.explorewithme.hit.QEndPointHit;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Service
public class StatService {
    EndPointRepository endPointRepository;

    EntityManager entityManager;

    public StatService(EndPointRepository endPointRepository, EntityManager entityManager) {
        this.endPointRepository = endPointRepository;
        this.entityManager = entityManager;
    }

    public List<ViewStats> getStat(GetStatRequest req) {
        QEndPointHit endPointHit = QEndPointHit.endPointHit;
        List<BooleanExpression> conditions = new ArrayList<>();
        conditions.add(endPointHit.timestamp.between(req.getStart(), req.getEnd()));
        conditions.add(endPointHit.uri.in(req.getUris()));

        BooleanExpression finalCondition = conditions.stream()
                .reduce(BooleanExpression::and)
                .get();

        //EntityManager em = EntityManagerFactory.createEntityManager();
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        NumberPath<Long> count = Expressions.numberPath(Long.class, "c");

        List<Tuple> test = queryFactory.select(
            endPointHit.uri, endPointHit.id.count().as(count))
                .from(endPointHit)
                .where(finalCondition)
                .groupBy(endPointHit.uri)
                .fetch();

        List<ViewStats> stats = new ArrayList<>();
        for (Tuple t : test) {
            ViewStats viewStats = new ViewStats(
                    "ewm-main-service",
                    t.get(endPointHit.uri),
            t.get(endPointHit.id.count().as(count)));
            stats.add(viewStats);

        }
        return stats;
        //Iterable<EndPointHit> endPoints = endPointRepository;


        /*


List<Tuple> userTitleCounts = queryFactory.select(
  blogPost.title, blogPost.id.count().as(count))
  .from(blogPost)
  .groupBy(blogPost.title)
  .orderBy(count.desc())
  .fetch();



        for(EndPointHit i : endPoints) {
            ViewStats viewStats = new ViewStats(i.getApp(), i.getUri(), )
        }*/
    }
}
