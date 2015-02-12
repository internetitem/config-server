package com.internetitem.config.server.db.dataModel;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SettingUser")
@NamedQueries(
	@NamedQuery(name = "User.fetchWithPermissions", query = "SELECT u FROM SettingUser u LEFT JOIN FETCH u.groups g LEFT JOIN FETCH g.grants gt WHERE u.username = :username")
)
public class SettingUser {

	@Id
	@GeneratedValue(generator = "seqUser")
	@SequenceGenerator(name = "seqUser", sequenceName = "SeqSettingUser")
	@Column(name = "UserId")
	private long userId;

	@Column(name = "Username", nullable = false, unique = true, length = 50)
	private String username;

	@Column(name = "Password", length = 100)
	private String password;

	@Column(name = "Active", nullable = false)
	private boolean active;

	@ManyToMany(mappedBy = "members")
	private Set<SettingUserGroup> groups = new HashSet<>();

	protected SettingUser() {
	}

	public SettingUser(String username, String password, boolean active) {
		this.username = username;
		this.password = password;
		this.active = active;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<SettingUserGroup> getGroups() {
		return groups;
	}

	public void setGroups(Set<SettingUserGroup> groups) {
		this.groups = groups;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SettingUser)) return false;

		SettingUser that = (SettingUser) o;

		if (userId != that.userId) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return (int) (userId ^ (userId >>> 32));
	}
}
