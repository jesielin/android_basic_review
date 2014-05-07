package com.example.data_sql.test;

import java.util.List;

import android.test.AndroidTestCase;

import com.example.data_sql.dao.Person;
import com.example.data_sql.dao.PersonDao;

public class DaoTest extends AndroidTestCase {
	public void testAdd() throws Exception{
		PersonDao dao = new PersonDao(getContext());
		Person p = new Person();
		p.setAge(12);
		p.setName("ะกร๗");
		long result = dao.addPerson(p);
		//assertEquals(1, result);
	}
	
	public void testFindAll() throws Exception{
		PersonDao dao = new PersonDao(getContext());

		List<Person> list = dao.findAll();
		System.out.println(list);
	}
	
	public void testDelete() throws Exception{
		PersonDao dao = new PersonDao(getContext());
		List<Person> list = dao.findAll();
		int id = list.get(0).getPersonid();
		int deleteById = dao.deleteById(id);
		assertEquals(1, deleteById);
		
	}
	
	public void testUpdate() throws Exception{
		PersonDao dao = new PersonDao(getContext());
		Person p = new Person();
		List<Person> list = dao.findAll();
		int id = list.get(0).getPersonid();
		p.setAge(22);
		p.setPersonid(id);
		p.setName("shawn");
		int update = dao.update(p);
		System.out.println(update);
	}
	
}
