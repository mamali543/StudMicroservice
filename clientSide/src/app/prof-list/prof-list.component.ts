import { Component, OnInit } from '@angular/core';
import Professor from '../professor';
import { ProfessorService } from '../professor.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { StudentService } from '../student.service';
import { Student } from '../student';


@Component({
  selector: 'app-prof-list',
  templateUrl: './prof-list.component.html',
  styleUrls: ['./prof-list.component.css']
})
export class ProfListComponent implements OnInit{

  professors!: Professor[];
  student!: Student;

  constructor(private professorService: ProfessorService, private route:ActivatedRoute, private studentService: StudentService) {}

  ngOnInit() {
    console.log("heeey Professors Table HHHHHHHH");
    console.log("here's the profId >>>>> : ", this.route.snapshot.paramMap.get('id'));


    this.getProfessors();
  }

  public getProfessors(): void {
    let id = parseInt(this.route.snapshot.paramMap.get('id')||"")

    this.studentService.getStudent(id).subscribe(
      (response: Student) => {
        this.student = response;
        console.log("_____Student Object >>>> ___: ", this.student);
        this.professorService.getProfessorsList(this.student.professors).subscribe(
          (response: Professor[])=>{
            this.professors = response;
            console.log("___ArrayOf Professors ___: ", response);
          }
        )
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }
}
