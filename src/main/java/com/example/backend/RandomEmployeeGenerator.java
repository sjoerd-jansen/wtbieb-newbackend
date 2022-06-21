package com.example.backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.example.backend.tables.Employee;

public class RandomEmployeeGenerator
{
	private List<String> employeeNames = Arrays.asList(new String[] 
			{
				"Jorrit van der Kooi",
				"Wouter Schmeetz",
				"Arnold Savenije",
				"Lucas Schuit",
				"Tico van Hoek",
				"Sjoerd Jansen",
				"Jesse van den Ende",
				"Imane Al Gharib",
				"Ee Jie Tan"
			});
	
	private List<String> employeeAvatar = Arrays.asList(new String[] 
			{
				"https://this-person-does-not-exist.com/img/avatar-eecfe393c6fb76165d90bab31f8dafba.jpg",
				"https://this-person-does-not-exist.com/img/avatar-05b0fb8cc050671c231d9ec74a8a844f.jpg",
				"https://this-person-does-not-exist.com/img/avatar-acc4c829cd0499d3145730e1a23da68d.jpg",
				"https://this-person-does-not-exist.com/img/avatar-2e85f4f41b5810ef6ca99de63db72513.jpg",
				"https://this-person-does-not-exist.com/img/avatar-52d1451ef88bbfbf8e224f66e253d966.jpg",
				"https://this-person-does-not-exist.com/img/avatar-17d9b260127f578d0b35c121edd55b40.jpg",
				"https://this-person-does-not-exist.com/img/avatar-4e8278ddd10f97919af95eabbaa62d08.jpg",
				"https://this-person-does-not-exist.com/img/avatar-f8dccb5b6fed888d9019b15661e3b366.jpg",
				"https://this-person-does-not-exist.com/img/avatar-c3fc89087e0d96c1ed9fa0dea027d443.jpg"
			});
	
	public List<Employee> GenerateEmployees()
	{
		List<Employee> employees = new ArrayList<Employee>();
		
		for (int i = 0; i < employeeNames.size(); i++)
		{
			Employee employee = new Employee();
			String name = employeeNames.get(i);
			String[] names = name.split(" ");
			String email = names[0].toLowerCase() + "@wt.nl";
			
			employee.setEmployeeName(name);
			employee.setEmployeeEmail(email);
			employee.setEmployeePassword(GeneratePassword());
			employee.setEmployeeAdmin(Math.random() > 0.75);
			employee.setEmployeeAvatar(employeeAvatar.get(i));
			employee.setEmployeeActive(true);			
			employees.add(employee);
		}
		
		return employees;
	}
	
	private static String GeneratePassword()
	{
		String password = "";
	    Random random = new Random();

	    char[] word = new char[random.nextInt(8)+7]; // words of length 7 through 10. (1 and 2 letter words are boring.)
	    for(int j = 0; j < word.length; j++)
	    {
	    	word[j] = (char)('a' + random.nextInt(26));
	    }
	    
	    password = new String(word);
	    
	    return password;
	}
}
