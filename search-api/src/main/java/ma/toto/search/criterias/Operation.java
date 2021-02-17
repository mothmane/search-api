package ma.toto.search.criterias;

/** The enum Operation. */
public enum Operation {
  /** Higher or equal than operation. */
  HIGHER_OR_EQUAL_THAN(">"),
  /** Less or equal than operation. */
  LESS_OR_EQUAL_THAN("<"),
  /** Equals operation. */
  EQUALS(":"),
  /** Like operation. */
  LIKE(":-"),
  /** In operation. */
  IN("~");

  private final String value;

  Operation(String value) {
    this.value = value;
  }

  /**
   * Gets operation as String.
   *
   * @return the string operation
   */
  public String getStringOperation() {
    return value;
  }

  /**
   * Convert a string to an operation enum.
   *
   * @param text the text
   * @return the operation
   */
  public static Operation fromString(String text) {
    for (Operation operation : Operation.values()) {
      if (operation.value.equalsIgnoreCase(text)) {
        return operation;
      }
    }
    throw new IllegalArgumentException(text);
  }

  @Override
  public String toString() {
    return "Operation{" + "value='" + value + '\'' + '}';
  }
}
