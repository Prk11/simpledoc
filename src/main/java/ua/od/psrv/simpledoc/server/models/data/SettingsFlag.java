package ua.od.psrv.simpledoc.server.models.data;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * Справочник всех настроек
 */
@Entity
public class SettingsFlag implements Serializable {

	private static final long serialVersionUID = -5755708823061137368L;

	/** Идентификатор записи */
	@Id
	@GeneratedValue
	private int Id;

	/** Название настройки(стоит задуматься о точечной номенклатуре) */
	private String Name;

	/** Описание настройки */
	private String Description;

	public SettingsFlag() {
		this.Id = -1;
		this.Name = "";
		this.Description = "";
	}

	public int getId() {
		return this.Id;
	}

	public String getName() {
		return this.Name;
	}

	public void setName(String name) {
		this.Name = name;
	}

	public String getDescription() {
		return this.Description;
	}

	public void setDescription(String description) {
		this.Description = description;
	}

	@Override
	public String toString() {
		return this.getName();
	}
}
