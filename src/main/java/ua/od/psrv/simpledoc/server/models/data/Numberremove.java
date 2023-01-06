package ua.od.psrv.simpledoc.server.models.data;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * Список удаленых порядковых номеров. Нужен
 * для регистрации документов с порядковым
 * номером "дырки". После регистрации, свободный
 * номер из этого списка должен быть удален
 */
@Entity
public class Numberremove implements Serializable {

	private static final long serialVersionUID = 4497439856980339924L;

	/**
	 * Идентификатор записи
	 */
	@Id
	@GeneratedValue
	private long id;

	/**
	 * Группа документов к которой привязана запись нумератора
	 */
	@ManyToOne(cascade=CascadeType.REMOVE)
	private Docgroup docgroup;

	/**
	 * @see Numerator#Section Описание
	 */
	private String section;

	/**
	 * Порядковый номер удаленного документа
	 */
	private long num;

	public Numberremove() {
		this.id = -1;
		this.docgroup = null;
		this.section = "";
		this.num = 0;
	}

	public Numberremove(Docgroup docgroup) {
		this();
		this.docgroup = docgroup;
	}

	public long getId() {
		return this.id;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getSection() {
		return this.section;
	}

	public void setNum(long num) {
		this.num = num;
	}

	public long getNum() {
		return this.num;
	}

	protected void setDocgroup(Docgroup docgroup) {
		this.docgroup = docgroup;
	}

	public Docgroup getDocgroup() {
		return this.docgroup;
	}

}
