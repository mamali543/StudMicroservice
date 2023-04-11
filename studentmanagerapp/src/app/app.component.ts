import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Student } from './student';
import { StudentService } from './student.service';



@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  students!: Student[];
  editStudent!: Student | null;
  deleteStudent!: Student | null;
  assignStudent!: Student | null;

  constructor(private studentService: StudentService) { }

  ngOnInit() {

    this.getStudents();
  }

  public getStudents(): void {
    this.studentService.getStudents().subscribe(
      (response: Student[]) => {
        this.students = response;
        console.log(this.students);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  // public onAddStudent(addForm: NgForm): void {
  //   document.getElementById('add-student-form')?.click();
  //   console.log("From Valuuues >>> : ", addForm.value.groupe)
  //   this.studentService.addStudent(addForm.value).subscribe(
  //     (response: Student) => {
  //       console.log(response);
  //       this.getStudents();
  //       addForm.reset();
  //     },
  //     (error: HttpErrorResponse) => {
  //       alert(error.message);
  //       addForm.reset();
  //     }
  //   );
  // }

  public onAssignStudent(studentId: number, assignForm: NgForm): void
  {
    // document.getElementById('add-student-form')?.click();
    console.log("student id: ", studentId);
    console.log("prof id: ", assignForm.value.ProfId);
    this.studentService.assignProfessor(studentId, assignForm.value.ProfId).subscribe(
      (response: Student)=> {
        console.log(">>>After Assigning Profs to the student >>>>> : ", response);
        this.getStudents();
        assignForm.reset();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        assignForm.reset();
      }
    );
  }

  
  public onDeleteStudent(studentId: number): void {
    this.studentService.deleteStudent(studentId).subscribe(
      (response: void) => {
        console.log(response);
        this.getStudents();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
      );
    }
    
    public onUpdateStudent(student: Student): void {
      this.studentService.updateStudent(student).subscribe(
        (response: Student) => {
          console.log(response);
          this.getStudents();
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
  public onOpenModal(student: Student | null, mode: string): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addStudentModal');
    }
    if (mode === 'edit') {
      this.editStudent = student!;
      button.setAttribute('data-target', '#updateStudentModal');
    }
    if (mode === 'delete') {
      this.deleteStudent = student;
      button.setAttribute('data-target', '#deleteStudentModal');
    }
    if (mode === 'assign') {
      this.assignStudent = student;
      button.setAttribute('data-target', '#assignStudentModal');
    }
    container!.appendChild(button);
    button.click();
  }


  public searchStudents(key: string): void {
    console.log(key);
    const results: Student[] = [];
    for (const student of this.students) {
      if (student.name.toLowerCase().indexOf(key.toLowerCase()) !== -1
      || student.email.toLowerCase().indexOf(key.toLowerCase()) !== -1
      || student.telephone.toLowerCase().indexOf(key.toLowerCase()) !== -1
      || student.groupe.toLowerCase().indexOf(key.toLowerCase()) !== -1) {
        results.push(student);
      }
    }
    this.students = results;
    if (results.length === 0 || !key) {
      this.getStudents();
    }
  }
}
