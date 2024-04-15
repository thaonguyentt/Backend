package base.api.book.dto.search;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ListingSearchByOwnerAndNameDto {
    private String title;
    private Long ownerId;
}
