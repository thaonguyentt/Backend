package base.api.common.internal.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Table(name = "common_area", schema = "nhahocduong")
@Getter @Setter
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Area {
  @Id
  @Column(name = "id", nullable = false)
  private Integer id;

  @NotNull
  @Column(name = "code", nullable = false, length = Integer.MAX_VALUE)
  private String code;

  @Column(name = "name", length = Integer.MAX_VALUE)
  private String name;

  @Column(name = "type")
  private Integer type;

  @Column(name = "active")
  private Integer active;

  @Column(name = "parent_area_id")
  @JsonProperty("parent_area_id")
  private Integer parentAreaId;

  @SuppressWarnings("JpaAttributeTypeInspection")
  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "ancestor")
  private List<Area> ancestor;

}