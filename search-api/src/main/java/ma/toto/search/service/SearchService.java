package ma.toto.search.service;

import java.util.List;
import ma.toto.search.criterias.SearchCriteria;

/**
 * This interface define the service that should be exposed by the search api a simple search and a
 * paginated search
 *
 * @author Maniar Othmane
 */
public interface SearchService {

  /**
   * This method helps query for a list of the given klazz object having the criterias provided in
   * the the criterias object and deliver only the page desired
   *
   * @param criterias the criterias upon wich the search result will be filtered
   * @param klazz the class upon witch the search will be applied
   * @param first the index of the page to be returned
   * @param numberOfElements the number of elements in the page to be returned
   * @param <T,U>
   * @return List<U> the dto objects list result of the search query
   */
  <T, U> List<U> search(
      List<SearchCriteria> criterias,
      List<SearchCriteria> userCriterias,
      Class<T> klazz,
      int first,
      int numberOfElements);

  /**
   * This method helps query for a list of ids having the criterias provided in the list criterias
   * object and deliver only the page desired
   *
   * @param criterias the criterias upon wich the search result will be filtered
   * @return ID the dto objects list result of the search query
   */
  <T> List<Object> searchIds(List<SearchCriteria> criterias, Class<T> klazz);

  /**
   * This method helps query for a list of the given klazz object having the criterias provided in
   * the the criterias object and deliver only the page desired
   *
   * @param criterias the criterias upon wich the search result will be filtered
   * @param klazz the class upon witch the search will be applied
   * @return List<U> the dto objects list result of the search query
   */
  Long count(List<SearchCriteria> criterias, List<SearchCriteria> userCriterias, Class klazz);
}
