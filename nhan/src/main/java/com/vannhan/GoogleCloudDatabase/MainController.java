package com.vannhan.GoogleCloudDatabase;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.validator.cfg.context.MethodConstraintMappingContext;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.cj.api.jdbc.Statement;
import com.vannhan.GoogleCloudDatabase.Model.Student;

@Controller
public class MainController {
	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}
	
	@RequestMapping("/")
	public ModelAndView index() throws SQLException, ClassNotFoundException {
		ModelAndView index = new ModelAndView("index");
		
		Connection conn = MySQLConnector.getConnection();
		
		Statement stm = (Statement) conn.createStatement();
		
		String query = "Select * from student";
		
		ResultSet result = stm.executeQuery(query);
		
		List<Student> studentList = new ArrayList<Student>();
		
		while(result.next()) {
			Student student = new Student();
			
			String id = result.getString("idstudent");
			String name = result.getString("nameStudent");
			String description = result.getString("description");
			
			student.setMssv(id);
			student.setName(name);
			student.setDescription(description);
			
			studentList.add(student);
		}
		
		index.addObject("studentList", studentList);
		
		return index;
	}
	
	@RequestMapping(value="/edit/{idStudent}", method=RequestMethod.GET)
	public ModelAndView getEdit(@PathVariable("idStudent") String idStudent) throws SQLException, ClassNotFoundException {
		
		Student student = new Student();
		
		Connection conn = MySQLConnector.getConnection();
		Statement stm = (Statement) conn.createStatement();
		
		String query = "Select * from student where idstudent = '" + idStudent +"'";
		ResultSet result = stm.executeQuery(query);
		
		while(result.next()) {
			String id = result.getString("idstudent");
			String name = result.getString("nameStudent");
			String description = result.getString("description");
			
			student.setMssv(id);
			student.setName(name);
			student.setDescription(description);
		}
		
		ModelAndView edit = new ModelAndView("edit");
		edit.addObject("student", student);
		return edit;
	}
	
	@RequestMapping(value="/edit/{idStudent}", method = RequestMethod.POST)
	@ResponseBody
	public String postEdit(@PathVariable("idStudent") String idStudent, HttpServletRequest request) throws ClassNotFoundException, SQLException {
		try {
		    request.setCharacterEncoding("UTF-8");
		    String name = request.getParameter("txt_name");
		    String description = request.getParameter("txt_description");
		
    		Connection conn = MySQLConnector.getConnection();
	    	Statement stm = (Statement) conn.createStatement();
		  
    		String query = "UPDATE student set nameStudent = '" + name 
	    			+ "', description='" + description 
		    		+ "' where idstudent='" + idStudent + "'";
  		
	    	int result = stm.executeUpdate(query);
		
    		String alert = "";
	    	
		    if(result > 0) {
    			return "Cap nhat thanh cong..... <br /> <a href='/'>Quay lai Trang chu</a>";
	    	} else {
		    	return "Cap nhat that bai";
    		}
		} catch(UnsupportedEncodingException e) {
			return "Error UnsupportEncodeing";
		}
	}
}
