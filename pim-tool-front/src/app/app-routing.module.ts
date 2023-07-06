import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeModule } from './pages/home/home.module';
import { ViewProjectFormModule } from './shared/components/view-project/view-project-form.module';
import { ViewProjectsModule } from './shared/components/view-projects/view-projects.module';
import { HomeComponent } from './pages/home/home.component';
import { CreateProjectFormComponent } from './shared/components/view-project/view-project-form.component';
import { ViewProjectsComponent } from './shared/components/view-projects/view-projects.component';
import { HelpComponent } from './pages/help/help.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    children: [
      {
        path: '',
        redirectTo: 'view-projects',
        pathMatch: 'full'
      },
      {
        path: 'view-project/:projectId',
        component: CreateProjectFormComponent,
      },
      {
        path: 'view-project',
        component: CreateProjectFormComponent,
      },
      {
        path: 'view-projects',
        component: ViewProjectsComponent
      }
    ]
  },
  {
    path: 'help',
    component: HelpComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
