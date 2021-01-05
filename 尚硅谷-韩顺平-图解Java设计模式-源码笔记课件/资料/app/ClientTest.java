package com.atguigu.app;

/**
 * 状态模式测试类
 * @author Administrator
 *
 */
public class ClientTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 创建活动对象，奖品池有5个奖品
        RaffleActivity activity = new RaffleActivity(1);

        // 我们连续抽三次奖
        for (int i = 0; i < 300; i++) {
            System.out.println("--------第" + (i + 1) + "次抽奖----------");
            // 参加抽奖，第一步点击扣除积分
            activity.debuctMoney();

            // 第二步抽奖
            activity.raffle();
        }
	}

}
