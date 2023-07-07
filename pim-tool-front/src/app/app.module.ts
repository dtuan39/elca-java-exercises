import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { AddProjectComponent } from './component/add-project/add-project.component';
import { ListProjectComponent } from './component/list-project/list-project.component';
import { AppRoutingModule } from './app-routing.module';
import { NgbDropdownModule, NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { UpdateProjectComponent } from './component/update-project/update-project.component';

@NgModule({
  declarations: [AppComponent, AddProjectComponent, ListProjectComponent, UpdateProjectComponent],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot([]),
    AppRoutingModule,
    NgbPaginationModule
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
