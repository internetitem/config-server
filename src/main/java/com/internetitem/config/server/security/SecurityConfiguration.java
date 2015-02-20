package com.internetitem.config.server.security;

import com.internetitem.config.server.db.dataModel.*;
import com.internetitem.config.server.services.dataModel.PermissionItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private SettingsUserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.anyRequest().authenticated()
				.and()
			.httpBasic();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	@Bean
	@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public PermissionSet permissionList() {
		LoggedInUserDetails details = (LoggedInUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<PermissionItem> permissions = new ArrayList<>();
		for (SettingPermissionGrant grant : details.getGrants()) {
			SettingPermissionType permissionType = grant.getPermissionType();
			SettingPermissionGrantType grantType = grant.getGrantType();

			SettingApplicationGroup applicationGroup = grant.getApplicationGroup();
			Long applicationGroupId = null;
			if (applicationGroup != null) {
				applicationGroupId = Long.valueOf(applicationGroup.getApplicationGroupId());
			}
			SettingApplication application = grant.getApplication();
			Long applicationId = null;
			if (application != null) {
				applicationId = Long.valueOf(application.getApplicationId());
			}
			SettingComponent component = grant.getComponent();
			Long componentId = null;
			if (component != null) {
				componentId = Long.valueOf(component.getComponentId());
			}

			PermissionItem pi = new PermissionItem(grantType, permissionType, applicationGroupId, applicationId, componentId);
			permissions.add(pi);
		}
		return new PermissionSet(permissions);
	}

}
