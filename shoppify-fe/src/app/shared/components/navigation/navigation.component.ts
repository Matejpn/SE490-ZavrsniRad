import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from '../../services/local-storage.service';

@Component({
	selector: 'app-navigation',
	templateUrl: './navigation.component.html',
	styleUrls: [ './navigation.component.scss' ]
})
export class NavigationComponent implements OnInit {
	role: String;
	constructor(private router: Router, private localStorageService: LocalStorageService) {
		this.localStorageService.watchStorage().subscribe((role: String) => {
			if (role) {
				this.role = role;
			}
		});
	}

	ngOnInit(): void {
		this.role = localStorage.getItem('role');
	}

	logOut() {
		localStorage.clear();
		this.role = null;
		this.router.navigate([ '' ]);
	}
}
