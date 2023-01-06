package ua.od.psrv.simpledoc.server.models.data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;

/**
 * Справочник. Интерактивно пополняемый. Список граждан,
 * которые обратились с заявлениями, или которым отправили письма.
 */
@Entity
public class Citizen implements Serializable {

	private static final long serialVersionUID = -1800638145313484296L;

	/** Идентификатор записи */
	@Id
	@GeneratedValue
	private long id;

	/** Полное имя гражданина. */
	private String fullname;

	/** Адрес гражданина, куда отправляять письма, а не адрес вопроса. */
	private String address;

	/**
	 * Признак удаленой записи. Нужен чтобы не удалять полностью документ из базы
	 */
	private boolean deleted;

	/** Список социальных статусов гражданина */
	@ManyToMany
	@JoinTable(name = "CitizenStatusLink", joinColumns = @JoinColumn(name = "citizen_id"), inverseJoinColumns = @JoinColumn(name = "status_id"))
	private Set<Citizenstatus> status;

	/** Список социальных категорий гражданина */
	@ManyToMany
	@JoinTable(name = "CitizenCategoryLink", joinColumns = @JoinColumn(name = "citizen_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Citizencategory> category;

	public Citizen() {
		this.id = -1;
		this.fullname = "";
		this.address = "";
		this.deleted = false;
		this.category = new HashSet<Citizencategory>();
		this.status = new HashSet<Citizenstatus>();
	}

	public long getId() {
		return this.id;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getFullname() {
		return this.fullname;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return this.address;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isDeleted() {
		return this.deleted;
	}

	public void setStatus(Set<Citizenstatus> status) {
		this.status = status;
	}

	public Set<Citizenstatus> getStatus() {
		return this.status;
	}

	public void setCategory(Set<Citizencategory> category) {
		this.category = category;
	}

	public Set<Citizencategory> getCategory() {
		return this.category;
	}

	@Override
	public String toString() {
		return this.getFullname();
	}
}
