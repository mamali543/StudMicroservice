import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Student } from './student';
import { environment } from 'src/environments/environments';

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getStudents(): Observable<Student[]> {
    return this.http.get<Student[]>(`${this.apiServerUrl}/studentService/all`)
  }

  public getStudent(studentId: number): Observable<Student> {
    return this.http.get<Student>(`${this.apiServerUrl}/studentService/find/${studentId}`)
  }

  public addStudent(student: Student):  Observable<Student> {
    return this.http.post<Student>(`${this.apiServerUrl}/studentService/add`, student)
  }

  public updateStudent(student: Student):  Observable<Student> {
    return this.http.put<Student>(`${this.apiServerUrl}/studentService/update`, student)
  }

  public deleteStudent(studentId: number):  Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/studentService/delete/${studentId}`)
  }

  public assignProfessor(studentId: number, profId: number):  Observable<Student> {
    return this.http.put<Student>(`${this.apiServerUrl}/studentService/assign/${studentId}`, profId);
  }


}
