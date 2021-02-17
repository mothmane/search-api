package ma.toto.search.query;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import ma.toto.search.criterias.SearchCriteria;
import ma.toto.search.criterias.SearchQueryCriteriaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** The type Search jpa query builder. */
public class SearchJPAQueryBuilder {

  private static Logger logger = LoggerFactory.getLogger(SearchJPAQueryBuilder.class);

  private static final String ID_NAME = "id";

  private SearchJPAQueryBuilder() {}

  /**
   * Build search query from list criteria query.
   *
   * @param klazz the klazz
   * @param criterias the criterias
   * @param userCriterias the user criterias
   * @param em the em
   * @return query
   */
  public static Query buildSearchQueryFromListCriteria(
      Class<?> klazz,
      List<SearchCriteria> criterias,
      List<SearchCriteria> userCriterias,
      EntityManager em) {
    logger.debug(
        "a query is beeing created for list citerias   {}, user criterias {}",
        criterias,
        userCriterias);

    return buildSearchQueryFromListCriteria(klazz, criterias, userCriterias, em, false, false);
  }

  /**
   * Build search query from list criteria query.
   *
   * @param klazz the klazz
   * @param criterias the criterias
   * @param userCriterias the user criterias
   * @param em the em
   * @param onlyId the only id
   * @param count the count
   * @return query
   */
  public static Query buildSearchQueryFromListCriteria(
      Class<?> klazz,
      List<SearchCriteria> criterias,
      List<SearchCriteria> userCriterias,
      EntityManager em,
      boolean onlyId,
      boolean count) {
    logger.debug(
        "a query is beeing created for list citerias   {},  user criterias {}",
        criterias,
        userCriterias);
    CriteriaBuilder builder = em.getCriteriaBuilder();

    CriteriaQuery criteriaQuery;

    if (count) {
      criteriaQuery = builder.createQuery(Long.class);
    } else {
      criteriaQuery = builder.createQuery(klazz).distinct(true);
    }
    Root<?> r = criteriaQuery.from(klazz);
    if (onlyId) {
      criteriaQuery.select(r.get(ID_NAME)).distinct(true);
    }
    if (count) {
      criteriaQuery.select(builder.countDistinct(r));
    }

    Predicate predicate =
        buildPredicate(criterias, builder, r, builder::and, builder.conjunction());

    Predicate userPredicate =
        buildPredicate(
            userCriterias,
            builder,
            r,
            builder::or,
            (!userCriterias.isEmpty() ? builder.disjunction() : builder.conjunction()));

    criteriaQuery.where(builder.and(predicate, userPredicate));

    return em.createQuery(criteriaQuery);
  }

  private static Predicate buildPredicate(
      List<SearchCriteria> criterias,
      CriteriaBuilder builder,
      Root r,
      Aggregate aggregate,
      Predicate predicate) {
    SearchQueryCriteriaConsumer searchConsumer =
        new SearchQueryCriteriaConsumer(predicate, builder, r, aggregate);

    criterias.stream().forEach(searchConsumer);
    return searchConsumer.getPredicate();
  }
}
