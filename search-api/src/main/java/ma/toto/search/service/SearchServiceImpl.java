package ma.toto.search.service;

import java.util.List;
import ma.toto.search.criterias.SearchCriteria;
import ma.toto.search.mappers.GenericMapper;
import ma.toto.search.repository.SearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** {@inheritDoc} */
public class SearchServiceImpl<MAPPER extends GenericMapper> implements SearchService {

  private Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

  private static final int FILTERING_FIRST = 0;
  private static final int FILTERING_RESULT_MAX_COUNT = 10000;

  private SearchRepository searchRepository;
  private MAPPER mapper;

  public SearchServiceImpl(SearchRepository searchRepository, MAPPER mapper) {
    this.searchRepository = searchRepository;
    this.mapper = mapper;
  }

  @Override
  public <T, U> List<U> search(
      List<SearchCriteria> criterias,
      List<SearchCriteria> userCriterias,
      Class<T> klazz,
      int first,
      int numberOfElements) {
    logger.debug(
        "a search request is called using criterias : {} asking for first element {}, and number of elements {}",
        criterias,
        first,
        numberOfElements);
    return mapper.toDTOs(
        searchRepository.search(criterias, userCriterias, klazz, first, numberOfElements));
  }

  @Override
  public <T> List<Object> searchIds(List<SearchCriteria> criterias, Class<T> klazz) {
    logger.debug(
        "a search ID request is called using ciretrias : {} asking for first element {}, and number of elements {}",
        criterias,
        FILTERING_FIRST,
        FILTERING_RESULT_MAX_COUNT);
    return searchRepository.searchIds(
        criterias, klazz, FILTERING_FIRST, FILTERING_RESULT_MAX_COUNT);
  }

  @Override
  public Long count(
      List<SearchCriteria> criterias, List<SearchCriteria> userCriterias, Class klazz) {
    logger.debug(
        "a count request is called using ciretrias : {} and user criterias {}",
        criterias,
        userCriterias);
    return searchRepository.count(criterias, userCriterias, klazz);
  }
}
