package com.vexcellent.myjni.file;

import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.amap.api.maps.CoordinateConverter;
import com.amap.api.maps.model.LatLng;
import com.dianxiaoer.dutillibrary.file.FileUtils;
import com.vexcellent.myjni.R;
import com.vexcellent.myjni.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class ReadFileActivity extends BaseActivity {

    @BindView(R.id.read)
    Button read;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.read_ass)
    Button readAss;
    @BindView(R.id.read_class)
    Button readClass;

    String filePath = Environment.getExternalStorageDirectory().getPath() + "/AAAA/";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_read_file;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.read, R.id.read_ass, R.id.read_class})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.read:
                String file = null;
                try {
                    file = FileUtils.doReadFile("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (null == file) {
                    return;
                }
                doToBean(file);
                break;
            case R.id.read_ass:
                text.setText("正在读取中");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        String ass = doReadFileFromAss(getBaseContext(), "xunlian.geojson");
                        String ass = FileUtils.doReadFileFromAss(getBaseContext(), "xun.geojson");
                        doToBean(ass);
                    }
                }).start();

                break;
            case R.id.read_class:
//                doToBean(Text.msg);
//                String string = getResources().getString(R.string.xun);
//                DToast.error(this,string.length()+ "");
                break;
        }
    }

    private void doToBean(String file) {

        Bean bean = JSON.parseObject(file, Bean.class);
        if (bean.getFeatures().size() == 0) {

            text.setText("读取失败");
            return;
        }


//        StringBuffer stringBuffer = new StringBuffer();
//        for (Bean.FeaturesBean feature : bean.getFeatures()) {
//            List<List<Double>> lists = feature.getGeometry().getCoordinates().get(0).get(0);
//
//            List<Double> doubles = lists.get(0);
//            String s = doubles.get(0).toString();
//            String s1 = doubles.get(1).toString();
//            stringBuffer.append(s);
//            stringBuffer.append(",");
//            stringBuffer.append(s1);
//            stringBuffer.append("\n\n");
//        }

//        }


        BeanPolygon polygon = new BeanPolygon();
        final ArrayList<BeanPolygon.Polygon> polygons = new ArrayList<BeanPolygon.Polygon>();

//        final StringBuffer buffer = new StringBuffer();
        for (Bean.FeaturesBean feature : bean.getFeatures()) {

            List<List<List<List<Double>>>> coordinates = feature.getGeometry().getCoordinates();

            final ArrayList latLngs = new ArrayList<>();
            for (List<Double> doubles : coordinates.get(0).get(0)) {
//                String s2 = doubles.get(0).toString();
//                String s3 = doubles.get(1).toString();
//
                Double s = doubles.get(0);
                Double s1 = doubles.get(1);
//                buffer.append(s);
//                buffer.append(", ");
//                buffer.append(s1);
//                buffer.append("\n");

                LatLng latLng = new LatLng(s1, s);
                latLngs.add(ToGao(latLng));
            }
//            buffer.append(latLngs.toString());
//            buffer.append("\n------------\n");

            BeanPolygon.Polygon polygon1 = new BeanPolygon.Polygon();
            polygon1.setLatLngs(latLngs);
            polygons.add(polygon1);
        }

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                text.setText(polygons.toString());
////                text.setText(buffer.toString());
//            }
//        });


        polygon.setData(polygons);
        try {
            FileUtils.doWriteFile(JSON.toJSONString(polygon), filePath, "com/vexcellent/myjni/file/xunLian.txt");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text.setText("完成");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text.setText("失败");
                }
            });
        }


    }

    private CoordinateConverter converter;

    private LatLng ToGao(LatLng sourceLatLng) {
        if (converter == null) {
            converter = new CoordinateConverter(this);
        }
        // CoordType.GPS 待转换坐标类型
        // 执行转换操作
        converter.from(CoordinateConverter.CoordType.GPS).coord(sourceLatLng);
        LatLng desLatLng = converter.convert();
        return desLatLng;
    }
}
