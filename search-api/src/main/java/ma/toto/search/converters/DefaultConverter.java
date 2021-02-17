package ma.toto.search.converters;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * This class helps convert request params to appropriate types based on fileds types.
 *
 * @author Maniar Othmane
 */
public class DefaultConverter {

  /** map thats store the list of predefined converters and user defined ones */
  private Map<Class<?>, Function<String, ?>> converters = new HashMap<>();

  /** instance of the single DefaultConverter */
  private static DefaultConverter instance;
  /*
   * The private constructor to prevent users from instanicating more than one DefaultConverter
   */
  private DefaultConverter() {
    /** default converters are registrer here */
    converters.put(String.class, s -> s);
    /*
             primitive converters
    */
    converters.put(Byte.class, Byte::parseByte);
    converters.put(Short.class, Short::parseShort);
    converters.put(Integer.class, Integer::parseInt);
    converters.put(Long.class, Long::parseLong);
    converters.put(Float.class, Float::parseFloat);
    converters.put(Double.class, Double::parseDouble);
    converters.put(Boolean.class, Boolean::parseBoolean);
  }

  /**
   * Returns the singleton DefaultConverter.
   *
   * @return DefaultConverter
   */
  public static DefaultConverter getInstance() {
    if (instance == null) {
      instance = new DefaultConverter();
    }
    return instance;
  }

  /**
   * Helps search api users to register they own converters a converter is a function that transfom
   * a String to an Object it helps transform the params of the search request to real domain object
   * or values
   *
   * @param klazz
   * @param converter a Function<String, T>
   * @param <T>
   */
  public <T> void registerConverter(Class<T> klazz, Function<String, T> converter) {
    converters.put(klazz, converter);
  }

  /**
   * Converts the String to the appropriate type using the default and registrer converters. @See
   * ReflectionUtils#registerConverter
   *
   * @param stringValue
   * @param klazz
   * @return
   */
  public Object convertTo(String stringValue, Class<?> klazz) {
    return converters.get(klazz).apply(stringValue);
  }
}
