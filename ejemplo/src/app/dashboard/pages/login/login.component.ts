import { Component, OnInit, inject } from '@angular/core';
import { LoginService } from '../../../service/login.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { SettingService } from '../../../service/setting.service';
import { AuthService } from '../../../core/service/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent{

  
  private _loginService = inject(LoginService);
  private _authService = inject(AuthService)
  private _settingService = inject(SettingService)
  private router = inject(Router)
  
  
  user: FormGroup;
  constructor(private form: FormBuilder) {
    this.user = this.form.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    })
  }

  login() {
    const user = this.user.get('username')?.value;
    const password = this.user.get('password')?.value
    this._authService.login(user, password).subscribe({
      next: (response) => {
        console.log(this._authService.isAuthenticated())
        this.router.navigate([''])
      },
      error: (error) => {
        console.error(error);
        
      }
    });
  }
  

    
  private userExists(username: string , userList: any[]): boolean {
    return userList.some( user => user.username === username )
  }

  hasError(controlName: string, errorType: string) {
    return this.user.get(controlName)?.hasError(errorType) && this.user.get(controlName)?.touched
  }

}
