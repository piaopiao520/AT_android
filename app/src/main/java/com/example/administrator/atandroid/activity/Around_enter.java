package com.example.administrator.atandroid.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.atandroid.R;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.example.administrator.atandroid.Citythree.CityModel;
import com.example.administrator.atandroid.Citythree.DistrictModel;
import com.example.administrator.atandroid.Citythree.ProvinceModel;
import com.example.administrator.atandroid.Citythree.ScreenUtils;
import com.example.administrator.atandroid.Citythree.XmlParserHandler;
import com.example.administrator.atandroid.Citythree.base.TitleBaseActivity;
import wigdet.OnWheelChangedListener;
import wigdet.WheelView;
import wigdet.adapters.ArrayWheelAdapter;



public class Around_enter extends TitleBaseActivity implements View.OnClickListener, OnWheelChangedListener {
    /**
     * 所有省
     */
    protected String[] mProvinceData;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCityDataMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDataMap = new HashMap<String, String[]>();

    /**
     * 当前省的名称
     */
    protected String mCurrentProvinceName;
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName = "";
    private TextView mTxtCity;
    private Dialog mCityDialog;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private Button  biaoqian1,biaoqian2,biaoqian3,biaoqian4,biaoqian5,biaoqian6,Around_hold;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initCityPicker();
    }

    private void init() {
        mTxtCity = (TextView) findViewById(R.id.text_city);
        biaoqian1 = ((Button) findViewById(R.id.biaoqian1));
        biaoqian2 = ((Button) findViewById(R.id.biaoqian2));
        biaoqian3 = ((Button) findViewById(R.id.biaoqian3));
        biaoqian4 = ((Button) findViewById(R.id.biaoqian4));
        biaoqian5 = ((Button) findViewById(R.id.biaoqian5));
        biaoqian6 = ((Button) findViewById(R.id.biaoqian6));
        Around_hold = ((Button) findViewById(R.id.Around_hold));
        mTxtCity.setOnClickListener(this);
        biaoqian1.setOnClickListener(this);
        biaoqian2.setOnClickListener(this);
        biaoqian3.setOnClickListener(this);
        biaoqian4.setOnClickListener(this);
        biaoqian5.setOnClickListener(this);
        biaoqian6.setOnClickListener(this);
        Around_hold.setOnClickListener(this);
    }

    @Override
    public int getContentResId() {
        return R.layout.activity_around_enter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_city:
                mCityDialog.show();
                break;
            case R.id.cancel_city:
                mCityDialog.dismiss();
                break;
            case R.id.sure_city:
                if (mCurrentProvinceName.equals(mCurrentCityName)) {
                    mCurrentCityName = "";
                }
                mTxtCity.setText(mCurrentProvinceName + "-"+mCurrentCityName + "-"+mCurrentDistrictName);
                mCityDialog.dismiss();
                break;
            case R.id.biaoqian1:
                biaoqian1.setBackgroundResource(R.color.buttonRed);
                break;
            case R.id.biaoqian2:
                biaoqian2.setBackgroundResource(R.color.buttonRed);
                break;
            case R.id.biaoqian3:
                biaoqian3.setBackgroundResource(R.color.buttonRed);
                break;
            case R.id.biaoqian4:
                biaoqian4.setBackgroundResource(R.color.buttonRed);
                break;
            case R.id.biaoqian5:
                biaoqian5.setBackgroundResource(R.color.buttonRed);
                break;
            case R.id.biaoqian6:
                biaoqian6.setBackgroundResource(R.color.buttonRed);
                break;
            case R.id.Around_hold:
                Around_Hold();
        }
    }

    private void Around_Hold() {
        Toast.makeText(this, "提交成功！请耐心等待审核", Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent();
        intent1.setClass(this,Around.class);
        startActivity(intent1);
    }

    private void initCityPicker() {
        mCityDialog = new Dialog(this, R.style.CustomDialog);
        Window window = mCityDialog.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        // no frame
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mCityDialog.setContentView(R.layout.dialog_city_picker);
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.bottom_animation);

        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ScreenUtils.getWidth(this);
        window.setAttributes(params);
        mViewProvince = (WheelView) mCityDialog.findViewById(R.id.province_picker_dialog);
        mViewCity = (WheelView) mCityDialog.findViewById(R.id.city_picker_dialog);
        mViewDistrict = (WheelView) mCityDialog.findViewById(R.id.district_picker_dialog);
        mCityDialog.findViewById(R.id.cancel_city).setOnClickListener(this);
        mCityDialog.findViewById(R.id.sure_city).setOnClickListener(this);
        mViewProvince.addChangingListener(this);
        mViewCity.addChangingListener(this);
        mViewDistrict.addChangingListener(this);
        setUpData();
    }

    private void setUpData() {
        initProvinceData();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<>(this, mProvinceData));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();
    }

    public void initProvinceData() {
        /**
         * 解析省市区的XML数据
         */
        List<ProvinceModel> provinceList = null;
        AssetManager asset = getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            //*/ 初始化默认选中的省、市、区
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProvinceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                }
            }
            //*/
            mProvinceData = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceData[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    // 市-区/县的数据，保存到mDistrictDataMap
                    mDistrictDataMap.put(cityNames[j], distrinctNameArray);
                }
                // 省-市的数据，保存到mCityDataMap
                mCityDataMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
        }
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDataMap.get(mCurrentCityName)[newValue];
        }
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCityDataMap.get(mCurrentProvinceName)[pCurrent];
        String[] areas = mDistrictDataMap.get(mCurrentCityName);
        if (areas == null) {
            areas = new String[]{""};
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<>(this, areas));
        mViewDistrict.setCurrentItem(0);
        mCurrentDistrictName = areas[0];
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProvinceName = mProvinceData[pCurrent];
        String[] cities = mCityDataMap.get(mCurrentProvinceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<>(this, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }
}
