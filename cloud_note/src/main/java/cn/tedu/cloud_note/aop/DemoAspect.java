package cn.tedu.cloud_note.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import cn.tedu.cloud_note.service.UserNotFoundException;

/**
  * ����һ���������������һ����ͨ��javaBean 
 */
@Component
@Aspect
public class DemoAspect {
	
	//����test��������userService��ȫ������֮ǰִ��
	@Before("bean(userService)")
	public void test() {
		System.out.println("hello world");
	}
	//����test2��������userService��ȫ������֮��ִ��
	@After("bean(userService)")
	public void test2() {
		System.out.println("after");
	}
	
	@AfterReturning("bean(userService)")
	public void test3() {
		System.out.println("afterReturning");
	}
	
	@AfterThrowing("bean(userService)")
	public void test4() {
		System.out.println("afterThrowing");
	}
	
	/**
	 * ����֪ͨ ������
	 * 	1�������з���ֵObject
	 * 	2�������в��� ProceedingJoinPoint
	 * 	3�������׳��쳣
	 * 	4�������ڷ����е���jp.proceed()
	 * 	5������ҵ�񷽷��ķ���ֵ
	 * @param jp
	 * @return
	 * @throws Throwable
	 */
	//@Around("bean(userService)")
	public Object test5(ProceedingJoinPoint jp) 
			throws Throwable{
		System.out.println("val֮ǰ");
		Object val = jp.proceed();
		System.out.println("ҵ����"+val);
		throw new UserNotFoundException("û���ҵ��û�123");
	}
}
