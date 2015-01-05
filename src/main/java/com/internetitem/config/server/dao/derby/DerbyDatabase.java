package com.internetitem.config.server.dao.derby;

import com.internetitem.config.server.dao.rdbms.AbstractRdbmsDatabaseAccess;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
@ConditionalOnProperty(name = "derby.url")
public class DerbyDatabase extends AbstractRdbmsDatabaseAccess {

	public DerbyDatabase() {
		super("derby");
	}

	@Override
	protected Date getEndOfTime() {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.YEAR, 9999);
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		return cal.getTime();
	}
}
