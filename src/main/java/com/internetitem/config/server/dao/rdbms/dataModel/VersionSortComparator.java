package com.internetitem.config.server.dao.rdbms.dataModel;

import com.internetitem.config.server.dao.rdbms.dataModel.RawSetting;

public class VersionSortComparator implements java.util.Comparator<RawSetting> {

	private int curVersion;

	public VersionSortComparator(int curVersion) {
		this.curVersion = curVersion;
	}

	@Override
	public int compare(RawSetting o1, RawSetting o2) {
		if (o1.getFromVersion() == null && o2.getFromVersion() == null) {
			return 0;
		}
		if (o1.getFromVersion() == null) {
			return 1;
		}
		if (o2.getFromVersion() == null) {
			return -1;
		}
		int diff1 = versionDiff(o1);
		int diff2 = versionDiff(o2);
		return Integer.compare(diff1, diff2);
	}

	public int versionDiff(RawSetting setting) {
		return Math.abs(curVersion - setting.getFromVersionOrder()) + Math.abs(curVersion - setting.getToVersionOrder());
	}
}
