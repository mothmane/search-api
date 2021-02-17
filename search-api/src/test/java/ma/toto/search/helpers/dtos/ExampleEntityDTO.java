package ma.toto.search.helpers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExampleEntityDTO {
  private String name;
  private String code;
  private Boolean yes;
  private String same;
}
