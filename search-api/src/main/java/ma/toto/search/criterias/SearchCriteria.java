package ma.toto.search.criterias;

/** The type Search criteria. */
public class SearchCriteria {

  private String key;
  private Operation operation;
  private Object value;

  /**
   * Instantiates a new Search criteria.
   *
   * @param key the key
   * @param operation the operation
   * @param value the value
   */
  public SearchCriteria(String key, Operation operation, Object value) {
    this.key = key;
    this.operation = operation;
    this.value = value;
  }

  /**
   * Gets key.
   *
   * @return the key
   */
  public String getKey() {
    return key;
  }

  /**
   * Sets key.
   *
   * @param key the key
   */
  public void setKey(String key) {
    this.key = key;
  }

  /**
   * Gets operation.
   *
   * @return the operation
   */
  public Operation getOperation() {
    return operation;
  }

  /**
   * Sets operation.
   *
   * @param operation the operation
   */
  public void setOperation(Operation operation) {
    this.operation = operation;
  }

  /**
   * Gets value.
   *
   * @return the value
   */
  public Object getValue() {
    return value;
  }

  /**
   * Sets value.
   *
   * @param value the value
   */
  public void setValue(Object value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "SearchCriteria{"
        + "key='"
        + key
        + '\''
        + ", operation="
        + operation
        + ", value="
        + value
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SearchCriteria that = (SearchCriteria) o;

    if (key != null ? !key.equals(that.key) : that.key != null) return false;
    if (operation != that.operation) return false;
    return value != null ? value.equals(that.value) : that.value == null;
  }

  @Override
  public int hashCode() {
    int result = key != null ? key.hashCode() : 0;
    result = 31 * result + (operation != null ? operation.hashCode() : 0);
    result = 31 * result + (value != null ? value.hashCode() : 0);
    return result;
  }

  /**
   * To expression string.
   *
   * @return the string
   */
  public String toExpression() {
    return key + operation.getStringOperation() + value;
  }
}
