import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Student } from '../student';
import { StudentService } from '../student.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-assign-modal',
  templateUrl: './assign-modal.component.html',
  styleUrls: ['./assign-modal.component.css']
})
export class AssignModalComponent  implements OnInit {

  constructor(private studentService: StudentService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    button.setAttribute('data-target', '#assignStudentModal');
    container!.appendChild(button);
    button.click();
}

  public onAssignStudent(assignForm: NgForm): void
  {
    // document.getElementById('add-student-form')?.click();
    let studentId = parseInt(this.route.snapshot.paramMap.get('id') || "");
    
    console.log("prof id: ", assignForm.value.ProfId);
    console.log("student id: ", studentId);
    this.studentService.assignProfessor(studentId, assignForm.value.ProfId).subscribe(
      (response: Student)=> {
        this.router.navigate(['/']);
        console.log(">>>After Assigning Profs to the student >>>>> : ", response);
        assignForm.reset();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        assignForm.reset();
      }
    );
  }

}
