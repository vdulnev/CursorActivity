package com.vd.cursoractivity.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.vd.cursoractivity.Contact;

public class TestContact {
	
	@Test
	public void testEqual(){
		Contact c1 = new Contact(0, "123", "test");
		Contact c2 = new Contact(0, "123", "test");
		assertTrue(c1.equals(c2));
	}
	
	@Test
	public void testNotEqual(){
		Contact c1 = new Contact(0, "123", "test");
		Contact c2 = new Contact(0, "456", "test1");
		assertFalse(c1.equals(c2));
	}
	
	@Test
	public void testNotEqualDisplayName(){
		Contact c1 = new Contact(0, "123", "test");
		Contact c2 = new Contact(0, "123", "test1");
		assertFalse(c1.equals(c2));
	}
	
	@Test
	public void testNotEqualId(){
		Contact c1 = new Contact(0, "123", "test");
		Contact c2 = new Contact(0, "456", "test");
		assertFalse(c1.equals(c2));
	}
}
