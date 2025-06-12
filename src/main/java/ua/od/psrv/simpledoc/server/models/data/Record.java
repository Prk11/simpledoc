package ua.od.psrv.simpledoc.server.models.data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * "Тело" документа. Головной узел документа.
 * К нему привязываются отстальные "части" документа
 */
@Entity
public class Record implements Serializable {

	private static final long serialVersionUID = 908771635616694112L;

	/** Идентификатор записи */
	@Id
	@GeneratedValue
	private long Id;

	/** Журнал(Группа документов в котором регистрируются документы */
	@ManyToOne
	private Docgroup Docgroup;

	/** Порядковая часть номера документа */
	private long OrderNum;

	/** Регистрационный номер документа */
	private String Regnum;

	/** Дата регистрации документа */
	@Temporal(TemporalType.DATE)
	private Date Regdate;

	/** Состав/колличество листов */
	private String Consist;

	/** Краткое содержание */
	private String Content;

	/** Примечание */
	private String Note;

	/** Признак коллективности письма (Только для писем граждан) */
	private boolean Collective;

	/** Колличество подписей в обращении (Только для писем граждан) */
	private int SignCount;

	/** Резолюции наложенные на документ */
	@OneToMany(mappedBy = "Document")
	private Set<Resolution> Resolutions;

	/** Вид доставки */
	@ManyToOne(fetch = FetchType.LAZY)
	private Delivery Delivery;

	/** Исполнитель документа (Только для исходящих и внутреней документации) */
	@ManyToOne(fetch = FetchType.LAZY)
	private Department Executor;

	/** Визы и Подписи */
	@OneToMany(mappedBy = "Document")
	private Set<Vise> Vises;

	/** Файлы прикрепленные к документу */
	@OneToMany(mappedBy = "Document")
	private Set<FileLink> Files;

	/** Перечень кореспондентов (только для входящих и граждан) */
	@OneToMany(mappedBy = "Document")
	private Set<Correspondent> Correspondents;

	/** Рубрикатор */
	@ManyToMany
	private Set<Rubric> Rubrics;

	/** Связки с другими документами */
	@OneToMany(mappedBy = "Document")
	protected Set<LinkRecord> Links;

	public Record() {
		this.Id = -1;
		this.Collective = false;
		this.Consist = "";
		this.Content = "";
		this.Correspondents = new HashSet<>();
		this.Delivery = null;
		this.Docgroup = null;
		this.Executor = null;
		this.Files = new HashSet<>();
		this.Links = new HashSet<>();
		this.Note = "";
		this.OrderNum = 0;
		this.Regdate = null;
		this.Regnum = "";
		this.Resolutions = new HashSet<>();
		this.Rubrics = new HashSet<>();
		this.SignCount = 1;
		this.Vises = new HashSet<>();
	}

	public Record(Docgroup Docgroup, Date RegDate) {
		this();
		this.Docgroup = Docgroup;
		this.Regdate = RegDate;
	}

	public Record(Docgroup Docgroup, Date RegDate, String RegNum) {
		this();
		this.Docgroup = Docgroup;
		this.Regdate = RegDate;
		this.Regnum = RegNum;
	}

	public long getId() {
		return this.Id;
	}

	public void setOrderNum(long OrderNum) {
		this.OrderNum = OrderNum;
	}

	public long getOrderNum() {
		return this.OrderNum;
	}

	public void setRegnum(String Regnum) {
		this.Regnum = Regnum;
	}

	public String getRegnum() {
		return this.Regnum;
	}

	public void setRegdate(Date Regdate) {
		this.Regdate = Regdate;
	}

	public Date getRegdate() {
		return this.Regdate;
	}

	public void setConsist(String Consist) {
		this.Consist = Consist;
	}

	public String getConsist() {
		return this.Consist;
	}

	public void setContent(String Content) {
		this.Content = Content;
	}

	public String getContent() {
		return this.Content;
	}

	public void setNote(String Note) {
		this.Note = Note;
	}

	public String getNote() {
		return this.Note;
	}

	public void setCollective(boolean Collective) {
		this.Collective = Collective;
	}

	public boolean isCollective() {
		return this.Collective;
	}

	public void setSignCount(int SignCount) {
		this.SignCount = SignCount;
	}

	public int getSignCount() {
		return this.SignCount;
	}

	public void setDelivery(Delivery Delivery) {
		this.Delivery = Delivery;
	}

	public Delivery getDelivery() {
		return this.Delivery;
	}

	public Set<Resolution> getResolutions() {
		return this.Resolutions;
	}

	public void setExecutor(Department Executor) {
		this.Executor = Executor;
	}

	public Department getExecutor() {
		return this.Executor;
	}

	public Set<Vise> getVises() {
		return this.Vises;
	}

	public Set<FileLink> getFiles() {
		return this.Files;
	}

	public Set<Correspondent> getCorrespondents() {
		return this.Correspondents;
	}

	public Set<Rubric> getRubrics() {
		return this.Rubrics;
	}

	public Set<LinkRecord> getLinks() {
		return this.Links;
	}

	@Override
	public String toString() {
		return this.getRegnum() + " " + this.getRegdate().toString();
	}
}
