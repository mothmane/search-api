package ma.toto.search.mappers;

import java.lang.reflect.ParameterizedType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * {@inheritDoc} @param <T> the type parameter
 *
 * @param <U> the type parameter
 */
public class DefaultGenericMapper<T, U> implements GenericMapper<T, U> {

  @Autowired private ModelMapper modelMapper;

  private Class<T> domainClass;
  private Class<U> dtoClass;

  /** Instantiates a new Default generic mapper. */
  public DefaultGenericMapper() {
    this.domainClass =
        (Class<T>)
            ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    this.dtoClass =
        (Class<U>)
            ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
  }

  @Override
  public T fromDTO(U u) {
    return modelMapper.map(u, domainClass);
  }

  @Override
  public U toDTO(T t) {
    return modelMapper.map(t, dtoClass);
  }
}
