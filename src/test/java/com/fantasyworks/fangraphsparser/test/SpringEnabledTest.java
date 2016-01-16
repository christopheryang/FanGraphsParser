package com.fantasyworks.fangraphsparser.test;

import java.io.InputStreamReader;

import org.junit.Ignore;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;

import com.fantasyworks.fangraphsparser.app.AppContext;
import com.google.common.io.CharStreams;

/**
 * Extend this class to create a MockMVC based test class.
 * 
 * @author Christopher Yang
 */
@ContextConfiguration(classes=AppContext.class)
@Ignore
public abstract class SpringEnabledTest extends SpringEnabledWebTest {
	
	protected String fileResourceToString(Resource fileResource){
        try{
            return CharStreams.toString(new InputStreamReader(fileResource.getInputStream()));
        }
        catch(Exception ex){
            // Cannot recover, throw a RuntimException
            throw new RuntimeException(ex);
        }
    }

}
