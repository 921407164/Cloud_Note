package cn.tedu.cloud_note.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PointcutAspect {
	
	//@Before("bean(*Service)")
	//@Before("within(cn.tedu.cloud_note.*.Impl.*ServiceImpl)")
	public void test() {
		System.out.println("��������");
	}
	
	//@Before("execution(* cn.tedu.cloud_note.service.UserService.Login(..))")
	public void test2() {
		System.out.println("execution�����");
	}
	
	//@Before("execution(* cn.tedu.cloud_note.service.*Service.find*(..))")
	public void test3() {
		System.out.println("execution()find*�����");
	}
	
	
}
