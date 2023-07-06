import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HeaderComponent } from './shared/layout/header/header.component';
import { HomeComponent } from './pages/home/home.component';
import { HeaderModule } from './shared/layout/header/header.module';
import { HomeModule } from './pages/home/home.module';
import { CreateProjectFormComponent } from './shared/components/view-project/view-project-form.component';
import { ViewProjectsComponent } from './shared/components/view-projects/view-projects.component';
import { RouterModule } from '@angular/router';
import { HelpComponent } from './pages/help/help.component';
import { HelpModule } from './pages/help/help.module';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {DataService} from "./services/data.service";
import { MatDialogModule } from '@angular/material/dialog';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ConfirmationDialogComponent } from './confirmation-dialog/confirmation-dialog.component';
import { ErrorMessageDialogComponent } from './error-message-dialog-component/error-message-dialog-component.component';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
// Tạo một loader để tải các file ngôn ngữ dựa trên Http
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}
@NgModule({
  declarations: [
    AppComponent,
    ConfirmationDialogComponent,
    ErrorMessageDialogComponent,
  ],
  imports: [
    RouterModule,
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule,
    HeaderModule,
    HomeModule,
    HelpModule,
    MatDialogModule,
    BrowserAnimationsModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    })
  ],
  providers: [DataService],
  bootstrap: [AppComponent]
})
export class AppModule { }
