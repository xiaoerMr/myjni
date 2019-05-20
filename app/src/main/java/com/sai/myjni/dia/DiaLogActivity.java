package com.sai.myjni.dia;


import android.view.Gravity;
import android.view.View;

import com.sai.myjni.R;
import com.sai.myjni.base.BaseActivity;
import com.sai.myjni.livedata.SaiLiveDateBus;
import com.sai.sailib.smartdialog.ChooseListDialog;
import com.sai.sailib.smartdialog.ChooseResult;
import com.sai.sailib.smartdialog.ClickListDialog;
import com.sai.sailib.smartdialog.DialogBtnClickListener;
import com.sai.sailib.smartdialog.EnsureDialog;
import com.sai.sailib.smartdialog.InputTextDialog;
import com.sai.sailib.smartdialog.NotificationDialog;
import com.sai.sailib.smartdialog.SingleBigTextDialog;
import com.sai.sailib.smartdialog.SmartDialog;
import com.sai.sailib.toast.DToast;

import java.util.Arrays;

import butterknife.OnClick;

/**
 * @author dianxiaoer
 */
public class DiaLogActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dia_log;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.button, R.id.button2, R.id.button3,
            R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button10, R.id.button11,
            R.id.button4,R.id.button5,R.id.testlivedatabus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //一个按钮
            case R.id.button:
                singleBut();
                break;
            //两个按钮
            case R.id.button2:
                singleDef();
                break;
            //三个按钮
            case R.id.button3:
                singleThree();
                break;
            //单选
            case R.id.button4:
                break;
            //多选
            case R.id.button5:
                new SingleBigTextDialog()
                        .setShowScrollBar(true)
                        .title("请选择语言")
                        .defaultChoosePos(0)
                        .checkMarkPos(Gravity.LEFT)
                        .checkMarkColorRes(R.color.smairtColorPrimaryDark)
                        .choiceMode(ChooseListDialog.CHOICE_MODE_SINGLE)
                        .keepChosenPosByLast(true)
                        .items(new String[]{
                                "上海:\n外媒指出，百思买等商店此前曾出现过智能手机配件自动售货机，但像Straight Talk这样的公司五年前已推出类似的设备。消费者可以在美国一些沃尔玛商店的自动售货机上购买自己的Straight Talk智能手机。摩托罗拉在2006年也曾推出手机自动售货机",
                                "北京:\n小米近日推出了第一款智能手机自动售货机。第一个“Mi Express”自动售货机位于班加罗尔的Manyata科技园区。消费者可以使用UPI帐户（智能手机支付）、现金、借记卡、信用卡等方式进行支付",
                                "广州\n:目前印度是几个大型智能手机品牌的主要战场。它也是一种“狂野西部”环境，许多智能手机厂商在该市场难以取得进展，例如苹果公司。一加超越苹果和三星，成为2018年印度高端手机市场销量第一的品牌",
                                "深圳",
                                "杭州",
                                "青岛",
                                "苏州"
                        })
                        .confirmBtn("选好了", new DialogBtnClickListener() {
                            @Override
                            public void onBtnClick(SmartDialog dialog, int which, Object data) {
                                dialog.dismiss();
                                ChooseResult chooseResult = (ChooseResult) data;
                                String showMsg = "pos:" + Arrays.toString(chooseResult.getChoosePositions())
                                        + "\n\n"
                                        + "items:" + Arrays.toString(chooseResult.getChooseItems());
                                DToast.success(getBaseContext(),showMsg);
                            }
                        })
                        .showInActivity(this);
                break;
            //测试
            case R.id.testlivedatabus:
//                LiveDataBus.getInstance()
//                        .getChannel("TestLiveDataBus")
//                        .setValue("我是 DiaLogActivity 发送的事件");
                SaiLiveDateBus.SingletonHolder()
                        .with("TestLiveDataBus")
                        .setValue("我是Sai DiaLogActivity 发送的事件");
                break;
            // smairt 提示
            case R.id.button6:
              new NotificationDialog()
                      .message("重置成功")
                      .showInActivity(this);

                break;
            // smairt 选择提示
            case R.id.button7:
                 new EnsureDialog()
//                         .secondsDelayConfirm(5)//倒计时
                        .message("确定不再关注此人？")
                        .confirmBtn("确定", new DialogBtnClickListener() {
                            @Override
                            public void onBtnClick(SmartDialog dialog, int which, Object data) {
                                dialog.dismiss();
                                DToast.warning(getApplicationContext(),"取消成功");
                            }
                        })
                         .cancelBtn("取消")
                         .showInActivity(this);
                break;
            // smairt 菜单
            case R.id.button8:
                new ClickListDialog()
                        .itemCenter(true)
                        .items(new String[]{
                                "回复刻减刻减肥了升级附加赛刻减肥了升级附加赛佛;爱上刻减肥了升级附加赛佛;爱上飞机as浪费假设了的方式飞机as浪费假设了的方式佛;爱上飞机as浪费假设了的方式肥了升级附加赛佛;爱上飞机as浪费假设了的方式",
                                "转发",
                                "私信回复刻减肥刻减肥了升级附加赛佛;爱上飞机as浪刻减肥了升级附加赛佛;爱上飞机as浪费假设了的方式费假设了的方式了升刻减肥了升级附加赛佛;爱上飞机as浪费假设了的方式级附加赛佛;爱上飞机as浪费假设了的方式",
                                "复制",
                                "举报"
                        })
                        .itemClickListener(new ClickListDialog.OnItemClickListener() {
                            @Override
                            public void onItemClick(ClickListDialog dialog, int position, Object data) {
                                dialog.dismiss();
                                DToast.warning(getApplicationContext(),data.toString());
                            }
                        })
                        .showInActivity(this);
                break;
            // smairt 多选
            case R.id.button9:
               new ChooseListDialog()
                        .title("你喜欢哪个城市")
                        .defaultChoosePos(0, 1)
                        .choiceMode(ChooseListDialog.CHOICE_MODE_MULTIPLE)
                        .keepChosenPosByLast(true)
                        .setShowScrollBar(true)
                        .items(new String[]{
                                "上海:司法局啊立刻减肥了升级附加刻减肥了升级附加赛佛;爱上飞机as浪费假设了的方式赛佛;爱上飞机as浪费假设了的刻减肥了升级附加赛佛;爱上飞机as浪费假设了的方式方式地方是伐啦交首付傻了点积分",
                                "北京:司法局啊立刻减肥了升级附加刻减肥了升级附加赛佛;爱上飞机as浪费假设了的方式赛佛;爱上飞机as浪费假设了的方式刻减肥了升级附加赛佛;爱上飞机as浪费假设了的方式地方是伐啦交首付傻了点积分",
                                "北京",
                                "广州",
                                "深圳",
                                "杭州",
                                "青岛",
                                "苏州"
                        })
                        .confirmBtn("选好了", new DialogBtnClickListener() {
                            @Override
                            public void onBtnClick(SmartDialog dialog, int which, Object data) {
                                dialog.dismiss();
                                ChooseResult chooseResult = (ChooseResult) data;
                                String showMsg = "pos:" + Arrays.toString(chooseResult.getChoosePositions())
                                        + "\n\n"
                                        + "items:" + Arrays.toString(chooseResult.getChooseItems());
                                DToast.success(getBaseContext(),showMsg);
                            }
                        })
                        .showInActivity(this);
                break;
            // smairt 单选
            case R.id.button10:
                 new ChooseListDialog()
                        .title("请选择语言")
                        .defaultChoosePos(0)
                        .checkMarkPos(Gravity.LEFT)
                        .checkMarkColorRes(R.color.smairtColorPrimaryDark)
                        .choiceMode(ChooseListDialog.CHOICE_MODE_SINGLE)
                        .keepChosenPosByLast(true)
                        .items(new String[]{
                                "Java:",
                                "C++:"

                        })
                        .confirmBtn("确定", new DialogBtnClickListener() {
                            @Override
                            public void onBtnClick(SmartDialog dialog, int which, Object data) {
                                dialog.dismiss();
                                ChooseResult chooseResult = (ChooseResult) data;
                                String showMsg = "pos:" + Arrays.toString(chooseResult.getChoosePositions())
                                        + "\n\n"
                                        + "items:" + Arrays.toString(chooseResult.getChooseItems());
                                DToast.success(getBaseContext(),showMsg);
                            }
                        })
                         .showInActivity(this);

        break;
        //输入框
            case R.id.button11:
                new InputTextDialog()
                        .title("输入")
                        .textOfDefaultFill("默认填充的文本")
                        .hint("请输入建议")
                        .inputAtMost(50)
                        .clearInputPerShow(true)
                        .confirmBtn("确定", new DialogBtnClickListener() {
                            @Override
                            public void onBtnClick(SmartDialog dialog, int which, Object data) {
                                if (data.toString().length() > 50) {
                                    DToast.success(getBaseContext(),"最多输入50个字");
                                } else {
                                    dialog.dismiss();
                                    DToast.success(getBaseContext(),"输入的内容为：" + data.toString());
                                }
                            }
                        })
                        .showInActivity(this);
        break;
            default:
        }
    }
    //三个按钮
    private void singleThree() {

        final DialogUtils utils =  DialogUtils.newInstance(DialogUtils.DialogTypeThree,"3个按钮","收款方技术积分乐斯菲斯几十块的飞机撒发送费静安寺发送方式福建省安防","重置");
        utils.setThreeListener(new DialogThreeListener() {
            @Override
            public void OkListener() {
                utils.dismiss();
//                DToast.success(getBaseContext(),"成功");
            }

            @Override
            public void Cancel2Listener() {
                utils.dismiss();
            }

            @Override
            public void CancelListener() {
                utils.dismiss();
            }
        });
        utils.Show(getSupportFragmentManager());
    }

    //两个按钮
    private void singleDef() {

        final DialogUtils utils =  DialogUtils.newInstance(DialogUtils.DialogTypeDuf,"两个按钮","收款方技术积分乐斯菲斯几十块的飞机撒发送费静安寺发送方式福建省安防");
        utils.setListense(new DialogListener() {
            @Override
            public void OkListener() {
                utils.dismiss();
//                DToast.success(getBaseContext(),"成功");
            }

            @Override
            public void CancelListener() {
                utils.dismiss();
//                DToast.success(getBaseContext(),"取消");
            }
        });
        utils.Show(getSupportFragmentManager());


    }

    //一个按钮
    private void singleBut() {

        final DialogUtils utils =  DialogUtils.newInstance(DialogUtils.DialogTypeSingle,"1个按钮","收款方技术积分乐斯菲斯几十块的飞机撒发送费静安寺发送方式福建省安防");
        utils.setListense(new DialogListener() {
            @Override
            public void OkListener() {
                utils.dismiss();
//                DToast.success(getBaseContext(),"成功");
            }

            @Override
            public void CancelListener() {
                utils.dismiss();
//                DToast.success(getBaseContext(),"取消");
            }
        });
        utils.Show(getSupportFragmentManager());
    }
}
