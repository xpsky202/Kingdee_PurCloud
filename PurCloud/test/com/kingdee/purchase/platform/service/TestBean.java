package com.kingdee.purchase.platform.service;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.stereotype.Service;

@Service("testBean")
public class TestBean implements ITestBean{

	public String dosth(String s) {
		return "hello,"+s;
	}
	
	@Test
	public void name() {
		String state = "29_xyxyxyxyxyaazzdasf";
		long enterpriseId = Long.parseLong(state.substring(0, state.indexOf("_")));
		String companyId = state.substring(state.indexOf("_")+1);
		System.out.println(enterpriseId);
		System.out.println(companyId);
	}
	
	@Test
	public void uuid(){
		UUID uuid = UUID.randomUUID();
		System.out.println(uuid.toString());
		uuid = UUID.randomUUID();
		System.out.println(uuid.toString());
		uuid = UUID.randomUUID();
		System.out.println(uuid.toString());
		uuid = UUID.randomUUID();
		System.out.println(uuid.toString());
		uuid = UUID.randomUUID();
		System.out.println(uuid.toString());
	}
	
	@Test
	public void pattern(){
        String str = "aaaa@";
        Pattern pattern = Pattern.compile("^[0-9a-zA-Z _-]+$");
        Matcher matcher  = pattern.matcher(str);
        Assert.assertFalse(matcher.find());
        
        str = "adffa_afdsf12312DFADF23";
        matcher  = pattern.matcher(str);
        Assert.assertTrue(matcher.find());
        
        str = "adffa_afd@sf12312DFADF23";
        matcher  = pattern.matcher(str);
        Assert.assertFalse(matcher.find());
        
        str = "212312adffa_a$%fdsf12312DFADF23";
        matcher  = pattern.matcher(str);
        Assert.assertFalse(matcher.find());
        
        str = "1231adffa_afdsf2DFADF23";
        matcher  = pattern.matcher(str);
        Assert.assertTrue(matcher.find());
	}
}