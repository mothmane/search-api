package ma.toto.search.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ma.toto.search.TestSearchConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ContextConfiguration(classes = {TestSearchConfiguration.class})
@TestPropertySource(locations = "classpath:application-junit-test.properties")
@ActiveProfiles("junit-test")
@ExtendWith(SpringExtension.class)
@Sql({"/truncate.sql", "/script-data.sql"})
@SpringBootTest
@AutoConfigureMockMvc
class SearchControllerTest {
  @Autowired private MockMvc mockMvc;

  // TODO affiner les tests

  @Test
  void when_a_get_advanced_search_request_is_sent_should_return_second_page_result()
      throws Exception {

    // id higgher or equals than 3 code conatinings OD and same equals to SAME
    var expression = "id>3,code:-OD,same:SAME";

    // get the first page containings 4 elements
    this.mockMvc
        .perform(
            get("/example-entities/advanced-search?first=0&count=4&object=ExampleEntity&expression="
                    + expression)
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[*].code", containsInAnyOrder("CODE03", "CODE04")))
        .andExpect(jsonPath("$[*].name", containsInAnyOrder("nom03", "nom04")));
  }

  @Test
  void when_a_get_advanced_search_with_in_clause_request_is_sent_should_return_second_page_result()
      throws Exception {

    // id higgher or equals than 3 code conatinings OD and same equals to SAME
    var expression = "code:-OD,same:SAME,name~nom03-nom04";

    // get the first page containings 4 elements
    this.mockMvc
        .perform(
            get("/example-entities/advanced-search?first=0&count=4&object=ExampleEntity&expression="
                    + expression)
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[*].code", containsInAnyOrder("CODE03", "CODE04")))
        .andExpect(jsonPath("$[*].name", containsInAnyOrder("nom03", "nom04")));
  }

  @Test
  void
      when_a_get_advanced_search_that_needs_external_call_is_sent_should_return_second_page_result()
          throws Exception {

    // id higgher or equals than 3 code conatinings OD and same equals to SAME
    var expression = "code:-OD,same:SAME,name~nom03-nom04";

    // get the first page containings 4 elements
    this.mockMvc
        .perform(
            get("/example-entities/advanced-search?first=0&count=4&object=ExampleEntity&expression="
                    + expression)
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[*].code", containsInAnyOrder("CODE03", "CODE04")))
        .andExpect(jsonPath("$[*].name", containsInAnyOrder("nom03", "nom04")));
  }

  @Test
  void when_a_get_advanced_search_id_call_is_sent_should_return_correct_ids_list()
      throws Exception {

    // id higgher or equals than 3 code conatinings OD and same equals to SAME
    var expression = "code:-OD,same:SAME,name~nom03-nom04";

    // get the first page containings 4 elements
    this.mockMvc
        .perform(
            get("/example-entities/advanced-search-ids?object=ExampleEntity&expression="
                    + expression)
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[*]", containsInAnyOrder(3, 4)));
  }

  @Test
  void
      when_a_get_advanced_search_that_needs_external_call_is_sent_should_return_elements_between_2_and_4_result()
          throws Exception {

    // id higgher or equals than 3 code conatinings OD and same equals to SAME
    var expression = "code:-OD,same:SAME,name~nom03-nom04";

    // get the first page containings 4 elements
    this.mockMvc
        .perform(
            get("/example-entities/advanced-search/?first=0&count=4&object=ExampleEntity&expression="
                    + expression)
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[*].code", containsInAnyOrder("CODE03", "CODE04")))
        .andExpect(jsonPath("$[*].name", containsInAnyOrder("nom03", "nom04")));
  }

  @Test
  void when_a_get_advanced_search_count_call_is_sent_should_return_correct_count()
      throws Exception {

    // id higgher or equals than 3 code conatinings OD and same equals to SAME
    var expression = "code:-OD,same:SAME,name~nom03-nom04";

    // get the first page containings 4 elements
    this.mockMvc
        .perform(
            get("/example-entities/advanced-search/count/?first=0&count=4&object=ExampleEntity&expression="
                    + expression)
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("2"));
  }

  @Test
  void when_a_get_advanced_search_count_call_is_sent_should_return_correct_count_ZERO()
      throws Exception {

    // id higgher or equals than 3 code conatinings OD and same equals to SAME
    var expression = "code:-NOCODE,same:SAME,name~nom03-nom04";

    // get the first page containings 4 elements
    this.mockMvc
        .perform(
            get("/example-entities/advanced-search/count/?first=0&count=4&object=ExampleEntity&expression="
                    + expression)
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("0"));
  }
}
