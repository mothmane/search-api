package ma.toto.search;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;

public class SearchCriteriasConfig {

  private Map<String, Info> map;
  private Map<String, String> attributeObject;
  private Info internal = new Info("Internal", "Internal");

  public SearchCriteriasConfig() {
    map = new HashMap<>();
    attributeObject = new HashMap<>();
  }

  public void put(String id, String object, String attribute) {
    attributeObject.put(object, attribute);
    Info info = new Info(object, attribute);
    map.put(id, info);
  }

  public Info get(String id) {
    if (map.get(id) == null) {
      return internal;
    }
    return map.get(id);
  }

  public String getObject(String id) {
    return this.get(id).getObject();
  }

  public String getAttribute(String id) {
    return this.get(id).getAtribute();
  }

  public String getAttributeByObject(String object) {
    return attributeObject.get(object);
  }

  public int size() {
    return map.size();
  }

  public boolean isEmpty() {
    return map.isEmpty();
  }

  public boolean containsKey(Object o) {
    return map.containsKey(o);
  }

  public Set<String> keySet() {
    return map.keySet();
  }
}

@Data
@AllArgsConstructor
class Info {
  private String object;
  private String atribute;
}
