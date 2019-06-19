package cn.tedu.cloud_note.service.Impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import cn.tedu.cloud_note.dao.NoteDao;
import cn.tedu.cloud_note.dao.NotebookDao;
import cn.tedu.cloud_note.dao.UserDao;
import cn.tedu.cloud_note.entity.Note;
import cn.tedu.cloud_note.entity.User;
import cn.tedu.cloud_note.service.BookError;
import cn.tedu.cloud_note.service.NoteService;
import cn.tedu.cloud_note.service.UserNotFoundException;

@Service("noteService")
public class NoteServiceImpl implements NoteService {
	
	@Resource
	NoteDao notedao;
	@Resource
	UserDao userDao;
	@Resource
	NotebookDao notebookDao;
	
	public List<Map<String, Object>> findNoteByNoteBookId(String id) {
		if(id==null) {
			throw new BookError("δ֪�ʼǱ�");
		} 
		
		List<Map<String, Object>> notelist = 
				notedao.findNoteByNoteBookId(id);
		return notelist;
		
	}

	public Note findNoteBodyByNoteId(String id) {
		
		Note notebody = 
				notedao.findNoteBodyByNoteId(id);
		return notebody;
	
	}

	public Note addNote(String userId, String notebookId, String title) 
						throws 
						UserNotFoundException, 
						BookError{
		if(userId==null||userId.trim().isEmpty()){
			throw new UserNotFoundException("ID��");
		}
		User user=userDao.FindUserById(userId);
		if(user==null){
			throw new UserNotFoundException("ľ����");
		}
		if(notebookId==null||notebookId.trim().isEmpty()){
			throw new BookError("ID��");
		}
		int n=notebookDao.countNotebookById(notebookId);
		if(n!=1){
			throw new BookError("û�бʼǱ�");
		}
		if(title==null || title.trim().isEmpty()){
			title="��������";
		}
		String id = UUID.randomUUID().toString();
		String statusId = "1";
		String typeId = "0";
		String body = "";
		long time=System.currentTimeMillis();
		Note note = new Note(id, notebookId,
			userId, statusId, typeId, title, 
			body, time, time);
		n = notedao.addNote(note);
		if(n!=1){
			throw new BookError("����ʧ��");
		}
		return note;
	}

	public int updateNote(String noteId, String title, 
	        String body) throws BookError{
		
		if(noteId==null || noteId.trim().isEmpty()){
	        throw new BookError("ID���ܿ�");
	    }
		Note note = notedao.findNoteBodyByNoteId(noteId);
		if(note==null) {
			throw new BookError("û������ʼǰɣ���");
		}
		if(title!=null) {
			note.setTitle(title);
		}
		if(body!=null) {
			note.setBody(body);
		}
		long time = System.currentTimeMillis();
		note.setLastModifyTime(time);
		int cont = notedao.updateNoteByNote(note);
		return cont;
	}

	
	public int moveNote(String noteId, String notebookId) throws BookError {
		Note note = new Note();
		note.setId(noteId);
		note.setNotebookId(notebookId);
		int cont = notedao.updateNoteByNote(note);
		return cont;
	}

	public boolean deleteNote(String noteId) throws BookError {
		 if(noteId==null || noteId.trim().isEmpty()){
		        throw new BookError("ID���ܿ�");
		    }
		    Note note = notedao.findNoteByNoteId(noteId);
		    if(note==null){
		        throw new BookError("û�ж�Ӧ�ıʼ�");
		    } 
	    Note data = new Note();
	    data.setId(noteId);
	    data.setStatusId("0");
	    data.setLastModifyTime(System.currentTimeMillis());

	    int n = notedao.updateNoteByNote(data);
	    return n==1;   
	}

	@SuppressWarnings("null")
	public List<Note> findNoteByuserId(String userId) throws BookError {
		if(userId==null && userId.trim().isEmpty()) {
			throw new BookError("ID��");
		}
		User user = userDao.FindUserById(userId);
		if(user==null) {
			throw new BookError("û�������");
		}
		List<Note> noteslist = 
				notedao.findNoteByuserId(userId);
		return noteslist;
	}
	
	public int deleteNotebynoteId(String noteId) {
		if (noteId==null) {
			throw new BookError("ID��");
		}
		Note note = notedao.findNoteByNoteId(noteId);
		System.out.println(note);
		if(note==null) {
			throw new BookError("�ʼǲ�����");
		}
		int cont = notedao.deleteNoteByNoteId(noteId);
		return cont;
	}

	public int reovkeNote(String noteId,String nId) {
		if(noteId==null){
			throw new BookError("�ʼ�ID�ܶ���");
		}
		Note note = notedao.findNoteBodyByNoteId(noteId);
		if(note==null) {
			throw new BookError("û������ʼǣ����˸�Сbug");
		}
		String statusId = "1";
		note.setNotebookId(nId);
		note.setStatusId(statusId);
		int cont = notedao.updateNoteByNote(note);
		return cont;
	}
}
