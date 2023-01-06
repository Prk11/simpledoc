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
 * Справочник подразделений и должностных лиц
 */
@Entity
public class Department implements Serializable {

	private static final long serialVersionUID = -8551242211985208681L;

	/**
	 * Идентификатор записи
	 */
	@Id
	@GeneratedValue
	private long Id;

	/**
	 * Ссылка на родительский элемент
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Department Parent;

	/**
	 * Список дочерних элементов
	 */
	@OneToMany(mappedBy = "Parent")
	private Set<Department> Chields;

	/**
	 * Наименование подразделения или Имя должностного лица
	 */
	private String Name;

	/**
	 * Должность
	 */
	private String Post;

	/**
	 * Признак должностного лица
	 */
	private boolean Official;

	/**
	 * Признак удаленой записи. Нужен чтобы не удалять полностью документ из базы
	 */
	private boolean Deleted;

	public Department() {
		this.Id = -1;
		this.Parent = null;
		this.Chields = new HashSet<>();
		this.Name = "";
		this.Post = "";
		this.Official = false;
		this.Deleted = false;
	}

	public Department(Department Parent) {
		this();
		this.Parent = Parent;
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

	public void setPost(String Post) {
		this.Post = Post;
	}

	public String getPost() {
		return this.Post;
	}

	public void setOfficial(boolean Official) {
		this.Official = Official;
	}

	public boolean isOfficial() {
		return this.Official;
	}

	protected void setDeleted(boolean Deleted) {
		this.Deleted = Deleted;
	}

	public boolean isDeleted() {
		return this.Deleted;
	}

	protected void setParent(Department Parent) {
		this.Parent = Parent;
	}

	public Department getParent() {
		return this.Parent;
	}

	public Set<Department> getChields() {
		return this.Chields;
	}

	@Override
	public String toString() {
		if (this.isOfficial())
			return this.getName() + " - " + this.getPost();
		return this.getName();
	}
}
