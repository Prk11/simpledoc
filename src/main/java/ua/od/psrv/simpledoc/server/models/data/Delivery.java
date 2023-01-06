package ua.od.psrv.simpledoc.server.models.data;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * Список видов доставки
 */
@Entity
public class Delivery implements Serializable {

	private static final long serialVersionUID = -7994948593975921064L;

	/** Идентификатор записи */
	@Id
	@GeneratedValue
	private int id;

	/** Наименование вида доставки */
	private String name;

	/**
	 * Признак удаленой записи. Нужен чтобы не удалять полностью документ из базы
	 */
	private boolean deleted;

	public Delivery() {
		this.id = -1;
		this.name = "";
		this.deleted = false;
	}

	public int getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isDeleted() {
		return this.deleted;
	}

	@Override
	public String toString() {
		return this.getName();
	}

}
