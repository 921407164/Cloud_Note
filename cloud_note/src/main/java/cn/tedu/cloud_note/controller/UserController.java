package cn.tedu.cloud_note.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.naming.directory.DirContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.tedu.cloud_note.entity.User;
import cn.tedu.cloud_note.service.PasswordException;
import cn.tedu.cloud_note.service.UserNameException;
import cn.tedu.cloud_note.service.UserNotFoundException;
import cn.tedu.cloud_note.service.UserService;
import cn.tedu.cloud_note.uitl.JSONResult;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController{
	
	@Resource
	UserService userservice;
	
	@RequestMapping("/login.do")
	@ResponseBody
	public Object login (
			String name,String password,HttpSession session) {
			User user = userservice.Login(name, password);
			//System.out.println(new JSONResult(user));
			//��¼�ɹ�ʱ��, ��user��Ϣ���浽session
		    //�����ڹ������м���¼���
		    session.setAttribute("loginUser", user); 
			return new JSONResult(user);
	}
	
	@RequestMapping("/add.do")
	@ResponseBody
	public Object addUser(
			String name, String nick, String password, String confirm) {
		User user = userservice.regist(name, nick, password, confirm);
		return new JSONResult(user);
	}
	
	@RequestMapping("/heartbeat.do")
	@ResponseBody
	public Object heartbeat(){
	    Object ok = "ok";
	    return new JSONResult(ok);
	}
	
	
	/*
	 *  responseBody���Զ�������ֵ
	 *  1�������javabean�����ϣ���ֵ�����Զ�����json
	 *  2���������ֵ������ֱ����ӵ�body��
	 *   produces="image/png"��������Content-Type��ֵ
	 */
	//��ʾ���ɵ�ͼƬ
	@RequestMapping(value="/image.do",produces="image/png")
	@ResponseBody
	public byte[] image() throws IOException {
		return createPng();
	}
	//����ͼƬ
	@RequestMapping(value="/downloadimg.do",
			produces="application/octet-stream")
	@ResponseBody
	public byte[] download(HttpServletResponse res) throws IOException {
		res.setHeader("content-disposition","attachment; filename=\"demo.png\"");
		return createPng();
	}
	//����ͼƬ
	private byte[] createPng() throws IOException {
		BufferedImage image = 
				new BufferedImage(200, 80, BufferedImage.TYPE_3BYTE_BGR);
		image.setRGB(100, 40, 0xffffff);
		//��ͼƬ����Ϊpng
		ByteArrayOutputStream out = 
				new ByteArrayOutputStream();
		ImageIO.write(image, "png", out);
		out.close();
		byte[] data = out.toByteArray();
		return data;
	}
	
	//���ر��
	@RequestMapping(value="/downloadexcel.do",
			produces="application/octet-stream")
	@ResponseBody
	public byte[] excel(HttpServletResponse res) throws Exception {
		res.setHeader("content-disposition","attachment; filename=\"demo.xls\"");
		return createexcel();
	}
	
	//�ϴ��ļ�
	@RequestMapping("/uploadimg.do")
	@ResponseBody
	public JSONResult upload (
			MultipartFile userfile1,
			MultipartFile userfile2) throws Exception{
		//Spring MVC �п�������multipartFile
		//�������ص��ļ���
		//�ļ��е�һ�����ݶ����Դ�multipartfile���ҵ�
		
		String file1name = userfile1.getOriginalFilename();
		String file2name = userfile2.getOriginalFilename();
		System.out.println("���ص�ԭʼ�ļ���"+file1name);
		System.out.println("���ص�ԭʼ�ļ���"+file2name);
		//3���ļ��ı��淽��
		//1��userfile1.transferTo(Ŀ���ļ�)
		File dir = new File("F:/demo");
		dir.mkdirs();
		File file1 = new File(dir,file1name);
		File file2 = new File(dir,file2name);
		userfile1.transferTo(file1);	
		userfile2.transferTo(file2);
		//2��userfile1.getBytes()��ȡ�ļ���ȫ������
		// ���ļ���ȡ���ڴ��ʺϴ���С�ļ�
		//userfile1.getBytes();	
		//3��userfile1.getInputStream();
		//��ȡ�����ļ��������ʺϴ�����ļ�
		//��������������
		/*
		InputStream in1 = userfile1.getInputStream();	
		FileOutputStream fileOutputStream =
				new FileOutputStream(file1);
		int b;
		while(((b=in1.read())!=-1)) {
			fileOutputStream.write(b);
		}
		in1.close();
		fileOutputStream.close();
		//������������
		InputStream in2 = userfile2.getInputStream();
		FileOutputStream out2 = 
				new FileOutputStream(file2);
		byte[] buf = new byte[8*1024];
		int n ;
		while ((n=in2.read(buf))!=-1) {
			out2.write(buf,0,n);
		}
		in2.close();
		out2.close();*/
		return new JSONResult(true);
	}
	
	
	//���ɱ��	
	private byte[] createexcel() throws Exception {
		//����������
		HSSFWorkbook workbook = new HSSFWorkbook();
		//����������
		HSSFSheet sheet = 
					workbook.createSheet("Hello World");
		//����������
		HSSFRow row = 
				sheet.createRow(0);
		//�������е���
		HSSFCell cell = 
				 row.createCell(0);
		cell.setCellValue("Hello World");
		//��excel�ļ����浽dyte����
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		workbook.write(out);
		out.close();
		return out.toByteArray();
	}	
	
	
	
	/*
	 *����ע��ʱ�˺��쳣 
	 * 
	 */
	
	@ExceptionHandler(UserNameException.class)
	@ResponseBody
	public JSONResult handleUserNameException(Exception e) {
		return new JSONResult(4,e);
	}
	
	/*
	 *  �����˺��쳣
	 * 
	 */
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseBody
	public JSONResult handleUserNotFound(Exception e) {
		return new JSONResult(2,e);
		
	}
	/*
	  *  ���������쳣 
	 */
	@ExceptionHandler(PasswordException.class)
	@ResponseBody
	public JSONResult handlePassword(Exception e) {
		return new JSONResult(3, e);
	}
	
}
