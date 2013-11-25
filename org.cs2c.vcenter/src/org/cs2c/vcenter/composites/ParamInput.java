/**
 * 
 */
package org.cs2c.vcenter.composites;

import org.cs2c.nginlib.config.Parameter;
import org.cs2c.vcenter.metadata.ParameterMeta;

/**
 * @author CS2C
 *
 */
public interface ParamInput {

	void setMeta(ParameterMeta meta);
	
	Parameter getParameter();
}
