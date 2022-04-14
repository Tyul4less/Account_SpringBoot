package net.plang.HoWooAccount.system.common.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import net.plang.HoWooAccount.system.base.to.BaseBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/*자바빈즈의 세터 게터의 자동화. 여기서는 세터만해줌*/
public class BeanCreator {
    private static BeanCreator ourInstance = new BeanCreator();
    private BeanCreator() {
    }
    public static BeanCreator getInstance() {
        return ourInstance;
    }

    public <T extends BaseBean> T create(JSONObject jsonObject, Class<T> beanClass) { // json, slipBean.class or JournalBean.class
        T instance = null; //slipBean
        try {
            instance = beanClass.newInstance(); //slipBean 객체생성 
            Method[] methods=beanClass.getDeclaredMethods();
            for(Method method:methods) {
            	String mName=method.getName();
            	if(mName.contains("set")){
            		String s1=mName.replace("set","").substring(0,1).toLowerCase();  //b
            		String field=s1+mName.substring(4); //balanceDivision

					if(jsonObject.get(field)!=null)
						if(!jsonObject.get(field).equals(""))
							if(jsonObject.get(field) instanceof List) {
								if(((JSONArray)jsonObject.get(field)).size()==0) continue;

								ParameterizedType type =(ParameterizedType)method.getGenericParameterTypes()[0];
								Type actual = type.getActualTypeArguments()[0];

								Class<? extends BaseBean> beanOfList=(Class<? extends BaseBean>) Class.forName(actual.getTypeName());
								List<BaseBean> list=new ArrayList<>();
								for(Object jsonObj:JSONArray.fromObject(jsonObject.get(field))) {
									list.add(this.create(JSONObject.fromObject(jsonObj), beanOfList));
								}
								method.invoke(instance, list);
							}else {
								try {

									method.invoke(instance,jsonObject.get(field));
								}catch(IllegalArgumentException e) {

									continue;
								}
							}
            	}
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        
        catch (InvocationTargetException e) {
            e.printStackTrace();
        
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return instance;

    }
    
}
