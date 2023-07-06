import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root'
})
export class TranslationService {
  constructor(private translate: TranslateService) {
    this.translate.setDefaultLang('en'); // Ngôn ngữ mặc định
  }

  setLanguage(language: string): void {
    this.translate.use(language);
  }

  translateKey(key: string, params?: any): string {
    return this.translate.instant(key, params);
  }
}
