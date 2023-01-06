package ua.od.psrv.simpledoc.server.models.data;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * Отчеты исполнителей
 */
@Entity
public class Reply implements Serializable {

	private static final long serialVersionUID = -2059650748545250243L;

	/** Идентификатор записи */
	@Id
	@GeneratedValue
	private long Id;

	/** Ссылка на резолюцию содержащих ответы/исполнителей */
	@ManyToOne(fetch = FetchType.LAZY)
	private Resolution Resolution;

	/** Дата отчета */
	@Temporal(TemporalType.DATE)
	private Date ReplyDate;

	/** Варианты ответа */
	@Enumerated
	private CategoryReply Category;

	/** Ссылка на исполнителя */
	@ManyToOne
	private Department Executor;

	private String Content;

	public Reply() {
		this.Id = -1;
		this.Content = "";
		this.Category = null;
		this.Executor = null;
		this.ReplyDate = null;
		this.Resolution = null;
	}

	public Reply(Resolution Resolution, Department Executor) {
		this();
		this.Resolution = Resolution;
		this.Executor = Executor;
	}

	public long getId() {
		return this.Id;
	}

	public String getContent() {
		return this.Content;
	}

	public void setContent(String Content) {
		this.Content = Content;
	}

	public Resolution getResolution() {
		return this.Resolution;
	}

	protected void setResolution(Resolution resolution) {
		this.Resolution = resolution;
	}

	public Date getReplyDate() {
		return this.ReplyDate;
	}

	public void setReplyDate(Date replyDate) {
		this.ReplyDate = replyDate;
	}

	public CategoryReply getCategory() {
		return this.Category;
	}

	public void setCategory(CategoryReply category) {
		this.Category = category;
	}

	public Department getExecutor() {
		return this.Executor;
	}

	protected void setExecutor(Department executor) {
		this.Executor = executor;
	}

}
