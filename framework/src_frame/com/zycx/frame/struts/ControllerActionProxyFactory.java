package com.zycx.frame.struts;
import org.apache.struts2.impl.StrutsActionProxyFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;

public class ControllerActionProxyFactory extends StrutsActionProxyFactory {
	@Override
    public ActionProxy createActionProxy(ActionInvocation inv, String namespace, String actionName, String methodName, boolean executeResult, boolean cleanupContext) {
        
		ControllerActionProxy proxy = new ControllerActionProxy(inv, namespace, actionName, methodName, executeResult, cleanupContext);
        container.inject(proxy);
        proxy.prepare();
        return proxy;
    }
}
