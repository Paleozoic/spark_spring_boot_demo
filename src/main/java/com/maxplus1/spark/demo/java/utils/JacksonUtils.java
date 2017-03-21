package com.maxplus1.spark.demo.java.utils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 为google gson api 设置的辅助工具类
 * @author HC Paleo修改于2015-12
 */
public class JacksonUtils {
	
	
	private static final Logger log = LoggerFactory.getLogger(JacksonUtils.class);
	
	private static ObjectMapper mapper = new ObjectMapper();
	/**
	 * 转换对象为json数据
	 * @param obj
	 * @return
	 */
	public static String  obj2Json(Object object){
		if(object == null) return "";
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
			mapper.getSerializationConfig().with(formatter);
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			log.warn("write to json string error:" + object, e);
			return "";
		}
	}
	
	
	/**
	 * 转换json数据为对象
	 * @param json
	 * @param classOfT
	 * @return
	 */
	public static <T>  T  json2Obj(String json,Class<T> clazz){
		if (StringUtils.isEmpty(json)) {
			return null;
		}

		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
			mapper.getSerializationConfig().with(formatter);  
			return mapper.readValue(json, clazz);
		} catch (Exception e) {
			log.warn("parse json string error:" + json, e);
			return null;
		}
	}
	
	/**
	 * 转换json数据为对象列表
	 * @param json
	 * @param classOfT
	 * @return
	 */
	public static <T>  List<T>  json2List(String json,Class<T> classOfT){
		List<T> objList = null;  
        try {  
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
        	mapper.getSerializationConfig().with(formatter);
        	JavaType t = mapper.getTypeFactory().constructParametrizedType(  
                    List.class, List.class,classOfT); 
            objList = mapper.readValue(json, t);  
        } catch (Exception e) {  
        }  
        return objList;  
	}
	
	
	/**
	 * 转换json数据为对象
	 * @param json
	 * @param classOfT
	 * @return
	 */
	public static Map json2Map(String json){
		if(StringUtils.isNotEmpty(json)){
			return json2Obj(json, Map.class);
		}else{
			return null;
		}
	}
}