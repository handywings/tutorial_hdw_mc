package com.hdw.mccable.security;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.hdw.mccable.entity.Authentication;
import com.hdw.mccable.entity.Function;

public class Permission extends BodyTagSupport{
	private static final long serialVersionUID = 1667538059403710649L;
	
	public void setObject(String object){
        setValue("object", object);
    }
	
	@Override
	public int doStartTag() throws JspException {
		return BodyTag.EVAL_BODY_BUFFERED;
	}

	@Override
	public int doAfterBody() throws JspException {
		JspWriter out = bodyContent.getEnclosingWriter();
        String text = bodyContent.getString();
        
        Set<String> permission = new HashSet<String>();
        HttpSession session = pageContext.getSession();
        Authentication authen = (Authentication)session.getAttribute("authen");
        if(authen != null){
        	for(Function function : authen.getPersonnel().getPermissionGroup().getFunctions()){
        		//isCheck
        		if(function.isCheck()){
        			permission.add(function.getKeyName()+".check");
        		}
        		//view
        		if(function.isView()){
        			permission.add(function.getKeyName()+".view");
        		}
        		//add
        		if(function.isAdd()){
        			permission.add(function.getKeyName()+".add");
        		}
        		//edit
        		if(function.isEdit()){
        			permission.add(function.getKeyName()+".edit");
        		}
        		//delete
        		if(function.isDelete()){
        			permission.add(function.getKeyName()+".delete");
        		}
        		
        	}
        }
		 
		 try{
			if (permission != null && permission.contains((String) getValue("object"))) {
				 out.print(text);
			 }else{
				 out.print("");
			 }
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SKIP_BODY;
	}
	
}
