package ua.od.psrv.simpledoc.server.models.data;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * Таблица связи для организации связи многие-ко-многим
 * с документом. И для уточнениия характера этой связи.
 */
@Entity
public class Correspondent implements Serializable {

	private static final long serialVersionUID = -8940979865884790204L;

	/**
	 * Идентификатор записи
	 */
	@Id
	@GeneratedValue
	private long Id;

	/**
	 * Ссылка на документ содержащего корреспондента
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Record Document;

	/**
	 * Исходящий номер письма корреспондента или вхоящий адресата
	 */
	private String OutNum;

	/**
	 * Исходящая дата письма корреспондента или вхоящий адресата
	 */
	@Temporal(TemporalType.DATE)
	private Date OutDate;

	/**
	 * Примечание
	 */
	private String Note;

	/**
	 * Кто подписал
	 */
	private String Signatory;

	/**
	 * Ссылка на организацию-корреспондента
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Organization Organization;

	/**
	 * Ссылка на физлицо-корреспондента
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Citizen Citizen;

	/**
	 * Варианты корреспондентов
	 */
	@Enumerated
	private CorrespondentKind Kind;

	public Correspondent() {
		this.Id = -1;
		this.Document = null;
		this.Kind = null;
		this.OutNum = "";
		this.OutDate = null;
		this.Note = "";
		this.Signatory = "";
		this.Organization = null;
		this.Citizen = null;
	}

	public Correspondent(Record Document) {
		this();
		this.Document = Document;
	}

	public long getId() {
		return this.Id;
	}

	public void setOutNum(String OutNum) {
		this.OutNum = OutNum;
	}

	public String getOutNum() {
		return this.OutNum;
	}

	public void setOutDate(Date OutDate) {
		this.OutDate = OutDate;
	}

	public Date getOutDate() {
		return this.OutDate;
	}

	public void setNote(String Note) {
		this.Note = Note;
	}

	public String getNote() {
		return this.Note;
	}

	public void setSignatory(String Signatory) {
		this.Signatory = Signatory;
	}

	public String getSignatory() {
		return this.Signatory;
	}

	protected void setOrganization(Organization Organization) {
		this.Organization = Organization;
	}

	public Organization getOrganization() {
		return this.Organization;
	}

	protected void setCitizen(Citizen Citizen) {
		this.Citizen = Citizen;
	}

	public Citizen getCitizen() {
		return this.Citizen;
	}

	protected void setDocument(Record Document) {
		this.Document = Document;
	}

	public Record getDocument() {
		return this.Document;
	}

	public CorrespondentKind getKind() {
		return this.Kind;
	}

	protected void setKind(CorrespondentKind Kind) {
		this.Kind = Kind;
	}

	@Override
	public String toString() {
		switch (this.Kind) {
			case IncomingOrganization:
				if (this.getOrganization() != null)
					return this.getOrganization().toString();
			case IncomingCitizen:
				if (this.getCitizen() != null)
					return this.getCitizen().toString();
			case CoverLetter:
				if (this.getOrganization() != null)
					return this.getOrganization().toString();
			case OutgoingCitizen:
				if (this.getCitizen() != null)
					return this.getCitizen().toString();
			case OutgoingOrganization:
				if (this.getOrganization() != null)
					return this.getOrganization().toString();
		}
		return "";
	}
}
