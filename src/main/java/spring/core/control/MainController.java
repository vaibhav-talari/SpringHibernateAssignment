package spring.core.control;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import spring.core.configuration.AppConfig;
import spring.core.exception.Exception1;
import spring.core.exception.Exception2;

import spring.core.model.Book;
import spring.core.model.Subject;

import spring.core.service.LibraryService;

public class MainController {
	private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

	private static LibraryService libservice = context.getBean(LibraryService.class);

	private static Scanner scanner = null;

	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		int option;

		System.out.println("Welcome to the LIBRARY!!!");
		try {
			do {
				System.out.println();
				System.out.println("1. Add Subject");
				System.out.println("2. Add Book");
				System.out.println("3. Delete Subject");
				System.out.println("4. Delete Book");
				System.out.println("5. Search Subject");
				System.out.println("6. Search Book");
				System.out.println("7. Print Entity in sorted Manner");
				System.out.println("8. Exit");
				System.out.print("\nEnter your option: ");
				option = scanner.nextInt();
				try {
					switch (option) {
					case 1:
						createSubject();
						break;
					case 2:
						createBook();
						break;
					case 3:
						deleteSubject();
						break;
					case 4:
						deleteBook();
						break;
					case 5:
						searchSubject();
						break;
					case 6:
						searchBook();
						break;
					case 7:
						printsortedentity();
						//printReference();
						break;
					case 8:
						exit();
						break;

					default:
						System.out.println("Invalid option entered. Please enter correct option.");
						break;
					}
				} catch (Exception1 | Exception2 e) {
					System.out.println(e.getMessage());
				}

			} while (option != 8);
			System.out.println("\nThank you!!!");
		} finally {
			scanner.close();
		}
	}

	private static void createSubject() throws Exception1 {
		Subject subject = new Subject();

		System.out.println("\n-----Enter Subject details-----");
		System.out.print("\nEnter Subject ID: ");
		try {
			long id = Long.parseLong(scanner.next());
			scanner.nextLine();
			subject.setSubjectid(id);
		} catch (InputMismatchException | NumberFormatException e) {
			throw new Exception1("\n-+-+-+-\nInvalid Subject ID.\nPlease enter valid ID.\n-+-+-+-");
		}
		captureDetailSubject(subject);
		boolean isCreateSuccess = libservice.addSubject(subject);
		String message = isCreateSuccess ? "Subject created successfully." : "Subject creation failed.";
		System.out.println(message);

	}

	private static void captureDetailSubject(Subject subject) throws Exception1 {
		int i;
		System.out.print("Enter SUBJECT Title:");
		subject.setSubtitle(scanner.nextLine());
		System.out.print("Enter duration of SUBJECT:");
		try {
			int time = Integer.parseInt(scanner.next());
			subject.setDuration_in_hours(time);
		} catch (InputMismatchException | NumberFormatException e) {
			throw new Exception1("\n-+-+-+-\nInvalid Time.\nPlease enter valid Time Duration.\n-+-+-+-");
		}
		System.out.print("Enter the number of reference you want to Enter:");
		try {
			int no = Integer.parseInt(scanner.next());// if i use int no=scanner.nextInt() in try catch block then also
														// an exception is thrown.
			Set<Book> listref = new HashSet<Book>();
			for (i = 0; i < no; i++) {
				System.out.print("Enter the ID of Book:");
				long ref = scanner.nextLong();
				Book bks = libservice.getBook(ref);
				listref.add(bks);
			}

			subject.setReference(listref);
			System.out.println(subject);
		} catch (InputMismatchException | NumberFormatException e) {
			throw new Exception1("\n-+-+-+-\nInvalid Number.\nPlease enter valid Number.\n-+-+-+-");

		}

	}

	private static void deleteSubject() throws Exception2 {
		System.out.print("---Avaliable SUBJECTS---\n");
		listallsubjects();
		try {
			System.out.print("\nEnter ID of SUBJECT to delete:");
			long id = Long.parseLong(scanner.next());
			Subject subject = libservice.getSubject(id);
			if (subject == null) {
				throw new Exception2("\n-+-+-+-\nNo SUBJECT found with ID:" + id + "\n-+-+-+-");
			}
			boolean isDeleteSuccess = libservice.deleteSubject(id);
			String message = isDeleteSuccess ? "Subject deleted successfully." : "Subject deletion failed.";
			System.out.println(message);
			System.out.print("---SUBJECTS after deletion\n---");
			listallsubjects();
		} catch (InputMismatchException | NumberFormatException e) {
			throw new Exception2("\n-+-+-+-\nInvalid Subject ID.\nPlease enter valid Subject ID.\n-+-+-+-");

		}
	}

	private static void searchSubject() throws Exception2 {

		System.out.print("\nEnter ID of SUBJECT to view:");
		try {
			long id = Long.parseLong(scanner.next());
			Subject subject = libservice.getSubject(id);
			if (subject == null) {
				throw new Exception2("\n-+-+-+-\nNo RECORD of SUBJECT found with ID:" + id + "\n-+-+-+-");
			}
			printHeadersubject();
			printDetailSubject(subject);
			System.out.println();
		} catch (InputMismatchException | NumberFormatException e) {
			throw new Exception2("\n-+-+-+-\nInvalid Subject ID.\nPlease enter valid Subject ID.\n-+-+-+-");

		}
	}

	private static void createBook() throws Exception1 {
		Book book = new Book();
		System.out.println("\nEnter Book details...");
		System.out.print("\nEnter Book ID: ");
		try {
			long id = Long.parseLong((scanner.next()));
			scanner.nextLine();
			book.setBookid(id);
		} catch (NumberFormatException | InputMismatchException e) {
			throw new Exception1("\n-+-+-+-\nInvalid Book ID.\nPlease enter valid ID.\n-+-+-+-");
		}
		captureDetailBook(book);
		boolean isCreateSuccess = libservice.addBook(book);
		String message = isCreateSuccess ? "Book created successfully." : "Book creation failed.";
		System.out.println(message);

	}

	private static void captureDetailBook(Book book) throws Exception1 {
		System.out.print("Enter Book title: ");
		book.setTitle(scanner.nextLine());
		try {
			System.out.print("Enter Book price: ");
			int cost = Integer.parseInt(scanner.next());
			book.setPrice(cost);
		} catch (InputMismatchException | NumberFormatException e) {
			throw new Exception1("\n-+-+-+-\nEnter Valid Price\n-+-+-+-");
		}
		try {
			System.out.print("Enter Book Volume: ");
			int vol = Integer.parseInt(scanner.next());
			book.setVolume(vol);
		} catch (InputMismatchException | NumberFormatException e) {
			throw new Exception1("\n-+-+-+-\nEnter Valid Volume\n-+-+-+-");
		}
		try {
			System.out.print("Enter a Date(DD/MM/YYYY):");
			String date = scanner.next();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
			LocalDate publishdate = LocalDate.parse(date, formatter);
			book.setPublishDate(publishdate);
		} catch (InputMismatchException | DateTimeParseException e) {
			throw new Exception1("\n-+-+-+-\nEnter Valid Date. Accepted Format is DD/MM.YYYY|Ex. 01/05/1996\n-+-+-+-");
		}
	}

	private static void deleteBook() throws Exception2 {
		System.out.print("---Avaliable BOOKS---\n");
		listallbooks();
		System.out.print("\nEnter ID of BOOK to delete:");
		try {
			long id = Long.parseLong(scanner.next());
			Book book = libservice.getBook(id);
			if (book == null) {
				throw new Exception2("\n-+-+-+-\nNo BOOK found with ID:" + id + "\n-+-+-+-");
			}
			boolean isDeleteSuccess = libservice.deleteBook(id);
			String message = isDeleteSuccess ? "Book deleted successfully." : "Book deletion failed.";
			System.out.println(message);
			System.out.println("Book deleted-" + book);
			System.out.print("---BOOKS after deletion---\n");
			listallbooks();
		} catch (InputMismatchException | NumberFormatException e) {
			throw new Exception2("\n-+-+-+-\nInvalid Book ID.\nPlease enter valid Book ID.\n-+-+-+-");

		}

	}

	private static void searchBook() throws Exception2 {

		System.out.print("\nEnter ID of BOOK to view: ");
		try {
			long id = Long.parseLong(scanner.next());
			Book book = libservice.getBook(id);
			if (book == null) {
				throw new Exception2("\n-+-+-+-\nNo RECORD of Book found with ID:" + id + "\n-+-+-+-");
			}
			printHeaderbook();
			printDetailBook(book);
			System.out.println();
		} catch (InputMismatchException | NumberFormatException e) {
			throw new Exception2("\n-+-+-+-\nInvalid Book ID.\nPlease enter valid Book ID.\n-+-+-+-");

		}
	}

	private static void printHeadersubject() {
		System.out.format("\n%-8s%-15s%-15s", "ID", "Title", "Time Duration");
	}

	private static void printHeaderbook() {
		System.out.format("\n%-10s %-15s %-10s %-8s %-15s", "ID", "TITLE", "Price", "Volume", "Published Time");
	}

	private static void printDetailSubject(Subject subject) {
		if (subject == null) {
			System.out.format("\n%-15s%-15s%-15s", "NULL", "NULL", "NULL");
		}
		System.out.format("\n%-8s%-15s%-15s", "-----", "----------", "-----------");
		System.out.format("\n%-8s%-15s%-15s", subject.getSubjectid(), subject.getSubtitle(),
				subject.getDuration_in_hours());

	}

	private static void printDetailBook(Book book) {
		if (book == null) {
			return;
		}
		System.out.format("\n%-10s %-15s %-10s %-8s %-15s", "--------", "----------", "--------", "-------",
				"----------------------");
		System.out.format("\n%-10d %-15s %-10.2f %-8d %tA, %tB' %td", book.getBookid(), book.getTitle(),
				book.getPrice(), book.getVolume(), book.getPublishDate(), book.getPublishDate(), book.getPublishDate());
	}

	/*public static void printReference() throws Exception2 {
		System.out.println("Enter the Subject ID to print references:");
		long id = scanner.nextLong();
		Subject subject = libservice.getSubjectref(id);
		if (subject == null) {
			throw new Exception2("\n-+-+-+-\nNo RECORD of SUBJECT found with ID:" + id + "\n-+-+-+-");
		}
		Set<Book> ref = subject.getReference();
		if (ref.isEmpty()) {
			System.out.print("\n-+-+-+-\nNo reference avaliable\n-+-+-+-\n");
		} else {
			printHeaderbook();
			for (Book element : ref) {
				Book book = libservice.getBook(element.getBookid());
				if (book == null) {
					throw new Exception2(
							"\n-+-+-+-\nSorry there are no BOOK's for the specified SUBJECT ID\nThe Book listed in the reference is Deleted or not listed in database\nThe ID of the reference BOOK is:"
									+ element + "\n-+-+-+-");
				}
				printDetailBook(book);
				System.out.println();
			}
		}

	}*/

	private static void listallbooks() {

		List<Book> books = libservice.getAllBooks();

		printHeaderbook();

		for (Book bk : books) {
			printDetailBook(bk);
		}
		System.out.println();
	}

	private static void listallsubjects() {

		List<Subject> subjects = libservice.getAllSubjects();

		printHeadersubject();

		for (Subject sub : subjects) {
			printDetailSubject(sub);
		}
		System.out.println();
	}

	private static void printsortedentity() {
		System.out.println(
				"\nEnter 1<-- Sort Book by Title:\nEnter 2<-- Sort Subject by Subject Title\nEnter 3<-- Sort Book by Published Date");
		System.out.print("\nEnter choice:");
		int searchoption = scanner.nextInt();
		if (searchoption == 1) {
			List<Book> allbooks = libservice.getBookbyTitle();
			printHeaderbook();

			for (Book bk : allbooks) {
				printDetailBook(bk);
			}
			System.out.println();
		}
		if (searchoption == 2) {

			List<Subject> subjects = libservice.getSubjectbytitle();

			printHeadersubject();

			for (Subject sub : subjects) {
				printDetailSubject(sub);
			}
			System.out.println();
		}
		if (searchoption == 3) {
			List<Book> allbooks = libservice.getBookbyPublishedDate();
			printHeaderbook();

			for (Book bk : allbooks) {
				printDetailBook(bk);
			}
			System.out.println();
		}
	}

	private static void exit() {
		context.close();
		System.out.println("\nThank you. Closing DataBase Connection!!!");
		System.exit(0);
	}
}
