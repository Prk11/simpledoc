package ua.od.psrv.simpledoc.server.models.data;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * Хранение последнего порядкового номера,
 * при регистрации документов
 */
@Entity
public class Numerator implements Serializable {

	private static final long serialVersionUID = 8726390717611655608L;

	/**
	 * Идентификатор записи
	 */
	@Id
	@GeneratedValue
	private int id;

	/**
	 * Группа документов к которой привязана запись нумератора
	 */
	@ManyToOne(cascade=CascadeType.REMOVE)
	private Docgroup docgroup;

	/**
	 * Год ведения подсчета (Можно поразмышлять что делать
	 * если прошла реорганизация, или нумерация привязана
	 * к созыву сессии)
	 */
	private String section;

	/**
	 * Порядковый номер следующего документа
	 */
	private long lastnum;

	public Numerator() {
		this.id = -1;
		this.docgroup = null;
		this.section = "";
		this.lastnum = 1;
	}

	public Numerator(Docgroup docgroup) {
		this();
		this.docgroup = docgroup;
	}

	public int getId() {
		return this.id;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getSection() {
		return this.section;
	}

	public void setLastNum(long lastnum) {
		this.lastnum = lastnum;
	}

	public long getLastNum() {
		return this.lastnum;
	}

	protected void setDocgroup(Docgroup docgroup) {
		this.docgroup = docgroup;
	}

	public Docgroup getDocgroup() {
		return this.docgroup;
	}

}
