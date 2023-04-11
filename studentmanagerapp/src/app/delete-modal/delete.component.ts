import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { StudentService } from '../student.service';


@Component({
  selector: 'app-delete',
  templateUrl: './delete.component.html',
  styleUrls: ['./delete.component.css']
})
export class DeleteComponent {
  constructor(private studentService: StudentService,private route: ActivatedRoute,  private router: Router) { }

  ngOnInit() {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    button.setAttribute('data-target', '#deleteStudentModal');
    container!.appendChild(button);
    button.click();
}

  public onDeleteStudent(): void {
    let studentId = parseInt(this.route.snapshot.paramMap.get('id') || "");
    this.studentService.deleteStudent(studentId).subscribe(
      (response: void) => {
        console.log(response);
        this.router.navigate(['/']);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
      );
    }

}
