package ma.toto.search.criterias;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import javax.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import ma.toto.search.query.Aggregate;

/** The type Search query criteria consumer. */
@Slf4j
public class SearchQueryCriteriaConsumer implements Consumer<SearchCriteria> {

  /** The constant PERCENTAGE. */
  public static final String PERCENTAGE = "%";
  /** The constant IN_CLAUSE_VALUES_SEPARATOR. */
  public static final String IN_CLAUSE_VALUES_SEPARATOR = "-";

  private Predicate predicate;
  private CriteriaBuilder builder;
  private Root r;
  private Aggregate<Predicate, Predicate, Predicate> aggregate;

  private ZoneId zone = ZoneId.of("Europe/Paris");

  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

  /**
   * Instantiates a new Search query criteria consumer.
   *
   * @param predicate the predicate
   * @param builder the builder
   * @param r the r
   * @param aggregate the aggregate
   */
  public SearchQueryCriteriaConsumer(
      Predicate predicate, CriteriaBuilder builder, Root r, Aggregate aggregate) {
    this.predicate = predicate;
    this.builder = builder;
    this.r = r;
    this.aggregate = aggregate;
  }

  @Override
  public void accept(SearchCriteria searchCriteria) {
    log.trace("consuming criteria {}", searchCriteria);
    if (searchCriteria.getOperation().equals(Operation.IN)) {

      Path<Object> path = getPath(r, searchCriteria.getKey());
      predicate =
          aggregate.apply(
              predicate,
              path.in(
                  Arrays.asList(
                      getParamValue(searchCriteria).toString().split(IN_CLAUSE_VALUES_SEPARATOR))));
    }
    if (searchCriteria.getOperation().equals(Operation.HIGHER_OR_EQUAL_THAN)) {
      predicate =
          aggregate.apply(
              predicate,
              builder.greaterThanOrEqualTo(
                  getPath(r, searchCriteria.getKey()), (Comparable) getParamValue(searchCriteria)));
    } else if (searchCriteria.getOperation().equals(Operation.LESS_OR_EQUAL_THAN)) {
      predicate =
          aggregate.apply(
              predicate,
              builder.lessThanOrEqualTo(
                  getPath(r, searchCriteria.getKey()), (Comparable) getParamValue(searchCriteria)));
    } else if (searchCriteria.getOperation().equals(Operation.LIKE)) {
      if (getPath(r, searchCriteria.getKey()).getJavaType() == String.class) {
        predicate =
            aggregate.apply(
                predicate,
                builder.like(
                    builder.upper(getPath(r, searchCriteria.getKey())),
                    (PERCENTAGE + searchCriteria.getValue() + PERCENTAGE).toUpperCase()));
      }
    } else if (searchCriteria.getOperation().equals(Operation.EQUALS)) {
      predicate =
          aggregate.apply(
              predicate,
              builder.equal(getPath(r, searchCriteria.getKey()), getParamValue(searchCriteria)));
    }
  }
  // TODO
  private Object getParamValue(SearchCriteria param) {
    String value = param.getValue().toString();
    try {
      return toOffsetDateTime(value);
    } catch (Exception e) {
      return value;
    }
  }

  private OffsetDateTime toOffsetDateTime(String dateValue) {
    LocalDate date = LocalDate.parse(dateValue, formatter);
    return date.atStartOfDay(zone).toOffsetDateTime();
  }

  /**
   * Gets path.
   *
   * @param <T> the type parameter
   * @param base the base
   * @param name the name
   * @return the path
   */
  public <T> Path<T> getPath(final Path<?> base, final String name) {
    log.trace("getting persistence path for {}", name);
    final Path<T> result;
    final int index = name.indexOf('.');
    if (index == -1) {
      log.trace(" {} do not contain a DOT (.)", name);
      if (Collection.class.isAssignableFrom(base.get(name).getJavaType())) {
        log.trace(" {} is a collection ", name);
        final Join<?, ?> join = ((From<?, ?>) base).join(name, JoinType.LEFT);
        result = (Path<T>) join;
      } else {
        result = base.get(name);
      }
    } else {
      log.trace(" {} contains a DOT (.)", name);
      final String part = name.substring(0, index);
      final String rest = name.substring(index + 1);

      final Path<?> partPath = base.get(part);
      if (partPath.getModel() == null) {
        log.trace("joining on {}", part);
        final Join<?, ?> join = ((From<?, ?>) base).join(part);
        result = getPath(join, rest);
      } else {
        log.trace("getting path for{}", rest);
        result = getPath(partPath, rest);
      }
    }
    return result;
  }

  /**
   * Gets predicate.
   *
   * @return the predicate
   */
  public Predicate getPredicate() {
    return predicate;
  }

  /**
   * Sets predicate.
   *
   * @param predicate the predicate
   */
  public void setPredicate(Predicate predicate) {
    this.predicate = predicate;
  }

  /**
   * Gets builder.
   *
   * @return the builder
   */
  public CriteriaBuilder getBuilder() {
    return builder;
  }

  /**
   * Sets builder.
   *
   * @param builder the builder
   */
  public void setBuilder(CriteriaBuilder builder) {
    this.builder = builder;
  }

  /**
   * Gets r.
   *
   * @return the r
   */
  public Root getR() {
    return r;
  }

  /**
   * Sets r.
   *
   * @param r the r
   */
  public void setR(Root r) {
    this.r = r;
  }
}
