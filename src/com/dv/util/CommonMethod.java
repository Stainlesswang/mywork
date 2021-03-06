package com.dv.util;

import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import net.sf.json.JSONArray;
/**
 * 
 * 系统常用方法
 *
 */
public class CommonMethod {
	static Logger logger = Logger.getLogger(CommonMethod.class); // 记录日志
	/**
	 * 
	 * @methodDesc:  
	 * 生成一个 UUID
	 * @return
	 * @creater: 陈伏宝
	 * @creationDate:2016-5-30 上午11:09:20
	 */
	public static String getUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static String getDateFormat(Date date)
	{
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(date);
		}catch(Exception ex)
		{
			return null;
		}
		
	}
	
	/**
	 * 
	 * @methodDesc:  
	 *	只返回年月日
	 * @param date
	 * @return
	 * @creater: 朱弘威
	 * @creationDate:2016-7-10 下午01:57:00
	 */
	public static String getDateFormaty_M_d(Date date){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(date);
		}catch(Exception e){
			return null;
		}
	}
	
	public static String getDateFormat(Date date,String type)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(type);
		return sdf.format(date);
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 将字符串类型的值转换为 date数据
	 * @param time
	 * @return
	 * @creater: 陈伏宝
	 * @creationDate:2016-7-9 上午09:51:29
	 */
	public static Date getDate(String time)
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return df.parse(time);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public static Date getTypeDate(String time)
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return df.parse(time);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public static Date getTypeDatebyType(String time,String type)
	{
		DateFormat df = new SimpleDateFormat(type);
		try {
			return df.parse(time);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 获取一个TreeMap<String, String>对象
	 * @param objList
	 * @param index1
	 * @param index2
	 * @return
	 * @creater: 李梦婷
	 * @creationDate:2016年6月2日 下午3:21:47
	 */
	public TreeMap<String, String> getStringStringMap(List<Object[]> objList,int index1,int index2) {
		TreeMap<String, String> map = new TreeMap<String, String>();
		try {
			for(Object[] obj:objList)
			{
				String mapKey = obj[index1].toString();
				String mapValue = obj[index2].toString();
				map.put(mapKey, mapValue);
			}
			
		} catch (Exception e) {
			// 记录debug级别的信息  
			if (logger.isDebugEnabled()) {  
			    logger.debug(e.getMessage());  
			}  
			  
			// 记录info级别的信息  
			if (logger.isInfoEnabled()) {  
			    logger.info(e.getMessage());  
			}  
			  
			// 记录error级别的信息 
			logger.error(e.getMessage());
		} finally {
			
		}
		return map;
	}	
	
	
	
	
	
	
	
	public static String millsecondToDate(Long dateTime)
	{
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		long now = System.currentTimeMillis();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(dateTime);		
		return "";
	}
	
	
	/**
	 * 
	 * @methodDesc:  
	 *	对比时间，返回时间与当前系统时间相差的月数
	 * @param timeValue 需要对比的时间
	 * @return
	 * @creater: 陈伏宝
	 * @creationDate:2016-6-5 下午2:06:17
	 */
	public static Integer contrastTime(String timeValue)
	{
		Timestamp currentTime=new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d1=new Date();
		d1=currentTime;
		String t1=sdf.format(d1);
		
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(sdf.parse(t1));
			c2.setTime(sdf.parse(timeValue));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int XYear=c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
		int XMonth=Math.abs(c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH));
		int month=XYear*12+XMonth;
		
		return month;
	}
	
	
	
	
	
	
	
	
	/**
	 * 
	 * @methodDesc:  
	 * 比较时间与当前系统时间相差的秒数
	 * @param timeValue  需要比较的时间
	 * @return 返回相差的数据
	 * @creater: 陈伏宝
	 * @creationDate:2016-6-16 下午3:06:43
	 */
	public static String secondNumber(String timeValue)
	{
		try
		{
			if(timeValue != null && !timeValue.equals(""))
			{
				long ny = 1000 * 24 * 60 * 60 * 30L;// 一月的毫秒数   
		        long nd = 1000 * 24 * 60 * 60L;// 一天的毫秒数    
		        long nh = 1000 * 60 * 60L;// 一小时的毫秒数    
		        long nm = 1000 * 60L;// 一分钟的毫秒数    
		        
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
				Date date=sdf.parse(timeValue); 
				 
				Date sysDate = new Date();
				long a = sysDate.getTime() - date.getTime();
				if(a < nm)
				{
					return a/1000 +"秒前";
				}
				if(a>nm && a<nh)
				{
					return a/1000/60 +"分钟前";
				}
				if(a>nh && a<nd)
				{
					return a/1000/60/60+"小时前";
				}
				if(a>nd && a<ny)
				{
					timeValue = timeValue.substring(0, 10);
					String sysTime = getDateFormat(new Date());
					return daysBetween(timeValue,sysTime)+"天前";
				}
				if(a > ny)
				{
					Integer monthValue =  contrastTime(timeValue);
					if(monthValue >=3)
					{
						return timeValue;
					}
					else
					{
						return monthValue+"月前";
					}
				}
			}
		}
		catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * 
	 * @methodDesc:  
	 * 查看短信验证码是否发送
	 * 仅限于分钟
	 * @param timeValue
	 * @param limitMinitue限制的时间
	 * @return
	 * @creater: 陈伏宝
	 * @creationDate:2016-6-20 下午06:54:39
	 */
	public static boolean minNumber(String timeValue,int limitMinitue)
	{
		try
		{
			String a = secondNumber(timeValue);
			if(a.contains("秒前"))//秒前
			{
				return false;
			}
			if(a.contains("分钟前"))
			{
				a = a.substring(0, a.indexOf("分"));
				Integer  b = Integer.valueOf(a);
				if(b<=limitMinitue)
				{
					return false;
				}
			}
			
			return true;
			
		}
		catch (Exception e) {
			logger.error("校验短信验证时间信息失败:失败信息是:"+e.getMessage()+"失败的堆栈信息是:"+e.getStackTrace());
		}
		return true;
	}
	
	
	
	
	
	/**
	 * 
	 * @methodDesc:  
	 * 计算某一时间 与当前系统时间相差的周数
	 * @param contrastTime  需要对比的时间
	 * @return
	 * @throws ParseException
	 * @creater: 陈伏宝
	 * @creationDate:2016-6-18 上午11:29:53
	 */
	  public static Object weekBetween(String contrastTime) throws ParseException  
	  {  
		  String sysTime =  getDateFormat(new Date());// 当前系统时间
		  int betweenWeek = daysBetween(sysTime,contrastTime); // 返回的是天数
//		  if(betweenWeek < 0)
//		  {
////			  return "预不在孕周计算范围"; 
//			  return "预产时间已过";
//		  }
		  if(betweenWeek== 0 || betweenWeek < 0)
		  {
			  return "孕周 "+40+" 周";
		  }
		  int weekNumber = betweenWeek/7; // 周数 预产期距离当前系统时间的天数
		  int dayNumber = betweenWeek%7;// 不足一周显示的天数
		  
		  betweenWeek = 40-weekNumber;// 怀孕的周数
		  
		  if(betweenWeek < 0 || betweenWeek ==0)
		  {
			  return "孕周 0 周"; 
		  }
		 
		  if(dayNumber != 0)
		  {
			  dayNumber = 7-dayNumber;
		  }
		
		  
		  if(betweenWeek  == 0 && dayNumber != 0)  
		  {
			  return "孕周 "+dayNumber+" 天";  
		  }
		  
		  
		  if(dayNumber==0 && betweenWeek != 0 )
		  {
			  return "孕周 "+betweenWeek+" 周";
		  }
		  
		  if(betweenWeek > 1&& dayNumber != 0)
		  {
			  return "孕周 "+(betweenWeek-1)+"周 + "+dayNumber; 
		  }
		  if( betweenWeek == 1 && dayNumber != 0)
		  {
			  return "孕周  "+dayNumber+" 天";
		  }
		  
		  	return null;
		  
	    } 
	/**
	 * 
	 * @methodDesc:  
	 *	查看用户这个阶段的检查项目是否已经检查
	 * @param preWeekValue
	 * @param ante_stage
	 * @return
	 * @creater: 陈伏宝
	 * @creationDate:2016-7-9 下午03:59:49
	 */
	  public static Integer taskValue(Integer preWeekValue,String ante_stage)
	  {
		  if(preWeekValue == 0)
		  {
			  return 0;
		  }
		  
		  
		  if(ante_stage.contains("-"))
		  {
			  String a1 = ante_stage.split("-")[0];
			  String a2 = ante_stage.split("-")[1];
			  
			  if((Integer.valueOf(a1).equals(Integer.valueOf(a2)) || preWeekValue.equals(Integer.valueOf(a2))) || (Integer.valueOf(a1)<preWeekValue && preWeekValue< Integer.valueOf(a2)))
			  {
				 return 1; 
			  }
			  else {
				
				  return 0;
			}
			  
		  }
		  else 
		  {
			if(preWeekValue > Integer.valueOf(ante_stage))
			{
				return 1;
			}
			else
			{
				return 0;
			}
		  }
	  }
	
	  /**
	   * 
	   * @methodDesc:  
	   * 比较两个时间之间相差的天数
	   * @param smdate1  与当前系统时间比较的时间
	   * @param sysTime  当前系统时间
	   * @return  比当前系统时间大的 返回为负数 , 比当前系统时间小的 返回为正数
	   * @throws ParseException
	   * @creater: 陈伏宝
	   * @creationDate:2016-6-18 上午11:21:06
	   */
	  public static int daysBetween(String smdate1,String sysTime) throws ParseException  
	  {  
		  
		  
		  	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		    Date smdate=sdf.parse(smdate1);
		    Date bdate=sdf.parse(sysTime);
	        Calendar cal = Calendar.getInstance();  
	        cal.setTime(smdate);  
	        long time1 = cal.getTimeInMillis();               
	        cal.setTime(bdate);  
	        long time2 = cal.getTimeInMillis(); 
	        long between_days=(time2-time1)/(1000*3600*24L);
	       return Integer.parseInt(String.valueOf(between_days));         
	    } 
	  
	  /**
	   * 
	   * @methodDesc:  
	   *  计算两个日期之间相差的时间
	   * @param fDate 与系统时间相对比的时间
	   * @param oDate 系统时间
	   * @return
	   * @creater: 陈伏宝
	   * @creationDate:2016-7-24 上午08:25:59
	   */
	  public static int daysOfTwo(Date fDate, Date oDate) {

	       Calendar aCalendar = Calendar.getInstance();

	       aCalendar.setTime(fDate);

	       int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);

	       aCalendar.setTime(oDate);

	       int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);

	       return day2 - day1;

	    }
	
	
	// 将Object数据改为int,有异常时返回0
		public static int toInt(Object obj) {
			int result = 0;
			
			if(obj == null) {
				result = 0;
			} else if(obj instanceof String) {
				try {
					result = Integer.parseInt(obj.toString());
				} catch (NumberFormatException e) {
					result = 0;
					if (logger.isDebugEnabled()) {  
					    logger.debug(e.getMessage());  
					}  
					  
					// 记录info级别的信息  
					if (logger.isInfoEnabled()) {  
					    logger.info(e.getMessage());  
					}  
					  
					// 记录error级别的信息 
					logger.error(e.getMessage());
				}
			} else {
				try {
					result = (Integer) obj;
				} catch (Exception e) {
					result = 0;
					if (logger.isDebugEnabled()) {  
					    logger.debug(e.getMessage());  
					}  
					  
					// 记录info级别的信息  
					if (logger.isInfoEnabled()) {  
					    logger.info(e.getMessage());  
					}  
					  
					// 记录error级别的信息 
					logger.error(e.getMessage());
				}
			}
			return result;
		}
	
		/**
		 * 
		 * @methodDesc:  
		 * 生成批次号
		 * @param msgType
		 * @param SendOperator
		 * @param dateFormatStr
		 * @return
		 * @creater: 李梦婷
		 * @creationDate:2016年6月27日 下午6:45:06
		 */
		public static String generateBatchNo(String msgType,String SendOperator,String dateFormatStr)
		{
			String batchNo=msgType+SendOperator;
			int min=10000;
	        int max=99999;
	        Random random = new Random();
	        int s = random.nextInt(max)%(max-min+1) + min;
	        batchNo+=dateFormatStr+s+"";
	        return batchNo;
		}
		
		
		
		
		
		
		
		
		
		
		
		/**
		 * 
		 * @methodDesc ：
		 *得到几天前时间
		 * @param d 时间值
		 * @param day 天数
		 * @return
		 * @creater: 邢益伟
		 * @creationDate:2016-7-5 下午02:52:33
		 */
	    public static Date getDateBefore(Date d, int day) {  
	        Calendar now = Calendar.getInstance();  
	        now.setTime(d);  
	        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);  
	        return now.getTime();  
	    }
	    
	    public static int StringToInt(String strValue,int exValue)
	    {
	    	try
	    	{
	    		return Integer.valueOf(strValue);
	    	}
	    	catch(Exception ex)
	    	{
	    		return exValue;
	    	}
	    }
	
	    /**
	     * 
	     * @methodDesc:  
	     * 向下取整
	     * @param strValue
	     * @param exValue
	     * @return
	     * @creater: 李梦婷
	     * @creationDate:2016年11月17日 下午3:38:12
	     */
	    public static int StringToIntByFloor(String strValue,int exValue)
	    {
	    	try
	    	{
	    		Double d=Double.valueOf(strValue);
	    		double val=Math.floor(d);
	    		return (int)val;
	    	}
	    	catch(Exception ex)
	    	{
	    		return exValue;
	    	}
	    }
	    
	    /**
	     * 
	     * @methodDesc:  
	     * 获取项目路径
	     *  http://192.168.0.119:8080/xx
	     * @param request
	     * @return
	     * @creater: 李梦婷
	     * @creationDate:2016年7月20日 下午3:36:41
	     */
	    public static String getHttpUrl(HttpServletRequest request)
	    {
	    	String strBackUrl = "http://" + request.getServerName() //服务器地址  
                    + ":"   
                    + request.getServerPort()           //端口号  
                    + request.getContextPath(); 		//项目名称 
	    	return strBackUrl;
	    }
	    
	    /**
	     * 
	     * @methodDesc:  
	     * 根据总记录数和每页记录数，求总页数
	     * @param totalCount
	     * @param pageSize
	     * @return
	     * @creater: 李梦婷
	     * @creationDate:2016年10月30日 下午7:57:08
	     */
	    public static long getPagesByCount(long totalCount,int pageSize)
	    {
	    	if(totalCount%pageSize==0)
	    	{
	    		return totalCount/pageSize;
	    	}
	    	else
	    	{
	    		return totalCount/pageSize+1;
	    	}
	    }
	    
	    /**
	     * 
	     * @methodDesc:  
	     *  将json字符串解析封装成List
	     * @param parseStr
	     * @return
	     * @return  
	     * @creater: 李梦婷
	     * @creationDate:2017年3月3日 下午3:16:38
	     */
	    public static List<String> getObjList(String parseStr)
		{
			if(StringUtils.isEmpty(parseStr)||parseStr.equals("[]")||parseStr.equals("null"))
			{
				return null;
			}
			try {
//				JSONArray objArray=JSONArray.fromObject(parseStr);
//				Iterator iter = objArray.iterator();
//				List<String> list=new ArrayList<String>();
//				while(iter.hasNext())
//				{
//					list.add(iter.next().toString());
//				}
				JSONArray jsonArray2= JSONArray.fromObject(parseStr);
				List<String> list=JSONArray.toList(jsonArray2);
				return list;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("getObjList,解析出错，"+e.getMessage()+"解析内容:parseStr="+parseStr);
				return null;
			}
		}
	    
	    
	    public static String replaceFileSeparator(String path)
	    {
	        if (File.separator.equals("\\") && path.indexOf("/") >= 0)
	        {
	            return path.replace('/', File.separatorChar);
	        }
	        else if (File.separator.equals("/") && path.indexOf("\\") >= 0)
	        {
	            return path.replace('\\', File.separatorChar);
	        }
	        return path;
	    }
	    
	    public static void main(String[] args) {
			
		}
}
