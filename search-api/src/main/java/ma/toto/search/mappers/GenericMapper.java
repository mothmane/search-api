package ma.toto.search.mappers;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This interface represent a generic domain to dto mapper
 *
 * @author Maniar Othmane
 * @param <T>
 * @param <U>
 */
public interface GenericMapper<T, U> {

  T fromDTO(U u);

  U toDTO(T t);

  /**
   * This method maps a list of domain object to a list of dtos
   *
   * @param list
   * @return
   */
  default List<U> toDTOs(List<T> list) {
    return list != null ? list.stream().map(this::toDTO).collect(Collectors.toList()) : null;
  }

  /**
   * This method maps a list of dtos object to a list of domain objects
   *
   * @param list
   * @return
   */
  default List<T> fromDTOs(List<U> list) {
    return list.stream().map(this::fromDTO).collect(Collectors.toList());
  }

  /**
   * This method maps an iterable of domain objects to a list of dtos
   *
   * @param iterable
   * @return List<U>
   */
  default List<U> toDTOs(Iterable<T> iterable) {
    return this.toDTOs(ImmutableList.copyOf(iterable));
  }
  /**
   * This method maps an iterable of dtos to a list of domain objects
   *
   * @param iterable
   * @return List<U>
   */
  default List<T> fromDTOs(Iterable<U> iterable) {
    return this.fromDTOs(ImmutableList.copyOf(iterable));
  }
}
