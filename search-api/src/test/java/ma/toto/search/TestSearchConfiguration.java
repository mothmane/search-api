package ma.toto.search;

import java.util.function.Function;
import javax.persistence.EntityManager;
import ma.toto.search.helpers.ExampleEntityMapper;
import ma.toto.search.helpers.entities.ExampleEntity;
import ma.toto.search.parser.ExpressionParser;
import ma.toto.search.repository.SearchRepository;
import ma.toto.search.service.SearchService;
import ma.toto.search.service.SearchServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TestSearchConfiguration {

  @Bean
  public ExampleEntityMapper mapper() {
    return new ExampleEntityMapper();
  }

  @Bean
  public SearchRepository searchRepository(EntityManager em) {
    return new SearchRepository(em);
  }

  @Bean
  public SearchService searchService(
      SearchRepository searchRepository, ExampleEntityMapper mapper) {
    return new SearchServiceImpl(searchRepository, mapper);
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public Function<String, Class> converter() {
    return s -> ExampleEntity.class;
  }

  @Bean
  public SearchCriteriasConfig searchCriteriasConfig() {
    return new SearchCriteriasConfig();
  }

  @Bean
  public ExpressionParser parser() {
    return new ExpressionParser();
  }
}
