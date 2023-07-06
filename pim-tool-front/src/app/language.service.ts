import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root'
})
export class LanguageService {
  private currentLanguage: string;

  constructor(private translate: TranslateService) {
    this.currentLanguage = 'en'; // Set default language
    this.translate.setDefaultLang(this.currentLanguage);
  }

  get language(): string {
    return this.currentLanguage;
  }

  toggleLanguage() {
    this.currentLanguage = this.currentLanguage === 'en' ? 'fr' : 'en';
    this.translate.use(this.currentLanguage);
  }
}
