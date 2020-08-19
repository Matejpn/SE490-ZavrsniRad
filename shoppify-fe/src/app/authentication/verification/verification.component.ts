import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/core/services/authentication.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
	selector: 'app-verification',
	templateUrl: './verification.component.html',
	styleUrls: [ './verification.component.scss' ]
})
export class VerificationComponent implements OnInit {
	token: String;
	error;
	constructor(
		private auth: AuthenticationService,
		private route: ActivatedRoute,
		private router: Router,
		private toastr: ToastrService
	) {}

	ngOnInit() {
		this.error = false;
	}

	verifyUser() {
		this.route.params.subscribe((params) => {
			this.token = params.id;
			this.auth.verifyUser(this.token).subscribe(
				() => {
					this.toastr.success('Verification successful!');
					this.router.navigate([ '/' ]);
				},
				(error) => {
					if (error.error.text === 'Success') {
						this.toastr.success('Verification successful!');
						this.router.navigate([ '/' ]);
					} else {
						this.toastr.error('Something went wrong while verification process, please try again');
						this.error = error;
					}
				}
			);
		});
	}
}
