package com.dv.test;

 
import java.text.DecimalFormat;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dv.dao.user.UserMapper;
import com.dv.entity.user.User;
import com.dv.util.CommonMethod;

public class UserTest {
	 ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
	 User user=new User();
	 UserMapper a=ctx.getBean(UserMapper.class);
 
	 @Test
	 public void testOne(){
		 try {
			user.setLimit(10);
			 user.setStart(0);
			 user.setParent_code("1");
			 user.setOrg_id(1);
			 List<User> list=a.findUserByOrgId(user);
			 for (User user1 : list) {
				System.out.println(user1.getUser_name());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	 }
	 
	 public static void test()
	 {
		 Thread t =Thread.currentThread();
         t.setName("单例线程");
         System.out.println(t.getName()+" 正在运行");
         for(int i=0;i<20;i++){
             System.out.println("线程正在休眠："+maxNum);
             maxNum++;
             try {
                t.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("线程出现错误了！！");
            }
         }
	 }
	 
	 public static int maxNum=5;
	
//	 public static void main(String[] args) {
//		 for(int i=0;i<2;i++)
//		 {
//			 test();
//		 }
//		 
//	 }
 
		public static void main(String[] args) {
			int n=404;
			DecimalFormat f = new DecimalFormat("0000");
	        String str = f.format(n);
	        System.out.println(str);
	        System.out.println(CommonMethod.StringToInt(str, 0));
		}
 
 
		 @Test
		 public void test2(){
			 
			 
		 }
 
}
