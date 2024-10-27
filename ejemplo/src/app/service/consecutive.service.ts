import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Consecutive } from '../model/consecutivo.model';

@Injectable({
  providedIn: 'root'
})
export class ConsecutiveService {
  
  private apiUrl: string = 'http://localhost:8080/consecutive'
  private http = inject(HttpClient);

  constructor() { }

  getConsecutive(prefijo: string): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/getConsecutive/${prefijo}`);
  }
}
