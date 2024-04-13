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

    Page<Listing> findByOwnerId(Pageable pageable, Long id);
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
        where b.title like :title
        and l.status = 'AVAILABLE'
      """,
      nativeQuery = true,
      countQuery = """
        select count(*)from listing l join copy c on l.copy_id = c.id join book b on c.book_id = b.id
        where b.title like :title
        and l.status = 'AVAILABLE'
      """
    )
    Page<Listing> findByTitle(Pageable pageable, @Param("title") String title);

    @Query(value = """
        select l.* from listing l join copy c on l.copy_id = c.id join book b on c.book_id = b.id
        where exists (SELECT * FROM UNNEST (b.genre) genre WHERE genre LIKE :genre )
        and l.status = 'AVAILABLE'
      """,
      nativeQuery = true,
      countQuery = """
        select count(*) from listing l join copy c on l.copy_id = c.id join book b on c.book_id = b.id
        where exists (SELECT * FROM UNNEST (b.genre) genre WHERE genre LIKE :genre )
        and l.status = 'AVAILABLE'
      """)
    Page<Listing> findByGenre(Pageable pageable, @Param("genre") String genre);

    @Query(value = """
        select l.* from listing l join copy c on l.copy_id = c.id join book b on c.book_id = b.id
        where b.title like :title
        and exists (SELECT * FROM UNNEST (b.genre) genre WHERE genre LIKE :genre ) 
        and l.status = 'AVAILABLE'
      """,
        nativeQuery = true,
        countQuery = """
        select count(*) from listing l join copy c on l.copy_id = c.id join book b on c.book_id = b.id
        where b.title like :title
        and exists (SELECT * FROM UNNEST (b.genre) genre WHERE genre LIKE :genre ) 
        and l.status = 'AVAILABLE'
      """
    )
    Page<Listing> findByTitleLikeAndGenre(Pageable pageable, @Param("title") String title, @Param("genre") String genre);

}