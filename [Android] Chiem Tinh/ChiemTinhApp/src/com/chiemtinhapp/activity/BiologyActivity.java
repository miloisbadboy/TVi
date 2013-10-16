package com.chiemtinhapp.activity;

import java.util.Calendar;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.chiemtinhapp.R;
import com.chiemtinhapp.application.ChiemTinhApplication;
import com.chiemtinhapp.helper.DateFormatHelper;
import com.chiemtinhapp.model.User;

public class BiologyActivity extends Activity{

	private GraphicalView mChart;

    private XYMultipleSeriesDataset multipleDataset = new XYMultipleSeriesDataset();

    private XYMultipleSeriesRenderer multipleRenderer = new XYMultipleSeriesRenderer();
    
    private String[] titles = {"Sức khỏe", "Tình cảm", "Trí tuệ", "Trực giác"};
    private int[] colors = {Color.RED, Color.BLUE, Color.MAGENTA, Color.GREEN };
    
    private int TOTAL = 30;
    
    // Day limitation;
    private int COUNT = 10;

    private void initChart() {
    	// Add 4 lines
        for (int i = 0; i < 4; i++) {
        	XYSeries series = new XYSeries(titles[i]);
        	XYSeriesRenderer renderer = new XYSeriesRenderer();
        	renderer.setColor(colors[i]);
        	
        	multipleDataset.addSeries(series);
        	multipleRenderer.addSeriesRenderer(renderer);
        }
    	
        // Showing 10 days
        multipleRenderer.setXAxisMax(COUNT * 10);
        
        // Disable vertical panning
        multipleRenderer.setPanEnabled(true, false);
        
        // Limit on panning
        multipleRenderer.setPanLimits(new double[] {0, COUNT * TOTAL, 0, 0});
        
        multipleRenderer.setGridColor(Color.CYAN);
        multipleRenderer.setXLabels(0);
        
        // Custom label.
        for (int i = 0; i < 30; i++) {
        	Calendar date = Calendar.getInstance();
        	date.add(Calendar.DATE, i);
        	multipleRenderer.addXTextLabel(i * 10, DateFormatHelper.displayFormatter.format(date.getTime()));
        }
        
        for (int i = 0; i < 11; i++) {
        	multipleRenderer.addYTextLabel(i * 10,  i * 10 + "%");
        }
        multipleRenderer.setXLabelsColor(Color.BLACK);
        multipleRenderer.setYLabelsColor(0, Color.BLACK);
        multipleRenderer.setYLabelsAlign(Align.RIGHT);
        multipleRenderer.setShowCustomTextGrid(true);
        
        multipleRenderer.setBackgroundColor(Color.parseColor("#F5F5F5"));
        multipleRenderer.setMarginsColor(Color.parseColor("#F5F5F5"));
        multipleRenderer.setApplyBackgroundColor(true);
    }

    private void addSampleData() {
    	for (int i = 0; i < 4; i++) {
    		XYSeries series = multipleDataset.getSeriesAt(i);
    		calculateData(series, 23 + i * 5, TOTAL);
    	}
    	double healthToday = multipleDataset.getSeriesAt(0).getY(1);
    	Log.d("TEST", "" + healthToday);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biology);
    }

    protected void onResume() {
        super.onResume();
        LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
        if (mChart == null) {
            initChart();
            addSampleData();
            mChart = ChartFactory.getCubeLineChartView(this, multipleDataset, multipleRenderer, 0.3f);
            mChart.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 400));
            layout.addView(mChart);
        } else {
            mChart.repaint();
        }
    }
    
    /**
     * 
     * @param series 
     * @param cycle 
     * @param count number of days
     */
    private void calculateData(XYSeries series, int cycle, int count) {
    	User user = ((ChiemTinhApplication) getApplication()).getSelectedUser();
    	long birthtime = user.getBirthday().getTime();
    	long now = Calendar.getInstance().getTimeInMillis();
    	
    	long dayDiff = (now - birthtime) / 86400000;
    	
    	for (int i = 0; i < count + 1; i++) {
    		double angle = (double) ((dayDiff + i) % cycle) / cycle;
    		double yValue = 100 * (Math.sin(2 * Math.PI * angle) + 1) / 2;
    		series.add(i * 10, yValue);
    	}
    }
    
    
}
