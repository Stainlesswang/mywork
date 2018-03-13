package com.dv.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 前端向后台传输数据，由此对象包装
 * form中的数据包装成map中的键值对，例如通过pd.get("account")，获取前台传入的account
 * 前台通过json传入的数据，第一级节点与form类似，全部转化为String类型，如果为json数据，可以转化为json对象再处理
 * BootstrapTable传入的参数，有几个固定键值对，
 * search（关键字查询条件）,limit（每页行数）,offset（当前从第几行数据开始查询）,order（排序方式asc,desc），sort（需要排序的列名）
 * advsearch（高级查询时传入json对象）
 * @author zsuny
 *
 */
@SuppressWarnings("rawtypes")
public class FnfhPageData extends HashMap implements Map {
	
    private static final long serialVersionUID = 1L;

    Map map = null;
    HttpServletRequest request;
    
    Logger logger = Logger.getLogger(this.getClass()); // 记录日志

    @SuppressWarnings("unchecked")
	public FnfhPageData(HttpServletRequest request) {
        this.request = request;
    	String contentType = getHeadersInfo(request).get("content-type");

		if (null != contentType && contentType.contains("application/json")) {

			StringBuilder sb = new StringBuilder();
            BufferedReader reader = null;
            String line = null;
            try {
                reader = request.getReader();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
            JSONObject obj = JSONObject.fromObject(sb.toString());

	        map = new HashMap();
	        Object value = "";
            for (Object key : obj.keySet()) {
            	Object valueObj = obj.get(key);
	            if (null == valueObj) {
	                value = "";
	            } else if (valueObj instanceof String[]) {
	                String[] values = (String[]) valueObj;
	                for (int i = 0; i < values.length; i++) {
	                    value = values[i] + ",";
	                }
	                value = ((String)value).substring(0, ((String)value).length() - 1);
	            } else if (valueObj instanceof JSONArray) {
	                value = valueObj;
	            } else {
	                value = valueObj.toString();
	            }

	            map.put(key, value);

			}
    	} else /*if (contentType.contains("application/x-www-form-urlencoded"))*/ {    		
	        Map properties = request.getParameterMap();
	        Map returnMap = new HashMap();
	        Iterator entries = properties.entrySet().iterator();
	        Map.Entry entry;
	        String name = "";
	        String value = "";
	        while (entries.hasNext()) {
	            entry = (Map.Entry) entries.next();
	            name = (String) entry.getKey();
	            Object valueObj = entry.getValue();
	            if (null == valueObj) {
	                value = "";
	            } else if (valueObj instanceof String[]) {
	                String[] values = (String[]) valueObj;
	                if (values.length == 0) {
	                	value = "";
	                } else if (values.length == 1) {
	                	value = values[0];
	                } else {
	                	JSONArray vars = new JSONArray();
	                	for(String v : values)
	                		vars.add(v);
		                value = vars.toString();
		                if (name.lastIndexOf("[]") != -1) {
		                	name = name.substring(0, name.length() - 2);
		                }
	                }
	            } else {
	                value = valueObj.toString();

	            }
	            returnMap.put(name, value);
	        }
	        map = returnMap;

		}
//    	System.out.println(map.toString());
        logger.debug("[PARAMS][" + request.getMethod().toString() + "][" + request.getRequestURI() + "]" + map.toString());
    }

    public FnfhPageData() {
        map = new HashMap();
    }
    
    /**
     * 专用于BootstrapTable传参的limit
     * @return
     */
    public int getLimit() {
    	Object obj = map.get("limit");
    	if (null != obj) {
    		if (obj instanceof String)
    			return Integer.parseInt((String) obj);
    		else if (obj instanceof Integer)
    			return (Integer) obj;
    		else
    			return Integer.parseInt(obj.toString());
    	} else {
    		return 0;
    	}
    }
    
    /**
     * 专用于BootstrapTable传参的offset
     * @return
     */
    public int getOffset() {
    	Object obj = map.get("offset");
    	if (null != obj) {
    		if (obj instanceof String)
    			return Integer.parseInt((String) obj);
    		else if (obj instanceof Integer)
    			return (Integer) obj;
    		else
    			return Integer.parseInt(obj.toString());
    	} else {
    		return 0;
    	}
    }
    
    /**
     * 专用于BootstrapTable传参的order
     * @return
     */
    public String getOrder() {
    	return getString("order");
    }
    
    /**
     * 专用于BootstrapTable传参的sort
     * @return
     */
    public String getSort() {
    	return getString("sort");
    }
    
    /**
     * 专用于BootstrapTable传参的search
     * @return
     */
    public String getSearch() {
    	return getString("search");
    }
    
    /**
     * 专用于BootstrapTable传参的advsearch
     * @return
     */
    public JSONObject getAdvSearch() {
    	return JSONObject.fromObject(getString("advsearch"));
    }

    @Override
    public Object get(Object key) {
        Object obj = null;
        if (map.get(key) instanceof Object[]) {
            Object[] arr = (Object[]) map.get(key);
            obj = request == null ? arr
                    : (request.getParameter((String) key) == null ? arr
                    : arr[0]);
        } else {
            obj = map.get(key);
        }
        return obj;
    }

    public String getString(Object key) {
    	Object v = get(key);
    	if (null == v)
    		return null;
    	if (v instanceof String)
    		return (String) v;
    	else
    		return String.valueOf(v);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object put(Object key, Object value) {
        return map.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return map.remove(key);
    }

    public void clear() {
        map.clear();
    }

    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    public Set entrySet() {
        return map.entrySet();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public Set keySet() {
        return map.keySet();
    }

    @SuppressWarnings("unchecked")
	public void putAll(Map t) {
        map.putAll(t);
    }

    public int size() {
        return map.size();
    }

    public Collection values() {
        return map.values();
    }
    
	private Map<String, String> getHeadersInfo(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<?> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}
		return map;
	}
}
