package ma.toto.search.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ma.toto.search.TestSearchConfiguration;
import ma.toto.search.criterias.Operation;
import ma.toto.search.criterias.SearchCriteria;
import ma.toto.search.helpers.entities.ExampleEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@ContextConfiguration(classes = {TestSearchConfiguration.class})
@TestPropertySource(locations = "classpath:application-junit-test.properties")
@ActiveProfiles("junit-test")
@DataJpaTest
@Sql({"/truncate.sql", "/script-data.sql"})
class SearchRepositoryTest {

  @Autowired private SearchRepository searchRepository;

  @Test
  void search_repository_is_correctly_injected() {

    assertThat(searchRepository).isNotNull();
  }

  @Test
  void
      given_a_class_and_a_simple_equal_criteria_list_should_excute_query_and_get_2_elemets_starting_from_2() {
    // given
    var criterias = List.of(new SearchCriteria("same", Operation.EQUALS, "SAME"));

    // when
    List<ExampleEntity> actual =
        searchRepository.search(criterias, Collections.emptyList(), ExampleEntity.class, 2, 2);

    // then
    var e01 = ExampleEntity.createExampleEntity(3L, "nom03", "CODE03", true, 2003);
    var e02 = ExampleEntity.createExampleEntity(4L, "nom04", "CODE04", false, 2004);

    assertThat(actual).isNotNull();
    assertThat(actual).hasSize(2);
    assertThat(actual).containsExactlyInAnyOrder(e01, e02);
  }

  @Test
  void
      given_a_class_and_a_simple_like_criteria_list_should_excute_query_and_get_second_page_with_2_elements() {
    // given
    var criterias = List.of(new SearchCriteria("code", Operation.LIKE, "DE"));

    // when
    List<ExampleEntity> actual =
        searchRepository.search(criterias, Collections.emptyList(), ExampleEntity.class, 2, 2);

    // then
    var e01 = ExampleEntity.createExampleEntity(3L, "nom03", "CODE03", true, 2003);
    var e02 = ExampleEntity.createExampleEntity(4L, "nom04", "CODE04", false, 2004);

    assertThat(actual).isNotNull();
    assertThat(actual).containsExactlyInAnyOrder(e01, e02);
  }

  @Test
  void
      given_a_class_and_a_simple_higher_than_criteria_list_should_excute_query_and_get_first_page_with_4_elements() {
    // given
    var criterias = List.of(new SearchCriteria("id", Operation.HIGHER_OR_EQUAL_THAN, 3));

    // when
    List<ExampleEntity> actual =
        searchRepository.search(criterias, Collections.emptyList(), ExampleEntity.class, 0, 4);

    // then
    var e03 = ExampleEntity.createExampleEntity(3L, "nom03", "CODE03", true, 2003);
    var e04 = ExampleEntity.createExampleEntity(4L, "nom04", "CODE04", false, 2004);

    assertThat(actual).isNotNull();
    assertThat(actual).containsExactlyInAnyOrder(e03, e04);
  }

  @Test
  void
      given_a_class_and_a_simple_less_than_criteria_list_should_excute_query_and_get_first_page_with_4_elements() {
    // given
    var criterias = List.of(new SearchCriteria("id", Operation.LESS_OR_EQUAL_THAN, 2));

    // when
    List<ExampleEntity> actual =
        searchRepository.search(criterias, Collections.emptyList(), ExampleEntity.class, 0, 4);

    // then
    var e01 = ExampleEntity.createExampleEntity(1L, "nom01", "CODE01", true, 2001);
    var e02 = ExampleEntity.createExampleEntity(2L, "nom02", "CODE02", false, 2002);

    assertThat(actual).isNotNull();
    assertThat(actual).containsExactlyInAnyOrder(e01, e02);
  }

  @Test
  void
      given_a_class_and_a_simple_criteria_list_containing_a_relation_entity_attribute_should_excute_query_and_get_first_page_with_4_elements() {
    // given
    var criterias =
        List.of(
            new SearchCriteria("id", Operation.LESS_OR_EQUAL_THAN, 2),
            new SearchCriteria("relations.name", Operation.EQUALS, "r-name01"));

    // when
    List<ExampleEntity> actual =
        searchRepository.search(criterias, Collections.emptyList(), ExampleEntity.class, 0, 4);

    // then
    var e01 = ExampleEntity.createExampleEntity(1L, "nom01", "CODE01", true, 2001);

    assertThat(actual).isNotNull();
    assertThat(actual).contains(e01);
  }

  @Test
  void
      given_a_class_and_a_simple_criteria_list_containing_a_relation_entity_attribute_should_excute_query_and_get_0_elements() {
    // given
    var criterias =
        List.of(
            new SearchCriteria("id", Operation.LESS_OR_EQUAL_THAN, 1),
            new SearchCriteria("relations.name", Operation.EQUALS, "r-name05"));

    // when
    List<ExampleEntity> actual =
        searchRepository.search(criterias, Collections.emptyList(), ExampleEntity.class, 0, 4);

    // then
    var e01 = ExampleEntity.createExampleEntity(1L, "nom01", "CODE01", true, 2001);

    assertThat(actual).isNotNull();
    assertThat(actual).isEmpty();
  }

  @Test
  void
      given_a_class_and_a_simple_IN__clause_criteria_should_excute_query_and_get_first_page_with_4_elements() {
    // given
    var criterias = List.of(new SearchCriteria("code", Operation.IN, "CODE01-CODE02"));

    // when
    List<ExampleEntity> actual =
        searchRepository.search(criterias, Collections.emptyList(), ExampleEntity.class, 0, 4);

    // then
    var e01 = ExampleEntity.createExampleEntity(1L, "nom01", "CODE01", true, 2001);
    var e02 = ExampleEntity.createExampleEntity(2L, "nom02", "CODE02", false, 2002);

    assertThat(actual).isNotNull();
    assertThat(actual).containsExactlyInAnyOrder(e01, e02);
  }

  @Test
  void
      given_a_class_and_a_3_elements_query_containing_IN__clause_criteria_should_excute_query_and_get_first_page_with_4_elements() {
    // given "code:-'OD',same:'SAME',name||nom03;nom04"
    var criterias =
        List.of(
            new SearchCriteria("code", Operation.LIKE, "OD"),
            new SearchCriteria("same", Operation.EQUALS, "SAME"),
            new SearchCriteria("name", Operation.IN, "nom03-nom04"));

    // when
    List<ExampleEntity> actual =
        searchRepository.search(criterias, Collections.emptyList(), ExampleEntity.class, 0, 4);

    // then
    var e03 = ExampleEntity.createExampleEntity(3L, "nom03", "CODE03", true, 2003);
    var e04 = ExampleEntity.createExampleEntity(4L, "nom04", "CODE04", false, 2004);

    assertThat(actual).isNotNull();
    assertThat(actual).containsExactlyInAnyOrder(e03, e04);
  }

  @Test
  void given_a_class_and_a__query_containing_IN_clause_criteria_should_return_correct_ids() {
    // given "code:-'OD',same:'SAME',name||nom03;nom04"
    var criterias = List.of(new SearchCriteria("name", Operation.IN, "nom03-nom04"));

    // when
    List<Object> actual = searchRepository.searchIds(criterias, ExampleEntity.class, 0, 4);

    assertThat(actual).isNotNull().containsExactlyInAnyOrder(3L, 4L);
  }

  @Test
  void given_a_class_and_a_query_containing_IN_clause_criteria_should_return_empty_lists() {

    // given
    var criterias = List.of(new SearchCriteria("name", Operation.IN, "NONAME978-NONAME567"));

    // when
    List<Object> actual = searchRepository.searchIds(criterias, ExampleEntity.class, 0, 4);

    // then
    assertThat(actual).isNotNull().isEmpty();
  }

  @Test
  void given_a_class_and_a_query_containing_a_date__should_return_correct_ids() {
    // given "code:-'OD',same:'SAME',name||nom03;nom04"
    var criterias =
        List.of(new SearchCriteria("creationDate", Operation.HIGHER_OR_EQUAL_THAN, "01-01-2003"));

    // when
    List<Object> actual = searchRepository.searchIds(criterias, ExampleEntity.class, 0, 4);

    assertThat(actual).isNotNull().containsExactlyInAnyOrder(3L, 4L);
  }

  @Test
  void given_a_class_and_a_query_containing_a_equal_on_a_list_property__should_return_correct_() {
    // given "code:-'OD',same:'SAME',name||nom03;nom04"
    var criterias =
        List.of(
            new SearchCriteria("creationDate", Operation.HIGHER_OR_EQUAL_THAN, "01-01-2001"),
            new SearchCriteria("relations.name", Operation.EQUALS, "r-name03"));

    // when
    List<ExampleEntity> actual =
        searchRepository.search(criterias, new ArrayList<>(), ExampleEntity.class, 0, 4);

    // then
    var e01 = ExampleEntity.createExampleEntity(1L, "nom01", "CODE01", true, 2001);

    assertThat(actual).isNotNull();
    assertThat(actual).containsExactly(e01);
  }

  @Test
  void given_a_class_and_a_query_with_user_criterias_should_return_correct_() {
    // given "code:-'OD',same:'SAME',name||nom03;nom04"
    var criterias =
        List.of(new SearchCriteria("creationDate", Operation.HIGHER_OR_EQUAL_THAN, "01-01-2001"));
    var userCriterias =
        List.of(
            new SearchCriteria("name", Operation.EQUALS, "nom01"),
            new SearchCriteria("relations.name", Operation.EQUALS, "nom02"));

    // when
    List<ExampleEntity> actual =
        searchRepository.search(criterias, userCriterias, ExampleEntity.class, 0, 4);

    // then
    var e01 = ExampleEntity.createExampleEntity(1L, "nom01", "CODE01", true, 2001);
    ;

    assertThat(actual).isNotNull();
    assertThat(actual).containsExactly(e01);
  }

  @Test
  void given_a_class_and_a_count_query_with_user_criterias_should_return_correct_() {
    // given "code:-'OD',same:'SAME',name||nom03;nom04"
    var criterias =
        List.of(new SearchCriteria("creationDate", Operation.HIGHER_OR_EQUAL_THAN, "01-01-2001"));
    var userCriterias = List.of(new SearchCriteria("name", Operation.LIKE, "nom"));

    // when
    Long actualCount = searchRepository.count(criterias, userCriterias, ExampleEntity.class);

    assertThat(actualCount).isEqualTo(4L);
  }

  // null join problem correction
  @Test
  void given_a_class_and_a_count_query_with_multiple_user_criterias_should_return_correct() {
    // given "code:-'OD',same:'SAME',name||nom03;nom04"
    var criterias =
        List.of(new SearchCriteria("creationDate", Operation.HIGHER_OR_EQUAL_THAN, "01-01-2002"));
    var userCriterias =
        List.of(
            new SearchCriteria("name", Operation.LIKE, "nom"),
            new SearchCriteria("otherIds", Operation.EQUALS, "id02"));

    // when
    var actualList = searchRepository.search(criterias, userCriterias, ExampleEntity.class, 0, 100);

    var e02 = ExampleEntity.createExampleEntity(2L, "nom02", "CODE02", false, 2002);
    var e03 = ExampleEntity.createExampleEntity(3L, "nom03", "CODE03", true, 2003);
    var e04 = ExampleEntity.createExampleEntity(4L, "nom04", "CODE04", false, 2004);

    assertThat(actualList).isNotNull();
    assertThat(actualList).containsExactlyInAnyOrder(e02, e03, e04);
  }

  @Test
  void
      given_a_class_and_a_simple_like_criteria_not_in_same_case_excute_query_and_get_second_page_with_2_elements() {
    // given
    var criterias = List.of(new SearchCriteria("code", Operation.LIKE, "oDe"));

    // when
    List<ExampleEntity> actual =
        searchRepository.search(criterias, Collections.emptyList(), ExampleEntity.class, 2, 2);

    // then
    var e01 = ExampleEntity.createExampleEntity(3L, "nom03", "CODE03", true, 2003);
    var e02 = ExampleEntity.createExampleEntity(4L, "nom04", "CODE04", false, 2004);

    assertThat(actual).isNotNull();
    assertThat(actual).containsExactlyInAnyOrder(e01, e02);
  }
}
