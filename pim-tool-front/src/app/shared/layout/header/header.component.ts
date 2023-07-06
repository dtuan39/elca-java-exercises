import {Component} from '@angular/core';
import {LanguageService} from "../../../language.service";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  constructor(private languageService: LanguageService,
              public translate : TranslateService) {}

  toggleLanguage() {
    this.languageService.toggleLanguage();
  }
}
