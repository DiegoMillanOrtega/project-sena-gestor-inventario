import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl: string = "http://localhost:8080/auth/login";
  private tokenKey = 'authToken'

  constructor(private http: HttpClient, private router: Router) { }

  login(username: string, password: string):Observable<any> {
    return this.http.post<any>(this.apiUrl, {username, password}).pipe(
      tap(response => {
        if (response.token) {
          this.setToken(response.token)
          this.router.navigate(['inicio'])
        }
      })
    )
  }

  private setToken(token: string ): void {
    localStorage.setItem(this.tokenKey, token);
  }

  private getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  isAuthenticated(): boolean {
    const token = this.getToken();

    if (!token) {
      return false;
    }

    const payload = JSON.parse(atob(token.split('.')[1]))
    const exp = payload.exp * 1000
    console.log(exp);
    

    return Date.now() < exp;
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
    this.router.navigate(['/login'])
  }
}