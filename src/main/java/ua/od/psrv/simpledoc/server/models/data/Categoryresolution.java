package ua.od.psrv.simpledoc.server.models.data;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * @author Prk
 * Справочник. Обозначение состояния исполнения резолюции.
 * Часто используется при закрытии резолюции.
 * Есть часто используемые варианты.
 * <ul>
 * <li>Контроль продлен</li>
 * <li>Рассмотренно положительно</li>
 * <li>Рассмотренно отрицательно</li>
 * <li>Даны разъяснения</li>
 * <li>Возвращено автору</li>
 * <li>Переслано по назначению</li>
 * </ul>
 */
@Entity
public class Categoryresolution implements Serializable {

	private static final long serialVersionUID = -1459695860564592480L;

	/**
	 * Идентификатор записи
	 */
	@Id
	@GeneratedValue
	private int id;

	/**
	 * Текстовое обозначение категории резолюции.
	 */
	private String name;

	/**
	 * Признак удаленой записи. Нужен чтобы
	 * не удалять полностью документ из базы
	 */
	private boolean deleted;

	/**
	 * Конструктор по-умолчанию
	 */
	public Categoryresolution() {
		this.id = -1;
		this.name = "";
		this.deleted = false;
	}

	/**
	 * Идентификатор записи
	 * 
	 * @return Возвращает идентификатор записи
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Задаёт название состояния исполнения резолюции
	 * 
	 * @param Name - любое не пустое название
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Возвращает название состояния исполнения резолюции
	 * 
	 * @return Нвименование состояния исполнения
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Задает признак удаления (сокрытия) текущей записи.
	 * Запись удаляется с помощью этого признака при наличии
	 * ссылок в старых резолюциях на нее.
	 * 
	 * @param Deleted : истина - запись удалена, ложь - запись актуальна
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * Возвращает признак удаления (сокрытия) текущей записи.
	 * 
	 * @return истина - запись удалена, ложь - запись актуальна
	 */
	public boolean isDeleted() {
		return this.deleted;
	}

	@Override
	public String toString() {
		return this.getName();
	}
}
