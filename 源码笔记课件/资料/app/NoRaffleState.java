package com.atguigu.app;

/**
 * @author Administrator
 *
 */
public class NoRaffleState extends State {

	 // 初始化时传入活动引用，扣除积分后改变其状态
    RaffleActivity activity;

    public NoRaffleState(RaffleActivity activity) {
        this.activity = activity;
    }

    @Override
    public void deductMoney() {
        System.out.println("扣除50积分成功，您可以抽奖了");
        activity.setState(activity.getCanRaffleState());
    }

    @Override
    public boolean raffle() {
        System.out.println("扣了积分才能抽奖喔！");
        return false;
    }

    @Override
    public void dispensePrize() {
        System.out.println("不能发放奖品");
    }
}
