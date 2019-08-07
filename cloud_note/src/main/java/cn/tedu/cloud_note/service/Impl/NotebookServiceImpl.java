package cn.tedu.cloud_note.service.Impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.cloud_note.dao.NotebookDao;
import cn.tedu.cloud_note.dao.UserDao;
import cn.tedu.cloud_note.entity.Notebook;
import cn.tedu.cloud_note.entity.User;
import cn.tedu.cloud_note.service.BookError;
import cn.tedu.cloud_note.service.NotebookService;
import cn.tedu.cloud_note.service.UserNotFoundException;

@Service("notebookservice")
@Transactional
public class NotebookServiceImpl implements NotebookService {
	
	@Resource
	NotebookDao notebookDao;
	@Resource
	UserDao userDao;
	
	private int pageSize;
	
	public List<Map<String, Object>> findNotebookByUserId(String userid) {
		User user = userDao.FindUserById(userid);
		if(user==null) {
			throw new UserNotFoundException("�û�������");
		}
		List<Map<String, Object>> notebooklist =
				notebookDao.findNotebookByUserId(userid);
		
		return notebooklist;
	}

	public Map<String, Object> addNotebook(String name, String userId) throws BookError {
		String id = UUID.randomUUID().toString();
		String typeId = "";
		String desc = "";
		Timestamp createtime;
		
		Date date = new Date();
		createtime = new Timestamp(date.getTime());
		
		Notebook notebook = new Notebook(id, name, typeId, userId, desc,createtime);
		int cnt = notebookDao.findNotebookByuIdAndNoteBookName(name, userId);
		System.out.println(cnt);
		if(name.trim()==null) {
			throw  new  BookError("�ʼǱ�����Ϊ��");
		} else if (cnt!=0){
			throw new BookError("�ʼǱ��Ѿ����ڿ���");
		} else {
			int a = notebookDao.addNoteBook(notebook);
			Map<String, Object> book = notebookDao.findNotebookbyNoteBookId(id);
			System.out.println("����"+a+"������");
			return book;
		}
		
	}

	public List<Map<String, Object>> 
		findNotebookByUserId(String id, int page) 
				throws UserNotFoundException {
		User user = userDao.FindUserById(id);
		if(user==null) {
			throw new UserNotFoundException("�û�������");
		}
		
		pageSize = 4;
		int statr = page * pageSize;
		String table = "cn_notebook";
		List<Map<String , Object>> list = notebookDao.findNotebookByPage(id, statr, pageSize, table);
		return list;
	}
}
