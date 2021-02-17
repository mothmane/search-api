package ma.toto.search.helpers.entities;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExampleEntity {
  @Id private Long id;
  @Column private String name;
  @Column private String code;
  @Column private Boolean yes;
  @Column private String same;
  // TODO not exlude dates
  @EqualsAndHashCode.Exclude @Column private OffsetDateTime creationDate;

  @EqualsAndHashCode.Exclude
  @OneToMany(fetch = FetchType.EAGER)
  List<RelationEntity> relations;

  @EqualsAndHashCode.Exclude @ManyToOne private ExampleType type;

  @ElementCollection @ToString.Exclude @EqualsAndHashCode.Exclude private List<String> otherIds;

  public static ExampleEntity createExampleEntity(
      long l, String nom03, String code03, boolean b, int year) {
    ZoneId zone = ZoneId.of("Europe/Paris");
    ZoneOffset zoneOffSet = zone.getRules().getOffset(LocalDateTime.now());
    return new ExampleEntity(
        l,
        nom03,
        code03,
        b,
        "SAME",
        OffsetDateTime.of(LocalDateTime.of(year, 01, 22, 0, 0), zoneOffSet),
        new ArrayList<>(),
        new ExampleType(),
        new ArrayList<>());
  }
}
