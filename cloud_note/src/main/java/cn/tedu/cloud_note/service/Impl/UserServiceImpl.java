package cn.tedu.cloud_note.service.Impl;

import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.tedu.cloud_note.dao.UserDao;
import cn.tedu.cloud_note.entity.User;
import cn.tedu.cloud_note.service.PasswordException;
import cn.tedu.cloud_note.service.UserNameException;
import cn.tedu.cloud_note.service.UserNotFoundException;
import cn.tedu.cloud_note.service.UserService;


@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Resource(name="userDao")
	private UserDao userDao;
	
	@Value("#{db.salt}")
	private String salt;
	
	//ʵ�ֵ�¼����
	public User Login(String name, String password) 
			throws UserNotFoundException, PasswordException {
		if(password == null || 
				password.trim().isEmpty()){
			throw new PasswordException("����Ϊ��");
		}
		if(name == null ||
				name.trim().isEmpty()) {
			throw new UserNotFoundException("�û���Ϊ��");
		}
		User u = userDao.FindUserByName(name);
		if(u == null) {
			throw new UserNotFoundException("�û�������");
		}
		String pwd = DigestUtils.md5Hex(salt+password);
		if(pwd.equals(u.getPassword())) {
			return u;
		}
		throw new PasswordException("�������");
	}
	
	//ʵ��ע�᷽��
	public User regist(
			String name, String nick, String password, String confirm) 
					throws UserNameException {
		//���name���ظ����Ҳ�Ϊ��
		if(name == null || name.trim().isEmpty()) {
			throw new UserNameException("�˺�Ϊ��");
		}
		User one = userDao.FindUserByName(name);
		if(one != null) {
			throw new UserNameException("�˺��Ѿ�ע��");
		}
		//�������
		if(password == null || password.trim().isEmpty()) {
			throw new PasswordException("����Ϊ��");
		}
		if(!password.equals(confirm)) {
			throw new PasswordException("�������벻һ��");
		}
		//���nick
		if(nick==null || nick.trim().isEmpty()) {
			nick = name;
		}
		String id = UUID.randomUUID().toString();
		String token = "";
		password = DigestUtils.md5Hex(salt+password);
		User user = new User(id, name, password, token, nick); 
		int n = userDao.AddUser(user);
		if(n!=1) {
			throw new RuntimeException("���ʧ��");
		}
		return user;
	}


}
