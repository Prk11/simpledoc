package ua.od.psrv.simpledoc.server.models.data;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * Справочник организаций (корреспонденты и адресаты).
 * Интерактивно пополняемый.
 */
@Entity
public class Organization implements Serializable {

	private static final long serialVersionUID = -4545818359231970370L;

	/** Идентификатор записи */
	@Id
	@GeneratedValue
	private long Id;

	/** Название организации */
	private String Name;

	/** Код ЕДРПОУ или ОКПО */
	private String Code;

	/**
	 * Признак удаленой записи. Нужен чтобы не удалять полностью документ из базы
	 */
	private boolean Deleted;

	public Organization() {
		this.Id = -1;
		this.Name = "";
		this.Deleted = false;
		this.Code = "";
	}

	public long getId() {
		return this.Id;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public String getName() {
		return this.Name;
	}

	public void setCode(String Code) {
		this.Code = Code;
	}

	public String getCode() {
		return this.Code;
	}

	public void setDeleted(boolean Deleted) {
		this.Deleted = Deleted;
	}

	public boolean isDeleted() {
		return this.Deleted;
	}

	@Override
	public String toString() {
		return this.getName();
	}
}
