import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Prefijo } from '../model/prefijo.model';

@Injectable({
  providedIn: 'root'
})
export class PrefixService {
  
  private apiUrl: string = 'http://localhost:8080/prefix'
  private http = inject(HttpClient);

  constructor() { }

  getAllPrefix(): Observable<Prefijo[]> {
    return this.http.get<Prefijo[]>(`${this.apiUrl}/getAllPrefixes`);
  }

}
