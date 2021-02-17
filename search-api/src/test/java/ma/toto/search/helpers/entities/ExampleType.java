package ma.toto.search.helpers.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExampleType {

  @JsonIgnoreType
  public static enum Code {
    T1("T1"),
    T2("T2");

    private String code;

    private Code(String code) {
      this.code = code;
    }

    public String toString() {
      return code;
    }
  };

  @Id private Long id;
}
