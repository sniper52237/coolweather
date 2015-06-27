package com.wj.coolweather.app.db;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.wj.coolweather.app.model.City;
import com.wj.coolweather.app.model.County;
import com.wj.coolweather.app.model.Province;

public class CoolWeatherDB {
	/**
	 * ���ݿ�����
	 */
	public static final String DB_NAME = "cool_weather";
	
	/**
	 * ���ݿ�İ汾
	 */
	public static final int VERSION = 1;
	
	private static CoolWeatherDB coolWeatherDB;
	
	private SQLiteDatabase db;
	
	private CoolWeatherDB(Context context) {
		CoolWeatherOpenHelper coolWeatherOpenHelper = new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
		db = coolWeatherOpenHelper.getWritableDatabase();
	}
	
	/**
	 * ��ȡCoolWeatherDBʵ��
	 * @param context
	 * @return
	 */
	public synchronized static CoolWeatherDB getInstance(Context context) {
		if (coolWeatherDB == null) {
			coolWeatherDB = new CoolWeatherDB(context);
		}
		return coolWeatherDB;
	}
	
	/**
	 * ��Provinceʵ���洢�����ݿ�
	 */
	private void saveProvince(Province province){
		if (province != null) {
			ContentValues cv = new ContentValues();
			cv.put("province_name", province.getProvinceName());
			cv.put("province_code", province.getProvinceCode());
			db.insert("Province", null, cv);
		}
	}
	
	/**
	 * �����ݿ��ȡ���е�ȫ��ʡ����Ϣ
	 */
	private List<Province> loadProvinces() {
		List<Province> listProvinces = new ArrayList<Province>();
		Cursor cursor = db.query("Province", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Province province = new Province();
				int id = cursor.getInt(cursor.getColumnIndex("id"));
				String provinceName = cursor.getString(cursor.getColumnIndex("province_name"));
				String provinceCode = cursor.getString(cursor.getColumnIndex("province_code"));
				province.setId(id);
				province.setProvinceName(provinceName);
				province.setProvinceCode(provinceCode);
				listProvinces.add(province);
			} while (cursor.moveToNext());
		}
		return listProvinces;
	}
	
	/**
	 * ��Cityʵ���洢�����ݿ�
	 */
	public void saveCity(City city){
		if (city != null) {
			ContentValues cv = new ContentValues();
			cv.put("city_name", city.getCityName());
			cv.put("city_code", city.getCityCode());
			cv.put("province_id", city.getProvinceId());
			db.insert("City", null, cv);
		}
	}
	
	/**
	 * �����ݿ��ж�ȡĳʡ�����еĳ�����Ϣ
	 */
	public List<City> loadCity(int provinceId) {
		List<City> listCities = new ArrayList<City>();
		Cursor cursor = db.query("City", null, "province_id = ?", new String[] {String.valueOf(provinceId)}, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setProvinceId(cursor.getInt(cursor.getColumnIndex("province_id")));
				listCities.add(city);
			} while (cursor.moveToNext());
		}
		return listCities;
	}
	
	/**
	 * ��Countryʵ���洢�����ݿ�
	 */
	public void saveCounty(County county){
		if (county != null) {
			ContentValues cv = new ContentValues();
			cv.put("county_name", county.getCountyName());
			cv.put("county_code", county.getCountyCode());
			cv.put("city_id", county.getCityId());
			db.insert("County", null, cv);
		}
	}
	
	/**
	 * �����ݿ��ж�ȡĳ�����������ص���Ϣ
	 */
	public List<County> loadCounty(int cityId) {
		List<County> listCounties = new ArrayList<County>();
		Cursor cursor = db.query("County", null, "city_id = ?", new String[]{String.valueOf(cityId)}, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				County county = new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("county_id")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
				county.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
				listCounties.add(county);
			} while (cursor.moveToNext());
		}
		return listCounties;
	}	
}
