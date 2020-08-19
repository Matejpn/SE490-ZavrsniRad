import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from 'src/app/shared/services/product.service';
import { LocalStorageService } from 'src/app/shared/services/local-storage.service';
import { ToastrService } from 'ngx-toastr';

@Component({
	selector: 'app-single-product',
	templateUrl: './single-product.component.html',
	styleUrls: [ './single-product.component.scss' ]
})
export class SingleProductComponent implements OnInit {
	product: any = {};
	imagePosition = 0;
	pickedSize: Number;
	quantities = [];
	pickedQuantity: Number = 1;
	role: String;

	constructor(
		private route: ActivatedRoute,
		private productService: ProductService,
		private localStorageService: LocalStorageService,
		private toastr: ToastrService,
		private router: Router
	) {}

	ngOnInit(): void {
		this.productService.getById(Number(this.route.snapshot.paramMap.get('id'))).subscribe((res) => {
			this.product = res;
			this.pickedSize = res.productSizes[0].sizeId;
			this.setQuantity(res.productSizes[0].count);
		});

		this.role = localStorage.getItem('role');
	}

	nextImage() {
		this.imagePosition = this.imagePosition + 1;
	}

	previousImage() {
		this.imagePosition = this.imagePosition - 1;
	}

	previousDisabled() {
		return !(this.product.images && this.product.images.length && this.imagePosition !== 0);
	}

	selectSize(event) {
		this.pickedSize = Number(event.target.value);
		const size = this.product.productSizes.find((ps) => ps.sizeId === this.pickedSize);
		this.setQuantity(size.count);
	}

	nextDisabled() {
		return !(this.product.images && this.product.images.length && this.imagePosition !== this.product.images.length - 1);
	}

	setQuantity(number) {
		const quantity = [];
		for (let n = 1; n <= number; n++) {
			quantity.push(n);
		}
		this.quantities = quantity;
		this.pickedQuantity = 1;
	}

	selectQuantity(ev) {
		this.pickedQuantity = Number(ev.target.value);
	}

	addToCart() {
		if (this.role) {
			if (this.role === 'USER') {
				this.localStorageService.addToCart(
					{
						productId: this.product.id,
						sizeId: this.pickedSize,
						quantity: this.pickedQuantity,
						product: this.product
					},
					this.product.productSizes
				);
				return;
			}
			this.toastr.warning('Only USER role can add to cart!');
			return;
		}
		this.toastr.warning('Please Log In!');
	}

	uploadPhoto() {
		this.router.navigate([ '/product/photos', this.product.id ]);
	}
}
