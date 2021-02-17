package ma.toto.search;

import javax.persistence.EntityManager;
import ma.toto.search.mappers.DefaultGenericMapper;
import ma.toto.search.mappers.GenericMapper;
import ma.toto.search.parser.ExpressionParser;
import ma.toto.search.repository.SearchRepository;
import ma.toto.search.service.SearchService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class provide the autoconfiguration need to bootstrap the search api
 *
 * @author Maniar Othmane
 */
@Configuration
@ConditionalOnClass({SearchService.class})
public class SearchAutoConfiguration {

  /**
   * If the search api user do not provide a SearchRepository this bean will be created and provided
   * in the users Spring Context
   *
   * @param em
   * @return SearchRepository
   */
  @Bean
  @ConditionalOnMissingBean
  public SearchRepository searchRepository(EntityManager em) {
    return new SearchRepository(em);
  }

  /**
   * If the search api user do not provide a ModelMapper this bean will be created and provided in
   * the users Spring Context
   *
   * @return ModelMapper
   */
  @Bean
  @ConditionalOnMissingBean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  /**
   * If the search api user do not provide a GenericMapper this bean will be created and provided in
   * the users Spring Context
   *
   * @return GenericMapper
   */
  @Bean
  @ConditionalOnMissingBean
  public GenericMapper genericMapper() {
    return new GlobalMapper();
  }

  /**
   * If the search api user do not provide a SearchCriteriasConfig this bean will be created and
   * provided in the users Spring Context
   *
   * @return SearchCriteriasConfig
   */
  @Bean
  @ConditionalOnMissingBean
  public SearchCriteriasConfig searchCriteriasConfig() {
    return new SearchCriteriasConfig();
  }

  /**
   * If the search api user do not provide a ContextFilter this bean will be created and provided in
   * the users Spring Context
   *
   * @return ContextFilter
   */
  @Bean
  @ConditionalOnMissingBean
  public ExpressionParser parser() {
    return new ExpressionParser();
  }

  /** This class represent the simplest Mapper to be used By the Search APi */
  class GlobalMapper extends DefaultGenericMapper<Object, Object> {}
}
