package spring.core.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import javax.persistence.ForeignKey;
import javax.persistence.ConstraintMode;

@Entity
@Table(name = "Subjects")
public class Subject {

	@Id
	@Column(name = "subjectid")
	private long subjectid;
	@Column(name = "subjecttitle")
	private String subtitle;
	@Column(name = "subjectduration")
	private int duration_in_hours;

	@ManyToMany(fetch = FetchType.LAZY, 
			cascade = { 
					CascadeType.DETACH, 
					CascadeType.MERGE, 
					CascadeType.REFRESH,
					 }, 
			targetEntity = Book.class)
	@JoinTable(name = "subject_book", 
	inverseJoinColumns = @JoinColumn(name = "bookid", nullable = false, updatable = false), 
	joinColumns = @JoinColumn(name = "subjectid", nullable = false, updatable = false), 
	foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT), 
	inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
	private Set<Book> reference = new HashSet<Book>();

	public Subject() {
	}

	public Subject(long subjectid, String subtitle, int duration_in_hours, Set<Book> reference) {
		super();
		this.subjectid = subjectid;
		this.subtitle = subtitle;
		this.duration_in_hours = duration_in_hours;
		this.reference = reference;
	}

	public long getSubjectid() {
		return subjectid;
	}

	public void setSubjectid(long subjectid) {
		this.subjectid = subjectid;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public int getDuration_in_hours() {
		return duration_in_hours;
	}

	public void setDuration_in_hours(int duration_in_hours) {
		this.duration_in_hours = duration_in_hours;
	}

	public Set<Book> getReference() {
		return reference;
	}

	public void setReference(Set<Book> reference) {
		this.reference = reference;
	}

	@Override
	public String toString() {
		return "Subject [subjectid=" + subjectid + ", subtitle=" + subtitle + ", duration_in_hours=" + duration_in_hours
				+ ", reference=" + reference + "]";
	}

}
