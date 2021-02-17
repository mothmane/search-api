package ma.toto.search.query;

import javax.persistence.criteria.Predicate;

/**
 * Aggregates multiple predicates.
 *
 * @param <P1> the first predicate
 * @param <P2> the second predicate
 * @param <R> the predicates aggregation result
 */
@FunctionalInterface
public interface Aggregate<P1 extends Predicate, P2 extends Predicate, R> {

  /**
   * Apply r.
   *
   * @param p1 the p 1
   * @param p2 the p 2
   * @return the r
   */
  R apply(P1 p1, P2 p2);
}
