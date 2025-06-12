package ua.od.psrv.simpledoc.server.models.data;

import java.io.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * Типы операций с документом (и его составных частях)
 * в протоколе
 */
@Entity
public class Operation implements Serializable {

	private static final long serialVersionUID = 5713766026955387080L;

	/**
	 * Идентификатор записи
	 */
	@Id
	@GeneratedValue
	private int Id;

	/**
	 * Описание операции
	 */
	private String Description;

	public Operation() {
		this.Id = -1;
		this.Description = "";
	}

	public int getId() {
		return this.Id;
	}

	public void setDescription(String Description) {
		this.Description = Description;
	}

	public String getDescription() {
		return this.Description;
	}

	@Override
	public String toString() {
		return this.getDescription();
	}
}
