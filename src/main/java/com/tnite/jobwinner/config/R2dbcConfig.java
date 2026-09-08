package com.tnite.jobwinner.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.mapping.R2dbcMappingContext;
import org.springframework.data.relational.RelationalManagedTypes;
import org.springframework.data.relational.core.mapping.DefaultNamingStrategy;
import org.springframework.data.relational.core.mapping.NamingStrategy;

@Configuration
public class R2dbcConfig {

	@Bean
	public R2dbcMappingContext r2dbcMappingContext(
			ObjectProvider<NamingStrategy> namingStrategy,
			R2dbcCustomConversions r2dbcCustomConversions,
			RelationalManagedTypes r2dbcManagedTypes) {
		R2dbcMappingContext context = R2dbcMappingContext.forPlainIdentifiers(
				namingStrategy.getIfAvailable(() -> DefaultNamingStrategy.INSTANCE));
		context.setSimpleTypeHolder(r2dbcCustomConversions.getSimpleTypeHolder());
		context.setManagedTypes(r2dbcManagedTypes);
		return context;
	}
}
