import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {MatSidenavModule} from '@angular/material/sidenav';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from "@angular/common/http";
import {ProjectComponent} from './project/project.component';
import {RouterModule, Routes} from "@angular/router";
import {ListComponent} from './list/list.component';
import {MatTableModule} from "@angular/material/table";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatSelectModule} from "@angular/material/select";
import {ErrorComponent} from './error/error.component';

const appRoute: Routes = [
  {path: '', redirectTo: 'project', pathMatch: "full"},
  {path: 'project/list', redirectTo: 'list', pathMatch: "full"},
  {path: 'list', component: ListComponent},
  {path: 'project', component: ProjectComponent},
  {path: 'error', component: ErrorComponent}
]

@NgModule({
  declarations: [
    AppComponent,
    ProjectComponent,
    ListComponent,
    ErrorComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatSidenavModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    RouterModule.forRoot(appRoute),
    MatTableModule,
    MatCheckboxModule,
    MatSelectModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
