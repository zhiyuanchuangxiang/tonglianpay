package com.zycx.frame.struts;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.impl.StrutsActionProxy;

import com.opensymphony.xwork2.ActionInvocation;

public class ControllerActionProxy extends StrutsActionProxy {

	private static final long serialVersionUID = 1L;

	public ControllerActionProxy(ActionInvocation inv, String namespace,
			String actionName, String methodName, boolean executeResult,
			boolean cleanupContext) {
		super(inv, namespace, actionName, StringUtils.isEmpty(methodName) ? methodName : methodName + GlobalParameters.STRUTS_ACTION_SUFFIX, executeResult, cleanupContext);
	}

	@Override
    protected void prepare() {
        super.prepare();
    }
}
