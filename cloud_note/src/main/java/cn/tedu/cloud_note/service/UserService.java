package cn.tedu.cloud_note.service;

import cn.tedu.cloud_note.entity.User;
/**
 * 
  *   ҵ��־ò�ӿ�
 * @author LCD
 *
 */
public interface UserService {
	/**
	  *  ��¼����
	 * @param name �û���
	 * @param password ����
	 * @return ��½�ɹ������û���Ϣ
	 * @throws UserNotFoundException �û�������
	 * @throws PasswordException �������
	 */
	User Login(String name,String password) 
		throws UserNotFoundException,PasswordException;
	public User regist(
			String name, String nick, String password, String confirm) 
			throws UserNameException;
}
