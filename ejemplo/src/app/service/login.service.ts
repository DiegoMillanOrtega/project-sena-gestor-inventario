import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, catchError, map, throwError } from 'rxjs';
import { Users } from '../model/user.model';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private isAuthenticatedUser: boolean = false;
  private apiUrl: string = "http://localhost:8080/auth";
  private _http = inject(HttpClient);
  
  

  login(username: string, password: string): Observable<any> {
    const body = {username, password};
    return this._http.post(`${this.apiUrl}/login`, body, { responseType: 'text' }).pipe(
      map((response: any) => {
        // Autenticación exitosa
        this.isAuthenticatedUser = true;
        return response;
      }),
      catchError((error) => {
        // Maneja el error de autenticación
        this.isAuthenticatedUser = false;
        return throwError(error);
      })
    );
  }

  isAuthenticated(): boolean {
    return this.isAuthenticatedUser;
  }
}
