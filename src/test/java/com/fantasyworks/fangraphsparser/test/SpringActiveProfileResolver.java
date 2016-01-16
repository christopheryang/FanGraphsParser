package com.fantasyworks.fangraphsparser.test;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfilesResolver;

/**
 *
 * @author Christopher Yang
 */
public class SpringActiveProfileResolver implements ActiveProfilesResolver {
	
	private static final Logger logger = LoggerFactory.getLogger(SpringActiveProfileResolver.class);
	
	@Override
	public String[] resolve(final Class<?> aClass) {
		final String activeProfile = System.getProperties().getProperty("spring.profiles.active");

		// If none is specified, "dev" is used as the default profile
		if(activeProfile==null){
			logger.info("Active Spring Profile not found in system property. Using 'dev' by default.");
			return new String[]{"dev"};
		}
		else{
			logger.info("Active Spring Profile: "+activeProfile);
			return new String[]{activeProfile};
		}
	}
	
}
