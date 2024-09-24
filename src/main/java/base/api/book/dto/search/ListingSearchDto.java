package base.api.book.dto.search;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class ListingSearchDto {
  private String title;
//  private String author;
  private String genre;

  private Number allowRent;

  private Number allowPurchase;
}
