import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { VerificationComponent } from './verification/verification.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { AuthenticationRoutingModule } from './authentication-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DashboardModule } from '../dashboard/dashboard.module';

@NgModule({
	declarations: [ LoginComponent, RegisterComponent, VerificationComponent, ChangePasswordComponent ],
	imports: [ CommonModule, AuthenticationRoutingModule, FormsModule, ReactiveFormsModule, DashboardModule ]
})
export class AuthenticationModule {}
