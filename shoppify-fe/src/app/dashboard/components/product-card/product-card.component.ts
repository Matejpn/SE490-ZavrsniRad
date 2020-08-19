import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
	selector: 'app-product-card',
	templateUrl: './product-card.component.html',
	styleUrls: [ './product-card.component.scss' ]
})
export class ProductCardComponent implements OnInit {
	@Input() product;

	constructor(private router: Router) {}

	ngOnInit(): void {}

	viewProduct() {
		this.router.navigate([ '/product/single', this.product.id ]);
	}
}
