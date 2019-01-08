package spring.core.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.core.dao.HibernateDao;
import spring.core.model.Book;
import spring.core.model.Subject;

@Service
public class LibraryService {
	
	@Autowired
	private HibernateDao dao;
	
	@Transactional
	public boolean addSubject(Subject subject)
	{
		return dao.addSubject(subject);
	}
	
	@Transactional
	public Subject getSubject(long refid)
	{
		return dao.getSubject(refid);
	}
	
	@Transactional
	public Subject getSubjectref(long refid)
	{
		return dao.getref(refid);
	}
	
	@Transactional
	public boolean deleteSubject(long refid)
	{
		return dao.deleteSubject(refid);
	}
	
	@Transactional
	public List<Subject> getAllSubjects()
	{
		return dao.getAllSubjects();
	}
	
	@Transactional
	public boolean addBook(Book book)
	{
		return dao.addBook(book);
	}
	
	@Transactional
	public Book getBook(long refid)
	{
		return dao.getBook(refid);
	}
	
	@Transactional
	public boolean deleteBook(long refid)
	{
		return dao.deleteBook(refid);
	}

	@Transactional
	public List<Book> getAllBooks()
	{
		return dao.getAllBooks();
	}
	
	public List<Book> getBookbyTitle()
	{
		List<Book> allbooks = dao.getAllBooks();
		allbooks.sort(Comparator.comparing(Book::getTitle));
		return allbooks;
	}
	
	public List<Book> getBookbyPublishedDate()
	{
		List<Book> allbooks = dao.getAllBooks();
		allbooks.sort(Comparator.comparing(Book::getPublishDate));
		return allbooks;
	}
	
	public List<Subject> getSubjectbytitle()
	{
		List<Subject> allsubjects = dao.getAllSubjects();
		allsubjects.sort(Comparator.comparing(Subject::getSubtitle));
		return allsubjects;
	}
	

	
}
