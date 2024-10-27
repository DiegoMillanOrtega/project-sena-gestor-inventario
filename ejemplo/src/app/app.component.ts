import { Component, OnInit, inject } from '@angular/core';
import { LoginService } from './service/login.service';
import { SettingService } from './service/setting.service';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { filter, map } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  
})
export class AppComponent implements OnInit{
  
  
  public _settingService = inject(SettingService);
  public _loginService = inject(LoginService);
  private route = inject(Router)
  private activatedRoute = inject(ActivatedRoute)

  
  nameSystem: string | undefined = '';
  logoSrc: string | null = null;
  paginaActual: string = '';

  ngOnInit(): void {
    
    if(this._loginService.isAuthenticated()) {
      this.getNameSystem();
      this.getLogoSrc();
    }

    this.route.events.pipe(
      filter(event => event instanceof NavigationEnd),
      map(() => {
        let route = this.activatedRoute;
        while (route.firstChild) {
          route = route.firstChild
        }
        return route.snapshot.data['title'];
      })
    ).subscribe((title: string) => {
      this.paginaActual = title || ''
    })
  }

  getLogoSrc() {
    this.logoSrc = this._settingService.getLogoSrc();
  }

  getNameSystem() {
    this.nameSystem = this._settingService.obtainedUserSetting()?.nameSystem;
  }

    
}
