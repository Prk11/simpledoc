package ua.od.psrv.simpledoc.server.models.data;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

/**
 * Протокол работы с документом. Отражает
 * любые изменения в нем
 */
@Entity
public class Protocol implements Serializable {

	private static final long serialVersionUID = -2320106839045012153L;

	/** Идентификатор записи */
	@Id
	@GeneratedValue
	private long Id;

	/** Пользователь которого логируем */
	@ManyToOne(fetch = FetchType.LAZY)
	private Userentry User;

	/** Действие которое логируется */
	@ManyToOne
	private Operation Operation;

	/** Время события */
	//@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date Date;

	/**
	 * Ссылка на объект, изменения в котором мы логируем
	 */
	@Column(name = "link_id")
	private long Link;

	public Protocol() {
		this.Id = -1;
		//this.Date = java.util.Calendar.getInstance().getTime();
		this.Date = null;
		this.User = null;
		this.Operation = null;
		this.Link = 0;
	}

	public long getId() {
		return this.Id;
	}

	public void setDate(Date Date) {
		this.Date = Date;
	}

	public Date getDate() {
		return this.Date;
	}

	public void setLink(long Link) {
		this.Link = Link;
	}

	public long getLink() {
		return this.Link;
	}

	public void setOperation(Operation Operation) {
		this.Operation = Operation;
	}

	public Operation getOperation() {
		return this.Operation;
	}

	public void setUser(Userentry User) {
		this.User = User;
	}

	public Userentry getUser() {
		return this.User;
	}

}
