package cloud_note_test;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import cn.tedu.cloud_note.dao.NoteDao;
import cn.tedu.cloud_note.entity.Note;
import cn.tedu.cloud_note.service.NoteService;

public class notetest extends cloud_note_test.Test{
	NoteDao dao;
	NoteService service;
	@Before
	public void initDao() {
		dao = ctx.getBean("noteDao",NoteDao.class);
		service = ctx.getBean("noteService",NoteService.class);
	}
	
	@Test
	public void NoteDaoTest() {
		String id = "6d763ac9-dca3-42d7-a2a7-a08053095c08";
		List<Map<String, Object>> list = dao.findNoteByNoteBookId(id);
		for(Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
	
	@Test
	public void NoteServceTest() {
		String id = "1d46f5db-f569-4c05-bdba-75106108fcba";
		List<Map<String, Object>> list = service.findNoteByNoteBookId(id);
		for(Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
	
	@Test
	public void NoteServiceBodyTest() {
		String id = "2f85e280-60c6-41a8-a1c3-e27df98dc7fd";
		Note list = service.findNoteBodyByNoteId(id);
		System.out.println(list);
	}
	
	@Test
	public void NoteServiceaddNote() {
		String userId = "39295a3d-cc9b-42b4-b206-a2e7fab7e77c";
		String notebookId = "6d763ac9-dca3-42d7-a2a7-a08053095c08";
		String  noteName = "擎天柱";
		Note note = service.addNote(userId, notebookId, noteName);
		System.out.println(note);
	}
	
	@Test 
	public void NoteDaoaddNote() {
		Note note = new Note("123", "aaa", "dsfs", "1", "45454", "54544545", "1454454", 454545454L, 45454545454L);
		dao.addNote(note);
	}
	
	@Test
	public void testUpdate(){
	    String id = "019cd9e1-b629-4d8d-afd7-2aa9e2d6afe0";
	    String title = "Test";
	    String body = "今天天气不错";
	    int cont = service.updateNote(id, title, body);
	    Note note = service.findNoteBodyByNoteId(id);
	    System.out.println(cont);
	    System.out.println(note);
	}
	
	@Test
	public void testMoveservice() {
		String noteId = "39295a3d-cc9b-42b4-b206-a2e7fab7e77c";
		String notebookId = "0037215c-09fe-4eaa-aeb5-25a340c6b39b";
		int cont = service.moveNote(noteId, notebookId);
		System.out.println(cont);
	}
	
	@Test 
	public void testMoveDao() {
		String noteId = "003ec2a1-f975-4322-8e4d-dfd206d6ac0c";
		String notebookId = "13456";
		Note note = new Note();
		note.setId(noteId);
		note.setNotebookId(notebookId);
		System.out.println(note.getId());
		int cont = dao.updateNoteByNote(note);
		System.out.println(cont);
	}
	
	@Test
	public void testfindNoteByNoteStatusIdDao() {
		String statusId = "0";
		List<Note>  note = dao.findNoteByuserId(statusId);
		System.out.println(note);
	}
	
	@Test 
	public void testfindNoteByNoteStatusIdservice() {
		String userId = "";
		List<Note> list = service.findNoteByuserId(userId);
		System.out.println(list);
	}
	
	@Test
	public void testdeleteNotebyNoteIdDao() {
		String noteId = "ec2f202a-5259-497a-bba1-ba76ad861ea0";
		int cont = dao.deleteNoteByNoteId(noteId);
		System.out.println(cont);
	}
	
	@Test
	public void testdeleteNotebyNoteIdService() {
		String noteId = "5291d0a8-3ae2-4d64-ac74-0e0b536788ba";
		int cont = service.deleteNotebynoteId(noteId);
		System.out.println(cont);
	}
	
	@Test
	public void testreovkeNotesevice() {
		String noteId = "594668cf-5a88-41bf-90ea-23014d9e7428";
		String nId = "11";
		int cont = service.reovkeNote(noteId,nId);
		System.out.println(cont);
	}
	
	@Test 
	public void testfindNotes() {
		String userId = "39295a3d-cc9b-42b4-b206-a2e7fab7e77c";
		String notebookId = null;
		String statusId = "1";
		
		List<Map<String, Object>> list = dao.findNotes(userId, notebookId, statusId);
		System.out.println(list);
	}
	
	@Test
	public void testdeleteNotes() {
		String id1 = "003ec2a1-f975-4322-8e4d-dfd206d6ac0c";
		String id2 = "019cd9e1-b629-4d8d-afd7-2aa9e2d6afe0";
		String id3 = "01da5d69-89d5-4140-9585-b559a97f9cb0";
		int n = dao.deleteNotes(id1,id2,id3);
		System.out.println(n);
	}
}
