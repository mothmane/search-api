package ma.toto.search;

import java.util.function.Function;
import ma.toto.search.helpers.ExampleEntityMapper;
import ma.toto.search.helpers.entities.ExampleEntity;
import ma.toto.search.parser.ExpressionParser;
import ma.toto.search.repository.SearchRepository;
import ma.toto.search.service.SearchService;
import ma.toto.search.service.SearchServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceTestSearchConfiguration {

  @Bean
  public ExampleEntityMapper mapper() {
    return new ExampleEntityMapper();
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
    var scc = new SearchCriteriasConfig();
    scc.put("dossier_f01", "stakeholder", "accountId");
    scc.put("dossier_f02", "stakeholder", "accountId");
    scc.put("client_f05", "company", "companyId");
    return scc;
  }

  @Bean
  public ExpressionParser parser() {
    return new ExpressionParser();
  }
}
