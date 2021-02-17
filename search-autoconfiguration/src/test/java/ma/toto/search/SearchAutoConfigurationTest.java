package ma.toto.search;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Function;
import javax.persistence.EntityManager;
import ma.toto.search.mappers.DefaultGenericMapper;
import ma.toto.search.repository.SearchRepository;
import ma.toto.search.service.SearchService;
import ma.toto.search.service.SearchServiceImpl;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class Test the search api autoconfiguration
 *
 * @author Maniar Othmane
 */
@SpringBootTest
class SearchAutoConfigurationTest {

  private final ApplicationContextRunner runner =
      new ApplicationContextRunner()
          .withConfiguration(AutoConfigurations.of(SearchAutoConfiguration.class));

  // TODO implements those tests
  @Test
  void given_emptyconfiguration_autoconf_should_popup() {
    runner
        .withUserConfiguration(EmptyConfiguration.class)
        .run(
            context -> {
              assertThat(context).hasBean("modelMapper");
              assertThat(context).hasBean("searchRepository");
              assertThat(context).hasBean("genericMapper");
            });
  }

  @Test
  void given_user_autoconf_should_backoff() {
    runner
        .withUserConfiguration(UserConfiguration.class)
        .run(
            context -> {
              assertThat(context).hasBean("userMapper");
              assertThat(context).hasBean("userSearchRepository");
              assertThat(context).hasBean("userModelMapper");
              assertThat(context).hasBean("userSearchService");
              assertThat(context).hasSingleBean(SearchRepository.class);
              assertThat(context).hasSingleBean(SearchCriteriasConfig.class);
              assertThat(context).hasSingleBean(SearchService.class);
              assertThat(context).hasSingleBean(ModelMapper.class);
            });
  }

  @Configuration
  @EnableAutoConfiguration
  static class EmptyConfiguration {}

  @Configuration
  @EnableAutoConfiguration
  static class UserConfiguration {

    class TestMapper extends DefaultGenericMapper<Object, Object> {}

    @Bean
    public TestMapper userMapper() {
      return new TestMapper();
    }

    @Bean
    public SearchRepository userSearchRepository(EntityManager em) {
      return new SearchRepository(em);
    }

    @Bean
    public SearchService userSearchService(SearchRepository searchRepository, TestMapper mapper) {
      return new SearchServiceImpl(searchRepository, mapper);
    }

    @Bean
    public SearchCriteriasConfig userSearchCriteriasConfig() {
      SearchCriteriasConfig searchCriteriasConfig = new SearchCriteriasConfig();
      return searchCriteriasConfig;
    }

    @Bean
    public ModelMapper userModelMapper() {
      return new ModelMapper();
    }

    @Bean
    public Function<String, Class> userConverter() {
      return s -> SearchAutoConfigurationTest.class;
    }
  }
}
