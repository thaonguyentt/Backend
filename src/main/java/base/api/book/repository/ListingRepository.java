package base.api.book.repository;


import base.api.book.entity.Listing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long>, JpaSpecificationExecutor<Listing> {
    List<Listing> findByOwnerId(Long id);

    Page<Listing> findByCopyIdIn(Pageable pageable, List<Long> copyId);

    @Query(value = """
         select l.* from listing l join copy c on l.copy_id = c.id join book b on c.book_id = b.id
         where EXISTS (SELECT * FROM UNNEST (b.authors) author WHERE author LIKE :author )
      """
    ,
      nativeQuery = true,
      countQuery = """
          select count(*) from listing l join copy c on l.copy_id = c.id join book b on c.book_id = b.id
          where EXISTS (SELECT * FROM UNNEST (b.authors) author WHERE author LIKE :author )
    """
    )
    Page<Listing> findByAuthor(Pageable pageable, @Param("author") String author);

    @Query(value = """
        select l.* from listing l join copy c on l.copy_id = c.id join book b on c.book_id = b.id
        where b.title like :title
      """,
      nativeQuery = true,
      countQuery = """
        select count(*)from listing l join copy c on l.copy_id = c.id join book b on c.book_id = b.id
        where b.title like :title
      """
    )
    Page<Listing> findByTitle(Pageable pageable, @Param("title") String title);

    @Query(value = """
        select l.* from listing l join copy c on l.copy_id = c.id join book b on c.book_id = b.id
        where exists (SELECT * FROM UNNEST (b.genre) genre WHERE genre LIKE :genre )
      """,
      nativeQuery = true,
      countQuery = """
        select count(*) from listing l join copy c on l.copy_id = c.id join book b on c.book_id = b.id
        where exists (SELECT * FROM UNNEST (b.genre) genre WHERE genre LIKE :genre )
      """)
    Page<Listing> findByGenre(Pageable pageable, @Param("genre") String genre);

    @Query(value = """
        select l.* from listing l join copy c on l.copy_id = c.id join book b on c.book_id = b.id
        where b.title like :title
        and exists (SELECT * FROM UNNEST (b.genre) genre WHERE genre LIKE :genre ) 
      """,
        nativeQuery = true,
        countQuery = """
        select count(*) from listing l join copy c on l.copy_id = c.id join book b on c.book_id = b.id
        where b.title like :title
        and exists (SELECT * FROM UNNEST (b.genre) genre WHERE genre LIKE :genre ) 
      """
    )
    Page<Listing> findByTitleLikeAndGenre(Pageable pageable, @Param("title") String title, @Param("genre") String genre);

}