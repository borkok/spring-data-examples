package com.example.springdata.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	//derived query
	List<Book> searchByTitle(String title);
	Page<Book> searchByTitle(String title, Pageable pageable);

	List<Book> findFirst3ByPublishedAfterOrderByPublished(LocalDate published);

	//jpql query
	@Query("select b from Book b where b.title = :title")
	List<Book> findBy(@Param("title") String title);
	@Query("select b from Book b where b.title = :title")
	List<Book> findBy(@Param("title") String title, Pageable pageable);

	List<Book> seekBooksThatWerePublishedBefore(@Param("published") LocalDate publishDate);
	List<Book> seekBooksThatWerePublishedBefore(@Param("published") LocalDate publishDate, Pageable pageable);
}
