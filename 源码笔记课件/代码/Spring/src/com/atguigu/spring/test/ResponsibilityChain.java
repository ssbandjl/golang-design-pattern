package com.atguigu.spring.test;

import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerInterceptor;

public class ResponsibilityChain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// DispatcherServlet 
		
		//说明
		/*
		 * 
		 *  protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 *   HandlerExecutionChain mappedHandler = null; 
		 *   mappedHandler = getHandler(processedRequest);//获取到HandlerExecutionChain对象
		 *    //在 mappedHandler.applyPreHandle 内部 得到啦 HandlerInterceptor interceptor
		 *    //调用了拦截器的  interceptor.preHandle
		 *   if (!mappedHandler.applyPreHandle(processedRequest, response)) {
					return;
				}
				
			  //说明：mappedHandler.applyPostHandle 方法内部获取到拦截器，并调用 
			  //拦截器的  interceptor.postHandle(request, response, this.handler, mv);
			 mappedHandler.applyPostHandle(processedRequest, response, mv);
		 *  }
		 *  
		 *  
		 *  //说明：在  mappedHandler.applyPreHandle内部中，
		 *  还调用了  triggerAfterCompletion 方法，该方法中调用了  
		 *  HandlerInterceptor interceptor = getInterceptors()[i];
			try {
				interceptor.afterCompletion(request, response, this.handler, ex);
			}
			catch (Throwable ex2) {
				logger.error("HandlerInterceptor.afterCompletion threw exception", ex2);
			}
		 */
	
	}

}
