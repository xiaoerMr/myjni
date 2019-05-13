package com.sai.myjni.dia;


import android.view.Gravity;
import android.view.View;

import com.sai.myjni.R;
import com.sai.myjni.base.BaseActivity;
import com.sai.sailib.livedata.LiveDataBus;
import com.sai.sailib.smartdialog.ChooseListDialog;
import com.sai.sailib.smartdialog.ChooseResult;
import com.sai.sailib.smartdialog.ClickListDialog;
import com.sai.sailib.smartdialog.DialogBtnClickListener;
import com.sai.sailib.smartdialog.EnsureDialog;
import com.sai.sailib.smartdialog.InputTextDialog;
import com.sai.sailib.smartdialog.NotificationDialog;
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
                break;
            //测试
            case R.id.testlivedatabus:
//                LiveDataBus.getInstance().getChannel("")
//                        .getChannel("TestLiveDataBus").setValue("我是 DiaLogActivity 发送的事件");
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
                                "回复",
                                "转发",
                                "私信回复",
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
                        .items(new String[]{
                                "上海",
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
                                "Java",
                                "Kotlin",
                                "C",
                                "C++",
                                "C#",
                                "Html"
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
