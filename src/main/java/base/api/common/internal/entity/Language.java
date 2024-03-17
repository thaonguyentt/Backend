package base.api.common.internal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "common_language")
public class Language {
  @Id
  @Column(name = "code", nullable = false, length = Integer.MAX_VALUE)
  private String code;

  @NotNull
  @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
  private String name;

  @NotNull
  @Column(name = "native_name", nullable = false, length = Integer.MAX_VALUE)
  private String nativeName;

}