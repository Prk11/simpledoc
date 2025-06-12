package ua.od.psrv.simpledoc.server.models.data;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * Настройки работы клиента
 */
@Entity
public class Settings implements Serializable {

	private static final long serialVersionUID = -142298292332956352L;

	/**
	 * Идентификатор записи
	 */
	@Id
	@GeneratedValue
	private long Id;

	/**
	 * Ссылка на пользователя для которого предназначени
	 * эти настройки. Может быть пустым в случае
	 * общего характера настройки.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Userentry User;

	/**
	 * Номенклатура настройки
	 */
	@ManyToOne
	private SettingsFlag Flag;

	/**
	 * Заданое значение для настройки
	 */
	private String Value;

	public Settings() {
		this.Id = -1;
		this.User = null;
		this.Flag = null;
		this.Value = "";
	}

	public Settings(Userentry User) {
		this();
		this.User = User;
	}

	public long getId() {
		return this.Id;
	}

	public Userentry getUser() {
		return this.User;
	}

	protected void setUser(Userentry user) {
		this.User = user;
	}

	public SettingsFlag getFlag() {
		return this.Flag;
	}

	public void setFlag(SettingsFlag flag) {
		this.Flag = flag;
	}

	public String getValue() {
		return this.Value;
	}

	public void setValue(String value) {
		this.Value = value;
	}

	@Override
	public String toString() {
		return this.getValue();
	}

}
