import { Component, OnInit } from '@angular/core';
import { OrderService } from 'src/app/shared/services/order.service';
import { PurchaseService } from 'src/app/shared/services/purchase.service';
import { ToastrService } from 'ngx-toastr';

@Component({
	selector: 'app-orders',
	templateUrl: './orders.component.html',
	styleUrls: [ './orders.component.scss' ]
})
export class OrdersComponent implements OnInit {
	orders = [];
	role;
	constructor(private ordersService: OrderService, private purchaseService: PurchaseService, private toastr: ToastrService) {}

	ngOnInit(): void {
		this.role = localStorage.getItem('role');
		this.getOrders();
	}

	getOrders() {
		if (this.role === 'USER') {
			this.ordersService.getMy().subscribe((res) => {
				this.orders = res;
			});
		} else if (this.role === 'ADMIN') {
			this.ordersService.getAll().subscribe((res) => {
				this.orders = res;
			});
		}
	}

	confirmOrder(id) {
		this.purchaseService.confirmPurchase(id).subscribe(() => {
			this.toastr.success('Order Confirmed!');
			this.orders.map((order) => {
				if (order.id === id) {
					order.isConfirmed = true;
				}
			});
		});
	}
}
