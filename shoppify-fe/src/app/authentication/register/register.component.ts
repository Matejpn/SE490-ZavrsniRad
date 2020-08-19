import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { WelcomeSideComponent } from 'src/app/dashboard/components/partials/welcome-side/welcome-side.component';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationService } from 'src/app/core/services/authentication.service';
import { NgForm } from '@angular/forms';
import { User } from '../models/user.model';

@Component({
	selector: 'app-register',
	templateUrl: './register.component.html',
	styleUrls: [ './register.component.scss' ]
})
export class RegisterComponent implements OnInit {
	user: User = this.auth.newUser();
	@ViewChild('password', { static: false })
	password: ElementRef;
	@ViewChild('repeatPass', { static: false })
	repeatPass: ElementRef;
	@ViewChild(WelcomeSideComponent, { static: false })
	welcomeSide: WelcomeSideComponent;

	@ViewChild('registrationForm', { static: false })
	registerForm: NgForm;

	constructor(private auth: AuthenticationService, private router: Router, private toastr: ToastrService) {}

	ngOnInit() {}

	onSubmit() {
		if (this.registerForm.valid && this.password.nativeElement.value === this.repeatPass.nativeElement.value) {
			this.auth.registerUser(this.user).subscribe(
				() => {
					this.toastr.success('Success! To continue confirm your email');
					this.router.navigate([ '/' ]);
				},
				(error) => this.toastr.error(error.error)
			);
		} else {
			this.toastr.error(`Form is not valid or password doesn't match`);
		}
	}
}
