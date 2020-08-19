import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { AuthenticationService } from '../services/authentication.service';
import { RoleEnum } from '../enums/role.enum';

@Injectable({
	providedIn: 'root'
})
export class AdminGuard implements CanActivate {
	constructor(private auth: AuthenticationService, private router: Router) {}
	canActivate(): Observable<any> {
		if (localStorage.getItem('role') === RoleEnum.ADMIN) {
			return of(true);
		}
		this.router.navigate([ '' ]);
		return of(false);
	}
}
