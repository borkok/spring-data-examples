package com.example.springdatarest.jpa;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@RepositoryRestResource(path="ksiazki", collectionResourceRel="ksiazeczki", itemResourceRel="ksiazka", excerptProjection = BookProjection.class)
public interface BookRepository extends JpaRepository<Book, Long> {

	//derived query
	@RestResource(path="tytul", rel="tytulik")
	public List<Book> searchByTitle(String title);

	public List<Book> findFirst3ByPublishedAfterOrderByPublished(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate published);

	//jpql query
	@Query("select b from Book b where b.title = :title")
	public List<Book> findBy(@Param("title") String title, Pageable pageable);

	public List<Book> seekBooksThatWerePublishedBefore(@Param("published")
													   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
													   LocalDate publishDate,
													   Pageable pageable);
}
