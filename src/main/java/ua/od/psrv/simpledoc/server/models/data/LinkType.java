package ua.od.psrv.simpledoc.server.models.data;

import java.io.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

/**
 * Варианты связок между документами
 */
@Entity
public class LinkType implements Serializable {

	private static final long serialVersionUID = -1262558019888646204L;

	/** Идентификатор записи */
	@Id
	@GeneratedValue
	private int Id;

	/** Название связки */
	private String Name;

	/** Ссылка на обратную связка (при наличии) */
	@OneToOne(optional = true)
	private LinkType Pare;

	/**
	 * Признак удаленой записи. Нужен чтобы не удалять полностью документ из базы
	 */
	private boolean Deleted;

	public LinkType() {
		this.Id = -1;
		this.Name = "";
		this.Deleted = false;
		this.Pare = null;
	}

	public int getId() {
		return this.Id;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public String getName() {
		return this.Name;
	}

	protected void setDeleted(boolean Deleted) {
		this.Deleted = Deleted;
	}

	public boolean isDeleted() {
		return this.Deleted;
	}

	protected void setPare(LinkType Pare) {
		this.Pare = Pare;
	}

	public LinkType getPare() {
		return this.Pare;
	}

	@Override
	public String toString() {
		return this.getName();
	}
}
