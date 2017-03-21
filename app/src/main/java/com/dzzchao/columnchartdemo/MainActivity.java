package com.dzzchao.columnchartdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initChart();
    }

    /**
     * 初始化单柱柱状图
     */
    private void initChart() {
        ColumnChartView mMySingleChartView = (ColumnChartView) findViewById(R.id.my_single_chart_view);
        List<Float> singlelist = new ArrayList<>();
        singlelist.add(100f);
        singlelist.add(90f);
        singlelist.add(20f);
        singlelist.add(80f);
        singlelist.add(50f);
        singlelist.add(30f);
        singlelist.add(80f);
        mMySingleChartView.setList(singlelist);
    }
}
