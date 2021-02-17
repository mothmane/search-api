package ma.toto.search.utils;

import java.lang.reflect.Field;

/**
 * This utilities class helps do some reflection innternaly in the search-api
 *
 * @author Othmane Maniar
 */
public final class ReflectionUtils {

  // private constructior to hide the implicit one
  private ReflectionUtils() {}

  /**
   * This method return the filed Type
   *
   * @param klazz the class containing the field
   * @param fieldName the filed name
   * @return Class representing the filed type
   */
  public static Class<?> getFieldType(Class<?> klazz, String fieldName) {
    return getField(klazz, fieldName).getType();
  }

  /**
   * This method search in the class and all parent classes for a field having the name "fieldName"
   *
   * @param clazz
   * @param fieldName
   * @return
   */
  public static Field getField(Class<?> clazz, String fieldName) {
    Class<?> tmpClass = clazz;
    do {
      try {
        return tmpClass.getDeclaredField(fieldName);
      } catch (NoSuchFieldException e) {
        tmpClass = tmpClass.getSuperclass();
      }
    } while (tmpClass != null);
    // TODO return a custom exception
    throw new RuntimeException("Field '" + fieldName + "' not found on class " + clazz);
  }
}
