import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { VerificationComponent } from './verification/verification.component';

const routes: Routes = [
	{ path: '', component: LoginComponent },
	{ path: 'register', component: RegisterComponent },
	{ path: 'change-password', component: ChangePasswordComponent },
	{ path: 'verify/:id', component: VerificationComponent }
];

@NgModule({
	declarations: [],
	imports: [ RouterModule.forChild(routes) ],
	exports: [ RouterModule ]
})
export class AuthenticationRoutingModule {}
