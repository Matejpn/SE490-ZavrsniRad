import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
	selector: 'app-welcome-side',
	templateUrl: './welcome-side.component.html',
	styleUrls: [ './welcome-side.component.scss' ]
})
export class WelcomeSideComponent implements OnInit {
	@Input() isLogin: boolean;

	constructor() {}

	ngOnInit(): void {}
}
