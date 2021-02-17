package ma.toto.search.criterias;

import ma.toto.search.parser.ExpressionParser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ExpressionParserTest {

  @Test
  void given_an_expression_shouldreturn_correct_search_criterias() {
    var expression = "prop01>20,prop02:-toto,prop03:tata";

    var actual = new ExpressionParser().parse(expression);

    Assertions.assertThat(actual)
        .containsExactlyInAnyOrder(
            new SearchCriteria("prop01", Operation.HIGHER_OR_EQUAL_THAN, "20"),
            new SearchCriteria("prop02", Operation.LIKE, "toto"),
            new SearchCriteria("prop03", Operation.EQUALS, "tata"));
  }

  @Test
  void given_an_expression_with_in_clause_shouldreturn_correct_search_criterias() {
    var expression = "prop01>20,prop02:-toto,prop03:tata,prop04~titi-tyty";

    var actual = new ExpressionParser().parse(expression);

    Assertions.assertThat(actual)
        .containsExactlyInAnyOrder(
            new SearchCriteria("prop01", Operation.HIGHER_OR_EQUAL_THAN, "20"),
            new SearchCriteria("prop02", Operation.LIKE, "toto"),
            new SearchCriteria("prop03", Operation.EQUALS, "tata"),
            new SearchCriteria("prop04", Operation.IN, "titi-tyty"));
  }

  @Test
  void given_an_expression_with_one_dot_in_name_shouldreturn_correct_search_criterias() {
    var expression = "object.prop01>20,prop02:-toto,prop03:tata,prop04~titi-tyty";

    var actual = new ExpressionParser().parse(expression);

    Assertions.assertThat(actual)
        .containsExactlyInAnyOrder(
            new SearchCriteria("object.prop01", Operation.HIGHER_OR_EQUAL_THAN, "20"),
            new SearchCriteria("prop02", Operation.LIKE, "toto"),
            new SearchCriteria("prop03", Operation.EQUALS, "tata"),
            new SearchCriteria("prop04", Operation.IN, "titi-tyty"));
  }

  @Test
  void given_an_expression_with_two_dot_in_name_shouldreturn_correct_search_criterias() {
    var expression = "object.prop01>20,object.parent.prop02:-toto,prop03:tata,prop04~titi-tyty";

    var actual = new ExpressionParser().parse(expression);

    Assertions.assertThat(actual)
        .containsExactlyInAnyOrder(
            new SearchCriteria("object.prop01", Operation.HIGHER_OR_EQUAL_THAN, "20"),
            new SearchCriteria("object.parent.prop02", Operation.LIKE, "toto"),
            new SearchCriteria("prop03", Operation.EQUALS, "tata"),
            new SearchCriteria("prop04", Operation.IN, "titi-tyty"));
  }

  @Test
  void given_an_expression_key_size_two_characters_in_name_shouldreturn_correct_search_criterias() {
    var expression = "id:3,code:-OD,same:SAME";

    var actual = new ExpressionParser().parse(expression);

    Assertions.assertThat(actual)
        .containsExactlyInAnyOrder(
            new SearchCriteria("id", Operation.EQUALS, "3"),
            new SearchCriteria("code", Operation.LIKE, "OD"),
            new SearchCriteria("same", Operation.EQUALS, "SAME"));
  }

  @Test
  void given_an_expression_containing_a_date_shouldreturn_correct_search_criterias() {
    var expression = "id:3,code:-OD,same:SAME,creationDate:2010-12-12";

    var actual = new ExpressionParser().parse(expression);

    Assertions.assertThat(actual)
        .containsExactlyInAnyOrder(
            new SearchCriteria("id", Operation.EQUALS, "3"),
            new SearchCriteria("code", Operation.LIKE, "OD"),
            new SearchCriteria("same", Operation.EQUALS, "SAME"),
            new SearchCriteria("creationDate", Operation.EQUALS, "2010-12-12"));
  }

  @Test
  void given_an_expression_containing_a_space_shouldreturn_correct_search_criterias() {
    var expression = "id:3,code:-OD,same:SAME DATA,creationDate:2010-12-12";

    var actual = new ExpressionParser().parse(expression);

    Assertions.assertThat(actual)
        .containsExactlyInAnyOrder(
            new SearchCriteria("id", Operation.EQUALS, "3"),
            new SearchCriteria("code", Operation.LIKE, "OD"),
            new SearchCriteria("same", Operation.EQUALS, "SAME DATA"),
            new SearchCriteria("creationDate", Operation.EQUALS, "2010-12-12"));
  }

  @Test
  void given_an_expression_containing_a_email_shouldreturn_correct_search_criterias() {
    var expression = "id:3,code:-OD,same:SAME DATA,email:toto@gmail.com";

    var actual = new ExpressionParser().parse(expression);

    Assertions.assertThat(actual)
        .containsExactlyInAnyOrder(
            new SearchCriteria("id", Operation.EQUALS, "3"),
            new SearchCriteria("code", Operation.LIKE, "OD"),
            new SearchCriteria("same", Operation.EQUALS, "SAME DATA"),
            new SearchCriteria("email", Operation.EQUALS, "toto@gmail.com"));
  }

  @Test
  void
      given_an_expression_containing_a_email_in_the_middle_shouldreturn_correct_search_criterias() {
    var expression = "id:3,code:-OD,same:SAME DATA,email:toto@gmail.com,last:value";

    var actual = new ExpressionParser().parse(expression);

    Assertions.assertThat(actual)
        .containsExactlyInAnyOrder(
            new SearchCriteria("id", Operation.EQUALS, "3"),
            new SearchCriteria("code", Operation.LIKE, "OD"),
            new SearchCriteria("same", Operation.EQUALS, "SAME DATA"),
            new SearchCriteria("email", Operation.EQUALS, "toto@gmail.com"),
            new SearchCriteria("last", Operation.EQUALS, "value"));
  }

  @Test
  void given_an_expression_containing_a__shouldreturn_correct_search_criterias() {
    var expression = "id:3,code:-OD,same:SAME DATA,name:jean-baptiste,last:value";

    var actual = new ExpressionParser().parse(expression);

    Assertions.assertThat(actual)
        .containsExactlyInAnyOrder(
            new SearchCriteria("id", Operation.EQUALS, "3"),
            new SearchCriteria("code", Operation.LIKE, "OD"),
            new SearchCriteria("same", Operation.EQUALS, "SAME DATA"),
            new SearchCriteria("name", Operation.EQUALS, "jean-baptiste"),
            new SearchCriteria("last", Operation.EQUALS, "value"));
  }
}
