package base.api.book.repository;


import base.api.book.entity.Listing;
import base.api.book.entity.support.ListingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long>, JpaSpecificationExecutor<Listing> {

    @Query(
            value = """
        select * from listing l
        where l.owner_id = :id
      """,
            nativeQuery = true
    )
    Page<Listing> findListingByOwnerId(Pageable pageable, Long id);
    Page<Listing> findByListingStatus(Pageable pageable, ListingStatus listingStatus);

    Page<Listing> findByCopyIdIn(Pageable pageable, List<Long> copyId);

    @Query(value = """
         select l.* from listing l join copy c on l.copy_id = c.id join book b on c.book_id = b.id
         where EXISTS (SELECT * FROM UNNEST (b.authors) author WHERE author LIKE :author )
          and l.status = 'AVAILABLE'
      """
    ,
      nativeQuery = true,
      countQuery = """
          select count(*) from listing l join copy c on l.copy_id = c.id join book b on c.book_id = b.id
          where EXISTS (SELECT * FROM UNNEST (b.authors) author WHERE author LIKE :author )
           and l.status = 'AVAILABLE'
    """
    )
    Page<Listing> findByAuthor(Pageable pageable, @Param("author") String author);

    @Query(value = """
        select l.* from listing l join copy c on l.copy_id = c.id join book b on c.book_id = b.id
        where b.title like CONCAT('%',:title,'%')
        and l.status = 'AVAILABLE'
        and :allowRent = l.allow_rent
        and :allowPurchase = l.allow_purchase
      """,
      nativeQuery = true,
      countQuery = """
        select count(*)from listing l join copy c on l.copy_id = c.id join book b on c.book_id = b.id
        where b.title like CONCAT('%',:title,'%')
        and l.status = 'AVAILABLE'
        and :allowRent = l.allow_rent
        and :allowPurchase = l.allow_purchase
      """
    )
    Page<Listing> findByTitle(Pageable pageable, @Param("title") String title,
                                                 @Param("allowRent") Number allowRent,
                                                 @Param("allowPurchase") Number allowPurchase);

    @Query(value = """
        select l.* from listing l join copy c on l.copy_id = c.id join book b on c.book_id = b.id
        where exists (SELECT * FROM UNNEST (b.genre) genre WHERE genre LIKE :genre )
        and :allowRent = l.allow_rent
        and :allowPurchase = l.allow_purchase
        and l.status = 'AVAILABLE'
      """,
      nativeQuery = true,
      countQuery = """
        select count(*) from listing l join copy c on l.copy_id = c.id join book b on c.book_id = b.id
        where exists (SELECT * FROM UNNEST (b.genre) genre WHERE genre LIKE :genre)
        and :allowRent = l.allow_rent
        and :allowPurchase = l.allow_purchase
        and l.status = 'AVAILABLE'
      """)
    Page<Listing> findByGenre(Pageable pageable, @Param("genre") String genre,
                                                 @Param("allowRent") Number allowRent,
                                                 @Param("allowPurchase") Number allowPurchase);

    @Query(value = """
        select l.* from listing l join copy c on l.copy_id = c.id join book b on c.book_id = b.id
        where b.title like CONCAT('%',:title,'%')
        and exists 
            (SELECT * FROM UNNEST (b.genre) genre WHERE genre LIKE :genre ) 
        and l.status = 'AVAILABLE'
        and :allowRent = l.allow_rent
        and :allowPurchase = l.allow_purchase
      """,
        nativeQuery = true,
        countQuery = """
        select count(*) from listing l join copy c on l.copy_id = c.id join book b on c.book_id = b.id
        where b.title like CONCAT('%',:title,'%')
        and exists 
            (SELECT * FROM UNNEST (b.genre) genre WHERE genre LIKE :genre) 
        and l.status = 'AVAILABLE'
        and :allowRent = l.allow_rent
        and :allowPurchase = l.allow_purchase
      """
    )
    Page<Listing> findByTitleLikeAndGenre(Pageable pageable, @Param("title") String title,
                                                             @Param("genre") String genre,
                                                             @Param("allowRent") Number allowRent,
                                                             @Param("allowPurchase") Number allowPurchase);

    Long countListingByOwnerId(Long id);

    Long countListingByOwnerIdAndListingStatus(Long id, ListingStatus listingStatus);


    @Query(value = """ 
            select l.* from listing l join copy c on l.copy_id = c.id join book b on c.book_id = b.id 
            where b.title like CONCAT('%',:title,'%') and l.owner_id = :id
            """,
            nativeQuery = true,
            countQuery = """
            select count(*)from listing l join copy c on l.copy_id = c.id join book b on c.book_id = b.id
            where b.title like CONCAT('%',:title,'%') and l.owner_id = id
      """
    )
    Page<Listing> findByIdAndBookTitleContaining(Pageable pageable, Long id, String title);
}