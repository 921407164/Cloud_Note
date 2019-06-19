package cn.tedu.cloud_note.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.tedu.cloud_note.entity.User;
import cn.tedu.cloud_note.uitl.JSONResult;

@Component
public class AccessInterceptor implements HandlerInterceptor {

	public boolean preHandle(
			HttpServletRequest req, 
			HttpServletResponse res, 
			Object handler)
			throws Exception {
		//String path=req.getRequestURI();
        //System.out.println("Interceptor:"+path);
        HttpSession session = req.getSession();
        User user = (User)session
                .getAttribute("loginUser");
        //���û�е�¼�ͷ��ش����JSON��Ϣ
        if(user==null){
            JSONResult result = 
                new JSONResult("��Ҫ���µ�¼!");
            //����response ���������
            res.setContentType(
                "application/json;charset=UTF-8");
            res.setCharacterEncoding("UTF-8");
            ObjectMapper mapper = 
                    new ObjectMapper();
            String json=mapper
                .writeValueAsString(result);
            res.getWriter().println(json);
            res.flushBuffer();
            return false;
        }
        //�����¼�˾ͷŹ�����
        return true;//�Ź�����
  }
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
