package com.self.dailyweather.db;

import java.util.ArrayList;
import java.util.List;

import com.self.dailyweather.model.City;
import com.self.dailyweather.model.County;
import com.self.dailyweather.model.Province;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DailyWeatherDao {
	public static final String DB_NAME="dailyweather.db";
	private static DailyWeatherDao dao;
	private SQLiteDatabase db;
	
	//构造方法私有化
	private DailyWeatherDao(Context context){
		MyOpenHelper helper=new MyOpenHelper(context, DB_NAME, null, 1);
		db=helper.getWritableDatabase();
	}
	//获取DailyWeatherDao的实例
	public synchronized static DailyWeatherDao getInstance(Context context){
		if(dao==null){
			dao=new DailyWeatherDao(context);
		}
		return dao;
	}
	//
	public void saveProvince(Province province){
		if(province!=null){
			ContentValues values=new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("province", null, values);
		}
	}
	
	public List<Province> loadProvinces(){
		List<Province> list=new ArrayList<Province>();
		Cursor cursor=db.query("province", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				Province province=new Province();
				province.setId(cursor.getInt(0));
				province.setProvinceName(cursor.getString(1));
				province.setProvinceCode(cursor.getString(2));
				list.add(province);
			}while(cursor.moveToNext());
		}
		return list;
	}
	
	//
	public void saveCity(City city){
		if(city!=null){
			ContentValues values=new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert("city", null, values);
		}
	}
	
	public List<City> loadCities(int provinceId){
		List<City> list=new ArrayList<City>();
		Cursor cursor=db.query("city", null, "province_id=?", new String[]{String.valueOf(provinceId)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				City city=new City();
				city.setId(cursor.getInt(0));
				city.setCityName(cursor.getString(1));
				city.setCityCode(cursor.getString(2));
				city.setProvinceId(provinceId);
				list.add(city);
			}while(cursor.moveToNext());
		}
		return list;
	}
	
	//
	public void saveCounty(County county){
		if(county!=null){
			ContentValues values=new ContentValues();
			values.put("county_name", county.getCountyName());
			values.put("county_code", county.getCountyCode());
			values.put("city_id", county.getCityId());
			db.insert("county", null, values);
		}
	}
	
	public List<County> loadCounties(int cityId){
		List<County> list=new ArrayList<County>();
		Cursor cursor=db.query("county", null, "city_id=?", new String[]{String.valueOf(cityId)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				County county=new County();
				county.setId(cursor.getInt(0));
				county.setCountyName(cursor.getString(1));
				county.setCountyCode(cursor.getString(2));
				county.setCityId(cityId);
				list.add(county);
			}while(cursor.moveToNext());
		}
		return list;
	}
}
