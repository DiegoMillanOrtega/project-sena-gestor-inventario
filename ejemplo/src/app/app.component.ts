import { Component, Input, OnInit, inject } from '@angular/core';
import { LoginService } from './service/login.service';
import { SettingService } from './service/setting.service';
import { UsersSettings } from './model/userSetting.model';
import { animate, style, transition, trigger } from '@angular/animations';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  
})
export class AppComponent{
  
  
  public _settingService = inject(SettingService);
  public _loginService = inject(LoginService);
  

}
