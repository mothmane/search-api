package ma.toto.search.service;

import static ma.toto.search.helpers.entities.ExampleEntity.createExampleEntity;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import java.util.List;
import ma.toto.search.ServiceTestSearchConfiguration;
import ma.toto.search.helpers.entities.ExampleEntity;
import ma.toto.search.repository.SearchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ServiceTestSearchConfiguration.class})
@ActiveProfiles("junit-test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class SearchServiceTest {

  @Autowired SearchService searchService;

  @MockBean SearchRepository searchRepository;

  List<ExampleEntity> repositoryList;

  @BeforeEach
  void setMockOutput() {
    repositoryList =
        List.of(
            createExampleEntity(1L, "nom01", "CODE01", true, 2001),
            createExampleEntity(3L, "nom03", "CODE03", true, 2003));
    when(searchRepository.search(
            any(), any(), ArgumentMatchers.eq(ExampleEntity.class), anyInt(), anyInt()))
        .thenReturn(repositoryList);
  }
}
