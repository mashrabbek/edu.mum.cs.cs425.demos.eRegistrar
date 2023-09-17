package edu.mum.cs.cs425.demos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private StudentRepo studentRepo;

    @GetMapping(value = {"/", "/elibrary", "/elibrary/home"})
    public String displayHomePage() {
        return "/home/index";
    }

    @GetMapping("/students")
    public String listStudents(Model model) {
        List<Student> students = studentRepo.findAll();
        model.addAttribute("students", students);
        return "home/list"; // Thymeleaf template name (e.g., list.html)
    }
    @GetMapping("/students/register")
    public String registerStudent(Model model) {
//        List<Student> students = studentRepo.findAll();
//        model.addAttribute("students", students);
        return "home/register";
    }

    @PostMapping("/students/save")
    public String registerUser(@ModelAttribute("student") Student student) {
        studentRepo.save(student);
        return "redirect:/students";
    }

    @PostMapping("/students/update")
    public String updateStudent(@ModelAttribute("student") Student updatedStudent) {
        studentRepo.save(updatedStudent);
        return "redirect:/students"; // Redirect to the list of students after updating
    }

    @GetMapping("/students/edit/{studentId}")
    public String editStudent(@PathVariable Long studentId, Model model) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + studentId));
        model.addAttribute("student", student);
        return "home/edit";
    }
    @GetMapping("/students/delete/{studentId}")
    public String deleteStudent(@PathVariable Long studentId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + studentId));
        studentRepo.deleteById(studentId);
        return "redirect:/students";
    }

}
