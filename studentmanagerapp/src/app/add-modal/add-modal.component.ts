import { Component, OnInit } from '@angular/core';
import { Student } from '../student';
import { StudentService } from '../student.service';
import { NgForm } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-modal',
  templateUrl: './add-modal.component.html',
  styleUrls: ['./add-modal.component.css']
})
export class AddModalComponent implements OnInit{

  constructor(private studentService: StudentService, private router: Router) { }


    ngOnInit() {
      const container = document.getElementById('main-container');
      const button = document.createElement('button');
      button.type = 'button';
      button.style.display = 'none';
      button.setAttribute('data-toggle', 'modal');
      button.setAttribute('data-target', '#addStudentModal');
      container!.appendChild(button);
      console.log("yoooo my little genius HHHHHH");
      button.click();
  }

  public onAddStudent(addForm: NgForm): void {
    document.getElementById('add-student-form')?.click();
    // console.log("From Valuuues >>> : ", addForm.value.groupe)
    this.studentService.addStudent(addForm.value).subscribe(
      (response: void) => {
        this.router.navigate(['/']);
        // this.getStudents();
        addForm.reset();
        console.log("wesh hnaaa a khuuuuutiiii ?   ", response);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
      }
    );
  }

}
