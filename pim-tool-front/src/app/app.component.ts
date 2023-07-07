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

  constructor(private router: Router, private sharedService: SharedService) {}

  isActiveRoute(route: string): boolean {
    return this.router.url === route;
  }
}
