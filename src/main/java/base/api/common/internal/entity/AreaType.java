package base.api.common.internal.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AreaType {
  PROVINCE(1),
  DISTRICT(2),
  COMMUNE(3),
  ;

  private int code;
  AreaType(int code) {
    this.code = code;
  }

  @JsonValue
  public int getCode() {
    return code;
  }
}