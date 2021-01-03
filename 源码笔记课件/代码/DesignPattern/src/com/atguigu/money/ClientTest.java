package com.atguigu.money;

/**测试类*/
public class ClientTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Context context = new Context();
        context.setState(new PublishState());
        //然后可以根据操作变化状态.
        
        //publish --> not pay
       context.acceptOrderEvent(context);
//        //not pay --> paid
        context.payOrderEvent(context);
//        // 失败, 检测失败时，会抛出异常
//        try {
//        	context.checkFailEvent(context);
//        	System.out.println("流程正常..");
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println(e.getMessage());
//		}
        
	}

}
