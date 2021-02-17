package ma.toto.search.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ma.toto.search.criterias.Operation;
import ma.toto.search.criterias.SearchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** author Maniar Othmane */
public class ExpressionParser {

  private Logger logger = LoggerFactory.getLogger(ExpressionParser.class);

  /** The constant EXPRESSION_REGEX. */
  public static final String EXPRESSION_REGEX = "(\\w+?\\.*\\w*\\.*\\w*)(:-|:|<|>|~)([^,]+)";

  /**
   * Parses an expression and return a list of search criterias.
   *
   * @param expression the expression
   * @return the search criterias list
   */
  public List<SearchCriteria> parse(String expression) {

    logger.debug("the search expression is beeing parsed: {}", expression);

    List<SearchCriteria> params = new ArrayList<>();
    if (expression != null) {
      Pattern pattern;
      pattern = Pattern.compile(EXPRESSION_REGEX);
      Matcher matcher = pattern.matcher(expression + ",");
      while (matcher.find()) {
        logger.trace("a matcher is found {}", matcher);
        params.add(
            new SearchCriteria(
                matcher.group(1), Operation.fromString(matcher.group(2)), matcher.group(3)));
      }
    }
    logger.debug("the expresion parsing resulted to : {}", params);
    return params;
  }
}
