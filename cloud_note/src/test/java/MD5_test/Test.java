package MD5_test;

import org.apache.commons.codec.digest.DigestUtils;

public class Test {
	
	@org.junit.Test
	public void test() {
		String str = "123456";
		String md5 = DigestUtils.md5Hex(str);
		System.out.println(md5);
		//����ժҪ
		String salt = "�Է���ô";
		md5 = DigestUtils.md5Hex(salt+str);
		System.out.println(md5);
	}
	
}
