
package com.spring.demo.graphql;

import com.spring.demo.domain.Student;
import com.spring.demo.dto.StudentWriteDto;
import com.spring.demo.service.StudentService;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class StudentGraphQLController {

    private final StudentService studentService;

    // Constructor injection
    public StudentGraphQLController(StudentService studentService) {
        this.studentService = studentService;
    }

    // QUERY: Get a student by ID
    @QueryMapping
    public Student studentById(@Argument String id) {
        return studentService.getStudentById(
            Integer.parseInt(id)
        );
    }

    // MUTATION: Create a new student
    @MutationMapping
    public Student addStudent(
        @Argument String firstName,
        @Argument String lastName,
        @Argument String email
    ) {

        // Create a StudentWriteDto object
        StudentWriteDto student = new StudentWriteDto();

        // Set the student's information
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);

        // Save student using the existing service
        return studentService.insertStudent(student);
    }
}
