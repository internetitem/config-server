package com.internetitem.config.server.db.dataModel;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SettingUserGroup")
public class SettingUserGroup {

	@Id
	@GeneratedValue(generator = "seqUserGroup")
	@SequenceGenerator(name = "seqUserGroup", sequenceName = "SeqSettingUserGroup")
	@Column(name = "UserGroupId")
	private long groupId;

	@Column(name = "GroupName", nullable = false, length = 50, unique = true)
	private String groupName;

	@ManyToMany
	@JoinTable(name = "SettingUserGroupMember", joinColumns = @JoinColumn(name = "UserGroupId"), inverseJoinColumns = @JoinColumn(name = "UserId"))
	private Set<SettingUser> members = new HashSet<>();

	@ManyToMany
	@JoinTable(name= "SettingGroupPermissionGrant", joinColumns = @JoinColumn(name = "UserGroupId"), inverseJoinColumns = @JoinColumn(name = "PermissionGrantId"))
	private Set<SettingPermissionGrant> grants = new HashSet<>();

	protected SettingUserGroup() {
	}

	public SettingUserGroup(String groupName, Set<SettingUser> members, Set<SettingPermissionGrant> grants) {
		this.groupName = groupName;
		this.members = members;
		this.grants = grants;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Set<SettingUser> getMembers() {
		return members;
	}

	public void setMembers(Set<SettingUser> members) {
		this.members = members;
	}

	public Set<SettingPermissionGrant> getGrants() {
		return grants;
	}

	public void setGrants(Set<SettingPermissionGrant> grants) {
		this.grants = grants;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SettingUserGroup)) return false;

		SettingUserGroup that = (SettingUserGroup) o;

		if (groupId != that.groupId) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return (int) (groupId ^ (groupId >>> 32));
	}
}
