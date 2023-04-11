
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from 'src/environments/environments';
import Professor from './professor';

@Injectable({
  providedIn: 'root'
})
export class ProfessorService {
  endpoint: string = 'http://172.16.16.247:8090/professorService';
  constructor(private http: HttpClient) { }
  getProfessorsList(ids: number[]): Observable<Professor[]> {
    let params = new HttpParams().set('ids', ids.join(',')); // Construct the query parameter string
    return this.http.get<Professor[]>(`${this.endpoint}/get/list`, { params: params});
  }
}
