import { Injectable } from '@angular/core';
import { Subject, Observable, of } from 'rxjs';
import { ToastrService } from 'ngx-toastr';

@Injectable({
	providedIn: 'root'
})
export class LocalStorageService {
	private storageSub = new Subject<String>();

	constructor(private toastr: ToastrService) {}

	watchStorage(): Observable<any> {
		return this.storageSub.asObservable();
	}

	roleChanged(role) {
		this.storageSub.next(role);
	}

	addToCart(item, productSizes) {
		let items = JSON.parse(localStorage.getItem('items'));
		if (!items) {
			items = [];
		}
		const filtered = items.find((i) => i.productId === item.productId && i.sizeId === item.sizeId);
		if (filtered) {
			const productSize = productSizes.find((ps) => ps.sizeId === item.sizeId);
			if (filtered.quantity + item.quantity > productSize.count) {
				this.toastr.warning('Not enough product quantity!');
				return;
			}
			filtered.quantity += item.quantity;
		} else {
			items.push(item);
		}

		localStorage.setItem('items', JSON.stringify(items));
		this.toastr.success('Added!');
	}
}
