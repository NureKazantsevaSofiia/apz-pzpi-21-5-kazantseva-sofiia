import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {LoginService} from "./_services/login.service";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {

  protected readonly LoginService = LoginService;

  constructor(private router: Router, private translate: TranslateService) {
    translate.addLangs(['en', 'uk']);
    translate.setDefaultLang('en');
  }

  switchLanguage(lang: string) {
    this.translate.use(lang);
  }

  protected async logout() {
    await LoginService.logout(this.router)
  }
}
