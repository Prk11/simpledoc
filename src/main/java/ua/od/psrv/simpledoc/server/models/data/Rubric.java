package ua.od.psrv.simpledoc.server.models.data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

/**
 * Иерархический справочник "Рубрикатор".
 * Нужен для указания тематики вопроса в письме
 */
@Entity
public class Rubric implements Serializable {

	private static final long serialVersionUID = 4834180509833455741L;

	/** Идентификатор записи */
	@Id
	@GeneratedValue
	private int Id;

	/** Код тематики вопроса (В некоторіх случаях обязательное) */
	private String Code;

	/** Наименование рубрики */
	private String Name;

	/**
	 * Признак удаленой записи. Нужен чтобы не удалять полностью документ из базы
	 */
	private boolean Deleted;

	/** Ссілка на родительскую папку */
	@ManyToOne(fetch = FetchType.LAZY)
	private Rubric Parent;

	/** Перечень дочерних рубрик */
	@OneToMany(mappedBy = "Parent")
	private Set<Rubric> Chields;

	public Rubric() {
		this.Id = -1;
		this.Code = "";
		this.Name = "";
		this.Deleted = false;
		this.Parent = null;
		this.Chields = new HashSet<>();
	}

	public Rubric(Rubric Parent) {
		this();
		this.Parent = Parent;
	}

	public int getId() {
		return this.Id;
	}

	public String getCode() {
		return this.Code;
	}

	public void setCode(String code) {
		this.Code = code;
	}

	public String getName() {
		return this.Name;
	}

	public void setName(String name) {
		this.Name = name;
	}

	public boolean isDeleted() {
		return this.Deleted;
	}

	public void setDeleted(boolean deleted) {
		this.Deleted = deleted;
	}

	public Rubric getParent() {
		return this.Parent;
	}

	protected void setParent(Rubric parent) {
		this.Parent = parent;
	}

	public Set<Rubric> getChields() {
		return this.Chields;
	}

	@Override
	public String toString() {
		return this.getName();
	}

}
