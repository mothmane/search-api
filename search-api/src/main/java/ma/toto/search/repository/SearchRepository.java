package ma.toto.search.repository;

import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import ma.toto.search.criterias.SearchCriteria;
import ma.toto.search.query.SearchJPAQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represent a generic Search Repository, it helps search on multiple object type
 * databases, using a Map<String,String> of params (filters)
 *
 * @author Maniar Othmane
 */
public class SearchRepository {

  private Logger logger = LoggerFactory.getLogger(SearchRepository.class);

  private EntityManager entityManager;

  /**
   * Instantiates a new Search repository.
   *
   * @param entityManager the entity manager
   */
  public SearchRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  /**
   * This method helps query for a list of the given klazz object having the criterias provided in
   * the params map object and deliver only the page desired
   *
   * @param <T> the type parameter
   * @param criterias the criteria list upon wich the search result will be filtered
   * @param userCriterias the user criterias
   * @param klazz the class upon witch the search will be applied
   * @param first the index of the page to be returned
   * @param numberOfElements the number of elements in the page to be returned
   * @return List<T> the domain objects list result of the search query
   */
  public <T> List<T> search(
      List<SearchCriteria> criterias,
      List<SearchCriteria> userCriterias,
      Class<T> klazz,
      int first,
      int numberOfElements) {
    logger.debug(
        "a search request is called using criterias : {} ,  asking for first element {}, and number of elements {}",
        criterias,
        first,
        numberOfElements);
    return SearchJPAQueryBuilder.buildSearchQueryFromListCriteria(
            klazz, criterias, userCriterias, entityManager)
        .setMaxResults(numberOfElements)
        .setFirstResult(first)
        .getResultList();
  }

  /**
   * Search ids list.
   *
   * @param <T> the type parameter
   * @param criterias the criterias
   * @param klazz the klazz
   * @param first the first
   * @param numberOfElements the number of elements
   * @return the list
   */
  public <T> List<Object> searchIds(
      List<SearchCriteria> criterias, Class<T> klazz, int first, int numberOfElements) {
    logger.debug(
        "a search request is called using criterias : {}   asking for first element {}, and number of elements {}",
        criterias,
        first,
        numberOfElements);
    return SearchJPAQueryBuilder.buildSearchQueryFromListCriteria(
            klazz, criterias, Collections.emptyList(), entityManager, true, false)
        .setMaxResults(numberOfElements)
        .setFirstResult(first)
        .getResultList();
  }

  /**
   * Count long.
   *
   * @param criterias the criterias
   * @param userCriterias the user criterias
   * @param klazz the klazz
   * @return the long
   */
  public Long count(
      List<SearchCriteria> criterias, List<SearchCriteria> userCriterias, Class<?> klazz) {
    logger.debug("a search request is called using criterias : {} ", criterias);
    return (Long)
        SearchJPAQueryBuilder.buildSearchQueryFromListCriteria(
                klazz, criterias, userCriterias, entityManager, false, true)
            .getSingleResult();
  }
}
