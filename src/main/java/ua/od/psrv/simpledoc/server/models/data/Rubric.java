package ua.od.psrv.simpledoc.server.models.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
	private int id;

	/** Код тематики вопроса (В некоторіх случаях обязательное) */
	private String code;

	/** Наименование рубрики */
	private String name;

	/**
	 * Признак удаленой записи. Нужен чтобы не удалять полностью документ из базы
	 */
	private boolean deleted;
	
	/** Признак елемента выбора. Лист */
	private boolean node;

	public boolean isNode() {
		return node;
	}

	public void setNode(boolean node) {
		this.node = node;
	}

	/** Ссілка на родительскую папку */
	@ManyToOne(fetch = FetchType.LAZY)
	private Rubric parent;

	/** Перечень дочерних рубрик */
	@OneToMany(mappedBy = "parent")
	private Set<Rubric> chields;

	public Rubric() {
		this.id = -1;
		this.code = "";
		this.name = "";
		this.deleted = false;
		this.parent = null;
		this.node = false;
		this.chields = new HashSet<>();
	}

	public Rubric(Rubric parent) {
		this();
		this.parent = parent;
		this.code = parent.getCode()+"-?";
	}

	public int getId() {
		return this.id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDeleted() {
		return this.deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Rubric getParent() {
		return this.parent;
	}

	protected void setParent(Rubric parent) {
		this.parent = parent;
	}

	public Set<Rubric> getChields() {
		return this.chields;
	}

	public List<Rubric> getParents() {
		List<Rubric> result=new ArrayList<>();
		result.add(this);
		Rubric cursor = this.getParent();
		while(cursor!=null) {
			result.add(0,cursor);
			cursor = cursor.getParent();
		}
		return result;
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
        if (getClass() != obj.getClass())
            return false;
			Rubric other = (Rubric) obj;
        if (id != other.id)
            return false;
        return true;
    }
}
