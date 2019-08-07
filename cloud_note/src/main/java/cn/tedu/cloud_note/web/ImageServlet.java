package cn.tedu.cloud_note.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ImageServlet
 */
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
         
    public ImageServlet() {
        super();
       
    }

	protected void doGet
			(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//����ͼƬ
		byte[] png = createPng();
		response.setContentType("image/png");
		response.setContentLength(png.length);
		//����Ϣbody�з�����Ϣ����
		response.getOutputStream().write(png);
	}

	
	protected void doPost
			(HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException {
		
		doGet(request, response);
	}
	/**
	 * ����һ��ͼƬ���ұ���Ϊpng��ʽ
	 * ���ҷ��ر�������
	 * @throws IOException 
	 *  
	 */
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

}
