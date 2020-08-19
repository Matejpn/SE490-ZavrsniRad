import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { ToastrService } from 'ngx-toastr';
import { PurchaseService } from 'src/app/shared/services/purchase.service';

@Component({
	selector: 'app-shopping-cart',
	templateUrl: './shopping-cart.component.html',
	styleUrls: [ './shopping-cart.component.scss' ]
})
export class ShoppingCartComponent implements OnInit {
	items = [];
	constructor(private location: Location, private toastr: ToastrService, private purchaseService: PurchaseService) {}

	ngOnInit(): void {
		const items = JSON.parse(localStorage.getItem('items'));
		if (items) {
			this.items = items;
		}
	}

	backClicked() {
		this.location.back();
	}

	getProductSize(product) {
		return product.product.productSizes.find((ps) => ps.sizeId === product.sizeId).size;
	}

	getTotalPrice() {
		let totalPrice = 0;
		this.items.forEach((product) => {
			totalPrice += product.quantity * product.product.price;
		});
		return totalPrice;
	}

	removeProduct(product) {
		this.items = this.items.filter((item) => !(item.productId === product.productId && item.sizeId === product.sizeId));
		localStorage.setItem('items', JSON.stringify(this.items));
	}

	onSubmit() {
		this.purchaseService.addPurchase({ items: this.items }).subscribe(() => {
			this.items = [];
			localStorage.removeItem('items');
			this.toastr.success('Success! You will be notified when your purchase is confirmed.');
		});
	}
}
