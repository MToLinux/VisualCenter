package org.cs2c.vcenter.composites;

import java.util.List;

import org.cs2c.nginlib.config.Parameter;
import org.cs2c.vcenter.metadata.ParameterMeta;
import org.eclipse.swt.widgets.Composite;

public class BaseParamInput extends Composite implements ParamInput {

	public BaseParamInput(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	public void setMeta(ParameterMeta meta) {

	}

	@Override
	public Parameter getParameter() {
		return null;
	}
	
	public void setInputData(List<Parameter> params){
	}
	
	public static boolean isNum(String str){
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
	
	public static boolean isnotIntParam(String str)
	{
		int i;
		for (i = str.length();--i>=0;)
		{
			if (Character.isDigit(str.charAt(i)))
			{
				break;
			}
		}
		
		if(isNum(str) || (i>0 && isNum(str.substring(0, i+1))) )
		{
			return false;
		}
		
		return true;
	}
	
	public static String deleteExtraSpace(String str)
	{  
        if(str==null)
        {  
            return null;  
        }  
        if(str.length()==0 || str.equals(" "))
        {  
            return new String();  
        }  
        char[] oldStr=str.toCharArray();  
        int len=str.length();  
        char[] tmpStr=new char[len];  
        boolean keepSpace=false;  
        int j=0;
        for(int i=0;i<len;i++)
        {  
            char tmpChar=oldStr[i];  
            if(oldStr[i]!=' ')
            {  
                tmpStr[j++]=tmpChar;  
                keepSpace=true;  
            }
            else if(keepSpace)
            {  
                tmpStr[j++]=tmpChar;  
                keepSpace=false;  
            }  
        }  
          
        int newLen=j;  
        if(tmpStr[j-1]==' ')
        {  
            newLen--;  
        }  
        char[] newStr=new char[newLen];  
        for(int i=0;i<newLen;i++)
        {  
            newStr[i]=tmpStr[i];  
        }  
        return new String(newStr);
	}


}
