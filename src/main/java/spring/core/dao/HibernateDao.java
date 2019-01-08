package spring.core.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import spring.core.model.Book;
import spring.core.model.Subject;

@Transactional
@Repository
public class HibernateDao {

	@PersistenceContext
	EntityManager manager;
		
	
	public boolean addSubject(Subject subject)
	{		
		 manager.persist(subject); 
		 return true;
	}
	
	public boolean deleteSubject(long refid)
	{
		Subject subject=manager.find(Subject.class, refid);
		manager.remove(subject);
		return true;
	}
	
	public Subject getSubject(long refid)
	{
		Subject subject=manager.find(Subject.class, refid);
		return subject;
	}
	
	public List<Subject> getAllSubjects()
	{
		String sqlquery="select s from Subject s";
		Query hquery = manager.createQuery(sqlquery);
		@SuppressWarnings("unchecked")
		List<Subject> allsubjects=hquery.getResultList();
		return allsubjects;
	}
	

	public boolean addBook(Book book)
	{
		 manager.persist(book); 
		 return true;
	
	}
	
	public boolean deleteBook(long refid)
	{
		
		Book book=manager.find(Book.class, refid);
		manager.remove(book);
		return true;
		
	}
	
	public List<Book> getAllBooks()
	{
		String sqlquery="select b from Book b";
		Query hquery = manager.createQuery(sqlquery);
		@SuppressWarnings("unchecked")
		List<Book> allbooks=hquery.getResultList();
		return allbooks;
	}
	
	public Book getBook(long refid)
	{		
		Book book=manager.find(Book.class, refid);
		return book;
	}
	
	public Subject getref(long refid)
	{		
		Subject subject=manager.find(Subject.class, refid);
		subject.getReference().size();
		return subject;
	}
	
	
}
