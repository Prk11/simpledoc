package ua.od.psrv.simpledoc.server.models.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import ua.od.psrv.simpledoc.server.repository.NumberremoveRepository;
import ua.od.psrv.simpledoc.server.repository.NumeratorRepository;

/**
 * Группа документов (Журнал регистрации).
 * Иерархический справочник.
 */
@Entity
public class Docgroup implements Serializable {

	private static final long serialVersionUID = -8556100167510411343L;

	/** Идентификатор записи */
	@Id
	@GeneratedValue
	private int id;

	/** Ссылка на родительскую запись */
	@ManyToOne(fetch = FetchType.LAZY)
	private Docgroup parent;

	/**
	 * Дочерние записи по иерархии
	 */
	@OneToMany(mappedBy = "parent")
	private Set<Docgroup> chields;

	/** Варианты документов: входящие, письма граждан, исходящие */
	@Enumerated
	private DocgroupKind kind;

	/** Индекс по номенклатуре. Необходим для генерации номера */
	private String index;

	/** Название журнала */
	private String name;

	/** Признак елемента выбора. Лист */
	private boolean node;

	/** Шаблон для генерации номера */
	private String shablon;

	/**
	 * Признак удаленой записи. Нужен чтобы не удалять полностью документ из базы
	 */
	private boolean deleted;

	@OneToMany
	private Set<Numerator> numerators;


	@OneToMany
	private Set<Numberremove> numberremoves;


	@Transient
	private boolean autocorrectnode;

	@Transient
	@Autowired
	private NumeratorRepository numeratorRepository;

	@Transient
	@Autowired
	private NumberremoveRepository numberremoveRepository;
	
	@Transient
	@Autowired
	private EntityManager entityManager;


	public Docgroup() {
		this.id = -1;
		this.index = "";
		this.kind = null;
		this.name = "";
		this.node = false;
		this.parent = null;
		this.chields = new HashSet<>();
		this.deleted = false;
		this.shablon = "";
		this.autocorrectnode=true;
		this.numerators = new HashSet<>();
		this.numberremoves = new HashSet<>();
	}

	public Docgroup(Docgroup parent) {
		this();
		this.index = parent.index;
		this.parent = parent;
		this.shablon = parent.getShablon();
	}

	public int getId() {
		return this.id;
	}

	public Docgroup getParent() {
		return this.parent;
	}

	public Set<Docgroup> getChields() {
		return this.chields;
	}

	public void setKind(DocgroupKind kind) {
		this.kind = kind;
		if (this.autocorrectnode) {
			if (kind!=null) 
				this.node=true;
			else	
				this.node=false;
		}
	}

	public DocgroupKind getKind() {
		return this.kind;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getIndex() {
		return this.index;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setNode(boolean node) {
		this.node = node;
		this.autocorrectnode = false;
	}

	public boolean isNode() {
		return this.node;
	}

	public void setShablon(String shablon) {
		this.shablon = shablon;
	}

	public String getShablon() {
		return this.shablon;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isDeleted() {
		return this.deleted;
	}

		
	public Set<Numerator> getNumerators() {
		return numerators;
	}

	public Set<Numberremove> getNumberremoves() {
		return numberremoves;
	}


	/** Сервисный метод, помогающий получить пеобходимый нумератор */
	public Numerator getNumerator(String section) {
		Numerator result;
		try {
			result=this.numeratorRepository.findByDocgroupAndSectionIgnoreCase(this, section);
			return result;
		}
		catch (NullPointerException e) {
			result = new Numerator(this);
			result.setSection(section);
			result.setLastNum(1);
			//this.numeratorRepository.save(result);
			return result;
		}
	}

	/**
	 * @see Numerator#Section Секция нумератора
	 *      Получение списка секций
	 */
	public List<String> getNumeratorSections() {
		List<String> result = this.entityManager
			.createQuery(
                   "select distinct n.section from Numerator where docgroup = :docgroup order by n.section disc", 
				   String.class)
			.setParameter("docgroup", this)
            .getResultList();
		return result;
	}

	/** Сервисный метод, помогающий получить список удаленых номеров */
	public Set<Numberremove> getRemoveNumbers(String section) {
		return this.numberremoveRepository.findByDocgroupAndSectionIgnoreCaseOrderByNumDesc(this, section);
	}

	public String getCurrentSection() {
		Integer year = Calendar.getInstance().get(Calendar.YEAR);
		String section = year.toString();
		return section;
	}

	public Long getLastnum(String section) {
		Numerator numerator = this.getNumerator(section);
		return (numerator!=null)?numerator.getLastNum():1;
	}

	public Long getLastnum() {
		String section = this.getCurrentSection();
		return this.getLastnum(section);
	}

	public void setLastnum(Long num) {
		if (num<1) return;
		if (this.getId()==-1) return;
		String section = this.getCurrentSection();
		Numerator numerator = this.getNumerator(section);
		if (numerator==null) {
			numerator = new Numerator();
			numerator.setDocgroup(this);
			numerator.setSection(section);
		}
		numerator.setLastNum(num);
		this.numeratorRepository.save(numerator);
	}

	public Long getNextnum() {
		Long num = this.getLastnum();
		Set<Numberremove> removes =  this.getNumberremoves();
		for (Numberremove numberremove : removes) {
			num = numberremove.getNum()<num ? numberremove.getNum() : num;
		}
		return num;
	}

	public List<Docgroup> getParents() {
		List<Docgroup> result=new ArrayList<>();
		result.add(this);
		Docgroup cursor = this.getParent();
		while(cursor!=null) {
			result.add(0,cursor);
			cursor = cursor.getParent();
		}
		return result;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Docgroup other = (Docgroup) obj;
        if (id != other.id)
            return false;
        return true;
    }

}
