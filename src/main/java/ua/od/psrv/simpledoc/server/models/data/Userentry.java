package ua.od.psrv.simpledoc.server.models.data;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Список пользователей
 */
@Entity
public class Userentry implements UserDetails {

	private static final long serialVersionUID = -2475548608804521218L;

	/** Идентификатор записи */
	@Id
	@GeneratedValue
	private int Id;

	/** Читаемое имя пользователя */
	private String FullName;

	/** Логин для авторизации в системе */
	private String username;

	/** Хеш пароля */
	private String Password;

	/** Свободное описание подразделения */
	private String Department;

	/** Признак удаления записи */
	private boolean Deleted;

	private boolean AccountNonExpired;

	private boolean AccountNonLocked;

	private boolean CredentialsNonExpired;

	private boolean Enabled;

	/** Настройки пользователя */
	@OneToMany(mappedBy = "User")
	private Set<Settings> Settings;

	@OneToMany(mappedBy = "User")
	private Set<Protocol> Protocols;

	public Userentry() {
		this.Id = -1;
		this.FullName = "";
		this.username = "";
		this.Password = "";
		this.Department = null;
		this.Deleted = false;
		this.Settings = new HashSet<>();
		this.Protocols = new HashSet<>();
		this.AccountNonExpired = true;
		this.AccountNonLocked = true;
		this.CredentialsNonExpired = true;
		this.Enabled = true;
	}

	public int getId() {
		return this.Id;
	}

	public String getFullname() {
		return this.FullName;
	}

	public void setFullname(String Fullname) {
		this.FullName = Fullname;
	}

	public String getPassword() {
		return this.Password;
	}

	public void setPassword(String password) {
		this.Password = password;
	}

	public String getDepartment() {
		return this.Department;
	}

	public void setDepartment(String department) {
		this.Department = department;
	}

	public boolean isDeleted() {
		return this.Deleted;
	}

	public void setDeleted(boolean deleted) {
		this.Deleted = deleted;
	}

	public Set<Settings> getSettings() {
		return this.Settings;
	}

	public Set<Protocol> getProtocols() {
		return Protocols;
	}

	//Fake. Хранение в Settings
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {				
				return "ROLE_USER";
			}			
		});
		return authorities;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.AccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.AccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.CredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.Enabled;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		AccountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		AccountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		CredentialsNonExpired = credentialsNonExpired;
	}

	public void setEnabled(boolean enabled) {
		Enabled = enabled;
	}

}
