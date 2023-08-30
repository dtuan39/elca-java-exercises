import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SharedService } from './service/shared.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'Project_Management_ELCA_FE';

  constructor(private router: Router, public sharedService: SharedService) {}

  isActiveRoute(route: string): boolean {
    return this.router.url === route;
  }

  navigateToProjectDetail() {
    this.router.navigate(['/project', '']);
  }

  changeIsUpdate(val : boolean) {
    this.sharedService.setIsUpdate(val); // or false
  }
}
