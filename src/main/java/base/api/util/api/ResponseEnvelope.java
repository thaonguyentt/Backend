package base.api.util.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
public class ResponseEnvelope<T> implements Serializable {

  protected Instant timestamp;
  // Store the response data from controllers
  protected T data;
  protected String errors;
  // Twitter-style "expansion data"
  protected String includes;

  // For pagination
  private PaginationMetadata page;

//  @Override
//  public String toString() {
//    try {
//      var ret = new ObjectMapper().writeValueAsString(this);
//      return ret;
//    } catch (JsonProcessingException e) {
//      throw new RuntimeException(e);
//    }
//  }
}
