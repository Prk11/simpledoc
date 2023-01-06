package ua.od.psrv.simpledoc.server.models.data;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * Справочник социальной категорий граждан.
 * Существует предопределенный справочник.
 */
@Entity
public class Citizencategory implements Serializable {

	private static final long serialVersionUID = -3597602172012857345L;

	/**
	 * Идентификатор записи
	 */
	@Id
	@GeneratedValue
	private int id;

	/** Наименование социальной категории гражданина */
	private String name;

	/**
	 * Признак удаленой записи. Нужен чтобы не удалять полностью документ из базы
	 */
	private boolean deleted;

	public Citizencategory() {
		this.id = -1;
		this.name = "";
		this.deleted = false;
	}

	public int getId() {
		return this.id;
	}

	protected void setId(int id) {
		this.id=id;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Citizencategory other = (Citizencategory) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
