package com.zycx.frame.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.zycx.frame.exception.CodeExpiredException;
import com.zycx.frame.exception.NoLoginException;
import com.zycx.frame.exception.PermitDeniedException;
import com.zycx.frame.util.DPUtil;

/**
 * 框架核心控制拦截器
 */
public class ControllerInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 1L;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Object object = invocation.getAction();
		if(object instanceof ControllerBase) {
			ControllerBase controller = (ControllerBase) object;
        	try {
    			controller.init(invocation);
				String actionResult = invocation.invoke();
				controller.destroy(actionResult);
				return actionResult;
			} catch (NoSuchMethodException exception) {
				String actionResult = controller.displayTemplate();
				controller.destroy(actionResult);
				return actionResult;
			} catch (NoLoginException exception) {
				return controller.memberException(exception);
			} catch (CodeExpiredException exception) {
				return controller.memberException(exception);
			} catch (PermitDeniedException exception) {
				return controller.memberException(exception);
			} catch (Exception exception) {
				exception.printStackTrace();
				throw exception;
			}
		} else if("com.opensymphony.xwork2.ActionSupport".equals(object.getClass().getName())) {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			String queryString = request.getQueryString();
			String url = request.getRequestURL().toString();
			if(!DPUtil.empty(queryString)) url += "?" + queryString;
			String str = invocation.getInvocationContext().getName();
			url = url.replace("/" + str, "!" + str);
			//request.getRequestDispatcher(url).forward(request,response);
			response.sendRedirect(url);
			return null;
		}
		return invocation.invoke();
	}

	@Inject
	public void setSuffix(@Inject("struts.convention.action.suffix") String suffix) {
		GlobalParameters.STRUTS_SUFFIX = suffix;
	}

	@Inject
	public void setPackages(@Inject("struts.convention.action.packages") String packages) {
		GlobalParameters.STRUTS_PACKAGES = packages;
	}
}
