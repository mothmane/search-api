package ma.toto.search.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import ma.toto.search.criterias.SearchCriteria;
import ma.toto.search.mappers.GenericMapper;
import ma.toto.search.parser.ExpressionParser;
import ma.toto.search.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** @author Maniar Othmane */
public class SearchController<MAPPER extends GenericMapper> {

  private final Logger logger = LoggerFactory.getLogger(SearchController.class);

  public static final String OBJECT = "object";
  public static final String EXPRESSION = "expression";

  @Autowired private SearchService searchService;

  @Autowired private Function<String, Class<?>> stringToClassConverter;

  @Autowired private ExpressionParser parser;

  /**
   * @param object the object to be searched
   * @param expression the search expression
   * @param first the index of the first element
   * @param count the total number of elements to be returned
   * @param <T>
   * @return a list of result
   */
  @GetMapping("/advanced-search")
  public <T> List<T> advancedSearch(
      @RequestParam(value = OBJECT, required = true) String object,
      @RequestParam(value = EXPRESSION, required = true) String expression,
      @RequestParam int first,
      @RequestParam int count) {

    logger.debug(
        "/advanced-search is called with object {}, expression {}, first {}, count {}",
        object,
        expression,
        first,
        count);

    Class klazz = stringToClassConverter.apply(object);

    List<SearchCriteria> criterias = parser.parse(expression);
    if (!criterias.isEmpty()) {
      return this.searchService.search(criterias, Collections.emptyList(), klazz, first, count);
    }
    return new ArrayList<>();
  }

  /**
   * @param object the object to be searched
   * @param expression the esearch expression
   * @param <T>
   * @return
   */
  @GetMapping("/advanced-search-ids")
  public <T> List<T> advancedSearchIds(
      @RequestParam(value = OBJECT, required = true) String object,
      @RequestParam(value = EXPRESSION, required = true) String expression) {

    logger.debug(
        "/advanced-search-ids is called with object {}, expression {}", object, expression);

    Class klazz = stringToClassConverter.apply(object);

    List<SearchCriteria> criterias = parser.parse(expression);
    if (!criterias.isEmpty()) {

      return this.searchService.searchIds(criterias, klazz);
    }
    return new ArrayList<>();
  }

  /**
   * @param object the object to be searched
   * @param expression the esearch expression
   * @return the count of search results
   */
  @GetMapping("/advanced-search/count")
  public Long count(
      @RequestParam(value = OBJECT, required = true) String object,
      @RequestParam(value = EXPRESSION, required = true) String expression) {
    logger.debug(
        "/advanced-search/count is called with object {}, expression {}", object, expression);

    Class<?> klazz = stringToClassConverter.apply(object);

    List<SearchCriteria> criterias = parser.parse(expression);
    if (!criterias.isEmpty()) {
      return this.searchService.count(criterias, Collections.emptyList(), klazz);
    }
    return 0L;
  }
}
