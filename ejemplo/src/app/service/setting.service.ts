import { Injectable, inject } from '@angular/core';
import { BehaviorSubject, Observable, } from 'rxjs';
import { UsersSettings } from '../model/userSetting.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SettingService {
  
  private apiUrl: string = "http://localhost:8080/users";
  private _http = inject(HttpClient);
  
  private username: string = '';
  private userSetting?: UsersSettings;
  
  
  getUserSetting(username: string): Observable<UsersSettings> {
    return this._http.get<UsersSettings>(`${this.apiUrl}/getUserSetting/${username}`)
  }

  postUserSetting(userSetting: FormData): Observable<any> {
    console.log('lo que llega aqui al servicio: ', userSetting)
    return this._http.post(`${this.apiUrl}/postUpdateUserSetting`, userSetting, {responseType: 'text'})
  }
  
  setSharedUsername(username: string): void {
    this.username = username;
  }

  getSharedUsername(): string {
    return this.username;
  }
  

  setUserSetting(userSetting: UsersSettings) {
    this.userSetting = userSetting;
  }
  
  obtainedUserSetting() {
    return this.userSetting
  }

  getLogoSrc(): string {
    // Verifica si el campo logo existe y no es nulo
    if (this.userSetting && this.userSetting.logo) {
      // Utiliza el formato base64 del campo logo para crear una URL de datos
      return `data:image/png;base64,${this.userSetting.logo}`;
    }

    return ''; // Puedes establecer un valor predeterminado o manejarlo según tus necesidades
  }
}
