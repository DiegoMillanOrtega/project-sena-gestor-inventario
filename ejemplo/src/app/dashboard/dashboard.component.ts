import { Component, OnInit, inject } from '@angular/core';
import { SettingService } from '../service/setting.service';
import { UserSetting } from '../model/userSetting.model';
import { Router } from '@angular/router';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export default class DashboardComponent implements OnInit{
  
  private _settingService = inject(SettingService);
  private router = inject(Router);
  
  userSetting?: UserSetting;
  menuSelect: String = ''; 
  isCollapsed: boolean = true;
  tab: String = 'Vista General';
  url: String = '';

  ngOnInit(): void {
    this._settingService.getUserSetting(this._settingService.getSharedUsername()).subscribe(
      params => {
        this._settingService.setUserSetting(params),
        this.userSetting = params
      },
      error => console.error('error al obtener userSetting', error)
      )
      this.router.events.subscribe(() => {
        this.url = this.router.url;
      });

    }
    
    onOption(menuSelect: String) {
      this.menuSelect = menuSelect;
    }
    
    toggleCollapse() {
      this.isCollapsed = !this.isCollapsed;
  }
}
