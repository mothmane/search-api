package ma.toto.search.query;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import ma.toto.search.TestSearchConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TestSearchConfiguration.class})
@TestPropertySource(locations = "classpath:application-junit-test.properties")
@ActiveProfiles("junit-test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class SearchJPAQueryBuilderTest {

  @Autowired public EntityManager entityManager;

  @Test
  void injected_entityManager_is_not_null() {
    assertThat(entityManager).isNotNull();
  }
}
