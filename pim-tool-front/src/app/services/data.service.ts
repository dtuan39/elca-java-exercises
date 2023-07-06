import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  private keyword: string = ''
  private status: string = 'Project status';

  constructor() { }

  setKeyword(keyword: string) {
    this.keyword = keyword;
  }

  getKeyword(): string {
    return this.keyword;
  }

  setStatus(status: string) {
    this.status = status;
  }

  getStatus(): string {
    return this.status;
  }
}
