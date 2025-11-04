import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';
import { TripListComponent } from './planner/trip-list/trip-list.component';
import { TripFormComponent } from './planner/trip-form/trip-form.component';
import { AuthGuard } from './core/services/auth.guard';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  {
    path: 'planner',
    canActivate: [AuthGuard],
    children: [
      { path: '', component: TripListComponent },
      { path: 'new', component: TripFormComponent },
      { path: 'edit/:id', component: TripFormComponent }
    ]
  },
  { path: '', redirectTo: '/planner', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }